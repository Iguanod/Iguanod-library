package es.iguanod.collect;

import es.iguanod.base.Objects;
import es.iguanod.util.Maybe;
import es.iguanod.util.MaybeM;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @param <T>
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.6.1.a
 * @version
 */
public abstract class AbstractTree<T> implements Tree<T>, Serializable{

	//TODO: copy constructor
	//************
	private static final long serialVersionUID=-31230909014986462L;
	//************
	private int max_sons;
	private boolean nulls_allowed;

	protected static abstract class TNode<T> extends TreeNode implements Serializable{

		private static final long serialVersionUID=-6678709653264734198L;
		//************
		private boolean valid;
		private final Tree tree;

		public TNode(Tree<? super T> tree){
			this.tree=tree;
			valid=true;
		}

		@Override
		protected void checkNode(Tree tree){
			if(!valid || this.tree != tree){
				throw new NoSuchElementException("The node doesn't belong to the tree");
			}
		}

		@Override
		protected void invalidate(Tree tree){
			this.checkNode(tree);
			valid=false;
		}
	}

	protected final boolean equalsValue(TreeNode node, Object value){
		return this.getValue(node).isPresent() && Objects.equals(this.getValue(node).get(), value);
	}

	protected final void invalidateSons(TreeNode node){
		for(TreeNode son:this.children(node)){
			this.invalidateSons(son);
		}
		node.invalidate(this);
	}

	public AbstractTree(int max_sons, boolean nulls_allowed){
		this.max_sons=max_sons;
		this.nulls_allowed=nulls_allowed;
	}

	public AbstractTree(){
		this(0, true);
	}

	@Override
	public void pushAll(Collection<? extends T> col){
		if(!nulls_allowed && col.contains(null)){
			throw new NullPointerException("The tree doesn't accept null values");
		}

		for(T elem:col){
			push(elem);
		}
	}

	@Override
	public boolean nullsAllowed(){
		return nulls_allowed;
	}

	@Override
	public int maxSons(){
		return max_sons;
	}

	@Override
	public boolean removeAll(Collection<?> col){
		boolean ret=false;
		for(Object elem:col){
			ret|=this.remove(elem);
		}
		return ret;
	}

	@Override
	public boolean addAll(TreeNode node, Collection<? extends T> col){
		node.checkNode(this);
		if(max_sons > 0 && this.childrenSize(node) + col.size() > max_sons)
			throw new IllegalStateException("The node has no space for all the elements");
		if(!nulls_allowed){
			for(T elem:col){
				if(elem == null)
					throw new NullPointerException("The tree doesn't accept null values");
			}
		}
		boolean ret=false;
		for(T elem:col){
			ret|=this.add(node, elem);
		}
		return ret;
	}

	@Override
	public boolean contains(Object obj){
		return this.isEmpty() || this.contains(this.root().get(), obj);
	}

	@Override
	public boolean containsAll(Collection<?> col){
		for(Object obj:col){
			if(!this.contains(obj))
				return false;
		}
		return true;
	}

	@Override
	public boolean contains(TreeNode node, Object obj){
		node.checkNode(this);
		return equalsValue(node, obj) || this.containsDescendant(node, obj);
	}

