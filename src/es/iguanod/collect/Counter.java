/*
 * -------------------- DO NOT REMOVE OR MODIFY THIS HEADER --------------------
 * 
 * Copyright (C) 2014 The Iguanod Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * A copy of the License should accompany this file or the folder or folder
 * hierarchy where this file is located, or should have been provided by whoever
 * or wherever you obtained this file. If that is not the case you may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied;
 * and no Contributor shall be liable for damages, including any direct,
 * indirect, special, incidental, or consequential damages of any character
 * arising as a result of this License or out of the use or inability to use
 * the Work (including but not limited to damages for loss of goodwill, work
 * stoppage, computer failure or malfunction, or any and all other commercial
 * damages or losses), even if such Contributor has been advised of the
 * possibility of such damages.
 * 
 * See the License for further details and the specific language governing
 * permissions and limitations under the License.
 */
package es.iguanod.collect;

import es.iguanod.util.Caster;
import es.iguanod.util.tuples.Tuple2;
import java.util.Collection;
import java.util.Map;

/**
 * An implementation of a counter (which associates keys with numbers) based on
 * a {@link java.util.Map}. This interface provides mehtods to encapsulate
 * operations such as adding or deducting values to the current value associated
 * with a key, or replacing the old value with a new one if the later is
 * higher/lower.
 *
 * <p>{@code Counter} operats with {@link java.lang.Number Numbers} to store the
 * values associated with the keys, but different classes subclassing
 * {@code Number} cannot be operated with one another directly. For that reason,
 * it is not natively possible to perform perfectly logical operations as adding
 * every mapping in a {@code Counter} that counts with {@code Intgers} to a
 * {@code Counter} that counts with {@code Doubles}. That's why an auxiliary
 * class called {@link es.iguanod.util.Caster Caster} is used to cast elements
 * of different classes to the appropriate class a given {@code Counter}
 * operates with.</p>
 *
 * <p>As the {@code Counter} interface extends the {@code Map} interface, all
 * the general contracts in {@code Map} should be maintained. However, the
 * {@code Counter} interfaces adds a couple of specifications. First of all,
 * {@code Map} allows for implementing classes to decide wether they want to
 * allow {@code null} keys and values, but {@code Counter} restricts this since
 * in a {@code Counter}, a single {@code null} key may or may not be allowed,
 * but {@code null} values are forbidden. Besides, the {@code Map} interface
 * specifies some methods as optional operations, which may be implemented to
 * allways throw
 * {@link java.lang.UnsupportedOperationException UnsupportedOperationException}
 * if the implementation is unmodifiable. {@code Counter} follows this pattern
 * so all its methods can throw {@code UnsupportedOperationException} if the
 * implementation is unmodifiable. However, an unmodifiable {@code Counter} (as
 * is presented in this interface, without any other methods) doesn't make a lot
 * of sense apart from its {@code Map} behaviour, as all the methods it adds
 * change the mappings. Anyway, the possibility to throw
 * {@code UnsupportedOperationException} is allowed in case a subclass or
 * subinterface of {@code Counter} adds methods that don't modify the
 * {@code Counter}, in which case an unmodifiable {@code Map} and an
 * unmodifiable object of that sublcass of {@code Counter} wouldn't behave the
 * same.<p>
 *
 * @param <K> the class of the keys stored in the {@code Counter} with their
 * respective values
 * @param <V> a subclass of {@link java.lang.Number Number}, being the class of
 * the values associated with the keys stored in the {@code Counter}
 *
 * @see java.util.Map
 * @see es.iguanod.util.Caster
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.2.1.a
 * @version 1.0.1.b
 */
public interface Counter<K, V extends Number> extends Map<K, V>{

	/**
	 * Adds one to the current value associated with the specified key
	 * (optional operation). If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param key key which value is to be modified
	 *
	 * @return a tuple containing in its first place the previous value
	 * associated with {@code key}, or {@code null} if the key was not
	 * contained in the {@code Counter}; and in its second place the new value
	 * associated with {@code key}
	 *
	 * @throws NullPointerException if the specified key is {@code null} and
	 * this {@code Counter} does not permit {@code null} keys
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sum(Object,Number)
	 */
	public Tuple2<V, V> sum(K key);

