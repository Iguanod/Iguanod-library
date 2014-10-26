package es.iguanod.collect;

import es.iguanod.util.Maybe;
import es.iguanod.util.MaybeM;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * NOTE: Any subclass that implements its own TreeNode <b>MUST</b> override the
 * {@link #provideNode(Maybe,LinkedTNode) provideNode} method to make it return
 * an instance of said TreeNode.
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since
 * @version
 */
public abstract class AbstractLinkedTree<T> extends AbstractTree<T>{

	private static final long serialVersionUID=58820643596164250L;
	//************
	protected LinkedTNode<T> root;

	protected static class LinkedTNode<T> extends TNode<T>{

		private static final long serialVersionUID=-6959758149717434927L;
		//************
		public MaybeM<T> value;
		public LinkedTNode<T> parent;
		public ArrayList<LinkedTNode<T>> sons;

		public LinkedTNode(Maybe<? extends T> value, LinkedTNode<T> parent, Tree<? super T> tree){
			super(tree);
			this.value=MaybeM.<T>copyOf(value);
			this.parent=parent;
			this.sons=new ArrayList<>();
		}
	}

	/**
	 * New nodes susceptibles of ever being returned by a method, must be
	 * instantiated through this method instead of by a {@code new} call. In
	 * case of doubt, this method is preferred over a constructor, as the cost
	 * overhead should be negligible.
	 *
	 * @param value
	 * @param parent
	 *
	 * @return
	 */
	protected LinkedTNode<T> provideNode(Maybe<? extends T> value, LinkedTNode<T> parent){
		return new LinkedTNode(value, parent, this);
	}

	public AbstractLinkedTree(int max_sons){
		super(max_sons);
		this.root=null;
	}

	public AbstractLinkedTree(){
		this(0);
	}

	@SuppressWarnings("LeakingThisInConstructor") // There is no leak because copyStructure is just a private auxiliary function
	public AbstractLinkedTree(Tree<? extends T> tree){
		this(tree, tree.maxSons());
	}

	@SuppressWarnings("LeakingThisInConstructor") // There is no leak because copyStructure is just a private auxiliary function
	public AbstractLinkedTree(Tree<? extends T> tree, int max_sons){
		super(max_sons);
		if(tree.isEmpty()){
			root=null;
			return;
		}
		TreeNode tree_root=tree.root().get();
		if(!this.nullsAllowed() && !checkNulls(tree, tree_root))
			throw new IllegalArgumentException("A node of the passed tree has a null value but this tree doesn't accept them");
		if(this.maxSons() > 0 && !checkSons(tree, tree_root, this.maxSons()))
			throw new IllegalArgumentException("A node of the passed tree has more children than the accepted by this tree (" + this.maxSons() + ")");
		this.copyStructure(tree, tree_root);
	}
	
	protected abstract boolean nodeValueModifiable();

	private void copyStructure(Tree<? extends T> tree_src, TreeNode node){
		//TODO: test this function
		root=provideNode(tree_src.getValue(node), null);
		LinkedList<TreeNode> to_copy=new LinkedList<>();
		LinkedList<LinkedTNode<T>> copied=new LinkedList<>();
		to_copy.push(node);
		copied.push(root);
		while(!to_copy.isEmpty()){
			TreeNode next_copy=to_copy.pop();
			LinkedTNode<T> next_copied=copied.pop();
			for(TreeNode son:tree_src.children(next_copy)){
				LinkedTNode<T> to_add=provideNode(tree_src.getValue(son), next_copied);
				next_copied.sons.add(to_add);
				to_copy.push(son);
				copied.push(to_add);
			}
		}
	}

	private boolean checkNulls(Tree tree, TreeNode node){
		if(equalsValue(node, null)){
			return false;
		}
		for(TreeNode son:(Iterable<TreeNode>)tree.children(node)){
			if(!checkNulls(tree, son)){
				return false;
			}
		}
		return true;
	}

	private boolean checkSons(Tree tree, TreeNode node, int nsons){
		if(tree.childrenSize(node) > nsons)
			return false;
		for(TreeNode son:(Iterable<TreeNode>)tree.children(node)){
			if(!checkSons(tree, son, nsons))
				return false;
		}
		return true;
	}

	/**
	 *
	 * @param node
	 *
	 * @return
	 */
	@Override
	public Maybe<T> getValue(TreeNode node){
		node.checkNode(this);
		return ((LinkedTNode<T>)node).value;
	}

	@Override
	public Maybe<T> setValue(TreeNode node, T value){
		node.checkNode(this);
		if(!this.nodeValueModifiable()){
			throw new UnsupportedOperationException("Node values not modifiables");
		}
		if(!this.nullsAllowed() && value == null){
			throw new NullPointerException("The tree doesn't accept null values");
		}
		Maybe<T> old_value=Maybe.copyOf(((LinkedTNode<T>)node).value);
		((LinkedTNode<T>)node).value.set(value);
		return old_value;
	}

	protected void toTree(TreeNode node, AbstractLinkedTree<T> dest){
		node.checkNode(this);
		dest.copyStructure(this, node);
	}

	@Override
	public void trim(){
		LinkedList<LinkedTNode<T>> queue=new LinkedList<>();
		queue.push(this.root);
		while(!queue.isEmpty()){
			LinkedTNode<T> next=queue.pop();
			next.sons.trimToSize();
			for(LinkedTNode<T> son:next.sons){
				queue.push(son);
			}
		}
	}

	@Override
	public void clear(){
		if(this.isEmpty())
			return;
		this.invalidateBranch(root);
		this.root=null;
	}

	@Override
	public Maybe<TreeNode> root(){
		return root == null?Maybe.ABSENT:Maybe.from(root);
	}

	@Override
	public boolean isEmpty(){
		return root == null;
	}

	@Override
	public int childrenSize(TreeNode node){
		node.checkNode(this);
		return ((LinkedTNode<T>)node).sons.size();
	}

	@Override
	public Iterable<TreeNode> children(final TreeNode node){
		node.checkNode(this);
		return new Iterable<TreeNode>(){
			@Override
			public Iterator<TreeNode> iterator(){
				return new Iterator<TreeNode>(){
					int i=0;

					@Override
					public boolean hasNext(){
						return i != ((LinkedTNode<T>)node).sons.size();
					}

					@Override
					public TreeNode next(){
						if(!hasNext()){
							throw new IllegalStateException("The iterator has no more elements");
						}
						i++;
						return ((LinkedTNode<T>)node).sons.get(i - 1);
					}

					@Override
					public void remove(){
						throw new UnsupportedOperationException("The children Iterator of a Tree doesn't support element removal");
					}
				};
			}
		};
	}

	@Override
	public Maybe<TreeNode> getChild(TreeNode node, int index){
		node.checkNode(this);
		if(((LinkedTNode<T>)node).sons.size() > index){
			return Maybe.<TreeNode>from(((LinkedTNode<T>)node).sons.get(index));
		}else{
			return Maybe.ABSENT;
		}
	}

	@Override
	public Maybe<TreeNode> parent(TreeNode node){
		node.checkNode(this);
		return ((LinkedTNode<T>)node).parent == null?Maybe.ABSENT:Maybe.from(((LinkedTNode<T>)node).parent);
	}

	protected LinkedTNode<T> pvtAdd(TreeNode node, T value){
		if(!nullsAllowed() && value == null)
			throw new NullPointerException("The tree doesn't accept null values");
		LinkedTNode<T> ret=provideNode(Maybe.from(value), ((LinkedTNode<T>)node));
		((LinkedTNode<T>)node).sons.add(ret);
		return ret;
	}

	protected void pvtRemove(TreeNode node, int index){
		invalidateBranch(node);
		((LinkedTNode<T>)node).sons.remove(index);
	}

	@Override
	public boolean add(TreeNode node, T value){
		if(!structureModifiable()){
			throw new UnsupportedOperationException("Addition and removal directly to nodes unsupported");
		}
		node.checkNode(this);
		if(!nullsAllowed() && value == null)
			throw new NullPointerException("The tree doesn't accept null values");
		return ((LinkedTNode<T>)node).sons.add(provideNode(Maybe.from(value), ((LinkedTNode<T>)node)));
	}

	@Override
	public boolean remove(Object obj){

		if(!structureModifiable()){
			throw new UnsupportedOperationException("Addition and removal directly to nodes unsupported");
		}

		if(root == null){
			return false;
		}
		if(equalsValue(root, obj)){
			root=null;
			return true;
		}

		LinkedList<LinkedTNode<T>> queue=new LinkedList<>();
		queue.push(root);

		while(!queue.isEmpty()){
			LinkedTNode<T> node=queue.pop();
			Iterator<LinkedTNode<T>> iter=node.sons.iterator();
			while(iter.hasNext()){
				LinkedTNode<T> son=iter.next();
				if(equalsValue(son, obj)){
					iter.remove();
					invalidateBranch(son);
					return true;
				}
				queue.add(son);
			}
		}
		return false;
	}

	@Override
	public void removeSons(TreeNode node){
		if(!structureModifiable()){
			throw new UnsupportedOperationException("Addition and removal directly to nodes unsupported");
		}
		node.checkNode(this);
		for(TreeNode son:this.children(node)){
		invalidateBranch(son);
		}
		((LinkedTNode<T>)node).sons.clear();
	}

	@Override
	public boolean remove(TreeNode node, Object obj){
		if(!structureModifiable()){
			throw new UnsupportedOperationException("Addition and removal directly to nodes unsupported");
		}
		node.checkNode(this);
		Iterator<LinkedTNode<T>> iter=((LinkedTNode<T>)node).sons.iterator();
		while(iter.hasNext()){
			LinkedTNode<T> son=iter.next();
			if(equalsValue(son, obj)){
				iter.remove();
				invalidateBranch(son);
				return true;
			}
		}
		return false;
	}

	@Override
	public Maybe<T> remove(TreeNode node, int index){
		if(!structureModifiable()){
			throw new UnsupportedOperationException("Addition and removal directly to nodes unsupported");
		}
		node.checkNode(this);
		Maybe<T> ret;
		if(((LinkedTNode<T>)node).sons.size() > index){
			ret=this.getValue(((LinkedTNode<T>)node).sons.remove(index));
			invalidateBranch(node);
		}else{
			ret=Maybe.ABSENT;
		}
		return ret;
	}

	@Override
	public boolean retainAll(TreeNode node, Collection<?> col){
		if(!structureModifiable()){
			throw new UnsupportedOperationException("Addition and removal directly to nodes unsupported");
		}
		node.checkNode(this);

		boolean modif=false;
		Iterator<LinkedTNode<T>> iter=((LinkedTNode<T>)node).sons.iterator();
		while(iter.hasNext()){
			LinkedTNode<T> son=iter.next();
			boolean contained=false;
			for(Object obj:col){
				if(equalsValue(son, obj)){
					contained=true;
					break;
				}
			}
			if(!contained){
				iter.remove();
				invalidateBranch(son);
				modif=true;
				break;
			}
		}
		return modif;
	}
}