	@Override
	public boolean containsChild(TreeNode node, Object obj){
		node.checkNode(this);
		for(TreeNode son:this.children(node)){
			if(equalsValue(node, obj)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsDescendant(TreeNode node, Object obj){
		if(this.containsChild(node, obj))
			return true;
		for(TreeNode son:this.children(node)){
			if(this.containsDescendant(son, obj))
				return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(TreeNode node, Collection<?> col){
		for(Object obj:col){
			if(!this.contains(node, obj))
				return false;
		}
		return true;
	}

	@Override
	public boolean containsAllChildren(TreeNode node, Collection<?> col){
		for(Object obj:col){
			if(!this.containsChild(node, obj))
				return false;
		}
		return true;
	}

	@Override
	public boolean containsAllDescendants(TreeNode node, Collection<?> col){
		for(Object obj:col){
			if(!this.containsDescendant(node, obj))
				return false;
		}
		return true;
	}

	@Override
	public boolean hasChildren(TreeNode node){
		return this.childrenSize(node) != 0;
	}

	@Override
	public boolean isFull(TreeNode node){
		return max_sons > 0 && this.childrenSize(node) == max_sons;
	}

	@Override
	public boolean removeAll(TreeNode node, Collection<?> col){
		boolean modif=false;
		for(Object elem:col){
			modif|=remove(node, elem);
		}
		return modif;
	}

	@Override
	public int height(){
		if(this.isEmpty())
			return 0;
		return this.height(this.root().get());
	}

	@Override
	public int height(TreeNode node){
		if(!this.hasChildren(node))
			return 1;
		int acc=0;
		for(TreeNode son:this.children(node)){
			acc=Math.max(acc, this.height(son));
			if(acc < 0)
				return Integer.MAX_VALUE;
		}
		return acc + 1;
	}

	@Override
	public int depth(TreeNode node){
		int acc;
		Maybe<TreeNode> p=this.parent(node);
		for(acc=0; p.isPresent(); acc++){
			p=this.parent(p.get());
		}
		return acc;
	}

	@Override
	public int size(){
		if(this.isEmpty())
			return 0;
		return this.size(this.root().get());
	}

	@Override
	public int size(TreeNode node){
		if(this.isEmpty())
			return 1;
		int acc=1;
		for(TreeNode son:this.children(node)){
			acc+=this.size(son);
			if(acc < 0)
				return Integer.MAX_VALUE;
		}
		return acc;
	}

	/**
	 * Overriding classes probably want to override
	 *
	 * @param node
	 *
	 * @return
	 */
	@Override
	public List<TreeNode> childrenCopy(TreeNode node){
		node.checkNode(this);
		ArrayList<TreeNode> ret=new ArrayList<>();
		for(TreeNode child:children(node)){
			ret.add(child);
		}
		return ret;
	}

	/**
	 * Overriding classes probably want to override. Zero indexed
	 *
	 * @param node
	 *
	 * @return
	 */
	@Override
	public Maybe<TreeNode> getChild(TreeNode node, int i){
		node.checkNode(this);
		int count=0;
		for(TreeNode child:children(node)){
			if(count == i){
				return Maybe.<TreeNode>from(child);
			}
		}
		return Maybe.ABSENT;
	}

	@Override
	public List<Maybe<T>> postOrderDeepFirstTraversal(){
		if(this.isEmpty()){
			return new ArrayList<>();
		}else{
			return postOrderDeepFirstTraversal(this.root().get());
		}
	}

	@Override
	public List<Maybe<T>> preOrderDeepFirstTraversal(){
		if(this.isEmpty()){
			return new ArrayList<>();
		}else{
			return preOrderDeepFirstTraversal(this.root().get());
		}
	}

	@Override
	public List<Maybe<T>> breedFirstTraversal(){
		if(this.isEmpty()){
			return new ArrayList<>();
		}else{
			return breedFirstTraversal(this.root().get());
		}
	}

	@Override
	public Maybe<TreeNode> postOrderDeepFirstSearch(T value){
		if(this.isEmpty()){
			return Maybe.ABSENT;
		}else{
			return postOrderDeepFirstSearch(this.root().get(), value);
		}
	}

	@Override
	public Maybe<TreeNode> preOrderDeepFirstSearch(T value){
		if(this.isEmpty()){
			return Maybe.ABSENT;
		}else{
			return preOrderDeepFirstSearch(this.root().get(), value);
		}
	}

	@Override
	public Maybe<TreeNode> breedFirstSearch(T value){
		if(this.isEmpty()){
			return Maybe.ABSENT;
		}else{
			return breedFirstSearch(this.root().get(), value);
		}
	}

	@Override
	public List<Maybe<T>> postOrderDeepFirstTraversal(TreeNode node){
		node.checkNode(this);
		ArrayList<Maybe<T>> ret=new ArrayList<>();
		for(TreeNode son:this.children(node)){
			ret.addAll(this.postOrderDeepFirstTraversal(son));
		}
		ret.add(this.getValue(((TNode<T>)node)));
		return ret;
	}

	@Override
	public List<Maybe<T>> preOrderDeepFirstTraversal(TreeNode node){
		node.checkNode(this);
		ArrayList<Maybe<T>> ret=new ArrayList<>();
		ret.add(getValue(((TNode<T>)node)));
		for(TreeNode son:this.children(node)){
			ret.addAll(this.postOrderDeepFirstTraversal(son));
		}
		return ret;
	}

	@Override
	public List<Maybe<T>> breedFirstTraversal(TreeNode node){
		node.checkNode(this);

		ArrayList<Maybe<T>> ret=new ArrayList<>();
		LinkedList<TreeNode> queue=new LinkedList<>();
		queue.push(node);

		while(!queue.isEmpty()){
			TreeNode son=queue.pop();
			ret.add(this.getValue(son));
			for(TreeNode child:this.children(son)){
				queue.add(child);
			}
		}
		return ret;
	}

	@Override
	public Maybe<TreeNode> postOrderDeepFirstSearch(TreeNode node, T value){
		node.checkNode(this);

		Maybe<TreeNode> ret;
		for(TreeNode son:this.children(node)){
			ret=this.postOrderDeepFirstSearch(son, value);
			if(ret.isPresent()){
				return ret;
			}
		}

		if(equalsValue(node, value)){
			return Maybe.<TreeNode>from(node);
		}

		return Maybe.ABSENT;
	}

	@Override
	public Maybe<TreeNode> preOrderDeepFirstSearch(TreeNode node, T value){
		node.checkNode(this);

		if(equalsValue(node, value)){
			return Maybe.<TreeNode>from(node);
		}

		Maybe<TreeNode> ret;
		for(TreeNode son:this.children(node)){
			ret=this.preOrderDeepFirstSearch(son, value);
			if(ret.isPresent()){
				return ret;
			}
		}

		return Maybe.ABSENT;
	}

	@Override
	public Maybe<TreeNode> breedFirstSearch(TreeNode node, T value){
		node.checkNode(this);

		LinkedList<TreeNode> queue=new LinkedList<>();
		queue.push(node);

		while(!queue.isEmpty()){
			TreeNode son=queue.pop();
			if(equalsValue(node, value)){
				return Maybe.<TreeNode>from(node);
			}
			for(TreeNode child:this.children(son)){
				queue.add(child);
			}
		}

		return Maybe.ABSENT;
	}
}
