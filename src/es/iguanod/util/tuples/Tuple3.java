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

import es.iguanod.base.Objects;
import java.io.Serializable;

/**
 * Class representing an immutable tuple of 3 elements. The objects stored in
 * the tuple may be {@code null}.
 *
 * @param <F> the class of the first element
 * @param <S> the class of the second element
 * @param <T> the class of the third element
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 *
 * @see java.lang.Comparable
 * @see java.io.Serializable
 */
public class Tuple3<F, S, T> implements Comparable<Tuple3<F, S, T>>, Serializable{

	private static final long serialVersionUID=7265404223128636207L;
	//************
	/**
	 * The first element.
	 */
	protected F first;
	/**
	 * The second element.
	 */
	protected S second;
	/**
	 * The third element.
	 */
	protected T third;
	//************
	/**
	 * Backed tuple implementation to return a less-size-tuple view of this
	 * one.
	 */
	private BackedTupleImpl backed=new BackedTupleImpl();

	/**
	 * Constructs a {@code Tuple3} with the specified elements.
	 *
	 * @param first the first element of the tuple
	 * @param second the second element of the tuple
	 * @param third the third element of the tuple
	 */
	public Tuple3(F first, S second, T third){
		this.first=first;
		this.second=second;
		this.third=third;
	}

	/**
	 * Constructor for the BackedTuple.
	 */
	Tuple3(){
	}

	/**
	 * Getter of the first element of this {@code Tuple3}.
	 *
	 * @return the first element of the tuple
	 */
	public F getFirst(){
		return first;
	}

	/**
	 * Getter of the second element of this {@code Tuple3}.
	 *
	 * @return the second element of the tuple
	 */
	public S getSecond(){
		return second;
	}

	/**
	 * Getter of the third element of this {@code Tuple3}.
	 *
	 * @return the third element of the tuple
	 */
	public T getThird(){
		return third;
	}

	/**
	 * Compares this object with the specified object for order. Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 * <p>
	 * The comparison is done in order for the elements of the tuple: if the
	 * first elements of both tuples compare to non-zero that value is
	 * returned, otherwise the second elements are compared and so on.</p>
	 * <p>
	 * For the comparison
	 * {@link es.iguanod.base.Objects#compare(Object,Object) Objects.compare}
	 * is used, so it is {@code null}-safe and {@code nulls} compare to zero
	 * with other {@code nulls} and less than zero with anything else.</p>
	 *
	 * @throws NullPointerException if {@code tuple} is {@code null}
	 * @throws ClassCastException if any of the elements in this tuple doesn't
	 * implement {@link java.lang.Comparable Comparable} or if the i-th
	 * element of this tuple and the i-th element of the compared tuple aren't
	 * mutually comparable, for any 1&le;i&le;3. Note that this doesn't imply
	 * that said elements must be {@code Comparable} and mutually comparable,
	 * only that this method cannot be used if they are not.
	 */
	@Override
	public int compareTo(Tuple3<F, S, T> tuple){
		int ret;
		if((ret=Objects.compare(this.getFirst(), tuple.getFirst())) == 0){
			if((ret=Objects.compare(this.getSecond(), tuple.getSecond())) == 0){
				return Objects.compare(this.getThird(), tuple.getThird());
			}
		}
		return ret;
	}

	/**
	 * Indicates whether some other object is "equal to" this one. Two objects
	 * are considered equal if both are {@code Tuple3} and the i-th element of
	 * this tuple and the i-th element of the compared tuple are equal, for
	 * any 1&le;i&le;3. Equality is determined with
	 * {@link es.iguanod.base.Objects#equals(Object,Object) Objects.equals}.
	 * <p>
	 * In every other aspect this method follows the general contract of
	 * {@link java.lang.Object#equals(Object) Object.equals}.</p>
	 *
	 * @param obj the object to be tested for equality
	 *
	 * @return {@code true} if both objects are equals, {@code false}
	 * otherwise
	 */
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(obj == this){
			return true;
		}
		if(obj instanceof Tuple3){
			return Objects.equals(this.getFirst(), (((Tuple3)obj).getFirst()))
			&& Objects.equals(this.getSecond(), (((Tuple3)obj).getSecond()))
			&& Objects.equals(this.getThird(), (((Tuple3)obj).getThird()));
		}else{
			return false;
		}
	}

	/**
	 * Returns a hashCode value for this {@code Tuple3}.
	 * <p>
	 * This method follows the general contract of
	 * {@link java.lang.Object#hashCode() Object.hashCode}.</p>
	 *
	 * @return the hashCode of this object
	 */
	@Override
	public int hashCode(){
		int hash=7;
		hash=67 * hash + Objects.hashCode(this.getFirst());
		hash=67 * hash + Objects.hashCode(this.getSecond());
		hash=67 * hash + Objects.hashCode(this.getThird());
		return hash;
	}

	/**
	 * Returns a {@code String} representation of this {@code Tuple3}.
	 * <p>
	 * This method returns a String consisting of parenthesis enclosing the
	 * String representation of each element of the tuple, separated with
	 * {@code ", "}.</p>
	 *
	 * @return the {@code String} representation
	 */
	@Override
	public String toString(){
		return "(" + getFirst() + ", " + getSecond() + ", " + getThird() + ")";
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple2 Tuple2} representation
	 * of this {@code Tuple3}. The returned tuple is backed up by this one.
	 *
	 * @return the {@code Tuple2} view of this {@code Tuple3}
	 */
	public Tuple2 asTuple2(){
		return new BackedTuple2(backed);
	}

	/**
	 * Backed tuple implementation to return a less-size-tuple view of this
	 * one.
	 */
	class BackedTupleImpl implements BackedTuple{

		@Override
		public Object get(int i){
			switch(i){
				case 1:
					return getFirst();
				case 2:
					return getSecond();
				default:
					return null;
			}
		}
	}
}

/**
 * Tuple3 that gets its data from another tuple.
 */
class BackedTuple3<F, S, T> extends Tuple3<F, S, T>{

	private static final long serialVersionUID=-5078065028977472734L;
	private BackedTuple tuple;

	public BackedTuple3(BackedTuple tuple){
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
}