	/**
	 * Adds one to the current value associated with every key in the
	 * specified {@code Collection} (optional operation). If the key is not
	 * already present in this {@code Counter}, this method behaves as if it
	 * was present with an associated value of {@code 0} (zero).
	 *
	 * @param keys keys whose values are to be modified
	 *
	 * @throws NullPointerException if the specified {@code Collection} is
	 * {@code null}, or if any of the keys in the {@code Collection} is
	 * {@code null} and this {@code Counter} does not permit {@code null} keys
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sumAll(Collection,Number)
	 */
	public void sumAll(Collection<? extends K> keys);

	/**
	 * Adds one to the current value associated with every key in the
	 * specified array (optional operation). If the key is not already present
	 * in this {@code Counter}, this method behaves as if it was present with
	 * an associated value of {@code 0} (zero).
	 *
	 * @param keys keys whose values are to be modified
	 *
	 * @throws NullPointerException if the specified array is {@code null}, or
	 * if any of the keys in the array is {@code null} and this
	 * {@code Counter} does not permit {@code null} keys
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sumAll(Object[],Number)
	 */
	public void sumAll(K[] keys);

	/**
	 * Adds the specified value to the current value associated with the
	 * specified key (optional operation). If the key is not already present
	 * in this {@code
	 * Counter}, this method behaves as if it was present with an associated
	 * value of {@code 0} (zero).
	 *
	 * @param key key which value is to be modified
	 * @param value value to be added to the previous value associated with
	 * {@code key}
	 *
	 * @return a tuple containing in its first place the previous value
	 * associated with {@code key}, or {@code null} if the key was not
	 * contained in the {@code Counter}; and in its second place the new value
	 * associated with {@code key}
	 *
	 * @throws NullPointerException if the specified key is {@code null} and
	 * this {@code Counter} does not permit {@code null} keys, or if the
	 * specified value is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 */
	public Tuple2<V, V> sum(K key, V value);

	/**
	 * Applies the {@link #sum(Object, Number) sum} operation to every pair
	 * (key,value) contained in the specified {@code Map} (optional
	 * operation). Note that, since {@code Counter} extends {@code Map}, this
	 * method can be used to sum all the values in other {@code Counter} to
	 * this {@code Counter}.
	 *
	 * @param map the {@code Map} whose entries are to be sumed to this
	 * {@code Counter}
	 *
	 * @throws NullPointerException if the specified {@code Map} is
	 * {@code null}, or if any of the keys in the {@code Map} is {@code null}
	 * and this {@code Counter} does not permit {@code null} keys, or if any
	 * of the values in the {@code Map} is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sum(Object,Number)
	 */
	public void sumAll(Map<? extends K, ? extends V> map);

	/**
	 * Applies the {@link #sum(Object, Number) sum} operation to every pair
	 * (key,value) contained in the specified {@code Map}, by previously
	 * applying the {@link es.iguanod.util.Caster#cast(Object) Caster.cast}
	 * operation to the values (optional operation). Note that, since
	 * {@code Counter} extends {@code Map}, this method can be used to sum all
	 * the values in other {@code Counter} to this {@code Counter}.
	 *
	 * @param <X> the class of the values in the specified {@code Map}
	 *
	 * @param map the {@code Map} whose entries are to be sumed to this
	 * {@code Counter}
	 * @param caster the {@code Caster} used to cast the values from the
	 * {@code Map} to values appropriate to be used in this {@code Counter}
	 *
	 * @throws NullPointerException if the specified {@code Map} or the
	 * specified {@code Caster} is {@code null}, or if any of the keys in the
	 * {@code Map} is {@code null} and this {@code Counter} does not permit
	 * {@code null} keys, or if any of the values in the {@code Map} is
	 * {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 * @throws RuntimeException this method may throw any {@code Exception}
	 * thrown by the {@code caster}
	 *
	 * @see #sum(Object,Number)
	 * @see es.iguanod.util.Caster
	 */
	public <X> void sumAll(Map<? extends K, ? extends X> map, Caster<X, V> caster);

