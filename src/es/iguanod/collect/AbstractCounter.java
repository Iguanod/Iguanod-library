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
import java.util.Iterator;
import java.util.Map;

/**
 * This class provides a skeletal implementation of the {@code Counter}
 * interface, to minimize the effort required to implement this interface.
 *
 * <p>Since {@link java.lang.Number Numbers} cannot be operated directly, the
 * internal calculations are done using {@link java.math.BigDecimal BigDecimal}.
 * That's why the constructors take as parameter a
 * {@link es.iguanod.util.Caster Caster} to convert {@code BigDecimal} values to
 * {@code V} values.</p>
 *
 * <p>Despite being abstract, {@code AbstractCounter} does have some internal
 * fields, so it implements {@link java.io.Serializable Serializable} and it
 * will be {@code Serializable} as long as both the {@code Comparator} (if
 * given) and the elements returned by the {@code Caster} are
 * {@code Serializable}.</p>
 *
 * @param <K> the class of the keys stored in the {@code Counter} with their
 * respective values
 * @param <V> a subclass of {@link java.lang.Number Number}, being the class of
 * the values associated with the keys stored in the {@code Counter}
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 *
 * @see java.util.Map
 * @see es.iguanod.util.Caster
 * @see java.io.Serializable
 */
public abstract class AbstractCounter<K, V extends Number> implements Counter<K, V>, Serializable{

	private static final long serialVersionUID=926549406426989216L;
	//************
	/**
	 * {@code Caster} used to convert {@code BigDecimal} values to {@code V}
	 * values.
	 */
	public final Caster<BigDecimal, V> caster;
	/**
	 * {@code Comparator} used to compare two {@code V} values.
	 */
	public final Comparator<? super V> comparator;
	/**
	 * The value one (obtained by casting BigDecimal.ONE with the caster).
	 */
	private V one;

	/**
	 * Constructs a new {@code AbstractCounter} with the specified
	 * {@code Caster} and comparison of values using their natural ordering.
	 * Note that since natural ordering of the values is used, some methods
	 * will throw a {@link java.lang.ClassCastException ClassCastException}
	 * when invoked if {@code V} doesn't implement
	 * {@link java.lang.Comparable Comparable} or if two values aren't
	 * mutually comparable.
	 *
	 * @param caster the caster for converting {@code BigDecimal} to {@code V}
	 */
	public AbstractCounter(Caster<BigDecimal, V> caster){
		this(caster, null);
	}

	/**
	 * Constructs a new {@code AbstractCounter} with the specified
	 * {@code Caster} and the specified {@code Comparator}. If
	 * {@code comparator} is {@code null}, the natural ordering of the values
	 * is used (note that int that case some methods will throw a
	 * {@link java.lang.ClassCastException ClassCastException} when invoked if
	 * {@code V} doesn't implement {@link java.lang.Comparable Comparable} or
	 * if two values aren't mutually comparable).
	 *
	 * @param caster the caster for converting {@code BigDecimal} to {@code V}
	 * @param comparator the comparator for comparing values
	 */
	public AbstractCounter(Caster<BigDecimal, V> caster, Comparator<? super V> comparator){
		this.caster=caster;
		one=this.caster.cast(BigDecimal.ONE);
		this.comparator=comparator;
	}

	/**
	 * Auxiliary method to compare two values. Returns less-than-zero, zero or
	 * greater-than-zero if the first value is (respectively) lesser, equal or
	 * greater than the second. If a {@code Comparator} was specified, it is
	 * used, otherwise the elements are compared according to their natural
	 * ordering.
	 *
	 * @param value1 the first value to be compared
	 * @param value2 the second value to be compared
	 *
	 * @return the result of the comparison
	 *
	 * @throws ClassCastException if no {@code Comparator} is available to use
	 * and the elements aren't {@code Comparable} or mutually comparable
	 */
	protected final int compare(V value1, V value2){
		if(comparator != null){
			return comparator.compare(value1, value2);
		}else{
			return ((Comparable<V>)value1).compareTo(value2);
		}
	}

