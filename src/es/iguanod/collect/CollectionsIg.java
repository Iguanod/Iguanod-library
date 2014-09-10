package es.iguanod.collect;

import es.iguanod.collect.DoubleHashCounter.DoubleHashCounterBuilder;
import es.iguanod.collect.DoubleTreeCounter.DoubleTreeCounterBuilder;
import es.iguanod.collect.HashCounter.HashCounterBuilder;
import es.iguanod.collect.IntTreeCounter.IntTreeCounterBuilder;
import es.iguanod.collect.TreeCounter.TreeCounterBuilder;
import es.iguanod.util.Caster;
import es.iguanod.util.Maybe;
import es.iguanod.util.tuples.Tuple2;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
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

	public static final Counter EMPTY_COUNTER=new UnmodifiableCounter(new DoubleHashCounterBuilder().build());
	public static final SortedCounter EMPTY_SORTED_COUNTER=new UnmodifiableSortedCounter(new DoubleTreeCounterBuilder().build());
	public static final Tree EMPTY_TREE=new UnmodifiableTree(new LinkedTree());
	public static final BinaryTree EMPTY_BINARY_TREE=new UnmodifiableBinaryTree(null);
	public static final FixedCapacityQueue EMPTY_FIXED_CAPACITY_QUEUE=new UnmodifiableFixedCapacityQueue(new LinkedFixedCapacityQueue(1));

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

	public static <T, V extends Number> Counter<T, V> emptyCounter(){
		return EMPTY_COUNTER;
	}

	public static <T, V extends Number> SortedCounter<T, V> emptySortedCounter(){
		return EMPTY_SORTED_COUNTER;
	}

	public static <T> Tree<T> emptyTree(){
		return EMPTY_TREE;
	}

	public static <T> BinaryTree<T> emptyBinaryTree(){
		return EMPTY_BINARY_TREE;
	}

	public static <T> FixedCapacityQueue<T> emptyFixedCapacityQueue(){
		return EMPTY_FIXED_CAPACITY_QUEUE;
	}

	public static <K, V extends Number> Counter<K, V> singletonCounter(K key, V value){
		Counter<K, V> counter=new HashCounterBuilder<K, V>(new Caster<BigDecimal, V>(){
			private static final long serialVersionUID=-858620261254526366L;

			@Override
			public V cast(BigDecimal t){
				return null;
			}
		}).build();
		return new UnmodifiableCounter<>(counter);
	}

	public static <K, V extends Number> SortedCounter<K, V> singletonSortedCounter(K key, V value){
		SortedCounter<K, V> counter=new TreeCounterBuilder<K, V>(new Caster<BigDecimal, V>(){
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

	public static <T> BinaryTree<T> singletonBinaryTree(T item){
		BinaryTree<T> tree=null;
		tree.push(item);
		return new UnmodifiableBinaryTree<>(tree);
	}

	public static <T> FixedCapacityQueue<T> singletonFixedCapacityQueue(T item){
		FixedCapacityQueue<T> queue=new LinkedFixedCapacityQueue<>(1);
		queue.push(item);
		return new UnmodifiableFixedCapacityQueue<>(queue);
	}

	public static <T, V extends Number> Counter<T, V> unmodifiableCounter(Counter<T, V> counter){
		if(counter instanceof UnmodifiableCounter){
			return counter;
		}else{
			return new UnmodifiableCounter<>(counter);
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
		if(tree instanceof UnmodifiableSortedCounter){
			return tree;
		}else{
			return new UnmodifiableTree<>(tree);
		}
	}

	public static <T> BinaryTree<T> unmodifiableBinaryTree(BinaryTree<T> tree){
		if(tree instanceof UnmodifiableSortedCounter){
			return tree;
		}else{
			return new UnmodifiableBinaryTree<>(tree);
		}
	}

	public static <T> FixedCapacityQueue<T> unmodifiableFixedCapacityQueue(FixedCapacityQueue<T> queue){
		if(queue instanceof UnmodifiableSortedCounter){
			return queue;
		}else{
			return new UnmodifiableFixedCapacityQueue<>(queue);
		}
	}

	public static <K, V extends Number> Counter<K, V> synchronizedCounter(Counter<K, V> counter){
		if(counter instanceof SynchronizedCounter){
			return counter;
		}else{
			return new SynchronizedCounter<>(counter);
		}
	}

	public static <K, V extends Number> SortedCounter<K, V> synchronizedSortedCounter(SortedCounter<K, V> counter){
		if(counter instanceof SynchronizedSortedCounter){
			return counter;
		}else{
			return new SynchronizedSortedCounter<>(counter);
		}
	}

	public static <T> Tree<T> synchronizedTree(Tree<T> tree){
		if(tree instanceof SynchronizedTree){
			return tree;
		}else{
			return new SynchronizedTree<>(tree);
		}
	}

	public static <T> BinaryTree<T> synchronizedBinaryTree(BinaryTree<T> tree){
		if(tree instanceof SynchronizedTree){
			return tree;
		}else{
			return new SynchronizedBinaryTree<>(tree);
		}
	}

	public static <T> FixedCapacityQueue<T> synchronizedFixedCapacityQueue(FixedCapacityQueue<T> queue){
		if(queue instanceof SynchronizedFixedCapacityQueue){
			return queue;
		}else{
			return new SynchronizedFixedCapacityQueue<>(queue);
		}
	}

	private static class UnmodifiableCounter<K, V extends Number> implements Counter<K, V>, Serializable{

		private static final long serialVersionUID=336498287123521094L;
		//************
		Counter<K, V> counter;

		public UnmodifiableCounter(Counter<K, V> counter){
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
		public V get(Object key){
			return counter.get(key);
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

	private static class UnmodifiableSortedCounter<K, V extends Number> extends UnmodifiableCounter<K, V> implements SortedCounter<K, V>{

		private static final long serialVersionUID=4208105930154054870L;

		public UnmodifiableSortedCounter(SortedCounter<K, V> counter){
			super(counter);
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
		public Set<K> keySet(V value){
			return Collections.unmodifiableSet(((SortedCounter<K, V>)counter).keySet(value));
		}

		@Override
		public Set<Entry<V, Set<K>>> inverseEntrySet(){
			return Collections.unmodifiableSet(((SortedCounter<K, V>)counter).inverseEntrySet());
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
	}

	private static class UnmodifiableTree<T> implements Tree<T>, Serializable{

		private static final long serialVersionUID=-649494554005020202L;
		//************
		Tree<T> tree;

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

	private static class UnmodifiableBinaryTree<T> extends UnmodifiableTree<T> implements BinaryTree<T>{

		private static final long serialVersionUID=1L;

		public UnmodifiableBinaryTree(BinaryTree<T> tree){
			super(tree);
		}

		@Override
		public List<T> inOrderDeepFirstTraversal(TreeNode node){
			return ((BinaryTree<T>)tree).inOrderDeepFirstTraversal(node);
		}

		@Override
		public Maybe<TreeNode> inOrderDeepFirstSearch(TreeNode node, T value){
			return ((BinaryTree<T>)tree).inOrderDeepFirstSearch(node, value);
		}

		@Override
		public Maybe<TreeNode> left(TreeNode node){
			return ((BinaryTree<T>)tree).left(node);
		}

		@Override
		public Maybe<TreeNode> right(TreeNode node){
			return ((BinaryTree<T>)tree).right(node);
		}
	}

	private static class UnmodifiableFixedCapacityQueue<T> implements FixedCapacityQueue<T>, Serializable{

		private static final long serialVersionUID=-982310296149207480L;
		//**********
		FixedCapacityQueue<T> queue;

		public UnmodifiableFixedCapacityQueue(FixedCapacityQueue<T> queue){
			this.queue=queue;
		}

		@Override
		public Maybe<T> peek(){
			return queue.peek();
		}

		@Override
		public int capacity(){
			return queue.capacity();
		}

		@Override
		public boolean isFull(){
			return queue.isFull();
		}

		@Override
		public int size(){
			return queue.size();
		}

		@Override
		public boolean isEmpty(){
			return queue.isEmpty();
		}

		@Override
		public boolean contains(Object obj){
			return queue.contains(obj);
		}

		@Override
		public Iterator<T> iterator(){
			return new Iterator<T>(){

				Iterator<T> iter=queue.iterator();

				@Override
				public boolean hasNext(){
					return iter.hasNext();
				}

				@Override
				public T next(){
					return iter.next();
				}

				@Override
				public void remove(){
					throw new UnsupportedOperationException("Unmodifiable queue");
				}
			};
		}

		@Override
		public Object[] toArray(){
			return queue.toArray();
		}

		@Override
		public <T> T[] toArray(T[] a){
			return queue.toArray(a);
		}

		@Override
		public boolean containsAll(Collection<?> col){
			return queue.containsAll(col);
		}

		@Override
		public Maybe<T> push(T elem){
			throw new UnsupportedOperationException("Unmodifiable queue");
		}

		@Override
		public Maybe<T> pop(){
			throw new UnsupportedOperationException("Unmodifiable queue");
		}

		@Override
		public boolean add(T elem){
			throw new UnsupportedOperationException("Unmodifiable queue");
		}

		@Override
		public boolean remove(Object o){
			throw new UnsupportedOperationException("Unmodifiable queue");
		}

		@Override
		public boolean addAll(Collection<? extends T> c){
			throw new UnsupportedOperationException("Unmodifiable queue");
		}

		@Override
		public boolean removeAll(Collection<?> c){
			throw new UnsupportedOperationException("Unmodifiable queue");
		}

		@Override
		public boolean retainAll(Collection<?> c){
			throw new UnsupportedOperationException("Unmodifiable queue");
		}

		@Override
		public void clear(){
			throw new UnsupportedOperationException("Unmodifiable queue");
		}
	}

	private static class SynchronizedCounter<K, V extends Number> implements Counter<K, V>, Serializable{

		private static final long serialVersionUID=1982623946131654815L;
		//**********
		Counter<K, V> counter;
		final Object mutex;
		//**********
		private Set<K> keyset=null;
		private Collection<V> values=null;
		private Set<Entry<K, V>> entryset=null;

		public SynchronizedCounter(Counter<K, V> counter, Object mutex){
			this.counter=counter;
			this.mutex=mutex;
		}

		public SynchronizedCounter(Counter<K, V> counter){
			this.counter=counter;
			this.mutex=this;
		}

		@Override
		public Tuple2<V, V> sum(K key){
			synchronized(mutex){
				return counter.sum(key);
			}
		}

		@Override
		public void sumAll(Collection<? extends K> keys){
			synchronized(mutex){
				counter.sumAll(keys);
			}
		}

		@Override
		public void sumAll(K[] keys){
			synchronized(mutex){
				counter.sumAll(keys);
			}
		}

		@Override
		public Tuple2<V, V> sum(K key, V value){
			synchronized(mutex){
				return counter.sum(key, value);
			}
		}

		@Override
		public void sumAll(Map<? extends K, ? extends V> map){
			synchronized(mutex){
				counter.sumAll(map);
			}
		}

		@Override
		public <X> void sumAll(Map<? extends K, ? extends X> map, Caster<X, V> caster){
			synchronized(mutex){
				counter.sumAll(map, caster);
			}
		}

		@Override
		public void sumAll(Collection<? extends K> keys, V value){
			synchronized(mutex){
				counter.sumAll(keys, value);
			}
		}

		@Override
		public void sumAll(K[] keys, V value){
			synchronized(mutex){
				counter.sumAll(keys, value);
			}
		}

		@Override
		public void sumToAll(){
			synchronized(mutex){
				counter.sumToAll();
			}
		}

		@Override
		public void sumToAll(V value){
			synchronized(mutex){
				counter.sumToAll(value);
			}
		}

		@Override
		public void balancedSum(Collection<? extends K> keys){
			synchronized(mutex){
				counter.balancedSum(keys);
			}
		}

		@Override
		public void balancedSum(K[] keys){
			synchronized(mutex){
				counter.balancedSum(keys);
			}
		}

		@Override
		public void balancedSum(Collection<? extends K> keys, V value){
			synchronized(mutex){
				counter.balancedSum(keys, value);
			}
		}

		@Override
		public void balancedSum(K[] keys, V value){
			synchronized(mutex){
				counter.balancedSum(keys, value);
			}
		}

		@Override
		public void balancedSumToAll(){
			synchronized(mutex){
				counter.balancedSumToAll();
			}
		}

		@Override
		public void balancedSumToAll(V value){
			synchronized(mutex){
				counter.balancedSumToAll(value);
			}
		}

		@Override
		public Tuple2<V, V> deduct(K key){
			synchronized(mutex){
				return counter.deduct(key);
			}
		}

		@Override
		public void deductAll(Collection<? extends K> keys){
			synchronized(mutex){
				counter.deductAll(keys);
			}
		}

		@Override
		public void deductAll(K[] keys){
			synchronized(mutex){
				counter.deductAll(keys);
			}
		}

		@Override
		public Tuple2<V, V> deduct(K key, V value){
			synchronized(mutex){
				return counter.deduct(key, value);
			}
		}

		@Override
		public void deductAll(Map<? extends K, ? extends V> map){
			synchronized(mutex){
				counter.deductAll(map);
			}
		}

		@Override
		public <X> void deductAll(Map<? extends K, ? extends X> map, Caster<X, V> caster){
			synchronized(mutex){
				counter.deductAll(map, caster);
			}
		}

		@Override
		public void deductAll(Collection<? extends K> keys, V value){
			synchronized(mutex){
				counter.deductAll(keys, value);
			}
		}

		@Override
		public void deductAll(K[] keys, V value){
			synchronized(mutex){
				counter.deductAll(keys, value);
			}
		}

		@Override
		public void deductToAll(){
			synchronized(mutex){
				counter.deductToAll();
			}
		}

		@Override
		public void deductToAll(V value){
			synchronized(mutex){
				counter.deductToAll(value);
			}
		}

		@Override
		public void balancedDeduct(Collection<? extends K> keys){
			synchronized(mutex){
				counter.balancedDeduct(keys);
			}
		}

		@Override
		public void balancedDeduct(K[] keys){
			synchronized(mutex){
				counter.balancedDeduct(keys);
			}
		}

		@Override
		public void balancedDeduct(Collection<? extends K> keys, V value){
			synchronized(mutex){
				counter.balancedDeduct(keys, value);
			}
		}

		@Override
		public void balancedDeduct(K[] keys, V value){
			synchronized(mutex){
				counter.balancedDeduct(keys, value);
			}
		}

		@Override
		public void balancedDeductToAll(){
			synchronized(mutex){
				counter.balancedDeductToAll();
			}
		}

		@Override
		public void balancedDeductToAll(V value){
			synchronized(mutex){
				counter.balancedDeductToAll(value);
			}
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> map){
			synchronized(mutex){
				counter.putAll(map);
			}
		}

		@Override
		public <X> void putAll(Map<? extends K, ? extends X> map, Caster<X, V> caster){
			synchronized(mutex){
				counter.putAll(map, caster);
			}
		}

		@Override
		public Tuple2<V, V> putMax(K key, V value){
			synchronized(mutex){
				return counter.putMax(key, value);
			}
		}

		@Override
		public Tuple2<V, V> putMin(K key, V value){
			synchronized(mutex){
				return counter.putMin(key, value);
			}
		}

		@Override
		public int size(){
			synchronized(mutex){
				return counter.size();
			}
		}

		@Override
		public boolean isEmpty(){
			synchronized(mutex){
				return counter.isEmpty();
			}
		}

		@Override
		public boolean containsKey(Object key){
			synchronized(mutex){
				return counter.containsKey(key);
			}
		}

		@Override
		public boolean containsValue(Object value){
			synchronized(mutex){
				return counter.containsValue(value);
			}
		}

		@Override
		public V get(Object key){
			synchronized(mutex){
				return counter.get(key);
			}
		}

		@Override
		public V put(K key, V value){
			synchronized(mutex){
				return counter.put(key, value);
			}
		}

		@Override
		public V remove(Object key){
			synchronized(mutex){
				return counter.remove(key);
			}
		}

		@Override
		public void clear(){
			synchronized(mutex){
				counter.clear();
			}
		}

		@Override
		public Set<K> keySet(){
			synchronized(mutex){
				if(keyset == null){
					keyset=Collections.synchronizedSet(counter.keySet());
				}
				return keyset;
			}
		}

		@Override
		public Collection<V> values(){
			synchronized(mutex){
				if(values == null){
					values=Collections.synchronizedCollection(counter.values());
				}
				return values;
			}
		}

		@Override
		public Set<Entry<K, V>> entrySet(){
			synchronized(mutex){
				if(entryset == null){
					entryset=Collections.synchronizedSet(counter.entrySet());
				}
				return entryset;
			}
		}
	}

	private static class SynchronizedSortedCounter<K, V extends Number> extends SynchronizedCounter<K, V> implements SortedCounter<K, V>{

		private static final long serialVersionUID=-5162398716784325168L;
		//**********
		private Set<K> keyset=null;
		private Set<K> min_keyset=null;
		private Set<K> max_keyset=null;
		private Set<Entry<V, Set<K>>> inverse_entryset=null;

		public SynchronizedSortedCounter(SortedCounter<K, V> counter, Object mutex){
			super(counter, mutex);
		}

		public SynchronizedSortedCounter(SortedCounter<K, V> counter){
			super(counter);
		}

		@Override
		public V maxValue(){
			synchronized(mutex){
				return ((SortedCounter<K, V>)counter).maxValue();
			}
		}

		@Override
		public V minValue(){
			synchronized(mutex){
				return ((SortedCounter<K, V>)counter).minValue();
			}
		}

		@Override
		public Set<K> maxKeySet(){
			synchronized(mutex){
				if(max_keyset == null){
					max_keyset=Collections.synchronizedSet(((SortedCounter<K, V>)counter).maxKeySet());
				}
				return max_keyset;
			}
		}

		@Override
		public Set<K> minKeySet(){
			synchronized(mutex){
				if(min_keyset == null){
					min_keyset=Collections.synchronizedSet(((SortedCounter<K, V>)counter).minKeySet());
				}
				return min_keyset;
			}
		}

		@Override
		public Set<K> keySet(V value){
			synchronized(mutex){
				if(keyset == null){
					keyset=Collections.synchronizedSet(((SortedCounter<K, V>)counter).keySet());
				}
				return keyset;
			}
		}

		@Override
		public Set<Entry<V, Set<K>>> inverseEntrySet(){
			synchronized(mutex){
				if(inverse_entryset == null){
					inverse_entryset=Collections.synchronizedSet(((SortedCounter<K, V>)counter).inverseEntrySet());
				}
				return inverse_entryset;
			}
		}

		@Override
		public SortedCounter<K, V> tailCounter(V from_value){
			synchronized(mutex){
				return new SynchronizedSortedCounter<>(((SortedCounter<K, V>)counter).tailCounter(from_value), mutex);
			}
		}

		@Override
		public SortedCounter<K, V> tailCounter(V from_value, boolean inclusive){
			synchronized(mutex){
				return new SynchronizedSortedCounter<>(((SortedCounter<K, V>)counter).tailCounter(from_value, inclusive), mutex);
			}
		}

		@Override
		public SortedCounter<K, V> headCounter(V to_value){
			synchronized(mutex){
				return new SynchronizedSortedCounter<>(((SortedCounter<K, V>)counter).headCounter(to_value), mutex);
			}
		}

		@Override
		public SortedCounter<K, V> headCounter(V to_value, boolean inclusive){
			synchronized(mutex){
				return new SynchronizedSortedCounter<>(((SortedCounter<K, V>)counter).headCounter(to_value, inclusive), mutex);
			}
		}

		@Override
		public SortedCounter<K, V> subCounter(V from_value, V to_value){
			synchronized(mutex){
				return new SynchronizedSortedCounter<>(((SortedCounter<K, V>)counter).subCounter(from_value, to_value), mutex);
			}
		}

		@Override
		public SortedCounter<K, V> subCounter(V from_value, boolean from_inclusive, V to_value, boolean to_inclusive){
			synchronized(mutex){
				return new SynchronizedSortedCounter<>(((SortedCounter<K, V>)counter).subCounter(from_value, from_inclusive, to_value, to_inclusive), mutex);
			}
		}
	}

	private static class SynchronizedTree<T> implements Tree<T>, Serializable{

		private static final long serialVersionUID=4321673216498431349L;
		//**********
		Tree<T> tree;
		final Object mutex;

		public SynchronizedTree(Tree<T> tree){
			this.tree=tree;
			this.mutex=this;
		}

		public SynchronizedTree(Tree<T> tree, Object mutex){
			this.tree=tree;
			this.mutex=mutex;
		}

		@Override
		public boolean nullsAllowed(){
			synchronized(mutex){
				return tree.nullsAllowed();
			}
		}

		@Override
		public int maxSons(){
			synchronized(mutex){
				return tree.maxSons();
			}
		}

		@Override
		public Tree<T> toTree(TreeNode node){
			synchronized(mutex){
				return tree.toTree(node);
			}
		}

		@Override
		public void trim(){
			synchronized(mutex){
				tree.trim();
			}
		}

		@Override
		public TreeNode push(T elem){
			synchronized(mutex){
				return tree.push(elem);
			}
		}

		@Override
		public void pushAll(Collection<? extends T> col){
			synchronized(mutex){
				tree.pushAll(col);
			}
		}

		@Override
		public Maybe<T> pop(){
			synchronized(mutex){
				return tree.pop();
			}
		}

		@Override
		public boolean remove(Object obj){
			synchronized(mutex){
				return tree.remove(obj);
			}
		}

		@Override
		public boolean removeAll(Collection<?> col){
			synchronized(mutex){
				return tree.removeAll(col);
			}
		}

		@Override
		public Maybe<TreeNode> root(){
			synchronized(mutex){
				return tree.root();
			}
		}

		@Override
		public void clear(){
			synchronized(mutex){
				tree.clear();
			}
		}

		@Override
		public Maybe<T> getValue(TreeNode node){
			synchronized(mutex){
				return tree.getValue(node);
			}
		}

		@Override
		public Maybe<T> setValue(TreeNode node, T value){
			synchronized(mutex){
				return tree.setValue(node, value);
			}
		}

		@Override
		public boolean add(TreeNode node, T value){
			synchronized(mutex){
				return tree.add(node, value);
			}
		}

		@Override
		public boolean addAll(TreeNode node, Collection<? extends T> col){
			synchronized(mutex){
				return tree.addAll(node, col);
			}
		}

		@Override
		public boolean contains(Object obj){
			synchronized(mutex){
				return tree.contains(obj);
			}
		}

		@Override
		public boolean containsAll(Collection<?> col){
			synchronized(mutex){
				return tree.containsAll(col);
			}
		}

		@Override
		public boolean contains(TreeNode node, Object obj){
			synchronized(mutex){
				return tree.contains(node, obj);
			}
		}

		@Override
		public boolean containsChild(TreeNode node, Object obj){
			synchronized(mutex){
				return tree.containsChild(node, obj);
			}
		}

		@Override
		public boolean containsDescendant(TreeNode node, Object obj){
			synchronized(mutex){
				return tree.containsDescendant(node, obj);
			}
		}

		@Override
		public boolean containsAll(TreeNode node, Collection<?> col){
			synchronized(mutex){
				return tree.containsAll(node, col);
			}
		}

		@Override
		public boolean containsAllChildren(TreeNode node, Collection<?> col){
			synchronized(mutex){
				return tree.containsAllChildren(node, col);
			}
		}

		@Override
		public boolean containsAllDescendants(TreeNode node, Collection<?> col){
			synchronized(mutex){
				return tree.containsAllDescendants(node, col);
			}
		}

		@Override
		public boolean isEmpty(){
			synchronized(mutex){
				return tree.isEmpty();
			}
		}

		@Override
		public boolean hasChildren(TreeNode node){
			synchronized(mutex){
				return tree.hasChildren(node);
			}
		}

		@Override
		public boolean isFull(TreeNode node){
			synchronized(mutex){
				return tree.isFull(node);
			}
		}

		@Override
		public boolean remove(TreeNode node, Object obj){
			synchronized(mutex){
				return tree.remove(node, obj);
			}
		}

		@Override
		public Maybe<T> remove(TreeNode node, int index){
			synchronized(mutex){
				return tree.remove(node, index);
			}
		}

		@Override
		public void removeSons(TreeNode node){
			synchronized(mutex){
				tree.removeSons(node);
			}
		}

		@Override
		public boolean removeAll(TreeNode node, Collection<?> col){
			synchronized(mutex){
				return tree.removeAll(node, col);
			}
		}

		@Override
		public boolean retainAll(TreeNode node, Collection<?> col){
			synchronized(mutex){
				return tree.retainAll(node, col);
			}
		}

		@Override
		public int height(){
			synchronized(mutex){
				return tree.height();
			}
		}

		@Override
		public int height(TreeNode node){
			synchronized(mutex){
				return tree.height(node);
			}
		}

		@Override
		public int depth(TreeNode node){
			synchronized(mutex){
				return tree.depth(node);
			}
		}

		@Override
		public int size(){
			synchronized(mutex){
				return tree.size();
			}
		}

		@Override
		public int size(TreeNode node){
			synchronized(mutex){
				return tree.size(node);
			}
		}

		@Override
		public int childrenSize(TreeNode node){
			synchronized(mutex){
				return tree.childrenSize(node);
			}
		}

		@Override
		public Iterable<TreeNode> children(TreeNode node){
			synchronized(mutex){
				return tree.children(node);
			}
		}

		@Override
		public List<TreeNode> childrenCopy(TreeNode node){
			synchronized(mutex){
				return tree.childrenCopy(node);
			}
		}

		@Override
		public Maybe<TreeNode> getChild(TreeNode node, int index){
			synchronized(mutex){
				return tree.getChild(node, index);
			}
		}

		@Override
		public Maybe<TreeNode> parent(TreeNode node){
			synchronized(mutex){
				return tree.parent(node);
			}
		}

		@Override
		public List<Maybe<T>> postOrderDeepFirstTraversal(){
			synchronized(mutex){
				return tree.postOrderDeepFirstTraversal();
			}
		}

		@Override
		public List<Maybe<T>> preOrderDeepFirstTraversal(){
			synchronized(mutex){
				return tree.preOrderDeepFirstTraversal();
			}
		}

		@Override
		public List<Maybe<T>> breedFirstTraversal(){
			synchronized(mutex){
				return tree.breedFirstTraversal();
			}
		}

		@Override
		public Maybe<TreeNode> postOrderDeepFirstSearch(T value){
			synchronized(mutex){
				return tree.postOrderDeepFirstSearch(value);
			}
		}

		@Override
		public Maybe<TreeNode> preOrderDeepFirstSearch(T value){
			synchronized(mutex){
				return tree.preOrderDeepFirstSearch(value);
			}
		}

		@Override
		public Maybe<TreeNode> breedFirstSearch(T value){
			synchronized(mutex){
				return tree.breedFirstSearch(value);
			}
		}

		@Override
		public List<Maybe<T>> postOrderDeepFirstTraversal(TreeNode node){
			synchronized(mutex){
				return tree.postOrderDeepFirstTraversal(node);
			}
		}

		@Override
		public List<Maybe<T>> preOrderDeepFirstTraversal(TreeNode node){
			synchronized(mutex){
				return tree.preOrderDeepFirstTraversal(node);
			}
		}

		@Override
		public List<Maybe<T>> breedFirstTraversal(TreeNode node){
			synchronized(mutex){
				return tree.breedFirstTraversal(node);
			}
		}

		@Override
		public Maybe<TreeNode> postOrderDeepFirstSearch(TreeNode node, T value){
			synchronized(mutex){
				return tree.postOrderDeepFirstSearch(node, value);
			}
		}

		@Override
		public Maybe<TreeNode> preOrderDeepFirstSearch(TreeNode node, T value){
			synchronized(mutex){
				return tree.preOrderDeepFirstSearch(node, value);
			}
		}

		@Override
		public Maybe<TreeNode> breedFirstSearch(TreeNode node, T value){
			synchronized(mutex){
				return tree.breedFirstSearch(node, value);
			}
		}
	}

	private static class SynchronizedBinaryTree<T> extends SynchronizedTree<T> implements BinaryTree<T>{

		private static final long serialVersionUID=4506521387995421301L;

		public SynchronizedBinaryTree(BinaryTree<T> tree){
			super(tree);
		}

		@Override
		public List<T> inOrderDeepFirstTraversal(TreeNode node){
			synchronized(mutex){
				return ((BinaryTree<T>)tree).inOrderDeepFirstTraversal(node);
			}
		}

		@Override
		public Maybe<TreeNode> inOrderDeepFirstSearch(TreeNode node, T value){
			synchronized(mutex){
				return ((BinaryTree<T>)tree).inOrderDeepFirstSearch(node, value);
			}
		}

		@Override
		public Maybe<TreeNode> left(TreeNode node){
			synchronized(mutex){
				return ((BinaryTree<T>)tree).left(node);
			}
		}

		@Override
		public Maybe<TreeNode> right(TreeNode node){
			synchronized(mutex){
				return ((BinaryTree<T>)tree).right(node);
			}
		}
	}

	private static class SynchronizedFixedCapacityQueue<T> implements FixedCapacityQueue<T>, Serializable{

		private static final long serialVersionUID=7032878403121147486L;
		//**********
		FixedCapacityQueue<T> queue;
		final Object mutex;

		public SynchronizedFixedCapacityQueue(FixedCapacityQueue<T> queue){
			this.queue=queue;
			this.mutex=this;
		}

		public SynchronizedFixedCapacityQueue(FixedCapacityQueue<T> queue, Object mutex){
			this.queue=queue;
			this.mutex=mutex;
		}

		@Override
		public Maybe<T> push(T elem){
			synchronized(mutex){
				return queue.push(elem);
			}
		}

		@Override
		public Maybe<T> pop(){
			synchronized(mutex){
				return queue.pop();
			}
		}

		@Override
		public Maybe<T> peek(){
			synchronized(mutex){
				return queue.peek();
			}
		}

		@Override
		public int capacity(){
			synchronized(mutex){
				return queue.capacity();
			}
		}

		@Override
		public boolean isFull(){
			synchronized(mutex){
				return queue.isFull();
			}
		}

		@Override
		public boolean add(T elem){
			synchronized(mutex){
				return queue.add(elem);
			}
		}

		@Override
		public int size(){
			synchronized(mutex){
				return queue.size();
			}
		}

		@Override
		public boolean isEmpty(){
			synchronized(mutex){
				return queue.isEmpty();
			}
		}

		@Override
		public boolean contains(Object obj){
			synchronized(mutex){
				return queue.contains(obj);
			}
		}

		@Override
		public Iterator<T> iterator(){
			synchronized(mutex){
				return new Iterator<T>(){

					private Iterator<T> iter=queue.iterator();

					@Override
					public boolean hasNext(){
						synchronized(mutex){
							return iter.hasNext();
						}
					}

					@Override
					public T next(){
						synchronized(mutex){
							return iter.next();
						}
					}

					@Override
					public void remove(){
						synchronized(mutex){
							iter.remove();
						}
					}
				};
			}
		}

		@Override
		public Object[] toArray(){
			synchronized(mutex){
				return queue.toArray();
			}
		}

		@Override
		public <T> T[] toArray(T[] a){
			synchronized(mutex){
				return queue.toArray(a);
			}
		}

		@Override
		public boolean remove(Object o){
			synchronized(mutex){
				return queue.remove(o);
			}
		}

		@Override
		public boolean containsAll(Collection<?> c){
			synchronized(mutex){
				return queue.containsAll(c);
			}
		}

		@Override
		public boolean addAll(Collection<? extends T> c){
			synchronized(mutex){
				return queue.addAll(c);
			}
		}

		@Override
		public boolean removeAll(Collection<?> c){
			synchronized(mutex){
				return queue.removeAll(c);
			}
		}

		@Override
		public boolean retainAll(Collection<?> c){
			synchronized(mutex){
				return queue.retainAll(c);
			}
		}

		@Override
		public void clear(){
			synchronized(mutex){
				queue.clear();
			}
		}
	}
}