	/**
	 * Applies the {@link #sum(Object, Number) sum} operation to every key in
	 * the specified {@code Collection} and the specified value (optional
	 * operation).
	 *
	 * @param keys the {@code Collection} containing all the keys to be
	 * modified.
	 * @param value the value to be added to all the keys in the {@code
	 * Collection}.
	 *
	 * @throws NullPointerException if the specified Collection is null, or if
	 * this Counter does not permit null keys and the specified Collection
	 * contains null keys, or if the specified value is null.
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sum(Object,Number)
	 */
	public void sumAll(Collection<? extends K> keys, V value);

	/**
	 * Applies the {@link #sum(Object, Number) sum} operation to every key in
	 * the specified array and the specified value (optional operation).
	 *
	 * @param keys the array containing all the keys to be modified.
	 * @param value the value to be added to all the keys in the array.
	 *
	 * @throws NullPointerException if the specified array is null, or if this
	 * Counter does not permit null keys and the specified array contains null
	 * keys, or if the specified value is null.
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sum(Object,Number)
	 */
	public void sumAll(K[] keys, V value);

	/**
	 * Adds one to the current value associated with every key in this
	 * {@code Counter} (optional operation).
	 *
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sumToAll(Number)
	 */
	public void sumToAll();

	/**
	 * Adds the specified value to the current value associated with every key
	 * in this {@code Counter} (optional operation).
	 *
	 * @param value value to be added to the previous value associated with
	 * the keys
	 *
	 * @throws NullPointerException if the specified value is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sum(Object,Number)
	 */
	public void sumToAll(V value);

	/**
	 * Adds the same quantity (generally {@code 1} divided by
	 * {@code keys.}{@link java.util.Collection#size() size()}) to the current
	 * value associated with every key in the specified {@code Collection}
	 * (optional operation). If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys keys whose values are to be modified
	 *
	 * @throws NullPointerException if the specified {@code Collection} is
	 * {@code null}, or if any of the keys in the {@code Collection} is
	 * {@code null} and this {@code Counter} does not permit {@code null} keys
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sum(Object,Number)
	 */
	public void balancedSum(Collection<? extends K> keys);

	/**
	 * Adds the same quantity (generally {@code 1} divided by
	 * {@code keys.length}) to the current value associated with every key in
	 * the specified array (optional operation). If the key is not already
	 * present in this {@code Counter}, this method behaves as if it was
	 * present with an associated value of {@code 0} (zero).
	 *
	 * @param keys keys whose values are to be modified
	 *
	 * @throws NullPointerException if the specified array is {@code null}, or
	 * if any of the keys in the array is {@code null} and this
	 * {@code Counter} does not permit {@code null} keys
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sum(Object,Number)
	 */
	public void balancedSum(K[] keys);

	/**
	 * Adds the same quantity, based on {@code value} (generally {@code value}
	 * divided by {@code keys.}{@link java.util.Collection#size() size()}), to
	 * the current value associated with every key in the specified
	 * {@code Collection} (optional operation). If the key is not already
	 * present in this {@code Counter}, this method behaves as if it was
	 * present with an associated value of {@code 0} (zero).
	 *
	 * @param keys keys whose values are to be modified
	 * @param value value from which it is calculated the value to be added to
	 * the previous value associated with the keys
	 *
	 * @throws NullPointerException if the specified {@code Collection} is
	 * {@code null}, or if any of the keys in the {@code Collection} is
	 * {@code null} and this {@code Counter} does not permit {@code null}
	 * keys, or if the specified {@code value} is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sum(Object,Number)
	 */
	public void balancedSum(Collection<? extends K> keys, V value);

