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

import java.io.Serializable;

/**
 * Interface representing an object used to cast objects from one class to
 * another. Note that the {@code Caster} interface adds no stipulation to how
 * this cast has to happen: it can create a new object, use a decorator to
 * return an object backed by the original object to be casted, or any other
 * method.
 *
 * <p>Contrary to other simple interfaces part of the JDK (as {@link java.util.Comparator Comparator}),
 * {@code Caster} is {@code Serializable} itself to allow for anonymous
 * {@code Caster} creation in the cases in which the outer class has to be
 * {@code Serializable}; and any {@code Caster} shouldn't generally have any
 * fields to worry about anyway.</p>
 *
 * @param <T> the class of the objects to be casted
 * @param <U> the class of the resulting objects after the cast
 *
 * @see Serializable
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1.1.cb
 * @version 1.0.1.1.b
 */
public interface Caster<T, U> extends Serializable{

	/**
	 * Casts its parameter to an object of a different class.
	 *
	 * @param t the object to be casted
	 *
	 * @return the object returned by the cast
	 */
	public U cast(T t);
}
