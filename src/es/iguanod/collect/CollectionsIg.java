package es.iguanod.collect;

import es.iguanod.collect.DoubleHashCounter.DoubleHashCounterBuilder;
import es.iguanod.collect.HashCounter.HashCounterBuilder;
import es.iguanod.collect.IntTreeCounter.IntTreeCounterBuilder;
import es.iguanod.util.Caster;
import es.iguanod.util.Maybe;
import es.iguanod.util.tuples.Tuple2;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.2.1.a
 * @version
 */
public final class CollectionsIg{

	private CollectionsIg(){
	}
	public static final Counter EMPTY_COUNTER=new UnmodifiableSortedCounter(new DoubleHashCounterBuilder().build());
	public static final Tree EMPTY_TREE=new UnmodifiableTree(new LinkedTree());

	public static <T, V extends Number> Counter<T, V> emptyCounter(){
		return EMPTY_COUNTER;
	}

	public static <T> Tree<T> emptyTree(){
		return EMPTY_TREE;
	}

	public static <K, V extends Number> Counter<K, V> singletonCounter(K key, V value){
		Counter<K, V> counter=new HashCounterBuilder<K, V>(new Caster<BigDecimal, V>(){
			private static final long serialVersionUID=-858620261254526366L;

			@Override
			public V cast(BigDecimal t){
				return null;
			}
		}).build();
		return new UnmodifiableSortedCounter<>(counter);
	}

	public static <T> Tree<T> singletonTree(T item){
		Tree<T> tree=new LinkedTree();
		tree.push(item);
		return new UnmodifiableTree(tree);
	}

	public static <T> T[] toGenericArray(Collection<? extends T> col){

		IntTreeCounter<Class> counter=new IntTreeCounterBuilder<Class>().reverse(true).build();
		for(T elem:col){
			Class c=elem.getClass();
			do{
				counter.sum(c);
				c=c.getSuperclass();
			}while(c != null);
		}
		Class min_class=Object.class;
		for(Class c:counter.maxKeySet()){
			if(min_class.isAssignableFrom(c)){
				min_class=c;
			}
		}
		return col.toArray((T[])Array.newInstance(min_class, col.size()));
	}

	public static <T, V extends Number> Counter<T, V> unmodifiableCounter(Counter<T, V> counter){
		if(counter instanceof UnmodifiableSortedCounter){
			return counter;
		}else{
			return new UnmodifiableSortedCounter<>(counter);
		}
	}

	public static <T, V extends Number> SortedCounter<T, V> unmodifiableSortedCounter(SortedCounter<T, V> counter){
		if(counter instanceof UnmodifiableSortedCounter){
			return counter;
		}else{
			return new UnmodifiableSortedCounter<>(counter);
		}
	}

	public static <T> Tree<T> unmodifiableTree(Tree<T> tree){
		return new UnmodifiableTree<>(tree);
	}

	private static class UnmodifiableSortedCounter<K, V extends Number> implements SortedCounter<K, V>, Serializable{

		private static final long serialVersionUID=336498287123521094L;
		//************
		private Counter<K, V> counter;

		public UnmodifiableSortedCounter(Counter<K, V> counter){
			this.counter=counter;
		}

		@Override
		public int size(){
			return counter.size();
		}

		@Override
		public boolean isEmpty(){
			return counter.isEmpty();
		}

		@Override
		public boolean containsKey(Object key){
			return counter.containsKey(key);
		}

		@Override
		public boolean containsValue(Object value){
			return counter.containsValue(value);
		}

		@Override
		public Set<K> maxKeySet(){
			return Collections.unmodifiableSet(((SortedCounter<K, V>)counter).maxKeySet());
		}

		@Override
		public Set<K> minKeySet(){
			return Collections.unmodifiableSet(((SortedCounter<K, V>)counter).minKeySet());
		}

		@Override
		public V maxValue(){
			return ((SortedCounter<K, V>)counter).maxValue();
		}

		@Override
		public V minValue(){
			return ((SortedCounter<K, V>)counter).minValue();
		}

		@Override
		public Set<K> keySet(){
			return Collections.unmodifiableSet(counter.keySet());
		}

		@Override
		public Collection<V> values(){
			return Collections.unmodifiableCollection(counter.values());
		}

		@Override
		public Set<Entry<K, V>> entrySet(){
			return Collections.unmodifiableSet(counter.entrySet());
		}

		@Override
		public Set<K> keySet(V value){
			return Collections.unmodifiableSet(((SortedCounter<K, V>)counter).keySet(value));
		}

