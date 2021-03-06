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
 * Class representing a mutable tuple of 6 elements. The objects stored in the
 * tuple may be {@code null}.
 *
 * @param <Fst> the class of the first element
 * @param <Snd> the class of the second element
 * @param <Trd> the class of the third element
 * @param <Fth> the class of the fourth element
 * @param <Ffth> the class of the fifth element
 * @param <Sth> the class of the sixth element
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 *
 * @see java.lang.Comparable
 * @see java.io.Serializable
 */
public class Tuple6M<Fst, Snd, Trd, Fth, Ffth, Sth> extends Tuple6<Fst, Snd, Trd, Fth, Ffth, Sth>{

	private static final long serialVersionUID=2143910487335614514L;
	//************
	/**
	 * Backed tuple implementation to return a less-size-tuple view of this
	 * one.
	 */
	private BackedTupleImplM backed=new BackedTupleImplM();

	/**
	 * Constructs a {@code Tuple6M} with the specified elements.
	 *
	 * @param first the first element of the tuple
	 * @param second the second element of the tuple
	 * @param third the third element of the tuple
	 * @param fourth the fourth element of the tuple
	 * @param fifth the fifth element of the tuple
	 * @param sixth the sixth element of the tuple
	 */
	public Tuple6M(Fst first, Snd second, Trd third, Fth fourth, Ffth fifth, Sth sixth){
		super(first, second, third, fourth, fifth, sixth);
	}

	/**
	 * Constructor for the BackedTuple.
	 */
	Tuple6M(){
	}

	/**
	 * Setter of the first element of this {@code Tuple6M}.
	 *
	 * @return the previous value of the first element of the tuple
	 */
	public Fst setFirst(Fst first){
		Fst ret=this.first;
		this.first=first;
		return ret;
	}

	/**
	 * Setter of the second element of this {@code Tuple6M}.
	 *
	 * @return the previous value of the second element of the tuple
	 */
	public Snd setSecond(Snd second){
		Snd ret=this.second;
		this.second=second;
		return ret;
	}

	/**
	 * Setter of the third element of this {@code Tuple6M}.
	 *
	 * @return the previous value of the third element of the tuple
	 */
	public Trd setThird(Trd third){
		Trd ret=this.third;
		this.third=third;
		return ret;
	}

	/**
	 * Setter of the fourth element of this {@code Tuple6M}.
	 *
	 * @return the previous value of the fourth element of the tuple
	 */
	public Fth setFourth(Fth fourth){
		Fth ret=this.fourth;
		this.fourth=fourth;
		return ret;
	}

	/**
	 * Setter of the fifth element of this {@code Tuple6M}.
	 *
	 * @return the previous value of the fifth element of the tuple
	 */
	public Ffth setFifth(Ffth fifth){
		Ffth ret=this.fifth;
		this.fifth=fifth;
		return ret;
	}

	/**
	 * Setter of the sixth element of this {@code Tuple6M}.
	 *
	 * @return the previous value of the sixth element of the tuple
	 */
	public Sth setSixth(Sth sixth){
		Sth ret=this.sixth;
		this.sixth=sixth;
		return ret;
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple2M Tuple2M} representation
	 * of this {@code Tuple6M}. The returned tuple is backed up by this tuple,
	 * that is, changes to the returned tuple write through this one and
	 * viceversa.
	 *
	 * @return the {@code Tuple2M} view of this {@code Tuple6M}
	 */
	public Tuple2M asTuple2M(){
		return new BackedTuple2M(backed);
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple3M Tuple3M} representation
	 * of this {@code Tuple6M}. The returned tuple is backed up by this tuple,
	 * that is, changes to the returned tuple write through this one and
	 * viceversa.
	 *
	 * @return the {@code Tuple3M} view of this {@code Tuple6M}
	 */
	public Tuple3M asTuple3M(){
		return new BackedTuple3M(backed);
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple4M Tuple4M} representation
	 * of this {@code Tuple6M}. The returned tuple is backed up by this tuple,
	 * that is, changes to the returned tuple write through this one and
	 * viceversa.
	 *
	 * @return the {@code Tuple4M} view of this {@code Tuple6M}
	 */
	public Tuple4M asTuple4M(){
		return new BackedTuple4M(backed);
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple5M Tuple5M} representation
	 * of this {@code Tuple6M}. The returned tuple is backed up by this tuple,
	 * that is, changes to the returned tuple write through this one and
	 * viceversa.
	 *
	 * @return the {@code Tuple5M} view of this {@code Tuple6M}
	 */
	public Tuple5M asTuple5M(){
		return new BackedTuple5M(backed);
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
					return setFirst((Fst)value);
				case 2:
					return setSecond((Snd)value);
				case 3:
					return setThird((Trd)value);
				case 4:
					return setFourth((Fth)value);
				case 5:
					return setFifth((Ffth)value);
				default:
					return null;
			}
		}
	}
}
