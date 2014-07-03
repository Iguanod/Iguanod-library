package es.iguanod.collect;

import java.util.List;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.9.1.a
 * @version
 */
public interface BinaryTree<T> extends Tree<T>{

	public List<T> inOrderDeepFirstTraversal(TreeNode node);

	public TreeNode inOrderDeepFirstSearch(TreeNode node, T value);

	public TreeNode left(TreeNode node);

	public TreeNode right(TreeNode node);
}