	/**
	 * Adds the same quantity, based on {@code value} (generally {@code value}
	 * divided by {@code keys.length}), to the current value associated with
	 * every key in the specified array (optional operation). If the key is
	 * not already present in this {@code Counter}, this method behaves as if
	 * it was present with an associated value of {@code 0} (zero).
	 *
	 * @param keys keys whose values are to be modified
	 * @param value value from which it is calculated the value to be added to
	 * the previous value associated with the keys
	 *
	 * @throws NullPointerException if the specified array is {@code null}, or
	 * if any of the keys in the array is {@code null} and this
	 * {@code Counter} does not permit {@code null} keys, or if the specified
	 * {@code value} is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sum(Object,Number)
	 */
	public void balancedSum(K[] keys, V value);

	/**
	 * Adds the same quantity (generally {@code 1} divided by
	 * {@code this.}{@link #size() size()}) to the current value associated
	 * with every key in this {@code Counter} (optional operation).
	 *
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sum(Object,Number)
	 * @see #sumToAll(Number)
	 */
	public void balancedSumToAll();

	/**
	 * Adds the same quantity, based on {@code value} (generally {@code value}
	 * divided by {@code this.}{@link #size() size()}), to the current value
	 * associated with every key in this {@code Counter} (optional operation).
	 *
	 * @param value value from which it is calculated the value to be added to
	 * the previous value associated with the keys
	 *
	 * @throws NullPointerException if the specified {@code value} is
	 * {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #sum(Object,Number)
	 * @see #sumToAll(Number)
	 */
	public void balancedSumToAll(V value);

	/**
	 * Deducts one to the current value associated with the specified key
	 * (optional operation). If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param key key which value is to be modified
	 *
	 * @return a tuple containing in its first place the previous value
	 * associated with {@code key}, or {@code null} if the key was not
	 * contained in the {@code Counter}; and in its second place the new value
	 * associated with {@code key}
	 *
	 * @throws NullPointerException if the specified key is {@code null} and
	 * this {@code Counter} does not permit {@code null} keys
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deduct(Object,Number)
	 */
	public Tuple2<V, V> deduct(K key);

	/**
	 * Deducts one to the current value associated with every key in the
	 * specified {@code Collection} (optional operation). If the key is not
	 * already present in this {@code Counter}, this method behaves as if it
	 * was present with an associated value of {@code 0} (zero).
	 *
	 * @param keys keys whose values are to be modified
	 *
	 * @throws NullPointerException if the specified {@code Collection} is
	 * {@code null}, or if any of the keys in the {@code Collection} is
	 * {@code null} and this {@code Counter} does not permit {@code null} keys
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deductAll(Collection,Number)
	 */
	public void deductAll(Collection<? extends K> keys);

	/**
	 * Deducts one to the current value associated with every key in the
	 * specified array (optional operation). If the key is not already present
	 * in this {@code Counter}, this method behaves as if it was present with
	 * an associated value of {@code 0} (zero).
	 *
	 * @param keys keys whose values are to be modified
	 *
	 * @throws NullPointerException if the specified array is {@code null}, or
	 * if any of the keys in the array is {@code null} and this
	 * {@code Counter} does not permit {@code null} keys
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deductAll(Object[],Number)
	 */
	public void deductAll(K[] keys);

	/**
	 * Adds the specified value to the current value associated with the
	 * specified key (optional operation). If the key is not already present
	 * in this {@code Counter}, this method behaves as if it was present with
	 * an associated value of {@code 0} (zero).
	 *
	 * @param key key which value is to be modified
	 * @param value value to be deducted to the previous value associated with
	 * {@code key}
	 *
	 * @return a tuple containing in its first place the previous value
	 * associated with {@code key}, or {@code null} if the key was not
	 * contained in the {@code Counter}; and in its second place the new value
	 * associated with {@code key}
	 *
	 * @throws NullPointerException if the specified key is {@code null} and
	 * this {@code Counter} does not permit {@code null} keys, or if the
	 * specified value is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 */
	public Tuple2<V, V> deduct(K key, V value);

