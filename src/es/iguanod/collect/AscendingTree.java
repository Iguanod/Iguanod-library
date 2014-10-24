package es.iguanod.collect;

import es.iguanod.util.Maybe;
import es.iguanod.util.tuples.Tuple2;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since
 * @version
 */
public class AscendingTree<T> extends AbstractLinkedTree<T>{

	private static final long serialVersionUID=20174791549623287L;

	public AscendingTree(int num_sons){
		super(num_sons);
	}

	public AscendingTree(Tree<? extends T> tree){
		super(tree);
	}

	public AscendingTree(Tree<? extends T> tree, int num_sons){
		super(tree, num_sons);
	}
	
	@Override
	protected final boolean nullsAllowed(){
		return true;
	}

	@Override
	protected final boolean structureModifiable(){
		return false;
	}
	
	@Override
	protected final boolean nodeValueModifiable(){
		return false;
	}

	@Override
	public Tree<T> toTree(TreeNode node){
		AscendingTree<T> ret=new AscendingTree(this.maxSons());
		toTree(node, ret);
		return ret;
	}

	@Override
	public boolean isFull(TreeNode node){
		return super.isFull(node) && !this.hasUncompleteChilden(node);
	}

	public boolean hasUncompleteChilden(TreeNode node){
		node.checkNode(this);
		for(LinkedTNode<T> son:((LinkedTNode<T>)node).sons){
			if(son.value.isAbsent()){
				return true;
			}
		}
		return false;
	}

	public boolean hasAllUncompleteChildren(TreeNode node){
		node.checkNode(this);
		return !this.hasChildren(node) || this.getValue(((LinkedTNode<T>)node).sons.get(0)).isAbsent();
	}

	public int subTreeDepth(TreeNode node){
		int acc;
		Maybe<TreeNode> p=this.parent(node);
		for(acc=-1; p.isPresent() || this.getValue(p.get()).isPresent(); acc++){
			p=this.parent(p.get());
		}
		return acc;
	}

	public int nonEmptySize(){
		return nonEmptySize(root);
	}

	public int nonEmptySize(TreeNode node){
		if(!this.hasChildren(node))
			return 1;
		int acc=this.getValue(node).isAbsent()?0:1;
		for(TreeNode son:this.children(node)){
			acc+=this.nonEmptySize(son);
			if(acc < 0)
				return Integer.MAX_VALUE;
		}
		return acc;
	}

	@Override
	public TreeNode push(T value){

		if(this.isEmpty()){
			root=provideNode(Maybe.from(value), null);
			return root;
		}

		Tuple2<LinkedTNode<T>, Integer> result=this.pvtPush((LinkedTNode<T>)this.root().get(), value, 0, 0);
		if(result.getFirst() != null){
			return result.getFirst();
		}

		LinkedTNode<T> new_root=provideNode(Maybe.ABSENT, null);
		root.parent=new_root;
		new_root.sons.add(root);
		root=new_root;
		LinkedTNode<T> node=new_root;
		//TODO: don't use height() so it doesn't crash when height > INT_MAX
		int height=this.height() - 1;
		for(int i=0; i < height; i++){
			LinkedTNode<T> next=provideNode(Maybe.ABSENT, node);
			node.sons.add(next);
			node=next;
		}
		node.value.set(value);
		return node;
	}

	private Tuple2<LinkedTNode<T>, Integer> pvtPush(LinkedTNode<T> node, T value, int depth, int max_depth){

		for(LinkedTNode<T> son:node.sons){
			Tuple2<LinkedTNode<T>, Integer> result=this.pvtPush(son, value, depth + 1, Math.max(max_depth, depth + 1));
			if(result.getFirst() != null)
				return result;
			max_depth=Math.max(max_depth, result.getSecond());
		}
		if(this.parent(node) == null && this.getValue(node).isAbsent() && this.childrenSize(node) == 0){
			node.value.set(value);
			return new Tuple2<>(node, max_depth);
		}
		if(this.getValue(node).isPresent() || depth == max_depth){
			return new Tuple2<>(null, max_depth);
		}
		if(!this.isFull(node)){
			LinkedTNode<T> new_node=provideNode(Maybe.ABSENT, node);
			node.sons.add(new_node);
			for(int i=depth + 1; i < max_depth; i++){
				LinkedTNode<T> next=provideNode(Maybe.ABSENT, new_node);
				new_node.sons.add(next);
				new_node=next;
			}
			new_node.value.set(value);
			return new Tuple2<>(new_node, max_depth);
		}else if(this.getValue(node).isAbsent()){
			node.value.set(value);
			return new Tuple2<>(node, max_depth);
		}else{
			return new Tuple2<>(null, max_depth);
		}
	}

	@Override
	public Maybe<T> pop(){

		if(this.isEmpty()){
			return Maybe.ABSENT;
		}

		LinkedTNode<T> next=this.root;
		T elem=null;
		while(this.hasChildren(next) && elem == null){
			if(this.isFull(next) && this.getValue(next).isPresent()){
				elem=next.value.get();
				next.value.setAbsent();
			}else{
				next=next.sons.get(next.sons.size() - 1);
			}
		}

		if(elem != null){
			if(this.maxSons() == 1){
				if(next == this.root && this.hasChildren(next)){
					this.root=next.sons.get(0);
				}else{
					next.parent.sons.set(0, next.sons.get(0));
					next.sons.get(0).parent=next.parent;
				}
			}
			return Maybe.<T>from(elem);
		}

		Maybe<T> ret=this.getValue(next);
		if(next == root){
			this.invalidateSons(root);
			root=null;
			return Maybe.<T>from(elem);
		}

		next.parent.sons.remove(next.parent.sons.size() - 1);

		if(next.parent != this.root){
			int count=-1;
			do{
				next=(LinkedTNode<T>)this.parent(next).get();
				count++;
			}while(next != this.root && this.hasAllUncompleteChildren(next));

			if(count > 0)
				next.sons.remove(next.sons.size() - 1);
		}

		if(this.getValue(this.root).isAbsent() && this.childrenSize(this.root) == 1){
			this.root=this.root.sons.get(0);
			this.root.parent=null;
		}

		return Maybe.<T>from(elem);
	}
}
