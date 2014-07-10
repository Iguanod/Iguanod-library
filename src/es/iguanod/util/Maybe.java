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
package es.iguanod.util;

import es.iguanod.base.Objects;
import java.io.Serializable;

/**
 * An object that may contain a reference to another object. Each instance of
 * this type either contains a reference (that may or may not be null), or
 * contains nothing (in which case we say that the reference is "absent").
 *
 * <p>A non-null {@code Maybe<T>} reference can be used as a replacement for a
 * {@code T} reference. It allows you to represent "a {@code T} that must be
 * present" (in which case you use simply a {@code T}) and a "a {@code T} that
 * might be absent" (in which case you use a {@code Maybe<T>}) as two distinct
 * types in your program, which can aid clarity.</p>
 *
 * <p>Some uses of this class include:<br/>
 *
 * &nbsp;&nbsp;&nbsp;&nbsp;&bull; As a method return type, as an alternative to
 * returning {@code null} to indicate that no value was available.<br/>
 *
 * &nbsp;&nbsp;&nbsp;&nbsp;&bull; To distinguish between "unknown" (for example,
 * not present in a map) and "known to have no value" (present in the map, with
 * value {@link es.iguanod.util.Maybe#ABSENT Maybe.ABSENT}).<br/>
 *
 * &nbsp;&nbsp;&nbsp;&nbsp;&bull; To wrap nullable references for storage in a
 * collection that does not support {@code null} (though there are several other
 * approaches to this that should be considered first).</p>
 *
 * <p>If an API uses {@code Maybes}, sometimes having a {@code Maybe<T>}
 * returned from a method instead of simply a &lt;T&gt; may seem like a burden.
 * Some examples may be when another API or method works with null values to
 * indicate non-present, or when printing a collection of {@code Maybes} and
 * wanting to print "null" for absent values. However, in those cases the
 * apparent problem may be circunvented using
 * {@code maybe.}{@link #or(Object) or}{@code (null)}.</p>
 *
 * @param <T> the class of the object wrapped by this {@code Maybe}
 *
 * @see java.util.Serializable
 * @see java.lang.Comparable
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.8.1.a
 * @version 1.0.1.b
 */
public class Maybe<T> implements Serializable, Comparable<Maybe<T>>{

	private static final long serialVersionUID=552780249872165710L;
	//************
	/**
	 * Stores wether this {@code Maybe} has a content or is absent.
	 */
	protected boolean present;
	/**
	 * The value wrapped by this {@code Maybe}. The value is invalid if
	 * {@link #present}{@code ==false}.
	 */
	protected T value;
	//************
	/**
	 * Singleton instance of an absent {@code Maybe}.
	 */
	public static final Maybe ABSENT=new Maybe(false, null);

	/**
	 * Constructor used by the static creation methods. If {@code present} is
	 * {@code true}, this represents a wrapper for {@code value}. If
	 * {@code present} is {@code false}, this represents an absent
	 * {@code Maybe} and {@code value} is ignored (although it do is stored).
	 *
	 * @param present whether this has a value or is absent
	 * @param value the value to be wrapped, in case {@code present} is
	 * {@code true}
	 */
	protected Maybe(boolean present, T value){
		this.present=present;
		this.value=value;
	}

	/**
	 * Returns an absent an absent {@code Maybe}. This implementation simply
	 * returns {@link #ABSENT}.
	 *
	 * @param <T> the class of the object wrapped by this {@code Maybe}. Note
	 * that this is only useful for returning or assigning the correct class
	 * to a parameter, since the value is absent and so doesn't really have a
	 * class
	 *
	 * @return an absent {@code Maybe}
	 */
	public static <T> Maybe<T> absent(){
		return ABSENT;
	}

	/**
	 * Constructs a new {@code Maybe} that wraps {@code value}.
	 *
	 * @param <T> the class of the object wrapped by this {@code Maybe}
	 * @param value the value to be stored in this {@code Maybe}
	 *
	 * @return the constructed {@code Maybe}
	 */
	public static <T> Maybe<T> from(T value){
		return new Maybe<>(true, value);
	}

	/**
	 * Constructs a new {@code Maybe} using the information of another. If the
	 * {@code Maybe} passed as argument is absent, this will be absent;
	 * otherwise this will contain the same reference as the argument. Note
	 * that only the reference is copied, there is no deep copy of the wrapped
	 * object.
	 *
	 * @param <T> the class of the object wrapped by this {@code Maybe}
	 * @param maybe the {@code Maybe} to be copied
	 *
	 * @return the constructed {@code Maybe}
	 *
	 * @throws NullPointerException if {@code maybe} is {@code null}
	 */
	public static <T> Maybe<T> copyOf(Maybe<? extends T> maybe){
		if(maybe.isPresent()){
			return from(maybe.value);
		}else{
			return ABSENT;
		}
	}