	/**
	 * Applies the {@link #deduct(Object, Number) deduct} operation to every
	 * pair (key,value) contained in the specified {@code Map} (optional
	 * operation). Note that, since {@code Counter} extends {@code Map}, this
	 * method can be used to sum all the values in other {@code Counter} to
	 * this {@code Counter}.
	 *
	 * @param map the {@code Map} whose entries are to be deducted to this
	 * {@code Counter}
	 *
	 * @throws NullPointerException if the specified {@code Map} is
	 * {@code null}, or if any of the keys in the {@code Map} is {@code null}
	 * and this {@code Counter} does not permit {@code null} keys, or if any
	 * of the values in the {@code Map} is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deduct(Object,Number)
	 */
	public void deductAll(Map<? extends K, ? extends V> map);

	/**
	 * Applies the {@link #deduct(Object, Number) deduct} operation to every
	 * pair (key,value) contained in the specified {@code Map}, by previously
	 * applying the {@link es.iguanod.util.Caster#cast(Object) Caster.cast}
	 * operation to the values (optional operation). Note that, since
	 * {@code Counter} extends {@code Map}, this method can be used to sum all
	 * the values in other {@code Counter} to this {@code Counter}.
	 *
	 * @param <X> the class of the values in the specified {@code Map}
	 *
	 * @param map the {@code Map} whose entries are to be deducted to this
	 * {@code Counter}
	 * @param caster the {@code Caster} used to cast the values from the
	 * {@code Map} to values appropriate to be used in this {@code Counter}
	 *
	 * @throws NullPointerException if the specified {@code Map} or the
	 * specified {@code Caster} is {@code null}, or if any of the keys in the
	 * {@code Map} is {@code null} and this {@code Counter} does not permit
	 * {@code null} keys, or if any of the values in the {@code Map} is
	 * {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 * @throws RuntimeException this method may throw any {@code Exception}
	 * thrown by the {@code caster}
	 *
	 * @see #deduct(Object,Number)
	 * @see es.iguanod.util.Caster
	 */
	public <X> void deductAll(Map<? extends K, ? extends X> map, Caster<X, V> caster);

	/**
	 * Applies the {@link #deduct(Object, Number) deduct} operation to every
	 * key in the specified {@code Collection} and the specified value
	 * (optional operation).
	 *
	 * @param keys the {@code Collection} containing all the keys to be
	 * modified.
	 * @param value the value to be deducted to all the keys in the {@code
	 * Collection}.
	 *
	 * @throws NullPointerException if the specified {@code Collection} is
	 * {@code null}, or if this {@code Counter} does not permit {@code null}
	 * keys and the specified {@code Collection} contains {@code null} keys,
	 * or if the specified value is {@code null}.
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deduct(Object,Number)
	 */
	public void deductAll(Collection<? extends K> keys, V value);

	/**
	 * Applies the {@link #deduct(Object, Number) deduct} operation to every
	 * key in the specified array and the specified value (optional
	 * operation).
	 *
	 * @param keys the array containing all the keys to be modified.
	 * @param value the value to be deducted to all the keys in the array.
	 *
	 * @throws NullPointerException if the specified array is {@code null}, or
	 * if this {@code Counter} does not permit {@code null} keys and the
	 * specified array contains {@code null} keys, or if the specified value
	 * is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deduct(Object,Number)
	 */
	public void deductAll(K[] keys, V value);

	/**
	 * Deducts one to the current value associated with every key in this
	 * {@code Counter} (optional operation).
	 *
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deductToAll(Number)
	 */
	public void deductToAll();

	/**
	 * Deducts the specified value to the current value associated with every
	 * key in this {@code Counter} (optional operation).
	 *
	 * @param value value to be deducted to the previous value associated with
	 * the keys
	 *
	 * @throws NullPointerException if the specified value is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deduct(Object,Number)
	 */
	public void deductToAll(V value);

