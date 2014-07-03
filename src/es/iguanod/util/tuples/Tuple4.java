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

import es.iguanod.base.Objects;
import java.io.Serializable;

/**
 * Class representing an immutable tuple of 4 elements. The objects stored in
 * the tuple may be {@code null}.
 *
 * @param <Fst> the class of the first element
 * @param <Snd> the class of the second element
 * @param <Trd> the class of the third element
 * @param <Fth> the class of the fourth element
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1.1.cb
 * @version 1.0.1.1.cb
 *
 * @see java.lang.Comparable
 * @see java.io.Serializable
 */
public class Tuple4<Fst, Snd, Trd, Fth> implements Comparable<Tuple4<Fst, Snd, Trd, Fth>>, Serializable{

	private static final long serialVersionUID=8584500108705140584L;
	//************
	/**
	 * The first element.
	 */
	protected Fst first;
	/**
	 * The second element.
	 */
	protected Snd second;
	/**
	 * The third element.
	 */
	protected Trd third;
	/**
	 * The fourth element.
	 */
	protected Fth fourth;
	//************
	/**
	 * Backed tuple implementation to return a less-size-tuple view of this
	 * one.
	 */
	private BackedTupleImpl backed=new BackedTupleImpl();

	/**
	 * Constructs a {@code Tuple4} with the specified elements.
	 *
	 * @param first the first element of the tuple
	 * @param second the second element of the tuple
	 * @param third the third element of the tuple
	 * @param fourth the fourth element of the tuple
	 */
	public Tuple4(Fst first, Snd second, Trd third, Fth fourth){
		this.first=first;
		this.second=second;
		this.third=third;
		this.fourth=fourth;
	}

	/**
	 * Constructor for the BackedTuple.
	 */
	Tuple4(){
	}

	/**
	 * Getter of the first element of this {@code Tuple4}.
	 *
	 * @return the first element of the tuple
	 */
	public Fst getFirst(){
		return first;
	}

	/**
	 * Getter of the second element of this {@code Tuple4}.
	 *
	 * @return the second element of the tuple
	 */
	public Snd getSecond(){
		return second;
	}

	/**
	 * Getter of the third element of this {@code Tuple4}.
	 *
	 * @return the third element of the tuple
	 */
	public Trd getThird(){
		return third;
	}

	/**
	 * Getter of the fourth element of this {@code Tuple4}.
	 *
	 * @return the fourth element of the tuple
	 */
	public Fth getFourth(){
		return fourth;
	}

	/**
	 * Compares this object with the specified object for order. Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 *
	 * <p>The comparison is done in order for the elements of the tuple: if
	 * the first elements of both tuples compare to non-zero that value is
	 * returned, otherwise the second elements are compared and so on.</p>
	 *
	 * <p>For the comparison
	 * {@link es.iguanod.base.Objects#compare(Object,Object) Objects.compare}
	 * is used, so it is {@code null}-safe and {@code nulls} compare to zero
	 * with other {@code nulls} and less than zero with anything else.</p>
	 *
	 * @throws NullPointerException if {@code tuple} is {@code null}
	 * @throws ClassCastException if any of the elements in this tuple doesn't
	 * implement {@link java.lang.Comparable Comparable} or if the i-th
	 * element of this tuple and the i-th element of the compared tuple aren't
	 * mutually comparable, for any 1&le;i&le;4. Note that this doesn't imply
	 * that said elements must be {@code Comparable} and mutually comparable,
	 * only that this method cannot be used if they are not.
	 */
	@Override
	public int compareTo(Tuple4<Fst, Snd, Trd, Fth> tuple){
		int ret;
		if((ret=Objects.compare(this.getFirst(), tuple.getFirst())) == 0){
			if((ret=Objects.compare(this.getSecond(), tuple.getSecond())) == 0){
				if((ret=Objects.compare(this.getThird(), tuple.getThird())) == 0){
					return Objects.compare(this.getFourth(), tuple.getFourth());
				}
			}
		}
		return ret;
	}

	/**
	 * Indicates whether some other object is "equal to" this one. Two objects
	 * are considered equal if both are {@code Tuple4} and the i-th element of
	 * this tuple and the i-th element of the compared tuple are equal, for
	 * any 1&le;i&le;4. Equality is determined with
	 * {@link es.iguanod.base.Objects#equals(Object,Object) Objects.equals}.
	 *
	 * <p>In every other aspect this method follows the general contract of
	 * {@link java.lang.Object#equals(Object) Object.equals}.</p>
	 *
	 * @param obj the object to be tested for equality
	 *
	 * @return {@code true} if both objects are equals, {@code false}
	 * otherwise
	 */
	@Override
	public boolean equals(Object obj){
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(obj instanceof Tuple4){
			return Objects.equals(this.getFirst(), (((Tuple4)obj).getFirst()))
			&& Objects.equals(this.getSecond(), (((Tuple4)obj).getSecond()))
			&& Objects.equals(this.getThird(), (((Tuple4)obj).getThird()))
			&& Objects.equals(this.getFourth(), (((Tuple4)obj).getFourth()));
		}else{
			return false;
		}
	}

	/**
	 * Returns a hashCode value for this {@code Tuple4}.
	 *
	 * <p>This method follows the general contract of
	 * {@link java.lang.Object#hashCode() Object.hashCode}.</p>
	 *
	 * @return the hashCode of this object
	 */
	@Override
	public int hashCode(){
		int hash=7;
		hash=79 * hash + Objects.hashCode(this.getFirst());
		hash=79 * hash + Objects.hashCode(this.getSecond());
		hash=79 * hash + Objects.hashCode(this.getThird());
		hash=79 * hash + Objects.hashCode(this.getFourth());
		return hash;
	}

	/**
	 * Returns a {@code String} representation of this {@code Tuple4}.
	 *
	 * <p>This method returns a String consisting of parenthesis enclosing the
	 * String representation of each element of the tuple, separated with
	 * {@code ", "}.</p>
	 *
	 * @return the {@code String} representation
	 */
	@Override
	public String toString(){
		return "(" + getFirst() + ", " + getSecond() + ", " + getThird() + ", " + getFourth() + ")";
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple2 Tuple2} representation
	 * of this {@code Tuple4}. The returned tuple is backed up by this one.
	 *
	 * @return the {@code Tuple2} view of this {@code Tuple4}
	 */
	public Tuple2 asTuple2(){
		return new BackedTuple2(backed);
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple3 Tuple3} representation
	 * of this {@code Tuple4}. The returned tuple is backed up by this one.
	 *
	 * @return the {@code Tuple3} view of this {@code Tuple4}
	 */
	public Tuple3 asTuple3(){
		return new BackedTuple3(backed);
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
				case 3:
					return getThird();
				default:
					return null;
			}
		}
	}
}

/**
 * Tuple4 that gets its data from another tuple.
 */
class BackedTuple4<Fst, Snd, Trd, Fth> extends Tuple4<Fst, Snd, Trd, Fth>{

	private static final long serialVersionUID=8752873351860311873L;
	private BackedTuple tuple;

	public BackedTuple4(BackedTuple tuple){
		this.tuple=tuple;
	}

	@Override
	public Fst getFirst(){
		return (Fst)tuple.get(1);
	}

	@Override
	public Snd getSecond(){
		return (Snd)tuple.get(2);
	}

	@Override
	public Trd getThird(){
		return (Trd)tuple.get(3);
	}

	@Override
	public Fth getFourth(){
		return (Fth)tuple.get(4);
	}
}
