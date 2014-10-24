package es.iguanod.collect;

import es.iguanod.collect.Tree.TreeNode;
import es.iguanod.util.Maybe;
import java.util.LinkedList;

/**
 *
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.6.1.a
 * @version
 */
public class LinkedTree<T> extends AbstractLinkedTree<T>{

	private static final long serialVersionUID=1961696201478523697L;

	public LinkedTree(){
		super();
	}

	public LinkedTree(int max_sons){
		super(max_sons);
	}

	@SuppressWarnings("LeakingThisInConstructor") // There is no leak because copyStructure is just a private auxiliary function
	public LinkedTree(Tree<? extends T> tree){
		super(tree);
	}

	@SuppressWarnings("LeakingThisInConstructor") // There is no leak because copyStructure is just a private auxiliary function
	public LinkedTree(Tree<? extends T> tree, int max_sons){
		super(tree, max_sons);
	}
	
	@Override
	protected final boolean nullsAllowed(){
		return true;
	}

	@Override
	protected final boolean structureModifiable(){
		return true;
	}
	
	@Override
	protected final boolean nodeValueModifiable(){
		return true;
	}

	@Override
	public LinkedTree<T> toTree(TreeNode node){
		LinkedTree<T> ret=new LinkedTree<>(this.maxSons());
		super.toTree(node, ret);
		return ret;
	}

	@Override
	public TreeNode push(T value){
		if(this.maxSons() <= 0){
			throw new UnsupportedOperationException("Operation not supported if maximum of sons per node is not specified");
		}

		if(!this.nullsAllowed() && value == null){
			throw new NullPointerException("The tree doesn't accept null values");
		}

		if(this.isEmpty()){
			root=provideNode(Maybe.from(value), null);
			return root;
		}

		LinkedList<LinkedTNode<T>> queue=new LinkedList<>();
		queue.push(this.root);
		LinkedTNode<T> first=null;

		while(!queue.isEmpty()){
			LinkedTNode<T> node=queue.pop();
			if(!this.isFull(node)){
				LinkedTNode<T> ret=provideNode(Maybe.from(value), node);
				node.sons.add(ret);
				return ret;
			}else if(!this.hasChildren(node) && first == null){
				first=node;
			}
			queue.addAll(((LinkedTNode<T>)node).sons);
		}

		return pvtAdd(first, value);
	}

	@Override
	public Maybe<T> pop(){

		if(this.isEmpty()){
			return Maybe.ABSENT;
		}

		LinkedList<LinkedTNode<T>> queue=new LinkedList<>();
		queue.push(this.root);
		LinkedTNode<T> last=null;

		while(!queue.isEmpty()){
			last=queue.pop();
			queue.addAll(((LinkedTNode<T>)last).sons);
		}

		Maybe<T> ret=this.getValue(last);
		last.invalidate(this);

		if(last == root){
			root=null;
		}else{
			this.pvtRemove(last.parent, this.childrenSize(last.parent) - 1);
		}

		return ret;
	}
}
