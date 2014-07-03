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
 * Class representing a mutable tuple of 5 elements. The objects stored in the
 * tuple may be {@code null}.
 *
 * @param <Fst> the class of the first element
 * @param <Snd> the class of the second element
 * @param <Trd> the class of the third element
 * @param <Fth> the class of the fourth element
 * @param <Ffth> the class of the fifth element
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1.1.cb
 * @version 1.0.1.1.cb
 *
 * @see java.lang.Comparable
 * @see java.io.Serializable
 */
public class Tuple5M<Fst, Snd, Trd, Fth, Ffth> extends Tuple5<Fst, Snd, Trd, Fth, Ffth>{

	private static final long serialVersionUID=5851896239929947489L;
	//************
	/**
	 * Backed tuple implementation to return a less-size-tuple view of this
	 * one.
	 */
	private BackedTupleImplM backed=new BackedTupleImplM();

	/**
	 * Constructs a {@code Tuple5M} with the specified elements.
	 *
	 * @param first the first element of the tuple
	 * @param second the second element of the tuple
	 * @param third the third element of the tuple
	 * @param fourth the fourth element of the tuple
	 * @param fifth the fifth element of the tuple
	 */
	public Tuple5M(Fst first, Snd second, Trd third, Fth fourth, Ffth fifth){
		super(first, second, third, fourth, fifth);
	}

	/**
	 * Constructor for the BackedTuple.
	 */
	Tuple5M(){
	}

	/**
	 * Setter of the first element of this {@code Tuple5M}.
	 *
	 * @return the previous value of the first element of the tuple
	 */
	public Fst setFirst(Fst first){
		Fst ret=this.first;
		this.first=first;
		return ret;
	}

	/**
	 * Setter of the second element of this {@code Tuple5M}.
	 *
	 * @return the previous value of the second element of the tuple
	 */
	public Snd setSecond(Snd second){
		Snd ret=this.second;
		this.second=second;
		return ret;
	}

	/**
	 * Setter of the third element of this {@code Tuple5M}.
	 *
	 * @return the previous value of the third element of the tuple
	 */
	public Trd setThird(Trd third){
		Trd ret=this.third;
		this.third=third;
		return ret;
	}

	/**
	 * Setter of the fourth element of this {@code Tuple5M}.
	 *
	 * @return the previous value of the fourth element of the tuple
	 */
	public Fth setFourth(Fth fourth){
		Fth ret=this.fourth;
		this.fourth=fourth;
		return ret;
	}

	/**
	 * Setter of the fifth element of this {@code Tuple5M}.
	 *
	 * @return the previous value of the fifth element of the tuple
	 */
	public Ffth setFifth(Ffth fifth){
		Ffth ret=this.fifth;
		this.fifth=fifth;
		return ret;
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple2M Tuple2M} representation
	 * of this {@code Tuple5M}. The returned tuple is backed up by this tuple,
	 * that is, changes to the returned tuple write through this one and
	 * viceversa.
	 *
	 * @return the {@code Tuple2M} view of this {@code Tuple5M}
	 */
	public Tuple2M asTuple2M(){
		return new BackedTuple2M(backed);
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple3M Tuple3M} representation
	 * of this {@code Tuple5M}. The returned tuple is backed up by this tuple,
	 * that is, changes to the returned tuple write through this one and
	 * viceversa.
	 *
	 * @return the {@code Tuple3M} view of this {@code Tuple5M}
	 */
	public Tuple3M asTuple3M(){
		return new BackedTuple3M(backed);
	}

	/**
	 * Returns a {@link es.iguanod.util.tuples.Tuple4M Tuple4M} representation
	 * of this {@code Tuple5M}. The returned tuple is backed up by this tuple,
	 * that is, changes to the returned tuple write through this one and
	 * viceversa.
	 *
	 * @return the {@code Tuple4M} view of this {@code Tuple5M}
	 */
	public Tuple4M asTuple4M(){
		return new BackedTuple4M(backed);
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
				default:
					return null;
			}
		}
	}
}

/**
 * Tuple4M that gets and sets its data from and to another tuple.
 */
class BackedTuple5M<Fst, Snd, Trd, Fth, Ffth> extends Tuple5M<Fst, Snd, Trd, Fth, Ffth>{

	private static final long serialVersionUID=6910421872672422355L;
	private BackedTupleM tuple;

	public BackedTuple5M(BackedTupleM tuple){
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

	@Override
	public Fst setFirst(Fst first){
		return (Fst)tuple.set(1, first);
	}

	@Override
	public Snd setSecond(Snd second){
		return (Snd)tuple.set(2, second);
	}

	@Override
	public Trd setThird(Trd third){
		return (Trd)tuple.set(3, third);
	}

	@Override
	public Fth setFourth(Fth fourth){
		return (Fth)tuple.set(4, fourth);
	}

	@Override
	public Ffth setFifth(Ffth fifth){
		return (Ffth)tuple.set(5, fifth);
	}
}
