package es.iguanod.collect;

import es.iguanod.util.Maybe;
import java.util.Collection;
import java.util.List;

//TODO: al hacer la pasada final de todos los archivos, comprobar que todas las funciones hagan checkNode
/**
 * Especificar como construir árbol a partir de parte de otro
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.6.1.a
 * @version
 */
public interface Tree<T>{

	public abstract class TreeNode{

		protected abstract void checkNode(Tree tree);

		protected abstract void invalidate(Tree tree);
	}

	public int maxSons();

	/**
	 * Is not optional but as is a best effort function can simply return.
	 */
	public void trim();

	/**
	 * optional. push->pop not always the same
	 *
	 * @param elem
	 */
	public TreeNode push(T elem);

	/**
	 * optional
	 *
	 * @param col
	 */
	public void pushAll(Collection<? extends T> col);

	/**
	 * optional. push->pop not always the same
	 *
	 * @return
	 */
	public Maybe<T> pop();

	public boolean remove(Object obj);

	public boolean removeAll(Collection<?> col);

	public Maybe<TreeNode> root();

	/**
	 * optional
	 */
	public void clear();

	public Maybe<T> getValue(TreeNode node);

	/**
	 * optional
	 *
	 * @param value
	 *
	 * @return
	 */
	public Maybe<T> setValue(TreeNode node, T value);

	/**
	 * optional
	 *
	 * @param value
	 *
	 * @return
	 */
	public boolean add(TreeNode node, T value);

	/**
	 * optional
	 *
	 * @param col
	 *
	 * @return
	 */
	public boolean addAll(TreeNode node, Collection<? extends T> col);

	public boolean contains(Object obj);

	public boolean containsAll(Collection<?> col);

	public boolean contains(TreeNode node, Object obj);

	public boolean containsChild(TreeNode node, Object obj);

	public boolean containsDescendant(TreeNode node, Object obj);

	public boolean containsAll(TreeNode node, Collection<?> col);

	public boolean containsAllChildren(TreeNode node, Collection<?> col);

	public boolean containsAllDescendants(TreeNode node, Collection<?> col);

	public boolean isEmpty();

	public boolean hasChildren(TreeNode node);

	public boolean isFull(TreeNode node);

	/**
	 * optional
	 *
	 * @param obj
	 *
	 * @return
	 */
	public boolean remove(TreeNode node, Object obj);

	public Maybe<T> remove(TreeNode node, int index);

	/**
	 * optional
	 */
	public void removeSons(TreeNode node);

	/**
	 * optional
	 *
	 * @param col
	 *
	 * @return
	 */
	public boolean removeAll(TreeNode node, Collection<?> col);

	/**
	 * optional
	 *
	 * @param col
	 *
	 * @return
	 */
	public boolean retainAll(TreeNode node, Collection<?> col);

	public int height();

	public int height(TreeNode node);

	public int depth(TreeNode node);

	public int size();

	public int size(TreeNode node);

	public int childrenSize(TreeNode node);

	public Iterable<TreeNode> children(TreeNode node);

	public List<TreeNode> childrenCopy(TreeNode node);

	public Maybe<TreeNode> getChild(TreeNode node, int index);

	public Maybe<TreeNode> parent(TreeNode node);

	public List<Maybe<T>> postOrderDeepFirstTraversal();

	public List<Maybe<T>> preOrderDeepFirstTraversal();

	public List<Maybe<T>> breedFirstTraversal();

	public Maybe<TreeNode> postOrderDeepFirstSearch(T value);

	public Maybe<TreeNode> preOrderDeepFirstSearch(T value);

	public Maybe<TreeNode> breedFirstSearch(T value);

	public List<Maybe<T>> postOrderDeepFirstTraversal(TreeNode node);

	public List<Maybe<T>> preOrderDeepFirstTraversal(TreeNode node);

	public List<Maybe<T>> breedFirstTraversal(TreeNode node);

	public Maybe<TreeNode> postOrderDeepFirstSearch(TreeNode node, T value);

	public Maybe<TreeNode> preOrderDeepFirstSearch(TreeNode node, T value);

	public Maybe<TreeNode> breedFirstSearch(TreeNode node, T value);

	/**
	 * Compares the specified object with this {@code Tree} for equality.
	 * While the Tree interface adds no stipulations to the general contract
	 * for the {@link Object#equals(Object) Object.equals} method, programmers
	 * should take note that any class that overrides the
	 * {@code Object.equals} method must also override the
	 * {@link Object#hashCode() Object.hashCode} method in order to satisfy
	 * the general contract for the {@code Object.hashCode} method. In
	 * particular, {@code t1.equals(t2)} implies that
	 * {@code t1.hashCode()==t2.hashCode()}.
	 *
	 * <p>
	 * However, it is not necessary to override equals, and the simplest
	 * course of action is to rely on {@code Object}'s implementation, but the
	 * implementor may wish to implement a "value comparison" in place of the
	 * default "reference comparison."</p>
	 *
	 * @param obj object to be compared for equality with this TreeNode.
	 *
	 * @return true if the specified object is equal to this Tree, false
	 * otherwise.
	 */
	@Override
	public boolean equals(Object obj);

	/**
	 * Returns the hash code value for this {@code Tree}. While the Tree
	 * interface adds no stipulations to the general contract for the
	 * {@link Object#hashCode() Object.hashCode} method, programmers should
	 * take note that any class that overrides the
	 * {@link Object#equals(Object) Object.equals} method must also override
	 * the {@code Object.hashCode} method in order to satisfy the general
	 * contract for the {@code Object.hashCode} method. In particular,
	 * {@code t1.equals(t2)} implies that
	 * {@code t1.hashCode()==t2.hashCode()}.
	 *
	 * @return the hash code value for this Tree.
	 */
	@Override
	public int hashCode();
}
