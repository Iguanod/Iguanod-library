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
package es.iguanod.util.tuples;

/**
 * Class representing a mutable tuple of 3 elements. The objects stored in the
 * tuple may be {@code null}.
 *
 * @param <F> the class of the first element
 * @param <S> the class of the second element
 * @param <T> the class of the third element
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1.1.cb
 * @version 1.0.1.1.cb
 *
 * @see java.lang.Comparable
 * @see java.io.Serializable
 */
public class Tuple3M<F, S, T> extends Tuple3<F, S, T>{

	private static final long serialVersionUID=3317272342212783898L;
	//************
	/**
	 * Backed tuple implementation to return a less-size-tuple view of this
	 * one.
	 */
	private BackedTupleImplM backed=new BackedTupleImplM();

	/**
	 * Constructs a {@code Tuple3M} with the specified elements.
	 *
	 * @param first the first element of the tuple
	 * @param second the second element of the tuple
	 * @param third the third element of the tuple
	 */
	public Tuple3M(F first, S second, T third){
		super(first, second, third);
	}

	/**
	 * Constructor for the BackedTuple.
	 */
	Tuple3M(){
	}

	/**
	 * Setter of the first element of this {@code Tuple3M}.
	 *
	 * @return the previous value of the first element of the tuple
	 */
	public F setFirst(F first){
		F ret=this.first;
		this.first=first;
		return ret;
	}

	/**
	 * Setter of the second element of this {@code Tuple3M}.
	 *
	 * @return the previous value of the second element of the tuple
	 */
	public S setSecond(S second){
		S ret=this.second;
		this.second=second;
		return ret;
	}

	/**
	 * Setter of the third element of this {@code Tuple3M}.
	 *
	 * @return the previous value of the third element of the tuple
	 */
	public T setThird(T third){
		T ret=this.third;
		this.third=third;
		return ret;
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple2M Tuple2M} representation
	 * of this {@code Tuple3M}. The returned tuple is backed up by this tuple,
	 * that is, changes to the returned tuple write through this one and
	 * viceversa.
	 *
	 * @return the {@code Tuple2M} view of this {@code Tuple3M}
	 */
	public Tuple2M asTuple2M(){
		return new BackedTuple2M(backed);
	}

	/**
	 * Backed tuple implementation to return a less-size-tuple view of this
	 * one.
	 */
	class BackedTupleImplM extends BackedTupleImpl implements BackedTupleM{

		@Override
		public Object set(int i, Object value){
			switch(i){
				case 1:
					return setFirst((F)value);
				case 2:
					return setSecond((S)value);
				case 3:
					return setThird((T)value);
				default:
					return null;
			}
		}
	}
}

/**
 * Tuple3M that gets and sets its data from and to another tuple.
 */
class BackedTuple3M<F, S, T> extends Tuple3M<F, S, T>{

	private static final long serialVersionUID=2308089096857587298L;
	private BackedTupleM tuple;

	public BackedTuple3M(BackedTupleM tuple){
		this.tuple=tuple;
	}

	@Override
	public F getFirst(){
		return (F)tuple.get(1);
	}

	@Override
	public S getSecond(){
		return (S)tuple.get(2);
	}

	@Override
	public T getThird(){
		return (T)tuple.get(3);
	}

	@Override
	public F setFirst(F first){
		return (F)tuple.set(1, first);
	}

	@Override
	public S setSecond(S second){
		return (S)tuple.set(2, second);
	}

	@Override
	public T setThird(T third){
		return (T)tuple.set(3, third);
	}
}
