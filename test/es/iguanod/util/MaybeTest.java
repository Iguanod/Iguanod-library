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

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for {@link es.iguanod.util.Maybe} class.
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1.1.cb
 * @version 1.0.1.b
 */
public class MaybeTest{

	protected static Maybe absent1;
	protected static Maybe absent2;
	protected static Object m1_value;
	protected static Maybe m1;
	protected static Maybe m1_cpy;
	protected static Maybe m2;
	protected static Maybe uncomparable;
	protected static Maybe null1;
	protected static Maybe null2;

	@BeforeClass
	public static void setUpClass(){
		absent1=Maybe.ABSENT;
		absent2=Maybe.absent();
		m1_value=1;
		m1=Maybe.from(m1_value);
		m1_cpy=Maybe.from(m1_value);
		m2=Maybe.from(2);
		uncomparable=Maybe.from(new Object());
		null1=Maybe.from(null);
		null2=Maybe.from(null);
	}

	/**
	 * Test wether the singleton absent is built.
	 */
	@Test
	public void testSingletonAbsent1(){
		assertNotNull(Maybe.ABSENT);
	}

	/**
	 * Test wether an absent Maybe can be built.
	 */
	@Test
	public void testAbsent1(){
		assertNotNull(absent2);
	}

	/**
	 * Test wether the object returned by absent() is Maybe.ABSENT.
	 */
	@Test
	public void testAbsent2(){
		assertTrue(absent1 == absent2);
	}

	/**
	 * Test wether a non absent Maybe can be built.
	 */
	@Test
	public void testFrom1(){
		assertNotNull(m1);
	}

	/**
	 * Test wether a non absent Maybe can be built from null.
	 */
	@Test
	public void testFrom2(){
		assertNotNull(null1);
	}

	/**
	 * Test of isPresent method with an absent.
	 */
	@Test
	public void testIsPresent1(){
		assertFalse(absent1.isPresent());
	}

	/**
	 * Test of isPresent method with a non absent.
	 */
	@Test
	public void testIsPresent2(){
		assertTrue(m1.isPresent());
	}

	/**
	 * Test of isPresent method with a non absent with null value.
	 */
	@Test
	public void testIsPresent3(){
		assertTrue(null1.isPresent());
	}

	/**
	 * Test of isAbsent method with an absent.
	 */
	@Test
	public void testIsAbsent1(){
		assertTrue(absent1.isAbsent());
	}

	/**
	 * Test of isAbsent method with a non absent.
	 */
	@Test
	public void testIsAbsent2(){
		assertFalse(m1.isAbsent());
	}

	/**
	 * Test of isAbsent method with a non absent with null value.
	 */
	@Test
	public void testIsAbsent3(){
		assertFalse(null1.isAbsent());
	}

	/**
	 * Test wether isPresent()==!isAbsent()
	 */
	@Test
	public void testIsPresentIsAbsent(){
		assertEquals(absent1.isPresent(), !absent1.isAbsent());
		assertEquals(m1.isPresent(), !m1.isAbsent());
		assertEquals(null1.isPresent(), !null1.isAbsent());
	}

	/**
	 * Test of get method with a non absent.
	 */
	@Test
	public void testGet1(){
		assertSame(m1.get(), m1_value);
	}

	/**
	 * Test of get method with a non absent with null value.
	 */
	@Test
	public void testGet2(){
		assertSame(null1.get(), null);
	}

	/**
	 * Test of get method with an absent.
	 */
	@Test(expected=IllegalStateException.class)
	public void testGet3(){
		absent1.get();
	}

	/**
	 * Test of copyOf method from absent.
	 */
	@Test
	public void testCopyOf1(){
		Maybe copy=Maybe.copyOf(absent1);
		assertEquals(absent1.isAbsent(), copy.isAbsent());
	}

	/**
	 * Test of copyOf method from absent.
	 */
	@Test(expected=IllegalStateException.class)
	public void testCopyOf2(){
		Maybe copy=Maybe.copyOf(absent1);
		copy.get();
	}

	/**
	 * Test of copyOf method from non absent.
	 */
	@Test
	public void testCopyOf3(){
		Maybe copy=Maybe.copyOf(m1);
		assertEquals(m1.isAbsent(), copy.isAbsent());
	}

	/**
	 * Test of copyOf method from non absent.
	 */
	@Test
	public void testCopyOf4(){
		Maybe copy=Maybe.copyOf(m1);
		assertSame(m1.get(), copy.get());
	}

	/**
	 * Test of copyOf method from null.
	 */
	@Test(expected=NullPointerException.class)
	public void testCopyOf5(){
		Maybe copy=Maybe.copyOf(null);
	}

	/**
	 * Test of or(T) method with this absent.
	 */
	@Test
	public void testOr_T1(){
		Integer default_value=1;
		assertSame(absent1.or(default_value), default_value);
	}

	/**
	 * Test of or(T) method with this absent and null default_value
	 */
	@Test
	public void testOr_T2(){
		Integer default_value=null;
		assertSame(absent1.or(default_value), default_value);
	}

	/**
	 * Test of or(T) method with this non absent.
	 */
	@Test
	public void testOr_T3(){
		Integer default_value=2;
		assertSame(m1.or(default_value), m1_value);
	}