		@Override
		public Set<Entry<V, Set<K>>> inverseEntrySet(){
			return Collections.unmodifiableSet(((SortedCounter<K, V>)counter).inverseEntrySet());
		}

		@Override
		public V get(Object key){
			return counter.get(key);
		}

		@Override
		public SortedCounter<K, V> tailCounter(V from_value){
			return CollectionsIg.unmodifiableSortedCounter(((SortedCounter<K, V>)counter).tailCounter(from_value));
		}

		@Override
		public SortedCounter<K, V> tailCounter(V from_value, boolean inclusive){
			return CollectionsIg.unmodifiableSortedCounter(((SortedCounter<K, V>)counter).tailCounter(from_value, inclusive));
		}

		@Override
		public SortedCounter<K, V> headCounter(V to_value){
			return CollectionsIg.unmodifiableSortedCounter(((SortedCounter<K, V>)counter).headCounter(to_value));
		}

		@Override
		public SortedCounter<K, V> headCounter(V to_value, boolean inclusive){
			return CollectionsIg.unmodifiableSortedCounter(((SortedCounter<K, V>)counter).headCounter(to_value, inclusive));
		}

		@Override
		public SortedCounter<K, V> subCounter(V from_value, V to_value){
			return CollectionsIg.unmodifiableSortedCounter(((SortedCounter<K, V>)counter).subCounter(from_value, to_value));
		}

		@Override
		public SortedCounter<K, V> subCounter(V from_value, boolean from_inclusive, V to_value, boolean to_inclusive){
			return CollectionsIg.unmodifiableSortedCounter(((SortedCounter<K, V>)counter).subCounter(from_value, from_inclusive, to_value, to_inclusive));
		}

		@Override
		public String toString(){
			return counter.toString();
		}