	/**
	 * Returns wether this {@code Maybe} wraps an object. This method is
	 * equivalent to {@code !}{@link #isAbsent()}.
	 *
	 * @return {@code true} if there is a reference present in this
	 * {@code Maybe}, {@code false} if this {@code Maybe} is absent
	 *
	 * @see #isAbsent()
	 * @see #ABSENT
	 */
	public boolean isPresent(){
		return present;
	}

	/**
	 * Returns wether this {@code Maybe} is absent. This method is equivalent
	 * to {@code !}{@link #isPresent()}.
	 *
	 * @return {@code true} if this is {@code Maybe} is absent, {@code false}
	 * if there is a reference present in this {@code Maybe}
	 *
	 * @see #isPresent()
	 * @see #ABSENT
	 */
	public boolean isAbsent(){
		return !present;
	}

	/**
	 * Returns the object wrapped by this {@code Maybe}.
	 *
	 * @return the wrapped object
	 *
	 * @throws IllegalStateException if this {@code Maybe} is absent
	 */
	public T get(){
		if(!present)
			throw new IllegalStateException("The object is not present");
		return value;
	}

	/**
	 * If this {@code Maybe} is not absent, returns the object it wraps,
	 * otherwise {@code default_value} is returned.
	 *
	 * @param default_value the value to be returned in case this
	 * {@code Maybe} is absent
	 *
	 * @return {@link #get()} if this {@code Maybe} is not absent,
	 * {@code default_value} otherwise
	 */
	public T or(T default_value){
		if(this.present)
			return this.value;
		else
			return default_value;
	}

	/**
	 * If this {@code Maybe} is not absent, it is returned, otherwise
	 * {@code second_choice} is returned.
	 *
	 * @param second_choice the {@code Maybe} to be returned in case this
	 * {@code Maybe} is absent
	 *
	 * @return {@code this} if it is not absent, {@code second_choice}
	 * otherwise
	 */
	public Maybe<T> or(Maybe<T> second_choice){
		if(this.present)
			return this;
		else
			return second_choice;
	}

	/**
	 * Compares this object with the specified object for order. Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 *
	 * <p>An absent {@code Maybe} is considered less than any other
	 * {@code Maybe}, except for another absent {@code Maybe}, to which is
	 * equal. If none of the compared {@code Maybes} are absent the result of
	 * {@link es.iguanod.base.Objects#compare(Object,Object) Objects.compare(this,maybe)}
	 * is returned. This doesn't mean the value stored in this {@code Maybe}
	 * has to be {@code Comparable}, only that this method cannont be used if
	 * it isn't.</p>
	 *
	 * @param maybe the {@code Maybe} to be compared with {@code this}
	 *
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object
	 *
	 * @throws NullPointerException if {@code maybe} is {@code null}
	 * @throws ClassCastException if neither {@code this} nor {@code maybe} is
	 * absent, and the value stored in this {@code Maybe} doesn't implement
	 * {@code Comparable}
	 *
	 */
	@Override
	public int compareTo(Maybe<T> maybe){

		if(!present){
			return maybe.present?-1:0;
		}else if(!maybe.present){
			return 1;
		}else{
			return Objects.compare(value, maybe.value);
		}
	}

	/**
	 * Indicates whether some other object is "equal to" this one. Two
	 * {@code Maybes} are considered equals if both are absent, or both are
	 * non-absent and
	 * {@link es.iguanod.base.Objects#equals(Object,Object) Objects.equals(this,obj)}{@code ==true}.
	 *
	 * <p>In every other aspect this method follows the general contract of
	 * {@link java.lang.Object#equals(Object) Object.equals}.</p>
	 *
	 * @param obj the object to be tested for equality
	 *
	 * @return {@code true} if the passed argument is equal to
	 * {@code this}, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj){

		if(obj == null){
			return false;
		}

		if(obj == this){
			return true;
		}

		if(!(obj instanceof Maybe)){
			return false;
		}

		return present == ((Maybe)obj).present
		&& (!present || Objects.equals(value, ((Maybe)obj).value));
	}

	/**
	 * Returns a hashCode value for this {@code Maybe}. If this {@code Maybe}
	 * is absent, the returned hash code is 0 (zero). Note that if this
	 * {@code Maybe} is non-absent, its hash code is not the same as the hash
	 * code of the object it wraps.
	 *
	 * <p>This method follows the general contract of
	 * {@link java.lang.Object#hashCode() Object.hashCode}.</p>
	 *
	 * @return the hashCode of this object
	 */
	@Override
	public int hashCode(){
		if(present){
			return 83 * 7 + Objects.hashCode(this.value);
		}else{
			return 0;
		}
	}

	/**
	 * Returns a {@code String} representation of this {@code Maybe}.
	 *
	 * <p>If this is absent, the method returns: "Maybe -> Absent"</p>
	 *
	 * <p>If this is non absent, the method returns the equivalent of: "Maybe
	 * -> value = " +
	 * {@code (}{@link #get()}{@code !=null?get().toString():"null")}</p>
	 *
	 * @return the {@code String} representation
	 */
	@Override
	public String toString(){
		if(this.present)
			return "Maybe -> value = " + value;
		else
			return "Maybe -> Absent";
	}
}
