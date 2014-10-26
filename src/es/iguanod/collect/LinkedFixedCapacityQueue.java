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
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Implementation of the {@link es.iguanod.collect.FixedCapacityQueue} interface
 * based on a {@link java.util.LinkedList}.
 * <p>
 * This implementation accepts {@code null} elements.</p>
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @param <T> the class of the elements being stored
 *
 * @since 1.0.1.b
 * @version 1.0.1.b
 */
public class LinkedFixedCapacityQueue<T> extends AbstractCollection<T> implements FixedCapacityQueue<T>, Serializable{

	private static final long serialVersionUID=1249212336486513005L;
	//**********
	/**
	 * Maximum capacity of the queue.
	 */
	private int capacity;
	/**
	 * LinkedList that stores the elements.
	 */
	private LinkedList<T> queue=new LinkedList<>();

	/**
	 * Constructs a new {@code LinkedFixedCapacityQueue} with the specified
	 * maximum capacity.
	 *
	 * @param capacity the maximum capacity of the queue
	 *
	 * @throws IllegalArgumentException if {@code capacity} is non-possitive
	 */
	public LinkedFixedCapacityQueue(int capacity){
		if(capacity <= 0){
			throw new IllegalArgumentException("The capacity has to be possitive");
		}
		this.capacity=capacity;
	}

	/**
	 * Inserts the specified element in the queue, retrieving and removing the
	 * oldest element in case the queue is full.
	 *
	 * @param elem the element to be inserted
	 *
	 * @return {@link es.iguanod.util.Maybe#ABSENT Maybe.ABSENT} if the queue
	 * wasn't full prior to this call, or a {@link es.iguanod.util.Maybe}
	 * containing the removed element if it was
	 */
	@Override
	public Maybe<T> push(T elem){
		queue.addLast(elem);
		if(queue.size() > capacity){
			return Maybe.from(queue.removeFirst());
		}else{
			return Maybe.ABSENT;
		}
	}

	/**
	 * Retrieves and removes the oldest element of the queue, if there's any.
	 *
	 * @return {@link es.iguanod.util.Maybe#ABSENT Maybe.ABSENT} if the queue
	 * was empty prior to this call, or a {@link es.iguanod.util.Maybe}
	 * containing the removed element if it wasn't
	 */
	@Override
	public Maybe<T> pop(){
		if(queue.isEmpty()){
			return Maybe.ABSENT;
		}else{
			return Maybe.from(queue.removeFirst());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Maybe<T> peek(){
		if(queue.isEmpty()){
			return Maybe.ABSENT;
		}else{
			return Maybe.from(queue.peekFirst());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int capacity(){
		return capacity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size(){
		return queue.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFull(){
		return queue.size() == capacity;
	}

	/**
	 * Returns an {@link java.util.Iterator} over the elements in this
	 * collection. The elements are returned started with the oldest one and
	 * finishing with the newest one.
	 * <p>
	 * The {@code Iterator} supports the
	 * {@link java.util.Iterator#remove() remove} operation.</p>
	 *
	 * @return the {@code Iterator}
	 */
	@Override
	public Iterator<T> iterator(){
		return new Iterator<T>(){

			private Iterator<T> iter=queue.iterator();

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
				iter.remove();
			}
		};
	}

	/**
	 * Inserts the specified element in the queue, removing the oldest element
	 * in case the queue is full (optional operation). This method behaves
	 * just as {@link #push(Object) push(elem)} except it doesn't retrieve the
	 * removed element (if any) and should always returns true.
	 *
	 * @param elem the element to be inserted
	 *
	 * @return {@code true}
	 */
	@Override
	public boolean add(T elem){
		queue.addLast(elem);
		if(queue.size() > capacity){
			queue.removeFirst();
		}
		return true;
	}

	/**
	 * Adds all of the elements in the specified {@code Collection} to this
	 * collection.
	 * <p>
	 * If the specified {@code Collection} has more elements than space has
	 * this queue, the oldest elements are removed to create space for the new
	 * ones. If the specified {@code Collection} has more elements than the
	 * capacity of the queue, then only the last nth elements (where n is the
	 * capacity of the queue) returned by the {@code Collection}'s iterator
	 * are added, and all the elements contained in the queue prior to this
	 * call are removed.</p>
	 * <p>
	 * The behavior of this operation is undefined if the specified
	 * {@code Collection} is modified while the operation is in progress.
	 * (This implies that the behavior of this call is undefined if the
	 * specified {@code Collection} is this {@code Collection}, and this
	 * {@code Collection} is nonempty).</p>
	 *
	 * @param col the {@code Collection}
	 *
	 * @return {@code true} if the specified {@code Collection} is nonempty,
	 * {@code false} otherwise
	 */
	@Override
	public boolean addAll(Collection<? extends T> col){
		return super.addAll(col);
	}
}