		@Override
		public Tuple2<V, V> sum(K key){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void sumAll(Collection<? extends K> keys){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void sumAll(K[] keys){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void sumToAll(){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void sumToAll(V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public Tuple2<V, V> sum(K key, V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void sumAll(Map<? extends K, ? extends V> map){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public <X> void sumAll(Map<? extends K, ? extends X> map, Caster<X, V> caster){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void sumAll(Collection<? extends K> keys, V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void sumAll(K[] keys, V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void balancedSum(Collection<? extends K> keys){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void balancedSum(K[] keys){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public Tuple2<V, V> deduct(K key){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void deductAll(Collection<? extends K> keys){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void deductAll(K[] keys){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void deductToAll(){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void deductToAll(V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public Tuple2<V, V> deduct(K key, V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void deductAll(Map<? extends K, ? extends V> map){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public <X> void deductAll(Map<? extends K, ? extends X> map, Caster<X, V> caster){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void deductAll(Collection<? extends K> keys, V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void deductAll(K[] keys, V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void balancedDeduct(Collection<? extends K> keys){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void balancedDeduct(K[] keys){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public <X> void putAll(Map<? extends K, ? extends X> map, Caster<X, V> caster){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public Tuple2<V, V> putMax(K key, V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public Tuple2<V, V> putMin(K key, V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public V put(K key, V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public V remove(Object key){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> m){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void clear(){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void balancedSum(Collection<? extends K> keys, V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void balancedSum(K[] keys, V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void balancedSumToAll(){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void balancedSumToAll(V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void balancedDeduct(Collection<? extends K> keys, V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void balancedDeduct(K[] keys, V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void balancedDeductToAll(){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}

		@Override
		public void balancedDeductToAll(V value){
			throw new UnsupportedOperationException("Unmodifiable Counter");
		}
	}

	private static class UnmodifiableTree<T> implements Tree<T>, Serializable{

		private static final long serialVersionUID=-649494554005020202L;
		//************
		private Tree<T> tree;

		public UnmodifiableTree(Tree<T> tree){
			this.tree=tree;
		}

		@Override
		public boolean nullsAllowed(){
			return tree.nullsAllowed();
		}

		@Override
		public int maxSons(){
			return tree.maxSons();
		}

		@Override
		public Tree<T> toTree(TreeNode node){
			return tree.toTree(node);
		}

		@Override
		public void trim(){
			tree.trim();
		}

		@Override
		public Maybe<TreeNode> root(){
			return tree.root();
		}

		@Override
		public Maybe<T> getValue(TreeNode node){
			return tree.getValue(node);
		}

		@Override
		public boolean contains(Object obj){
			return tree.contains(obj);
		}

		@Override
		public boolean containsAll(Collection<?> col){
			return tree.containsAll(col);
		}

		@Override
		public boolean contains(TreeNode node, Object obj){
			return tree.contains(node, obj);
		}

		@Override
		public boolean containsChild(TreeNode node, Object obj){
			return tree.containsChild(node, obj);
		}

		@Override
		public boolean containsDescendant(TreeNode node, Object obj){
			return tree.containsDescendant(node, obj);
		}

		@Override
		public boolean containsAll(TreeNode node, Collection<?> col){
			return tree.containsAll(node, col);
		}

		@Override
		public boolean containsAllChildren(TreeNode node, Collection<?> col){
			return tree.containsAllChildren(node, col);
		}

		@Override
		public boolean containsAllDescendants(TreeNode node, Collection<?> col){
			return tree.containsAllDescendants(node, col);
		}

		@Override
		public boolean isEmpty(){
			return tree.isEmpty();
		}

		@Override
		public boolean hasChildren(TreeNode node){
			return tree.hasChildren(node);
		}

		@Override
		public boolean isFull(TreeNode node){
			return tree.isFull(node);
		}

		@Override
		public int height(){
			return tree.height();
		}

		@Override
		public int height(TreeNode node){
			return tree.height(node);
		}

		@Override
		public int depth(TreeNode node){
			return tree.depth(node);
		}

		@Override
		public int size(){
			return tree.size();
		}

		@Override
		public int size(TreeNode node){
			return tree.size(node);
		}

		@Override
		public int childrenSize(TreeNode node){
			return tree.childrenSize(node);
		}

		@Override
		public Iterable<TreeNode> children(TreeNode node){
			return tree.children(node);
		}

		@Override
		public List<TreeNode> childrenCopy(TreeNode node){
			return tree.childrenCopy(node);
		}

		@Override
		public Maybe<TreeNode> getChild(TreeNode node, int index){
			return tree.getChild(node, index);
		}

		@Override
		public Maybe<TreeNode> parent(TreeNode node){
			return tree.parent(node);
		}

		@Override
		public List<Maybe<T>> postOrderDeepFirstTraversal(){
			return tree.postOrderDeepFirstTraversal();
		}

		@Override
		public List<Maybe<T>> preOrderDeepFirstTraversal(){
			return tree.preOrderDeepFirstTraversal();
		}

		@Override
		public List<Maybe<T>> breedFirstTraversal(){
			return tree.breedFirstTraversal();
		}

		@Override
		public Maybe<TreeNode> postOrderDeepFirstSearch(T value){
			return tree.postOrderDeepFirstSearch(value);
		}

		@Override
		public Maybe<TreeNode> preOrderDeepFirstSearch(T value){
			return tree.preOrderDeepFirstSearch(value);
		}

		@Override
		public Maybe<TreeNode> breedFirstSearch(T value){
			return tree.breedFirstSearch(value);
		}

		@Override
		public List<Maybe<T>> postOrderDeepFirstTraversal(TreeNode node){
			return tree.postOrderDeepFirstTraversal(node);
		}

		@Override
		public List<Maybe<T>> preOrderDeepFirstTraversal(TreeNode node){
			return tree.preOrderDeepFirstTraversal(node);
		}

		@Override
		public List<Maybe<T>> breedFirstTraversal(TreeNode node){
			return tree.breedFirstTraversal(node);
		}

		@Override
		public Maybe<TreeNode> postOrderDeepFirstSearch(TreeNode node, T value){
			return tree.postOrderDeepFirstSearch(node, value);
		}

		@Override
		public Maybe<TreeNode> preOrderDeepFirstSearch(TreeNode node, T value){
			return tree.preOrderDeepFirstSearch(node, value);
		}

		@Override
		public Maybe<TreeNode> breedFirstSearch(TreeNode node, T value){
			return tree.breedFirstSearch(node, value);
		}

		@Override
		public TreeNode push(T elem){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}

		@Override
		public void pushAll(Collection<? extends T> col){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}

		@Override
		public Maybe<T> pop(){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}

		@Override
		public boolean remove(Object obj){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}

		@Override
		public boolean removeAll(Collection<?> col){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}

		@Override
		public void clear(){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}

		@Override
		public Maybe<T> setValue(TreeNode node, T value){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}

		@Override
		public boolean add(TreeNode node, T value){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}

		@Override
		public boolean addAll(TreeNode node, Collection<? extends T> col){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}

		@Override
		public boolean remove(TreeNode node, Object obj){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}

		@Override
		public Maybe<T> remove(TreeNode node, int index){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}

		@Override
		public void removeSons(TreeNode node){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}

		@Override
		public boolean removeAll(TreeNode node, Collection<?> col){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}

		@Override
		public boolean retainAll(TreeNode node, Collection<?> col){
			throw new UnsupportedOperationException("Unmodifiable tree");
		}
	}
}
