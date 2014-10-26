package es.iguanod.collect;

import es.iguanod.util.Maybe;
import java.util.List;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public interface BinaryTree<T> extends Tree<T>{

	public List<T> inOrderDeepFirstTraversal(TreeNode node);

	public Maybe<TreeNode> inOrderDeepFirstSearch(TreeNode node, T value);

	public Maybe<TreeNode> left(TreeNode node);

	public Maybe<TreeNode> right(TreeNode node);
}
