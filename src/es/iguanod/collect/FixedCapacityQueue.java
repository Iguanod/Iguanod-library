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

import es.iguanod.util.Maybe;
import java.util.Collection;

/**
 * This interface represents a LIFO queue of a limited capacity, in which if an
 * element is inserted when the queue is full, the oldest element is
 * automatically removed.
 * <p>
 * It is recommended that all classes that implement this interface offer a
 * constructor that accepts as a parameter the capacity of the queue (which must
 * be possitive).</p>
 * <p>
 * {@code FixedCapacityQueues} may or may not accept {@code null} values, but
 * unlike in the regular {@link java.util.Queue}, nothing in its design
 * discourage it.</p>
 *
 * @param <T> the class of the elements being stored
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 *
 * @since 1.0.1.b
 * @version 1.0.1.b
 */
public interface FixedCapacityQueue<T> extends Collection<T>{

	/**
	 * Inserts the specified element in the queue, retrieving and removing the
	 * oldest element in case the queue is full.
	 *
	 * @param elem the element to be inserted
	 *
	 * @return {@link es.iguanod.util.Maybe#ABSENT Maybe.ABSENT} if the queue
	 * wasn't full prior to this call, or a {@link es.iguanod.util.Maybe}
	 * containing the removed element if it was
	 *
	 * @throws NullPointerException if {@code elem} is {@code null} and the
	 * implementation of {@code FixedCapacityQueue} doesn't allow {@code null}
	 * elements
	 */
	public Maybe<T> push(T elem);

	/**
	 * Retrieves and removes the oldest element of the queue, if there's any.
	 *
	 * @return {@link es.iguanod.util.Maybe#ABSENT Maybe.ABSENT} if the queue
	 * was empty prior to this call, or a {@link es.iguanod.util.Maybe}
	 * containing the removed element if it wasn't
	 */
	public Maybe<T> pop();

	/**
	 * Retrieves without removing the oldest element of the queue, if there's
	 * any. That element is the one that would get returned by the next call
	 * to {@link #push(Object) push} or {@link #pop() pop} if the state of the
	 * queue doesn't change in between.
	 *
	 * @return {@link es.iguanod.util.Maybe#ABSENT Maybe.ABSENT} if the queue
	 * was empty prior to this call, or a {@link es.iguanod.util.Maybe}
	 * containing the oldest element if it wasn't
	 */
	public Maybe<T> peek();

	/**
	 * Returns the maximum capacity of this {@code FixedCapacityQueue}. If the
	 * capacity is higher than {@link java.lang.Integer#MAX_VALUE}, then
	 * {@code Integer.MAX_VALUE} is returned.
	 *
	 * @return the capacity
	 */
	public int capacity();

	/**
	 * Tells wether this queue is full. Note that being full doesn't mean it
	 * doesn't accept more elements, but if an element is inserted the oldest
	 * one will be removed.
	 *
	 * @return {@code true} if the queue is full, {@code false} otherwise
	 */
	public boolean isFull();

	/**
	 * Inserts the specified element in the queue, removing the oldest element
	 * in case the queue is full. This method behaves just as
	 * {@link #push(Object)} except it doesn't retrieve the removed element
	 * (if any) and should always returns true.
	 * <p>
	 * Note that unlike {@link java.util.Collection#add(Object)}, this
	 * operation is not optional.</p>
	 *
	 * @param elem the element to be inserted
	 *
	 * @return true
	 *
	 * @throws NullPointerException if {@code elem} is {@code null} and the
	 * implementation of {@code FixedCapacityQueue} doesn't allow {@code null}
	 * elements
	 */
	@Override
	public boolean add(T elem);
}