	/**
	 * Deducts the same quantity (generally {@code 1} divided by
	 * {@code keys.}{@link java.util.Collection#size() size()}) to the current
	 * value associated with every key in the specified {@code Collection}
	 * (optional operation). If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys keys whose values are to be modified
	 *
	 * @throws NullPointerException if the specified {@code Collection} is
	 * {@code null}, or if any of the keys in the {@code Collection} is
	 * {@code null} and this {@code Counter} does not permit {@code null} keys
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deduct(Object,Number)
	 */
	public void balancedDeduct(Collection<? extends K> keys);

	/**
	 * Deducts the same quantity (generally {@code 1} divided by
	 * {@code keys.length}) to the current value associated with every key in
	 * the specified array (optional operation). If the key is not already
	 * present in this {@code Counter}, this method behaves as if it was
	 * present with an associated value of {@code 0} (zero).
	 *
	 * @param keys keys whose values are to be modified
	 *
	 * @throws NullPointerException if the specified array is {@code null}, or
	 * if any of the keys in the array is {@code null} and this
	 * {@code Counter} does not permit {@code null} keys
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deduct(Object,Number)
	 */
	public void balancedDeduct(K[] keys);

	/**
	 * Deducts the same quantity, based on {@code value} (generally
	 * {@code value} divided by
	 * {@code keys.}{@link java.util.Collection#size() size()}), to the
	 * current value associated with every key in the specified
	 * {@code Collection} (optional operation). If the key is not already
	 * present in this {@code Counter}, this method behaves as if it was
	 * present with an associated value of {@code 0} (zero).
	 *
	 * @param keys keys whose values are to be modified
	 * @param value value from which it is calculated the value to be deducted
	 * to the previous value associated with the keys
	 *
	 * @throws NullPointerException if the specified {@code Collection} is
	 * {@code null}, or if any of the keys in the {@code Collection} is
	 * {@code null} and this {@code Counter} does not permit {@code null}
	 * keys, or if the specified {@code value} is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deduct(Object,Number)
	 */
	public void balancedDeduct(Collection<? extends K> keys, V value);

	/**
	 * Deducts the same quantity, based on {@code value} (generally
	 * {@code value} divided by {@code keys.length}), to the current value
	 * associated with every key in the specified array (optional operation).
	 * If the key is not already present in this {@code Counter}, this method
	 * behaves as if it was present with an associated value of {@code 0}
	 * (zero).
	 *
	 * @param keys keys whose values are to be modified
	 * @param value value from which it is calculated the value to be deducted
	 * to the previous value associated with the keys
	 *
	 * @throws NullPointerException if the specified array is {@code null}, or
	 * if any of the keys in the array is {@code null} and this
	 * {@code Counter} does not permit {@code null} keys, or if the specified
	 * {@code value} is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deduct(Object,Number)
	 */
	public void balancedDeduct(K[] keys, V value);

	/**
	 * Deducts the same quantity (generally {@code 1} divided by
	 * {@link #size() this.size()}) to the current value associated with every
	 * key in this {@code Counter} (optional operation).
	 *
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deduct(Object,Number)
	 * @see #deductToAll(Number)
	 */
	public void balancedDeductToAll();

	/**
	 * Deducts the same quantity, based on {@code value} (generally
	 * {@code value} divided by {@link #size() this.size()}), to the current
	 * value associated with every key in this {@code Counter} (optional
	 * operation).
	 *
	 * @param value value from which it is calculated the value to be deducted
	 * to the previous value associated with the keys
	 *
	 * @throws NullPointerException if the specified {@code value} is
	 * {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see #deduct(Object,Number)
	 * @see #deductToAll(Number)
	 */
	public void balancedDeductToAll(V value);

