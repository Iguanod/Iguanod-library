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
package es.iguanod.util;

/**
 * An object that may contain a reference to another object. Each instance of
 * this type either contains a reference (that may or may not be null), or
 * contains nothing (in which case we say that the reference is "absent").
 * Unlike {@link es.iguanod.util.Maybe Maybe}, {@code MaybeM} is mutable, and
 * can change the reference it stores or even change from being non absent to
 * absent and viceversa.
 *
 * <p>A non-null {@code MaybeM<T>} reference can be used as a replacement for a
 * {@code T} reference. It allows you to represent "a {@code T} that must be
 * present" (in which case you use simply a {@code T}) and a "a {@code T} that
 * might be absent" (in which case you use a {@code MaybeM<T>}) as two distinct
 * types in your program, which can aid clarity.</p>
 *
 * <p>Some uses of this class include:<br/>
 *
 * &nbsp;&nbsp;&nbsp;&nbsp;&bull; As a method return type, as an alternative to
 * returning {@code null} to indicate that no value was available.<br/>
 *
 * &nbsp;&nbsp;&nbsp;&nbsp;&bull; To distinguish between "unknown" (for example,
 * not present in a map) and "known to have no value" (present in the map, with
 * value {@link es.iguanod.util.MaybeM#absent() MaybeM.absent()}).<br/>
 *
 * &nbsp;&nbsp;&nbsp;&nbsp;&bull; To wrap nullable references for storage in a
 * collection that does not support {@code null} (though there are several other
 * approaches to this that should be considered first).</p>
 *
 * @param <T> the class of the object wrapped by this {@code MaybeM}
 *
 * @see java.util.Serializable
 * @see java.lang.Comparable
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.8.1.a
 * @version 1.0.1.b
 */
public class MaybeM<T> extends Maybe<T>{

	private static final long serialVersionUID=2436842962974533541L;
	//************

	/**
	 * Constructor used by the static creation methods. If {@code present} is
	 * {@code true}, this represents a wrapper for {@code value}. If
	 * {@code present} is {@code false}, this represents an absent
	 * {@code MaybeM} and {@code value} is ignored (although it do is stored).
	 *
	 * @param present whether this has a value or is absent
	 * @param value the value to be wrapped, in case {@code present} is
	 * {@code true}
	 */
	protected MaybeM(boolean present, T value){
		super(present, value);
	}

	/**
	 * Returns an absent an absent {@code MaybeM}.
	 *
	 * @param <T> the class of the object wrapped by this {@code MaybeM}. Note
	 * that this is only useful for returning or assigning the correct class
	 * to a parameter, or to be used later to store a reference to an object
	 * of class {@code T}, since when returned from this method the value is
	 * absent and so doesn't really have a class
	 *
	 * @return an absent {@code MaybeM}
	 */
	public static <T> MaybeM<T> absent(){
		return new MaybeM(false, null);
	}

	/**
	 * Constructs a new {@code MaybeM} that wraps {@code value}.
	 *
	 * @param <T> the class of the object wrapped by this {@code MaybeM}
	 * @param value the value to be stored in this {@code MaybeM}
	 *
	 * @return the constructed {@code MaybeM}
	 */
	public static <T> MaybeM<T> from(T value){
		return new MaybeM<>(true, value);
	}

	/**
	 * Constructs a new {@code MaybeM} using the information of another. If
	 * the {@code MaybeM} passed as argument is absent, this will be absent;
	 * otherwise this will contain the same reference as the argument. Note
	 * that only the reference is copied, there is no deep copy of the wrapped
	 * object.
	 *
	 * @param <T> the class of the object wrapped by this {@code MaybeM}
	 * @param maybe the {@code MaybeM} to be copied
	 *
	 * @return the constructed {@code MaybeM}
	 *
	 * @throws NullPointerException if {@code maybe} is {@code null}
	 */
	public static <T> MaybeM<T> copyOf(Maybe<? extends T> maybe){
		if(maybe.isPresent()){
			return from(maybe.value);
		}else{
			return absent();
		}
	}

	/**
	 * Sets the reference wrapped by this {@code MaybeM} to {@code value}.
	 * This will replace any prior reference stored by this {@code MaybeM},
	 * and if it was absent it will stop being so.
	 *
	 * @param value the new value to be stored
	 */
	public void set(T value){
		present=true;
		this.value=value;
	}

	/**
	 * Clears any reference wrapped by this {@code MaybeM} so after this call
	 * it is absent. If this {@code MaybeM} was already absent, this method
	 * has no effect.
	 */
	public void setAbsent(){
		present=false;
	}
}