	/**
	 * Auxiliary method to determine whether two values are equal. If a
	 * {@code Comparator} was specified, it is used, otherwise the elements
	 * are compared with the {@link java.lang.Object#equals(Object) equals}
	 * method.
	 *
	 * @param value1 the first value to be determined if is equal to the other
	 * @param value2 the second value to be determined if is equal to the
	 * other
	 *
	 * @return true if the values are equals, false otherwise
	 */
	protected final boolean equals(V value1, V value2){
		if(value1 == value2){
			return true;
		}
		if(value1 == null || value2 == null){
			return false;
		}
		if(comparator != null){
			return comparator.compare(value1, value2) == 0;
		}else{
			return value1.equals(value2);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param key {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public Tuple2<V, V> sum(K key){
		return sum(key, one);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 */
	@Override
	public void sumAll(Collection<? extends K> keys){
		for(K key:keys){
			sum(key);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 */
	@Override
	public void sumAll(K[] keys){
		for(K key:keys){
			sum(key);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param map {@inheritDoc}
	 */
	@Override
	public void sumAll(Map<? extends K, ? extends V> map){
		for(Entry<? extends K, ? extends V> entry:map.entrySet()){
			sum(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param <X> {@inheritDoc}
	 *
	 * @param map {@inheritDoc}
	 * @param caster {@inheritDoc}
	 */
	@Override
	public <X> void sumAll(Map<? extends K, ? extends X> map, Caster<X, V> caster){
		for(Entry<? extends K, ? extends X> entry:map.entrySet()){
			sum(entry.getKey(), caster.cast(entry.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 */
	@Override
	public void sumAll(Collection<? extends K> keys, V value){
		for(K key:keys){
			sum(key, value);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 */
	@Override
	public void sumAll(K[] keys, V value){
		for(K key:keys){
			sum(key, value);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sumToAll(){
		sumToAll(one);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param value {@inheritDoc}
	 */
	@Override
	public void sumToAll(V value){
		Iterator<Entry<K, V>> iter=entrySet().iterator();
		while(iter.hasNext()){
			Entry<K, V> entry=iter.next();
			entry.setValue(caster.cast(new BigDecimal(entry.getValue().toString()).add(new BigDecimal(value.toString()))));
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 */
	@Override
	public void balancedSum(Collection<? extends K> keys){
		sumAll(keys, caster.cast(BigDecimal.ONE.divide(new BigDecimal(keys.size()))));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 */
	@Override
	public void balancedSum(K[] keys){
		sumAll(keys, caster.cast(BigDecimal.ONE.divide(new BigDecimal(keys.length))));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 */
	@Override
	public void balancedSum(Collection<? extends K> keys, V value){
		sumAll(keys, caster.cast(new BigDecimal(value.toString()).divide(new BigDecimal(keys.size()))));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 */
	@Override
	public void balancedSum(K[] keys, V value){
		sumAll(keys, caster.cast(new BigDecimal(value.toString()).divide(new BigDecimal(keys.length))));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void balancedSumToAll(){
		sumToAll(this.caster.cast(BigDecimal.ONE.divide(new BigDecimal(size()))));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param value {@inheritDoc}
	 */
	@Override
	public void balancedSumToAll(V value){
		sumToAll(this.caster.cast(new BigDecimal(value.toString()).divide(new BigDecimal(size()))));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param key {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public Tuple2<V, V> deduct(K key){
		return deduct(key, one);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 */
	@Override
	public void deductAll(Collection<? extends K> keys){
		for(K key:keys){
			deduct(key);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 */
	@Override
	public void deductAll(K[] keys){
		for(K key:keys){
			deduct(key);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param map {@inheritDoc}
	 */
	@Override
	public void deductAll(Map<? extends K, ? extends V> map){
		for(Entry<? extends K, ? extends V> entry:map.entrySet()){
			deduct(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param <X> {@inheritDoc}
	 *
	 * @param map {@inheritDoc}
	 * @param caster {@inheritDoc}
	 */
	@Override
	public <X> void deductAll(Map<? extends K, ? extends X> map, Caster<X, V> caster){
		for(Entry<? extends K, ? extends X> entry:map.entrySet()){
			deduct(entry.getKey(), caster.cast(entry.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 */
	@Override
	public void deductAll(Collection<? extends K> keys, V value){
		for(K key:keys){
			deduct(key, value);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 */
	@Override
	public void deductAll(K[] keys, V value){
		for(K key:keys){
			deduct(key, value);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deductToAll(){
		deductToAll(one);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param value {@inheritDoc}
	 */
	@Override
	public void deductToAll(V value){
		for(K key:keySet()){
			deduct(key, value);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 */
	@Override
	public void balancedDeduct(Collection<? extends K> keys){
		deductAll(keys, caster.cast(BigDecimal.ONE.divide(new BigDecimal(keys.size()))));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 */
	@Override
	public void balancedDeduct(K[] keys){
		deductAll(keys, caster.cast(BigDecimal.ONE.divide(new BigDecimal(keys.length))));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 */
	@Override
	public void balancedDeduct(Collection<? extends K> keys, V value){
		deductAll(keys, caster.cast(new BigDecimal(value.toString()).divide(new BigDecimal(keys.size()))));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 */
	@Override
	public void balancedDeduct(K[] keys, V value){
		deductAll(keys, caster.cast(new BigDecimal(value.toString()).divide(new BigDecimal(keys.length))));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void balancedDeductToAll(){
		deductToAll(this.caster.cast(BigDecimal.ONE.divide(new BigDecimal(size()))));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param value {@inheritDoc}
	 */
	@Override
	public void balancedDeductToAll(V value){
		deductToAll(this.caster.cast(new BigDecimal(value.toString()).divide(new BigDecimal(size()))));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param key {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public Tuple2<V, V> putMax(K key, V value){
		if(!containsKey(key)){
			return new Tuple2<>(put(key, value), value);
		}else{
			V oldvalue=get(key);
			if(compare(oldvalue, value) < 0){
				return new Tuple2<>(put(key, value), value);
			}else{
				return new Tuple2<>(oldvalue, oldvalue);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param key {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public Tuple2<V, V> putMin(K key, V value){
		if(!containsKey(key)){
			return new Tuple2<>(put(key, value), value);
		}else{
			V oldvalue=get(key);
			if(compare(oldvalue, value) > 0){
				return new Tuple2<>(put(key, value), value);
			}else{
				return new Tuple2<>(oldvalue, oldvalue);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param map {@inheritDoc}
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> map){
		for(Entry<? extends K, ? extends V> entry:map.entrySet()){
			put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param <X> {@inheritDoc}
	 *
	 * @param map {@inheritDoc}
	 * @param caster {@inheritDoc}
	 */
	@Override
	public <X> void putAll(Map<? extends K, ? extends X> map, Caster<X, V> caster){
		for(Entry<? extends K, ? extends X> entry:map.entrySet()){
			put(entry.getKey(), caster.cast(entry.getValue()));
		}
	}
}
