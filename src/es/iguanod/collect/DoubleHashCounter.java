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
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class DoubleHashCounter<K> extends HashCounter<K, Double>{

	private static final long serialVersionUID=949120312105878799L;

	public static class DoubleHashCounterBuilder<K> extends HashCounterBuilder<K, Double>{

		private static final long serialVersionUID=4589621230685L;

		public DoubleHashCounterBuilder(){
			super(new Caster<BigDecimal, Double>(){
				private static final long serialVersionUID=541209606861896193L;

				@Override
				public Double cast(BigDecimal t){
					return t.doubleValue();
				}
			});
		}

		@Override
		public DoubleHashCounterBuilder<K> setInitialCapacity(int initial_capacity){
			super.setInitialCapacity(initial_capacity);
			return this;
		}

		@Override
		public DoubleHashCounterBuilder<K> setLoadFactor(float load_factor){
			super.setLoadFactor(load_factor);
			return this;
		}

		@Override
		public DoubleHashCounterBuilder<K> setComparator(Comparator<? super Double> comparator){
			super.setComparator(comparator);
			return this;
		}

		@Override
		public DoubleHashCounterBuilder<K> setInitialMappings(Map<? extends K, ? extends Double> map){
			super.setInitialMappings(map);
			return this;
		}

		@Override
		public DoubleHashCounter<K> build(){
			return new DoubleHashCounter<>(this);
		}
	}
	public static final Caster<Number, Double> NUM_CASTER=new Caster<Number, Double>(){
		private static final long serialVersionUID=316419239096031983L;

		@Override
		public Double cast(Number t){
			return t.doubleValue();
		}
	};

	protected DoubleHashCounter(DoubleHashCounterBuilder<K> builder){
		super(builder);
	}

	public DoubleHashCounter(){
		this(new DoubleHashCounterBuilder<K>());
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
	public Tuple2<Double, Double> sum(K key, Double value){
		if(value == null){
			throw new NullPointerException("Null values not allowed in Counters");
		}

		Double newvalue;

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
	public Tuple2<Double, Double> deduct(K key, Double value){
		if(value == null){
			throw new NullPointerException("Null values not allowed in Counters");
		}

		Double newvalue;

		if(!this.containsKey(key)){
			newvalue=-value;
		}else{
			newvalue=this.get(key) - value;
		}

		return new Tuple2<>(this.put(key, newvalue), newvalue);
	}

	/**
	 * Adds {@code 1.0/keys.}{@link java.util.Collection#size() size()} to the
	 * current value associated with every key in the specified. If the key is
	 * not already present in this {@code Counter}, this method behaves as if
	 * it was present with an associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(Collection<? extends K> keys){
		sumAll(keys, 1.0 / keys.size());
	}

	/**
	 * Adds {@code 1.0/keys.length} to the current value associated with every
	 * key in the specified. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(K[] keys){
		sumAll(keys, 1.0 / keys.length);
	}

	/**
	 * Adds {@code value/keys.}{@link java.util.Collection#size() size()} to
	 * the current value associated with every key in the specified
	 * {@code Collection}. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(Collection<? extends K> keys, Double value){
		sumAll(keys, value / keys.size());
	}

	/**
	 * Adds {@code value/keys.length} to the current value associated with
	 * every key in the specified array. If the key is not already present in
	 * this {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(K[] keys, Double value){
		sumAll(keys, value / keys.length);
	}

	/**
	 * Adds {@code 1.0/this.}{@link #size() size()}) to the current value
	 * associated with every key in this {@code Counter}.
	 *
	 * @see #sum(Object,Number)
	 * @see #sumToAll(Number)
	 */
	@Override
	public void balancedSumToAll(){
		sumToAll(1.0 / this.size());
	}

	/**
	 * Adds {@code value/this.}{@link #size() size()}), to the current value
	 * associated with every key in this {@code Counter}.
	 *
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 * @see #sumToAll(Number)
	 */
	@Override
	public void balancedSumToAll(Double value){
		sumToAll(value / this.size());
	}

	/**
	 * Deducts {@code 1.0/keys.}{@link java.util.Collection#size() size()} to
	 * the current value associated with every key in the specified. If the
	 * key is not already present in this {@code Counter}, this method behaves
	 * as if it was present with an associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(Collection<? extends K> keys){
		deductAll(keys, 1.0 / keys.size());
	}

	/**
	 * Deducts {@code 1.0/keys.length} to the current value associated with
	 * every key in the specified. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(K[] keys){
		deductAll(keys, 1.0 / keys.length);
	}

	/**
	 * Deducts {@code value/keys.}{@link java.util.Collection#size() size()}
	 * to the current value associated with every key in the specified
	 * {@code Collection}. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(Collection<? extends K> keys, Double value){
		deductAll(keys, value / keys.size());
	}

	/**
	 * Deducts {@code value/keys.length} to the current value associated with
	 * every key in the specified array. If the key is not already present in
	 * this {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(K[] keys, Double value){
		deductAll(keys, value / keys.length);
	}

	/**
	 * Deducts {@code 1/this.}{@link #size() size()}) to the current value
	 * associated with every key in this {@code Counter}.
	 *
	 * @see #deduct(Object,Number)
	 * @see #deductToAll(Number)
	 */
	@Override
	public void balancedDeductToAll(){
		deductToAll(1.0 / this.size());
	}

	/**
	 * Deducts {@code value/this.}{@link #size() size()}), to the current
	 * value associated with every key in this {@code Counter}.
	 *
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 * @see #deductToAll(Number)
	 */
	@Override
	public void balancedDeductToAll(Double value){
		deductToAll(value / this.size());
	}
}
