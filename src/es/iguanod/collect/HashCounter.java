/*
 * -------------------- DO NOT REMOVE OR MODIFY THIS HEADER --------------------
 * 
 * Copyright (C) 2014 The Iguanod Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * A copy of the License should have been provided along with this file, usually
 * under the name "LICENSE.txt". If that is not the case you may obtain a
 * copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.iguanod.collect;

import es.iguanod.util.Caster;
import es.iguanod.util.tuples.Tuple2;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Hash table based implementation of the {@code Counter} interface. This
 * implementation provides all of the optional {@code Counter} operations, and
 * permits the {@code null} key (the {@code Counter} stipulates no {@code null}
 * values are allowed). The {@code HashCounter} class is the {@code Counter}
 * equivalent to a {@link java.util.HashMap}. This class makes no guarantees as
 * to the order of the counter; in particular, it does not guarantee that the
 * order will remain constant over time.
 *
 * <p>An instance of {@code HashCounter} has two parameters that affect its
 * performance: initial capacity and load factor. To know more about these
 * parameters, please read the documentation of the {@link java.util.HashMap}
 * class.</p>
 *
 * <p>{@code HashCounter} provides no public constructors. Instead, objects are
 * instantiated with a builder,
 * {@link HashCounterBuilder HashCounterBuilder}.</p>
 *
 * <p>For methods inherited from {@code AbstractCounter}, its documentation
 * specifies they can throw {@code UnsupportedOperationException} if the
 * implementing class is not mutable, and {@code NullPointerException} if keys
 * passed to methods aer {@code null} and the implementing class doesn't accept
 * {@code null} keys. However, {@code HashCounter} is mutable and accepts
 * {@code null} keys (not values), so these points don't apply.</p>
 *
 * @param <K> the class of the keys stored in the {@code Counter} with their
 * respective values
 * @param <V> a subclass of {@link java.lang.Number Number}, being the class of
 * the values associated with the keys stored in the {@code Counter}
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class HashCounter<K, V extends Number> extends AbstractCounter<K, V>{

	private static final long serialVersionUID=891023785096730046L;
	//************
	/**
	 * Underlaying HashMap providing the Map functionality.
	 */
	private HashMap<K, V> map;

	/**
	 * Builder used to construct objects of the
	 * {@link es.iguanod.collect.HashCounter} class. A
	 * {@link es.iguanod.util.Caster} from {@link java.math.BigDecimal} to
	 * {@code V} must be provided. Aside from that, the initial capacity and
	 * load factor of the underlaying hash table, a
	 * {@link java.util.Comparator} for the values and a {@link java.util.Map}
	 * with the initial mappings for the {@code HashCounter} can be provided.
	 *
	 * <p>All the methods of the {@code HashCounterBuilder} (except for the
	 * {@link #build build} method) return {@code this}, so calls can be
	 * chained.</p>
	 *
	 * <p>If no initial capacity is specified, the default initial capacity
	 * (16) is used. If no load factor is specified, the default load factor
	 * is used (0.75).</p>
	 *
	 * <p>If no {@code Comparator} is specified, none will be used and the
	 * values will be compared according to their natural ordering.</p>
	 *
	 * <p>If no initial mappings are specfied, none will be used. If they are
	 * specified, the initial capacity and load factor are ignored, and the
	 * default load factor (0.75) and an initial capacity enough to hold all
	 * of the initial mappings are used.</p>
	 *
	 * @param <K> the class of the keys stored in the {@code Counter} with
	 * their respective values
	 * @param <V> a subclass of {@link java.lang.Number Number}, being the
	 * class of the values associated with the keys stored in the
	 * {@code Counter}
	 *
	 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio
	 * Fernández</a>
	 * @since 1.0.1
	 * @version 1.0.1
	 */
	public static class HashCounterBuilder<K, V extends Number> implements Serializable{

		private static final long serialVersionUID=897813998949064952L;
		//************
		/**
		 * The caster from BigDecimal to V.
		 */
		private final Caster<BigDecimal, V> caster;
		/**
		 * The comparator of the values.
		 */
		private Comparator<? super V> comparator=null;
		/**
		 * The initial capacity. Default=16.
		 */
		private int initial_capacity=16;
		/**
		 * The load factor. Default=0.75.
		 */
		private float load_factor=0.75f;
		/**
		 * The initial mappings.
		 */
		private Map<? extends K, ? extends V> initial_map=null;

		/**
		 * Constructs a {@code HashCounterBuilder} with the specified
		 * {@code Caster}.
		 *
		 * @param caster the {@code Caster} for the {@code HashCounter}
		 */
		public HashCounterBuilder(Caster<BigDecimal, V> caster){
			this.caster=caster;
		}

		/**
		 * Sets the initial capacity of the underlaying hash table of the
		 * {@code HashCounter}.
		 *
		 * @param initial_capacity the initial capacity
		 *
		 * @return this {@code HashCounterBuilder}
		 *
		 * @throws IllegalArgumentException if {@code initial_capacity} is
		 * negative
		 */
		public HashCounterBuilder<K, V> setInitialCapacity(int initial_capacity){
			if(initial_capacity < 0){
				throw new IllegalArgumentException("The initial capacity must be non-negative");
			}
			this.initial_capacity=initial_capacity;
			return this;
		}

		/**
		 * Sets the load factor of the underlaying hash table of the
		 * {@code HashCounter}.
		 *
		 * @param load_factor the load factor
		 *
		 * @return this {@code HashCounterBuilder}
		 *
		 * @throws IllegalArgumentException if {@code load_factor} is
		 * non-possitive
		 */
		public HashCounterBuilder<K, V> setLoadFactor(float load_factor){
			if(load_factor <= 0){
				throw new IllegalArgumentException("The load factor must be possitive");
			}
			this.load_factor=load_factor;
			return this;
		}

		/**
		 * Sets the {@code Comparator} for the values of the
		 * {@code HashCounter}. If {@code comparator} is {@code null}, no
		 * {@code Comparator} will be used (this is the default behaviour
		 * anyway).
		 *
		 * @param comparator the {@code Comparator}
		 *
		 * @return this {@code HashCounterBuilder}
		 */
		public HashCounterBuilder<K, V> setComparator(Comparator<? super V> comparator){
			this.comparator=comparator;
			return this;
		}

		/**
		 * Sets the initial mappings for the {@code HashCounter}. If initial
		 * mappings are specified, the set load factor and initial capacity
		 * are ignored.If {@code map} is {@code null}, no initial mappings
		 * will be used (this is the default behaviour anyway).
		 *
		 * @param map the {@code Map} with the pairs key-value to be used as
		 * initial mappings
		 *
		 * @return this {@code HashCounterBuilder}
		 *
		 * @throws NullPointerException if any of the values in {@code map}
		 * is {@code null}
		 */
		public HashCounterBuilder<K, V> setInitialMappings(Map<? extends K, ? extends V> map){
			if(map != null){
				for(V value:map.values()){
					if(value == null){
						throw new NullPointerException("Null values not allowed in Counters");
					}
				}
			}
			this.initial_map=map;
			return this;
		}

		/**
		 * Construct a new {@code HashCounter} with the attributes specified
		 * by the calls to this {@code HashCounterBuilder} methods.
		 *
		 * @return the {@code HashCounter}
		 */
		public HashCounter<K, V> build(){
			return new HashCounter<>(this);
		}
	}

	/**
	 * Construct a new {@code HashCounter} with the attributes specified by
	 * the {@code HashCounterBuilder}.
	 *
	 * @param builder the {@code HashCodeBuilder} with the parameters for this
	 * {@code HashCounter}
	 */
	protected HashCounter(HashCounterBuilder<K, V> builder){
		super(builder.caster, builder.comparator);
		if(builder.initial_map == null){
			map=new HashMap<>(builder.initial_capacity, builder.load_factor);
		}else{
			map=new HashMap<>(builder.initial_map);
		}
	}

	/**
	 * Adds the specified value to the current value associated with the
	 * specified key. If the key is not already present in this {@code
	 * Counter}, this method behaves as if it was present with an associated
	 * value of {@code 0} (zero).
	 *
	 * @param key {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 *
	 * @throws NullPointerException if the specified value is {@code null}
	 */
	@Override
	public Tuple2<V, V> sum(K key, V value){
		if(value == null){
			throw new NullPointerException("Null values not allowed in Counters");
		}

		V newvalue;

		if(!map.containsKey(key)){
			newvalue=value;
		}else{
			newvalue=caster.cast(new BigDecimal(map.get(key).toString()).add(new BigDecimal(value.toString())));
		}

		return new Tuple2<>(map.put(key, newvalue), newvalue);
	}

	/**
	 * Adds the specified value to the current value associated with the
	 * specified key. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param key {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 *
	 * @throws NullPointerException if the specified value is {@code null}
	 */
	@Override
	public Tuple2<V, V> deduct(K key, V value){
		if(value == null){
			throw new NullPointerException("Null values not allowed in Counters");
		}

		V newvalue;

		if(!map.containsKey(key)){
			newvalue=caster.cast(new BigDecimal(value.toString()).negate());
		}else{
			newvalue=caster.cast(new BigDecimal(map.get(key).toString()).subtract(new BigDecimal(value.toString())));
		}

		return new Tuple2<>(map.put(key, newvalue), newvalue);
	}

	/**
	 * Returns the number of key-value mappings on this {@code Counter}.
	 *
	 * @return the number of mappings
	 */
	@Override
	public int size(){
		return map.size();
	}

	/**
	 * Returns wether this {@code Counter} contains no key-value mappings.
	 *
	 * @return true if this {@code Counter} has no mappings, false otherwise
	 */
	@Override
	public boolean isEmpty(){
		return map.isEmpty();
	}

	/**
	 * Returns wether this {@code Counter} contains a mapping for the
	 * specified key.
	 *
	 * @param key the key whose presence in this {@code Counter} is to be
	 * tested
	 *
	 * @return true if this {@code Counter} contains a mapping for the
	 * specified key, false otherwise
	 */
	@Override
	public boolean containsKey(Object key){
		return map.containsKey(key);
	}

	/**
	 * Returns wether this {@code Counter} has at leas one key mapped to the
	 * specified value.
	 *
	 * @param value value whose presence in this map is to be tested
	 *
	 * @return true if this {@code Counter} contains at least one key mapped
	 * to the specified value, false otherwise
	 */
	@Override
	public boolean containsValue(Object value){
		if(isEmpty() || value == null){
			return false;
		}else if(comparator == null || !(map.values().iterator().next().getClass().isAssignableFrom(value.getClass()))){
			return map.containsValue(value);
		}else{
			for(V v:map.values()){
				if(comparator.compare(v, (V)value) == 0){
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * Returns the value to which the specified key is mapped, or {@code null}
	 * if this {@code Counter} contains no mapping for the key.
	 *
	 * @param key the key whose value is to be obtained
	 *
	 * @return the value to which the specified key is mapped, or {@code null}
	 * if there is no such mappnig
	 */
	@Override
	public V get(Object key){
		return map.get(key);
	}

	/**
	 * Associates the specified value with the specified key in this
	 * {@code Counter}. If the {@code Counter} previously contained a mapping
	 * for the key, the old value is replaced.
	 *
	 * @param key key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 *
	 * @return the previous value associated with {@code key}, or {@code null}
	 * if this {@code Counter} contained no previous mapping for {@code key}
	 *
	 * @throws NullPointerException if {@code value} is {@code null}
	 */
	@Override
	public V put(K key, V value){
		if(value == null){
			throw new NullPointerException("Null values not allowed in Counters");
		}

		return map.put(key, value);
	}

	/**
	 * Removes the mapping for the specified key from this {@code Counter} if
	 * present.
	 *
	 * @param key the key whose mapping is to be removed
	 *
	 * @return the previous value associated with {@code key}, or {@code null}
	 * if this {@code Counter} contained no previous mapping for {@code key}
	 */
	@Override
	public V remove(Object key){
		return map.remove(key);
	}

	/**
	 * Removes all the mappings from this {@code Counter}.
	 */
	@Override
	public void clear(){
		map.clear();
	}

	/**
	 * Returns a {@code Set} view of the keys contained in this
	 * {@code Counter}. The set is backed by the counter, so changes to the
	 * counter are reflected in the set, and vice-versa. If the counter is
	 * modified while an iteration over the set is in progress (except through
	 * the iterator's own {@code remove} operation), the results of the
	 * iteration are undefined. The set supports element removal, which
	 * removes the corresponding mapping from the counter, via the {@code Iterator.remove},
	 * {@code Set.remove}, {@code removeAll}, {@code retainAll}, and
	 * {@code clear} operations. It does not support the {@code add} or
	 * {@code addAll} operations.
	 *
	 * @return the {@code Set} view of the keys contained in this
	 * {@code Counter}
	 */
	@Override
	public Set<K> keySet(){
		return map.keySet();
	}

	/**
	 * Returns a {@code Collection} view of the values contained in this
	 * {@code Counter}. The collection is backed by the counter, so changes to
	 * the counter are reflected in the collection, and vice-versa. If the
	 * counter is modified while an iteration over the collection is in
	 * progress (except through the iterator's own {@code remove} operation),
	 * the results of the iteration are undefined. The collection supports
	 * element removal, which removes the corresponding mapping from the
	 * counter, via the {@code Iterator.remove}, {@code Collection.remove},
	 * {@code removeAll}, {@code retainAll}, and {@code clear} operations. It
	 * does not support the {@code add} or {@code addAll} operations.
	 *
	 * @return the {@code Collection} view of the values contained in this
	 * {@code Counter}
	 */
	@Override
	public Collection<V> values(){
		return map.values();
	}

	/**
	 * Returns a {@code Set} view of the mappings contained in this
	 * {@code Counter}. The set is backed by the counter, so changes to the
	 * counter are reflected in the set, and vice-versa. If the counter is
	 * modified while an iteration over the set is in progress (except through
	 * the iterator's own {@code remove} operation, or through the
	 * {@code setValue} operation on an {@code Entry} returned by the
	 * iterator), the results of the iteration are undefined. The set supports
	 * element removal, which removes the corresponding mapping from the
	 * counter, via the {@code Iterator.remove},
	 * {@code Set.remove}, {@code removeAll}, {@code retainAll}, and
	 * {@code clear} operations. It does not support the {@code add} or
	 * {@code addAll} operations.
	 *
	 * @return the {@code Set} view of the mappings contained in this
	 * {@code Counter}
	 */
	@Override
	public Set<Entry<K, V>> entrySet(){
		return map.entrySet();
	}

	/**
	 * Returns a string representation of this {@code Counter}. The string
	 * representation consists of a list of key-value mappings in the order
	 * returned by the counters's entrySet view's iterator, enclosed in braces
	 * ("{}"). Adjacent mappings are separated by the characters ", " (comma
	 * and space). Each key-value mapping is rendered as the key followed by
	 * an equals sign ("=") followed by the associated value. Keys and values
	 * are converted to strings as by
	 * {@link java.lang.String#valueOf(Object) String.valueOf(Object)}.
	 *
	 * @return
	 */
	@Override
	public String toString(){
		return map.toString();
	}
}
