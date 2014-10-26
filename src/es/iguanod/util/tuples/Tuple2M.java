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
package es.iguanod.util.tuples;

/**
 * Class representing a mutable tuple of 2 elements. The objects stored in the
 * tuple may be {@code null}.
 *
 * @param <F> the class of the first element
 * @param <S> the class of the second element
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 *
 * @see java.lang.Comparable
 * @see java.io.Serializable
 */
public class Tuple2M<F, S> extends Tuple2<F, S>{

	private static final long serialVersionUID=8030092401246972689L;

	/**
	 * Constructs a {@code Tuple2M} with the specified elements.
	 *
	 * @param first the first element of the tuple
	 * @param second the second element of the tuple
	 */
	public Tuple2M(F first, S second){
		super(first, second);
	}

	/**
	 * Constructor for the BackedTuple.
	 */
	Tuple2M(){
	}

	/**
	 * Setter of the first element of this {@code Tuple2M}.
	 *
	 * @return the previous value of the first element of the tuple
	 */
	public F setFirst(F first){
		F ret=this.first;
		this.first=first;
		return ret;
	}

	/**
	 * Setter of the second element of this {@code Tuple2M}.
	 *
	 * @return the previous value of the second element of the tuple
	 */
	public S setSecond(S second){
		S ret=this.second;
		this.second=second;
		return ret;
	}
}

/**
 * Tuple2M that gets and sets its data from and to another tuple.
 */
class BackedTuple2M<F, S> extends Tuple2M<F, S>{

	private static final long serialVersionUID=3658739324721015554L;
	private BackedTupleM tuple;

	public BackedTuple2M(BackedTupleM tuple){
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
	public F setFirst(F first){
		return (F)tuple.set(1, first);
	}

	@Override
	public S setSecond(S second){
		return (S)tuple.set(2, second);
	}
}
