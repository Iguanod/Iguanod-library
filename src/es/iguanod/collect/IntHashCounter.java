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

import es.iguanod.collect.HashCounter.HashCounterBuilder;
import es.iguanod.util.Caster;
import es.iguanod.util.tuples.Tuple2;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class IntHashCounter<K> extends HashCounter<K, Integer>{

	private static final long serialVersionUID=691954619064951720L;

	public static class IntHashCounterBuilder<K> extends HashCounterBuilder<K, Integer>{

		private static final long serialVersionUID=941223612239866510L;

		public IntHashCounterBuilder(){
			super(new Caster<BigDecimal, Integer>(){
				private static final long serialVersionUID=167910594249130L;

				@Override
				public Integer cast(BigDecimal t){
					return t.intValue();
				}
			});
		}

		@Override
		public IntHashCounterBuilder<K> setInitialCapacity(int initial_capacity){
			super.setInitialCapacity(initial_capacity);
			return this;
		}

		@Override
		public IntHashCounterBuilder<K> setLoadFactor(float load_factor){
			super.setLoadFactor(load_factor);
			return this;
		}

		@Override
		public IntHashCounterBuilder<K> setComparator(Comparator<? super Integer> comparator){
			super.setComparator(comparator);
			return this;
		}

		@Override
		public IntHashCounterBuilder<K> setInitialMappings(Map<? extends K, ? extends Integer> map){
			super.setInitialMappings(map);
			return this;
		}

		@Override
		public IntHashCounter<K> build(){
			return new IntHashCounter<>(this);
		}
	}
	public static final Caster<Number, Integer> NUM_CASTER=new Caster<Number, Integer>(){
		private static final long serialVersionUID=164912068267529453L;

		@Override
		public Integer cast(Number t){
			return t.intValue();
		}
	};

	protected IntHashCounter(IntHashCounterBuilder<K> builder){
		super(builder);
	}

	public IntHashCounter(){
		this(new IntHashCounterBuilder<K>());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param key {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 */
	@Override
	public Tuple2<Integer, Integer> sum(K key, Integer value){
		if(value == null){
			throw new NullPointerException("Null values not allowed in Counters");
		}

		Integer newvalue;

		if(!this.containsKey(key)){
			newvalue=value;
		}else{
			newvalue=this.get(key) + value;
		}

		return new Tuple2<>(this.put(key, newvalue), newvalue);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param key {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 */
	@Override
	public Tuple2<Integer, Integer> deduct(K key, Integer value){
		if(value == null){
			throw new NullPointerException("Null values not allowed in Counters");
		}

		Integer newvalue;

		if(!this.containsKey(key)){
			newvalue=-value;
		}else{
			newvalue=this.get(key) - value;
		}

		return new Tuple2<>(this.put(key, newvalue), newvalue);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param value {@inheritDoc}
	 */
	@Override
	public void sumToAll(Integer value){
		Iterator<Entry<K, Integer>> iter=entrySet().iterator();
		while(iter.hasNext()){
			Entry<K, Integer> entry=iter.next();
			entry.setValue(entry.getValue()+value);
		}
	}

	/**
	 * Adds {@code 1/keys.}{@link java.util.Collection#size() size()} to the
	 * current value associated with every key in the specified. If the key is
	 * not already present in this {@code Counter}, this method behaves as if
	 * it was present with an associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the collection is one, the amount summed to the
	 * elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(Collection<? extends K> keys){
		sumAll(keys, 1 / keys.size());
	}

	/**
	 * Adds {@code 1/keys.length} to the current value associated with every
	 * key in the specified. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the array is one, the amount summed to the elements
	 * will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(K[] keys){
		sumAll(keys, 1 / keys.length);
	}

	/**
	 * Adds {@code value/keys.}{@link java.util.Collection#size() size()} to
	 * the current value associated with every key in the specified
	 * {@code Collection}. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the collection is greater or equal than
	 * {@code value}, the amount summed to the elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(Collection<? extends K> keys, Integer value){
		sumAll(keys, value / keys.size());
	}

	/**
	 * Adds {@code value/keys.length} to the current value associated with
	 * every key in the specified array. If the key is not already present in
	 * this {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the array greater or equal than {@code value}, the
	 * amount summed to the elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(K[] keys, Integer value){
		sumAll(keys, value / keys.length);
	}

	/**
	 * Adds {@code 1/this.}{@link #size() size()}) to the current value
	 * associated with every key in this {@code Counter}.
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of this {@code Counter} is one, the amount summed to
	 * the elements will be zero.</p>
	 *
	 * @see #sum(Object,Number)
	 * @see #sumToAll(Number)
	 */
	@Override
	public void balancedSumToAll(){
		if(this.size() == 1){
			sumToAll(1);
		}
	}

	/**
	 * Adds {@code value/this.}{@link #size() size()}), to the current value
	 * associated with every key in this {@code Counter}.
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of this {@code Counter} is greater or equal than
	 * {@code value}, the amount summed to the elements will be zero.</p>
	 *
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 * @see #sumToAll(Number)
	 */
	@Override
	public void balancedSumToAll(Integer value){
		int size=this.size();
		if(value >= size){
			sumToAll(value / size);
		}
	}

	/**
	 * Deducts {@code 1/keys.}{@link java.util.Collection#size() size()} to
	 * the current value associated with every key in the specified. If the
	 * key is not already present in this {@code Counter}, this method behaves
	 * as if it was present with an associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the collection is one, the amount deducted to the
	 * elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(Collection<? extends K> keys){
		deductAll(keys, 1 / keys.size());
	}

	/**
	 * Deducts {@code 1/keys.length} to the current value associated with
	 * every key in the specified. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the array is one, the amount deducted to the
	 * elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(K[] keys){
		deductAll(keys, 1 / keys.length);
	}

	/**
	 * Deducts {@code value/keys.}{@link java.util.Collection#size() size()}
	 * to the current value associated with every key in the specified
	 * {@code Collection}. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the collection is greater or equal than
	 * {@code value}, the amount deducted to the elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(Collection<? extends K> keys, Integer value){
		deductAll(keys, value / keys.size());
	}

	/**
	 * Deducts {@code value/keys.length} to the current value associated with
	 * every key in the specified array. If the key is not already present in
	 * this {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the array greater or equal than {@code value}, the
	 * amount deducted to the elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(K[] keys, Integer value){
		deductAll(keys, value / keys.length);
	}

	/**
	 * Deducts {@code 1/this.}{@link #size() size()}) to the current value
	 * associated with every key in this {@code Counter}.
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of this {@code Counter} is one, the amount deducted to
	 * the elements will be zero.</p>
	 *
	 * @see #deduct(Object,Number)
	 * @see #deductToAll(Number)
	 */
	@Override
	public void balancedDeductToAll(){
		if(this.size() == 1){
			deductToAll(1);
		}
	}

	/**
	 * Deducts {@code value/this.}{@link #size() size()}), to the current
	 * value associated with every key in this {@code Counter}.
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of this {@code Counter} is greater or equal than
	 * {@code value}, the amount deducted to the elements will be zero.</p>
	 *
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 * @see #deductToAll(Number)
	 */
	@Override
	public void balancedDeductToAll(Integer value){
		int size=this.size();
		if(value >= size){
			deductToAll(value / size);
		}
	}
}