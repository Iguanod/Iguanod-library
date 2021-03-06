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
 * Class representing an immutable tuple of 5 elements. The objects stored in
 * the tuple may be {@code null}.
 *
 * @param <Fst> the class of the first element
 * @param <Snd> the class of the second element
 * @param <Trd> the class of the third element
 * @param <Fth> the class of the fourth element
 * @param <Ffth> the class of the fifth element
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 *
 * @see java.lang.Comparable
 * @see java.io.Serializable
 */
public class Tuple5<Fst, Snd, Trd, Fth, Ffth> implements Comparable<Tuple5<Fst, Snd, Trd, Fth, Ffth>>, Serializable{

	private static final long serialVersionUID=5507282598083156314L;
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
	/**
	 * The fifth element.
	 */
	protected Ffth fifth;
	//************
	/**
	 * Backed tuple implementation to return a less-size-tuple view of this
	 * one.
	 */
	private BackedTupleImpl backed=new BackedTupleImpl();

	/**
	 * Constructs a {@code Tuple5} with the specified elements.
	 *
	 * @param first the first element of the tuple
	 * @param second the second element of the tuple
	 * @param third the third element of the tuple
	 * @param fourth the fourth element of the tuple
	 * @param fifth the fifth element of the tuple
	 */
	public Tuple5(Fst first, Snd second, Trd third, Fth fourth, Ffth fifth){
		this.first=first;
		this.second=second;
		this.third=third;
		this.fourth=fourth;
		this.fifth=fifth;
	}

	/**
	 * Constructor for the BackedTuple.
	 */
	Tuple5(){
	}

	/**
	 * Getter of the first element of this {@code Tuple5}.
	 *
	 * @return the first element of the tuple
	 */
	public Fst getFirst(){
		return first;
	}

	/**
	 * Getter of the second element of this {@code Tuple5}.
	 *
	 * @return the second element of the tuple
	 */
	public Snd getSecond(){
		return second;
	}

	/**
	 * Getter of the third element of this {@code Tuple5}.
	 *
	 * @return the third element of the tuple
	 */
	public Trd getThird(){
		return third;
	}

	/**
	 * Getter of the fourth element of this {@code Tuple5}.
	 *
	 * @return the fourth element of the tuple
	 */
	public Fth getFourth(){
		return fourth;
	}

	/**
	 * Getter of the fifth element of this {@code Tuple5}.
	 *
	 * @return the fifth element of the tuple
	 */
	public Ffth getFifth(){
		return fifth;
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
	 * mutually comparable, for any 1&le;i&le;5. Note that this doesn't imply
	 * that said elements must be {@code Comparable} and mutually comparable,
	 * only that this method cannot be used if they are not.
	 */
	@Override
	public int compareTo(Tuple5<Fst, Snd, Trd, Fth, Ffth> tuple){
		int ret;
		if((ret=Objects.compare(this.getFirst(), tuple.getFirst())) == 0){
			if((ret=Objects.compare(this.getSecond(), tuple.getSecond())) == 0){
				if((ret=Objects.compare(this.getThird(), tuple.getThird())) == 0){
					if((ret=Objects.compare(this.getFourth(), tuple.getFourth())) == 0){
						return Objects.compare(this.getFifth(), tuple.getFifth());
					}
				}
			}
		}
		return ret;
	}

	/**
	 * Indicates whether some other object is "equal to" this one. Two objects
	 * are considered equal if both are {@code Tuple5} and the i-th element of
	 * this tuple and the i-th element of the compared tuple are equal, for
	 * any 1&le;i&le;5. Equality is determined with
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
		if(obj instanceof Tuple5){
			return Objects.equals(this.getFirst(), (((Tuple5)obj).getFirst()))
			&& Objects.equals(this.getSecond(), (((Tuple5)obj).getSecond()))
			&& Objects.equals(this.getThird(), (((Tuple5)obj).getThird()))
			&& Objects.equals(this.getFourth(), (((Tuple5)obj).getFourth()))
			&& Objects.equals(this.getFifth(), (((Tuple5)obj).getFifth()));
		}else{
			return false;
		}
	}

	/**
	 * Returns a hashCode value for this {@code Tuple5}.
	 * <p>
	 * This method follows the general contract of
	 * {@link java.lang.Object#hashCode() Object.hashCode}.</p>
	 *
	 * @return the hashCode of this object
	 */
	@Override
	public int hashCode(){
		int hash=7;
		hash=83 * hash + Objects.hashCode(this.getFirst());
		hash=83 * hash + Objects.hashCode(this.getSecond());
		hash=83 * hash + Objects.hashCode(this.getThird());
		hash=83 * hash + Objects.hashCode(this.getFourth());
		hash=83 * hash + Objects.hashCode(this.getFifth());
		return hash;
	}

	/**
	 * Returns a {@code String} representation of this {@code Tuple5}.
	 * <p>
	 * This method returns a String consisting of parenthesis enclosing the
	 * String representation of each element of the tuple, separated with
	 * {@code ", "}.</p>
	 *
	 * @return the {@code String} representation
	 */
	@Override
	public String toString(){
		return "(" + getFirst() + ", " + getSecond() + ", " + getThird() + ", " + getFourth() + ", " + getFifth() + ")";
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple2 Tuple2} representation
	 * of this {@code Tuple5}. The returned tuple is backed up by this one.
	 *
	 * @return the {@code Tuple2} view of this {@code Tuple5}
	 */
	public Tuple2 asTuple2(){
		return new BackedTuple2(backed);
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple3 Tuple3} representation
	 * of this {@code Tuple5}. The returned tuple is backed up by this one.
	 *
	 * @return the {@code Tuple3} view of this {@code Tuple5}
	 */
	public Tuple3 asTuple3(){
		return new BackedTuple3(backed);
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple4 Tuple4} representation
	 * of this {@code Tuple5}. The returned tuple is backed up by this one.
	 *
	 * @return the {@code Tuple4} view of this {@code Tuple5}
	 */
	public Tuple4 asTuple4(){
		return new BackedTuple4(backed);
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
				case 4:
					return getFourth();
				default:
					return null;
			}
		}
	}
}

/**
 * Tuple4 that gets its data from another tuple.
 */
class BackedTuple5<Fst, Snd, Trd, Fth, Ffth> extends Tuple5<Fst, Snd, Trd, Fth, Ffth>{

	private static final long serialVersionUID=8752873351860311873L;
	private BackedTuple tuple;

	public BackedTuple5(BackedTuple tuple){
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

	@Override
	public Ffth getFifth(){
		return (Ffth)tuple.get(5);
	}
}
