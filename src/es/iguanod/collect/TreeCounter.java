package es.iguanod.collect;

import es.iguanod.collect.HashCounter.HashCounterBuilder;
import es.iguanod.util.Caster;
import es.iguanod.util.tuples.Tuple2;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.AbstractCollection;
import java.util.AbstractMap.SimpleEntry;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class TreeCounter<K, V extends Number> extends AbstractSortedCounter<K, V>{

	private static final long serialVersionUID=652833293132954066L;
	//************
	private HashCounter<K, V> lookup;
	private NavigableMap<V, Set<K>> map;
	private boolean reverse;
	//************
	private Set<K> backed_keyset;
	private Collection<V> backed_values;
	private TreeCounterEntrySet backed_entryset;
	private Set<Entry<V, Set<K>>> backed_inverse_entryset;

	public static class TreeCounterBuilder<K, V extends Number> implements Serializable{

		private static final long serialVersionUID=897813998949064952L;
		private final Caster<BigDecimal, V> caster;
		private Comparator<? super V> comparator=null;
		private Map<? extends K, ? extends V> initial_map=null;
		private boolean reverse=false;
		private HashCounterBuilder<K, V> lookup_builder=null;

		public TreeCounterBuilder(Caster<BigDecimal, V> caster){
			this.caster=caster;
		}

		public TreeCounterBuilder<K, V> reverse(boolean reverse){
			this.reverse=reverse;
			return this;
		}

		public TreeCounterBuilder<K, V> setComparator(Comparator<? super V> comparator){
			this.comparator=comparator;
			return this;
		}

		public TreeCounterBuilder<K, V> setInitialMappings(Map<? extends K, ? extends V> map){
			this.initial_map=map;
			return this;
		}

		protected TreeCounterBuilder<K, V> setLookupBuilder(HashCounterBuilder<K, V> lookup_builder){
			this.lookup_builder=lookup_builder;
			return this;
		}

		public TreeCounter<K, V> build(){
			return new TreeCounter<>(this);
		}
	}

	private static <K, V extends Number> Comparator<? super V> preprocessComparator(TreeCounterBuilder<K, V> builder){
		if(builder.reverse){
			builder.comparator=Collections.reverseOrder(builder.comparator);
		}
		return builder.comparator;
	}

	protected TreeCounter(TreeCounterBuilder<K, V> builder){
		super(builder.caster, preprocessComparator(builder));
		if(builder.lookup_builder == null){
			lookup=new HashCounterBuilder<K, V>(this.caster).setComparator(this.comparator).setInitialMappings(builder.initial_map).build();
		}else{
			lookup=builder.lookup_builder.setComparator(this.comparator).setInitialMappings(builder.initial_map).build();
		}
		this.reverse=builder.reverse;
		map=new TreeMap<>(this.comparator);
		if(builder.initial_map != null){
			for(Entry<? extends K, ? extends V> entry:builder.initial_map.entrySet()){
				addToMap(entry.getKey(), entry.getValue());
			}
		}
	}

	private TreeCounter(TreeCounter<K, V> counter, NavigableMap<V, Set<K>> map){
		super(counter.caster, counter.comparator);
		this.lookup=counter.lookup;
		this.reverse=counter.reverse;
		this.map=map;
	}

	private TreeCounter(TreeCounter<K, V> counter){
		super(counter.caster, counter.comparator);
		this.lookup=counter.lookup;
		this.reverse=counter.reverse;
		this.map=counter.map;
	}

	private void addToMap(final K key, V value){
		if(value == null){
			return;
		}
		if(map.containsKey(value)){
			map.get(value).add(key);
		}else{
			Set<K> set=new HashSet<>();
			set.add(key);
			map.put(value, set);
		}
	}

	private void removeFromMap(K key, V value){
		if(value == null){
			return;
		}
		Set<K> oldset=map.get(value);
		if(oldset.size() == 1){
			map.remove(value);
		}else{
			oldset.remove(key);
		}
	}

	private void changeMap(K key, V oldvalue, V newvalue){
		if(!equals(oldvalue, newvalue)){
			removeFromMap(key, oldvalue);
			addToMap(key, newvalue);
		}
	}

	@Override
	public Tuple2<V, V> sum(K key, V value){
		Tuple2<V, V> ret=lookup.sum(key, value);
		changeMap(key, ret.getFirst(), ret.getSecond());
		return ret;
	}

	@Override
	public Tuple2<V, V> deduct(K key, V value){
		Tuple2<V, V> ret=lookup.deduct(key, value);
		changeMap(key, ret.getFirst(), ret.getSecond());
		return ret;
	}

	@Override
	public void sumToAll(V value){
		TreeCounterEntrySetIterator iter=entrySet().iterator();
		while(iter.hasNext()){
			Entry<K, V> entry=iter.next();
			entry.setValue(caster.cast(new BigDecimal(entry.getValue().toString()).add(new BigDecimal(value.toString()))));
		}
		iter.commitChanges();
	}

	@Override
	public int size(){
		return lookup.size();
	}

	@Override
	public boolean isEmpty(){
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key){
		return lookup.containsKey(key);
	}

	@Override
	@SuppressWarnings("element-type-mismatch")
	public boolean containsValue(Object value){
		return map.containsKey(value);
	}

	@Override
	public V get(Object key){
		return lookup.get(key);
	}

	@Override
	public V put(K key, V value){
		V oldvalue=lookup.put(key, value);
		changeMap(key, oldvalue, value);
		return oldvalue;
	}

	@Override
	public V remove(Object key){
		V oldvalue=lookup.remove(key);
		if(oldvalue != null){
			removeFromMap((K)key, oldvalue);
		}
		return oldvalue;
	}

	@Override
	public void clear(){
		lookup.clear();
		map.clear();
	}

	@Override
	public V maxValue(){
		if(reverse){
			return map.firstKey();
		}else{
			return map.lastKey();
		}
	}

	@Override
	public V minValue(){
		if(reverse){
			return map.lastKey();
		}else{
			return map.firstKey();
		}
	}

	@Override
	public Set<K> keySet(){
		if(backed_keyset == null){
			backed_keyset=new BackedKeySet();
		}
		return backed_keyset;
	}

	@Override
	public Set<K> keySet(V value){
		return new BackedKeySetValue(value);
	}

	@Override
	public Collection<V> values(){
		if(backed_values == null){
			backed_values=new BackedValues();
		}
		return backed_values;
	}

	@Override
	public TreeCounterEntrySet<K, V> entrySet(){
		if(backed_entryset == null){
			backed_entryset=new TreeCounterEntrySet<>(this);
		}
		return backed_entryset;
	}

	@Override
	public Set<Entry<V, Set<K>>> inverseEntrySet(){
		if(backed_inverse_entryset == null){
			backed_inverse_entryset=new BackedInverseEntrySet();
		}
		return backed_inverse_entryset;
	}

	@Override
	public SortedCounter<K, V> tailCounter(V from_value, boolean inclusive){
		return new SubTreeCounter<>(this, map.tailMap(from_value, inclusive), true, inclusive, from_value, false, false, null);
	}

	@Override
	public SortedCounter<K, V> headCounter(V to_value, boolean inclusive){
		return new SubTreeCounter<>(this, map.headMap(to_value, inclusive), false, false, null, true, inclusive, to_value);
	}

	@Override
	public SortedCounter<K, V> subCounter(V from_value, boolean from_inclusive, V to_value, boolean to_inclusive){
		return new SubTreeCounter<>(this, map.subMap(from_value, from_inclusive, to_value, to_inclusive), true, from_inclusive, from_value, true, to_inclusive, to_value);
	}

	private static abstract class AbstractBackedCollection<T> extends AbstractCollection<T> implements Serializable{

		private static final long serialVersionUID=546856254923595412L;
		//************
		private TreeCounter counter;

		public AbstractBackedCollection(TreeCounter counter){
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
		public void clear(){
			for(Iterator iter=counter.inverseEntrySet().iterator(); iter.hasNext();){
				iter.next();
				iter.remove();
			}
		}

		@Override
		public boolean equals(Object obj){
			if(this instanceof Set){
				if(obj == this)
					return true;

				if(!(obj instanceof Set))
					return false;
				Collection c=(Collection)obj;
				if(c.size() != size())
					return false;
				try{
					return containsAll(c);
				}catch(ClassCastException | NullPointerException unused){
					return false;
				}
			}else{
				return super.equals(obj);
			}
		}

		@Override
		public int hashCode(){
			if(this instanceof Set){
				int h=0;
				Iterator<T> i=iterator();
				while(i.hasNext()){
					T obj=i.next();
					if(obj != null)
						h+=obj.hashCode();
				}
				return h;
			}else{
				return super.hashCode();
			}
		}
	}

	private class BackedKeySet extends AbstractBackedCollection<K> implements Set<K>{

		private static final long serialVersionUID=649219593239652L;

		public BackedKeySet(){
			super(TreeCounter.this);
		}

		@Override
		@SuppressWarnings("element-type-mismatch")
		public boolean remove(Object key){
			V oldvalue=lookup.get(key);
			if(oldvalue == null || !map.containsKey(oldvalue)){
				return false;
			}
			lookup.remove(key);
			removeFromMap((K)key, oldvalue);
			return true;
		}

		@Override
		@SuppressWarnings("element-type-mismatch")
		public boolean contains(Object obj){
			return TreeCounter.this.containsKey(obj);
		}

		@Override
		public Iterator<K> iterator(){
			return new Iterator<K>(){
				private Iterator<Set<K>> iter=map.values().iterator();
				private Iterator<K> set_iter=null;
				private K last=null;

				@Override
				public boolean hasNext(){
					return iter.hasNext() || (set_iter != null && set_iter.hasNext());
				}

				@Override
				public K next(){
					if(set_iter == null || !set_iter.hasNext()){
						if(!iter.hasNext()){
							throw new NoSuchElementException();
						}
						set_iter=iter.next().iterator();
					}
					last=set_iter.next();
					return last;
				}

				@Override
				public void remove(){
					if(last == null){
						throw new IllegalStateException();
					}
					if(map.get(lookup.get(last)).size() == 1){
						iter.remove();
					}else{
						set_iter.remove();
					}
					lookup.remove(last);
					last=null;
				}
			};
		}
	}

	private class BackedKeySetValue extends AbstractSet<K> implements Serializable{

		private static final long serialVersionUID=-515132984986215859L;
		//************
		private V value;

		public BackedKeySetValue(V value){
			this.value=value;
		}

		@Override
		public int size(){
			Set<K> set=map.get(value);
			return set == null?0:set.size();
		}

		@Override
		public boolean isEmpty(){
			return map.get(value) == null;
		}

		@Override
		public boolean contains(Object obj){
			Set<K> set=map.get(value);
			return set == null?false:set.contains(obj);
		}

		@Override
		public void clear(){
			if(map.containsKey(value)){
				map.remove(value);
				for(Iterator<Entry<K, V>> iter=lookup.entrySet().iterator(); iter.hasNext();){
					Entry<K, V> entry=iter.next();
					if(entry.getValue().equals(value)){
						iter.remove();
					}
				}
			}
		}

		@Override
		@SuppressWarnings("element-type-mismatch")
		public boolean remove(Object key){
			Set<K> set=map.get(value);
			if(set == null || !set.contains(key)){
				return false;
			}
			lookup.remove(key);
			set.remove(key);
			return true;
		}

		@Override
		public Iterator<K> iterator(){
			return new Iterator<K>(){
				private Iterator<K> iter=null;
				private K last=null;

				{
					Set<K> set=map.get(value);
					if(set != null){
						iter=set.iterator();
					}
				}

				@Override
				public boolean hasNext(){
					return iter != null && iter.hasNext();
				}

				@Override
				public K next(){
					if(iter == null || !iter.hasNext()){
						throw new NoSuchElementException();
					}
					last=iter.next();
					return last;
				}

				@Override
				public void remove(){
					if(last == null){
						throw new IllegalStateException();
					}
					iter.remove();
					if(map.get(value).isEmpty()){
						map.remove(value);
					}
					lookup.remove(last);
					last=null;
				}
			};
		}
	}

	private class BackedValues extends AbstractBackedCollection<V>{

		private static final long serialVersionUID=-491695422698469246L;

		public BackedValues(){
			super(TreeCounter.this);
		}

		@Override
		@SuppressWarnings("element-type-mismatch")
		public boolean contains(Object obj){
			return map.containsKey(obj);
		}

		@Override
		@SuppressWarnings("element-type-mismatch")
		public boolean remove(Object obj){
			try{
				Set<K> set=map.get(obj);
				if(set == null){
					return false;
				}
				Iterator<K> iter=set.iterator();
				lookup.remove(iter.next());
				iter.remove();
				if(set.isEmpty()){
					map.remove(obj);
				}
				return true;
			}catch(ClassCastException cce){
				return false;
			}
		}

		@Override
		public Iterator<V> iterator(){
			return new Iterator<V>(){
				private Iterator<Entry<V, Set<K>>> iter=map.entrySet().iterator();
				private Iterator<K> set_iter;
				private V last_V=null;
				private K last_K=null;

				{
					if(iter.hasNext()){
						Entry<V, Set<K>> next=iter.next();
						last_V=next.getKey();
						set_iter=next.getValue().iterator();
					}
				}

				@Override
				public boolean hasNext(){
					return set_iter.hasNext() || iter.hasNext();
				}

				@Override
				public V next(){
					if(!set_iter.hasNext()){
						if(!iter.hasNext()){
							throw new NoSuchElementException();
						}
						Entry<V, Set<K>> next=iter.next();
						last_V=next.getKey();
						set_iter=next.getValue().iterator();
					}
					last_K=set_iter.next();
					return last_V;
				}

				@Override
				public void remove(){
					if(last_K == null){
						throw new IllegalStateException();
					}
					set_iter.remove();
					lookup.remove(last_K);
					if(map.get(last_V).isEmpty()){
						iter.remove();
					}
					last_K=null;
				}
			};
		}
	}

	public static class TreeCounterEntrySet<K, V extends Number> extends AbstractBackedCollection<Entry<K, V>> implements Set<Entry<K, V>>{

		private static final long serialVersionUID=952302930350005410L;
		//************
		private TreeCounter<K, V> counter;

		TreeCounterEntrySet(TreeCounter<K, V> counter){
			super(counter);
			this.counter=counter;
		}

		@Override
		@SuppressWarnings("element-type-mismatch")
		public boolean contains(Object obj){
			if(!(obj instanceof Entry)){
				return false;
			}
			Set<K> set=counter.map.get(((Entry)obj).getValue());
			return set != null && set.contains(((Entry)obj).getKey());
		}

		@Override
		@SuppressWarnings("element-type-mismatch")
		public boolean remove(Object obj){
			if(!(obj instanceof Entry)){
				return false;
			}
			Set<K> set=counter.map.get(((Entry)obj).getValue());
			if(set == null || !set.contains(((Entry)obj).getKey())){
				return false;
			}
			if(set.size() == 1){
				counter.map.remove(((Entry)obj).getValue());
			}else{
				set.remove(((Entry)obj).getKey());
			}
			counter.lookup.remove(((Entry)obj).getKey());
			return true;
		}

		@Override
		public TreeCounterEntrySetIterator<K, V> iterator(){
			return new TreeCounterEntrySetIterator(counter);
		}
	}

	public static class TreeCounterEntrySetIterator<K, V extends Number> implements Iterator<Entry<K, V>>{

		private TreeCounter<K, V> counter;
		private Iterator<K> set_iter;
		private Iterator<Entry<V, Set<K>>> iter;
		private K last=null;
		private Collection<Entry<K, V>> changes;

		public TreeCounterEntrySetIterator(TreeCounter<K, V> counter){
			this.counter=counter;
			iter=counter.map.entrySet().iterator();
			if(iter.hasNext()){
				set_iter=iter.next().getValue().iterator();
			}else{
				set_iter=null;
			}
			changes=new ArrayList<>();
		}

		@Override
		public boolean hasNext(){
			return set_iter != null && (iter.hasNext() || set_iter.hasNext());
		}

		@Override
		public Entry<K, V> next(){
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			if(!set_iter.hasNext()){
				set_iter=iter.next().getValue().iterator();
			}
			last=set_iter.next();
			return new SimpleEntry<K, V>(last, counter.lookup.get(last)){
				private static final long serialVersionUID=-510676230354982036L;

				@Override
				public V setValue(V value) throws UnsupportedOperationException{
					changes.add(this);
					return super.setValue(value);
				}
			};
		}

		@Override
		public void remove(){
			if(last == null){
				throw new IllegalStateException();
			}
			set_iter.remove();
			if(counter.map.get(counter.lookup.get(last)).isEmpty()){
				iter.remove();
			}
			counter.lookup.remove(last);
			last=null;
		}

		public void commitChanges(){
			if(changes != null){
				for(Entry<K, V> entry:changes){
					counter.put(entry.getKey(), entry.getValue());
				}
				changes=null;
			}
		}
	}

	private class BackedInverseEntrySet extends AbstractBackedCollection<Entry<V, Set<K>>> implements Set<Entry<V, Set<K>>>{
		
		private static final long serialVersionUID=235548298362812687L;

		public BackedInverseEntrySet(){
			super(TreeCounter.this);
		}

		@Override
		public boolean contains(Object o){
			return TreeCounter.this.map.entrySet().contains(o);
		}

		@Override
		public Iterator<Entry<V, Set<K>>> iterator(){
			return new Iterator<Entry<V, Set<K>>>(){
				private Iterator<Entry<V, Set<K>>> iter=TreeCounter.this.map.entrySet().iterator();
				private V last=null;

				@Override
				public boolean hasNext(){
					return iter.hasNext();
				}

				@Override
				public Entry<V, Set<K>> next(){
					if(!hasNext()){
						throw new NoSuchElementException();
					}
					Entry<V, Set<K>> next=iter.next();
					last=next.getKey();
					return new SimpleEntry<V, Set<K>>(next.getKey(), Collections.unmodifiableSet(next.getValue())){
						private static final long serialVersionUID=1629618630361929L;

						@Override
						public Set<K> setValue(Set<K> value) throws UnsupportedOperationException{
							throw new UnsupportedOperationException("TreeCounter inverseEntrySet Entries cannot be modified");
						}
					};
				}

				@Override
				public void remove(){
					if(last == null){
						throw new IllegalStateException();
					}
					last=null;
					iter.remove();
					for(Iterator<Entry<K, V>> it=lookup.entrySet().iterator(); it.hasNext();){
						Entry<K, V> entry=it.next();
						if(entry.getValue().equals(last)){
							it.remove();
						}
					}
				}
			};
		}
	}

	private static class SubTreeCounter<K, V extends Number> extends TreeCounter<K, V>{

		private static final long serialVersionUID=481922560589323254L;
		//************
		private boolean use_from;
		private boolean use_to;
		private boolean from_inc;
		private boolean to_inc;
		private V from;
		private V to;
		private boolean empty_size;

		public SubTreeCounter(TreeCounter<K, V> counter, NavigableMap<V, Set<K>> map, boolean use_from, boolean from_inc, V from, boolean use_to, boolean to_inc, V to){
			super(counter, map);
			this.use_from=use_from;
			this.use_to=use_to;
			this.from_inc=from_inc;
			this.to_inc=to_inc;
			this.from=from;
			this.to=to;
			calculateEmptySize();
		}

		public SubTreeCounter(SubTreeCounter<K, V> counter, boolean use_from, boolean from_inc, V from, boolean use_to, boolean to_inc, V to){
			super(counter);

			if(counter.use_from){
				this.use_from=true;
				if(use_from){
					int cmp=compare(counter.from, from);
					if(cmp == 0){
						this.from=counter.from;
						this.from_inc=counter.from_inc && from_inc;
					}else if(cmp < 0){
						this.from=from;
						this.from_inc=from_inc;
					}else if(cmp > 0){
						this.from=counter.from;
						this.from_inc=counter.from_inc;
					}
				}else{
					this.from=counter.from;
					this.from_inc=counter.from_inc;
				}
			}else if(use_from){
				this.use_from=true;
				this.from=from;
				this.from_inc=from_inc;
			}else{
				this.use_from=false;
			}

			if(counter.use_to){
				this.use_to=true;
				if(use_to){
					int cmp=compare(counter.to, to);
					if(cmp == 0){
						this.to=counter.to;
						this.to_inc=counter.to_inc && to_inc;
					}else if(cmp > 0){
						this.to=to;
						this.to_inc=from_inc;
					}else if(cmp < 0){
						this.to=counter.to;
						this.to_inc=counter.to_inc;
					}
				}else{
					this.to=counter.to;
					this.to_inc=counter.to_inc;
				}
			}else if(use_to){
				this.use_to=true;
				this.to=to;
				this.to_inc=to_inc;
			}else{
				this.use_to=false;
			}

			calculateEmptySize();

			if(empty_size){
				this.use_from=false;
				this.use_to=false;
				super.map=new TreeMap<>();
			}else{
				if(this.use_from){
					if(this.use_to){
						super.map=super.map.subMap(this.from, this.from_inc, this.to, this.to_inc);
					}else{
						super.map=super.map.tailMap(this.from, this.from_inc);
					}
				}else if(use_to){
					super.map=super.map.headMap(this.to, this.to_inc);
				}
			}
		}

		private void calculateEmptySize(){
			if(use_from && use_to){
				int cmp=compare(from, to);
				if((cmp == 0 && (!from_inc || !to_inc)) || cmp > 0){
					empty_size=true;
				}
			}
		}

		private boolean inLimits(V value){

			if(use_from){
				if(from_inc){
					if(compare(value, from) < 0){
						return false;
					}
				}else{
					if(compare(value, from) <= 0){
						return false;
					}
				}
			}

			if(use_to){
				if(to_inc){
					if(compare(value, from) > 0){
						return false;
					}
				}else{
					if(compare(value, from) >= 0){
						return false;
					}
				}
			}

			return true;
		}

		@Override
		public Tuple2<V, V> sum(K key, V value){
			throw new UnsupportedOperationException("Sub-TreeCounters items cannot be modified directly");
		}

		@Override
		public Tuple2<V, V> deduct(K key, V value){
			throw new UnsupportedOperationException("Sub-TreeCounters items cannot be modified directly");
		}

		@Override
		public V put(K key, V value){
			throw new UnsupportedOperationException("Sub-TreeCounters items cannot be modified directly");
		}

		@Override
		@SuppressWarnings("element-type-mismatch")
		public V remove(Object key){
			V value=super.lookup.get(key);
			if(value == null || !inLimits((V)value)){
				return null;
			}
			return super.remove(key);
		}

		@Override
		public void clear(){
			inverseEntrySet().clear();
		}

		/**
		 * WARNING: linear in the number of values
		 *
		 * @return
		 */
		@Override
		public int size(){
			long size=0;
			for(Set<K> set:super.map.values()){
				size+=set.size();
				if(size > Integer.MAX_VALUE){
					return Integer.MAX_VALUE;
				}
			}
			return (int)size;
		}

		@Override
		@SuppressWarnings("element-type-mismatch")
		public boolean containsKey(Object key){
			return super.containsKey(key) && inLimits(super.get(key));
		}

		@Override
		public V get(Object key){
			V ret=super.get(key);
			return ret != null && inLimits(ret)?ret:null;
		}

		@Override
		public SortedCounter<K, V> tailCounter(V from_value, boolean inclusive){
			return new SubTreeCounter<>(this, true, inclusive, from_value, false, false, null);
		}

		@Override
		public SortedCounter<K, V> headCounter(V to_value, boolean inclusive){
			return new SubTreeCounter<>(this, false, false, null, true, inclusive, to_value);
		}

		@Override
		public SortedCounter<K, V> subCounter(V from_value, boolean from_inclusive, V to_value, boolean to_inclusive){
			return new SubTreeCounter<>(this, true, from_inclusive, from_value, true, to_inclusive, to_value);
		}
	}
}