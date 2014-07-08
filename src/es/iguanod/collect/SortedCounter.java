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

import java.util.Map.Entry;
import java.util.Set;

/**
 * Extension of the {@code Counter} interface to add methods related with
 * {@code Counters} that maintain an order in their values.
 *
 * <p>Note that although {@code Counter} extends {@link java.util.Map},
 * {@code SortedCounter} does <b>not</b> extend {@link java.util.SortedMap}, and
 * their behaviour should not be confused: {@code SortedCounters} maintain an
 * ordering in their values, while {@code SortedMaps} maintain an ordering in
 * their keys.</p>
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1.1.cb
 * @version 1.0.1.1.b
 */
public interface SortedCounter<K, V extends Number> extends Counter<K, V>{

	/**
	 * Returns the highest value of this {@code SortedCounter}.
	 *
	 * @return the highest value of this {@code SortedCounter}
	 */
	public V maxValue();

	/**
	 * Returns the lowest value of this {@code SortedCounter}.
	 *
	 * @return the lowest value of this {@code SortedCounter}
	 */
	public V minValue();

	public Set<K> maxKeySet();

	public Set<K> minKeySet();

	public Set<K> keySet(V value);

	public Set<Entry<V, Set<K>>> inverseEntrySet();

	public SortedCounter<K, V> tailCounter(V from_value);

	public SortedCounter<K, V> tailCounter(V from_value, boolean inclusive);

	public SortedCounter<K, V> headCounter(V to_value);

	public SortedCounter<K, V> headCounter(V to_value, boolean inclusive);

	public SortedCounter<K, V> subCounter(V from_value, V to_value);

	public SortedCounter<K, V> subCounter(V from_value, boolean from_inclusive, V to_value, boolean to_inclusive);
}
