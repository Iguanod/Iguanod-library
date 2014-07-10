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
package es.iguanod.base;

import java.util.Arrays;
import java.util.Comparator;

/**
 * This class consists of static utility methods for operating on objects. These
 * methods serve as an alternative to some of those offered by
 * {@link java.util.Objects java.util.Objects}.
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.6.1.a
 * @version 1.0.1.b
 */
public final class Objects{

	/**
	 * Uninstantiable class
	 */
	private Objects(){
	}

	/**
	 * Compares to objects for equality, working correctly with arrays.
	 *
	 * <p>
	 * Returns true if the arguments are equal to each other and false
	 * otherwise. Consequently, if both arguments are null, true is returned
	 * and if exactly one argument is null, false is returned. Otherwise,
	 * equality is determined by using the equals method of the first argument
	 * if any of the arguments is not an array, or by using the
	 * {@link java.util.Arrays#deepEquals(Object[],Object[]) Arrays.deepEquals}
	 * method.</p>
	 *
	 * <p>
	 * If both arguments are arrays, and either of them contain themselves as
	 * elements either directly or indirectly through one or more levels of
	 * arrays, the behavior of this method is undefined.</p>
	 *
	 * <p>
	 * This method eliminates the need in
	 * {@link java.util.Objects java.util.Objects} to have two separated
	 * {@code equals} methods, as this one serves both for non-arrays and
	 * arrays of any dimension.</p>
	 *
	 * @param obj1 the first object to be compared for equality
	 * @param obj2 the second object to be compared for equality
	 *
	 * @return {@code true} if the arguments are equal to each other,
	 * {@code false} otherwise
	 */
	public static boolean equals(Object obj1, Object obj2){
		if(obj1 instanceof Object[] && obj2 instanceof Object[]){
			return Arrays.deepEquals((Object[])obj1, (Object[])obj2);
		}else{
			return java.util.Objects.equals(obj1, obj2);
		}
	}

	/**
	 * Returns the hash code of a non-null argument and 0 for a null argument.
	 * This method behaves exactly as
	 * {@link java.util.Objects#hashCode(Object) java.util.Objects.hashCode(Object)},
	 * as is provided only to avoid import conflicts for classes overriding
	 * {@code equals} and {@code hashCode} and willing to use
	 * {@link es.iguanod.base.Objects#equals(Object,Object) es.iguanod.base.Objects.equals(Object,Object)}.
	 *
	 * @param obj the object whose hashCode is to be calculated
	 *
	 * @return the hashCode of the object
	 */
	public static int hashCode(Object obj){
		return obj != null?obj.hashCode():0;
	}

	/**
	 * Compares two objects for order. This method is {@code null}-safe: a
	 * {@code null} object is always considered less than any other object,
	 * unless both are {@code null}, in which case the comparison returns 0.
	 * If both objects are non-{@code null} references, 0 is returned if they
	 * are identical references, otherwise {@code obj1} is casted to
	 * {@link java.util.Comparable Comparable} and the result of
	 * {@link java.util.Comparable#compareTo(Object) compareTo} is returned.
	 *
	 * @param <T> the class of the objects to be compared
	 *
	 * @param obj1 the first object to be compared
	 * @param obj2 the second object to be compared
	 *
	 * @return 0 if the objects are identical; -1, 0 or 1 if any of the
	 * arguments is {@code null} (as specified by the method description);
	 * {@code obj1.compareTo(obj2)} otherwise
	 *
	 * @throws ClassCastException if both objects are non-identical and
	 * non-null and {@code obj1} can't be casted to {@code Comparable} or
	 * {@code obj1.compareTo(obj2)} throws ClassCastException
	 *
	 * @see java.util.Comparable
	 * @see java.util.Comparable#compareTo(Object)
	 */
	public static <T> int compare(T obj1, T obj2) throws ClassCastException{

		return compare(obj1, obj2, null);
	}

	/**
	 * Compares two objects for order. This method is {@code null}-safe: a
	 * {@code null} object is always considered less than any other object,
	 * unless both are {@code null}, in which case the comparison returns 0.
	 * If both objects are non-{@code null} references, 0 is returned if they
	 * are identical references. Otherwise, if {@code comparator!=null},
	 * {@code comparator.}{@link java.util.Comparator#compare(Object,Object) compare}{@code (obj1,obj2)}
	 * is returned; or if {@code comparator==null}, {@code obj1} is casted to
	 * {@link java.util.Comparable Comparable} and the result of
	 * {@link java.util.Comparable#compareTo(Object) compareTo} is returned.
	 *
	 * @param <T> the class of the objects to be compared
	 *
	 * @param obj1 the first object to be compared
	 * @param obj2 the second object to be compared
	 * @param comparator the Comparator used for the comparison
	 *
	 * @return 0 if the objects are identical; -1, 0 or 1 if any of the
	 * arguments is {@code null} (as specified by the method description);
	 * {@code obj1.compareTo(obj2)} otherwise
	 *
	 * @throws ClassCastException if both objects are non-identical and
	 * non-null and the comparator is non-null and {@code obj1} can't be
	 * casted to {@code Comparable} or {@code obj1.compareTo(obj2)} throws
	 * ClassCastException
	 *
	 * @see java.util.Comparable
	 * @see java.util.Comparable#compareTo(Object)
	 * @see java.util.Comparator
	 * @see java.util.Comparator#compare(Object,Object)
	 */
	public static <T> int compare(T obj1, T obj2, Comparator<T> comparator){

		if(obj1 == null){
			if(obj2 == null){
				return 0;
			}else{
				return -1;
			}
		}else if(obj2 == null){
			return 1;
		}else if(obj1 == obj2){
			return 0;
		}else if(comparator != null){
			return comparator.compare(obj1, obj2);
		}else{
			return ((Comparable<T>)obj1).compareTo(obj2);
		}
	}
}