	/**
	 * Test of or(T) method with this non absent and null value.
	 */
	@Test
	public void testOr_T4(){
		Integer default_value=1;
		assertSame(null1.or(default_value), null);
	}

	/**
	 * Test of or(Maybe) method with this absent.
	 */
	@Test
	public void testOr_Maybe1(){
		assertSame(absent1.or(m1), m1);
	}

	/**
	 * Test of or(Maybe) method with this absent and null second_choice.
	 */
	@Test
	public void testOr_Maybe2(){
		assertSame(absent1.or(null), null);
	}

	/**
	 * Test of or(maybe) method with this non absent.
	 */
	@Test
	public void testOr_Maybe3(){
		assertSame(m1.or(m2), m1);
	}

	/**
	 * Test of or(Maybe) method with this non absent and null second choice.
	 */
	@Test
	public void testOr_Maybe4(){
		assertSame(m1.or(null), m1);
	}

	/**
	 * Test of toString method with this absent.
	 */
	@Test
	public void testToString1(){
		assertEquals("Maybe -> Absent", absent1.toString());
	}

	/**
	 * Test of toString method with this non absent.
	 */
	@Test
	public void testToString2(){
		assertEquals("Maybe -> value = " + (m1.get() != null?m1.get().toString():"null"), m1.toString());
	}

	/**
	 * Test of toString method with this non absent with null value.
	 */
	@Test
	public void testToString3(){
		assertEquals("Maybe -> value = " + (null1.get() != null?null1.get().toString():"null"), null1.toString());
	}

	/**
	 * Test of compareTo method with both absents.
	 */
	@Test
	public void testCompareTo1(){
		assertTrue(absent1.compareTo(absent2) == 0);
		assertTrue(absent2.compareTo(absent1) == 0);
	}

	/**
	 * Test of compareTo method with first absent.
	 */
	@Test
	public void testCompareTo2(){
		assertTrue(absent1.compareTo(m1) < 0);
	}

	/**
	 * Test of compareTo method with second absent.
	 */
	@Test
	public void testCompareTo3(){
		assertTrue(m1.compareTo(absent1) > 0);
	}

	/**
	 * Test of compareTo method with no absents and first null value.
	 */
	@Test
	public void testCompareTo4(){
		assertTrue(null1.compareTo(m1) < 0);
	}

	/**
	 * Test of compareTo method with no absents and second null value.
	 */
	@Test
	public void testCompareTo5(){
		assertTrue(m1.compareTo(null1) > 0);
	}

	/**
	 * Test of compareTo method with no absents and both null values.
	 */
	@Test
	public void testCompareTo6(){
		assertTrue(null1.compareTo(null2) == 0);
		assertTrue(null2.compareTo(null1) == 0);
	}

	/**
	 * Test of compareTo method with absent and null parameter.
	 */
	@Test(expected=NullPointerException.class)
	public void testCompareTo7(){
		absent1.compareTo(null);
	}

	/**
	 * Test of compareTo method with non-absent and null parameter.
	 */
	@Test(expected=NullPointerException.class)
	public void testCompareTo8(){
		m1.compareTo(null);
	}

	/**
	 * Test of compareTo method with non comparable.
	 */
	@Test(expected=ClassCastException.class)
	public void testCompareTo9(){
		uncomparable.compareTo(m1);
	}

	/**
	 * Test of compareTo method with non comparable.
	 */
	@Test(expected=ClassCastException.class)
	public void testCompareTo11(){
		m1.compareTo(uncomparable);
	}

	/**
	 * Test of equals method with absents.
	 */
	@Test
	public void testEquals1(){
		assertTrue(absent1.equals(absent2));
		assertTrue(absent2.equals(absent1));
	}

	/**
	 * Test of equals method with absents.
	 */
	@Test
	public void testEquals2(){
		assertFalse(m1.equals(absent1));
		assertFalse(absent1.equals(m1));
	}

	/**
	 * Test of equals method with no absents.
	 */
	public void testEquals3(){
		assertFalse(m1.equals(m2));
		assertFalse(m2.equals(m1));
	}

	/**
	 * Test of equals method with no absents.
	 */
	public void testEquals4(){
		assertTrue(m1.equals(m1_cpy));
		assertTrue(m1_cpy.equals(m1));
	}

	/**
	 * Test of equals method with no absents and one null value.
	 */
	@Test
	public void testEquals5(){
		assertFalse(m1.equals(null1));
		assertFalse(null1.equals(m1));
	}

	/**
	 * Test of equals method with no absents and both null values.
	 */
	@Test
	public void testEquals6(){
		assertTrue(null1.equals(null2));
		assertTrue(null2.equals(null1));
	}

	/**
	 * Test of hashCode method with absent.
	 */
	@Test
	public void testHashCode1(){
		assertEquals(absent1.hashCode(), 0);
	}

	/**
	 * Test of hashCode method with non absent.
	 */
	@Test
	public void testHashCode2(){
		assertFalse(m1.hashCode() == m1_value.hashCode());
	}

	/**
	 * Test of hashCode method of equals Maybes.
	 */
	@Test
	public void testHashCode3(){
		assertEquals(m1.hashCode(), m1_cpy.hashCode());
	}
}