	/**
	 * Copies all the mappings from the specified {@code Map} to this
	 * {@code Counter} (optional operation). If any mapping existed in this
	 * {@code Counter} for any key in {@code map}, it is replaced by the new
	 * one.
	 *
	 * @param map the {@code Map} whose entries are to be put to this
	 * {@code Counter}
	 *
	 * @throws NullPointerException if the specified {@code Map} is
	 * {@code null}, or if any of the keys in the {@code Map} is {@code null}
	 * and this {@code Counter} does not permit {@code null} keys, or if any
	 * of the values in the {@code Map} is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see java.util.Map#putAll(Map)
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> map);

	/**
	 * Copies all the mappings from the specified {@code Map} to this
	 * {@code Counter}, by previously applying the
	 * {@link es.iguanod.util.Caster#cast(Object) Caster.cast} operation to
	 * the values (optional operation). If any mapping existed in this
	 * {@code Counter} for any key in {@code map}, it is replaced by the new
	 * one.
	 *
	 * @param <X> the class of the values in the specified {@code Map}
	 *
	 * @param map the {@code Map} whose entries are to be put to this
	 * {@code Counter}
	 * @param caster the {@code Caster} used to cast the values from the
	 * {@code Map} to values appropriate to be used in this {@code Counter}
	 *
	 * @throws NullPointerException if the specified {@code Map} or the
	 * specified {@code Caster} is {@code null}, or if any of the keys in the
	 * {@code Map} is {@code null} and this {@code Counter} does not permit
	 * {@code null} keys, or if any of the values in the {@code Map} is
	 * {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 * @throws RuntimeException this method may throw any {@code Exception}
	 * thrown by the {@code caster}
	 *
	 * @see java.util.Map#putAll(Map)
	 * @see es.iguanod.util.Caster
	 */
	public <X> void putAll(Map<? extends K, ? extends X> map, Caster<X, V> caster);

	/**
	 * Sets the value associated with the specified key to the greatest
	 * between its current value and the specified value (optional operation).
	 * If the key is not already present in this {@code Counter}, this method
	 * behaves as if it was present with an associated value of minus
	 * infinity.
	 *
	 * <p>More formally, it behaves as {@code (}{@link java.util.Map#containsKey
	 * containsKey}{@code (key) ? }{@link java.lang.Math#max max}{@code
	 * (value,}{@link java.util.Map#get get}{@code (key)) : }{@link
	 * java.util.Map#put(Object,Object) put}{@code (key,value)}.</p>
	 *
	 * @param key key which value is to be modified
	 * @param value candidate value to replace to the previous value
	 * associated with {@code key}
	 *
	 * @return a tuple containing in its first place the previous value
	 * associated with {@code key}, or {@code null} if the key was not
	 * contained in the {@code Counter}; and in its second place the new value
	 * associated with {@code key}
	 *
	 * @throws NullPointerException if the specified key is {@code null} and
	 * this {@code Counter} does not permit {@code null} keys, or if the
	 * specified value is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see java.util.Map#put(Object,Object)
	 */
	public Tuple2<V, V> putMax(K key, V value);

	/**
	 * Sets the value associated with the specified key to the least between
	 * its current value and the specified value (optional operation). If the
	 * key is not already present in this {@code Counter}, this method behaves
	 * as if it was present with an associated value of infinity.
	 *
	 * <p>More formally, it behaves as {@code (}{@link java.util.Map#containsKey
	 * containsKey}{@code (key) ? }{@link java.lang.Math#min min}{@code
	 * (value,}{@link java.util.Map#get get}{@code (key)) : }{@link
	 * java.util.Map#put put}{@code (key,value)}.</p>
	 *
	 * @param key key which value is to be modified
	 * @param value candidate value to replace to the previous value
	 * associated with {@code key}
	 *
	 * @return a tuple containing in its first place the previous value
	 * associated with {@code key}, or {@code null} if the key was not
	 * contained in the {@code Counter}; and in its second place the new value
	 * associated with {@code key}
	 *
	 * @throws NullPointerException if the specified key is {@code null} and
	 * this {@code Counter} does not permit {@code null} keys, or if the
	 * specified value is {@code null}
	 * @throws UnsupportedOperationException if this {@code Counter} is
	 * unmodifiable and so this operation is not supported
	 *
	 * @see java.util.Map#put(Object,Object)
	 */
	public Tuple2<V, V> putMin(K key, V value);
}
