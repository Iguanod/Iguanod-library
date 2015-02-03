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
 * Unit tests for {@link es.iguanod.util.MaybeM} class.
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class MaybeMTest extends MaybeTest{

	protected static Maybe absentI;
	protected static Maybe m1I;
	protected static Maybe m2I;
	protected static Maybe nullI;
	protected static Maybe uncomparableI;

	@BeforeClass
	public static void setUpClass(){
		absent1=MaybeM.absent();
		absent2=MaybeM.absent();
		m1_value=1;
		m1=MaybeM.from(m1_value);
		m1_cpy=MaybeM.from(m1_value);
		m2=MaybeM.from(2);
		uncomparable=MaybeM.from(new Object());
		null1=MaybeM.from(null);
		null2=MaybeM.from(null);
		absentI=Maybe.ABSENT;
		m1I=Maybe.from(1);
		m2I=Maybe.from(2);
		nullI=Maybe.from(null);
		uncomparableI=Maybe.from(new Object());
	}

	/**
	 * NOT TRUE ANYMORE -> Test wether the object returned by absent() is
	 * Maybe.ABSENT.
	 */
	@Test
	@Override
	public void testAbsent_02(){
	}

	/**
	 * Test of copyOf method from absent and MaybeM parameter.
	 */
	@Test
	@Override
	public void testCopyOf_01(){
		MaybeM copy=MaybeM.copyOf(absent1);
		assertEquals(absent1.isAbsent(), copy.isAbsent());
	}

	/**
	 * Test of copyOf method from absent and MaybeM parameter.
	 */
	@Test(expected=IllegalStateException.class)
	@Override
	public void testCopyOf_02(){
		MaybeM copy=MaybeM.copyOf(absent1);
		copy.get();
	}

	/**
	 * Test of copyOf method from non absent and MaybeM parameter.
	 */
	@Test
	@Override
	public void testCopyOf_03(){
		MaybeM copy=MaybeM.copyOf(m1);
		assertEquals(m1.isAbsent(), copy.isAbsent());
	}

	/**
	 * Test of copyOf method from non absent and MaybeM parameter.
	 */
	@Test
	@Override
	public void testCopyOf_04(){
		MaybeM copy=MaybeM.copyOf(m1);
		assertSame(m1.get(), copy.get());
	}

	/**
	 * Test of copyOf method from null.
	 */
	@Test(expected=NullPointerException.class)
	@Override
	public void testCopyOf_05(){
		MaybeM copy=MaybeM.copyOf(null);
	}

	/**
	 * Test of copyOf method from absent and Maybe parameter.
	 */
	@Test
	public void testCopyOf_06(){
		MaybeM copy=MaybeM.copyOf(absentI);
		assertEquals(absentI.isAbsent(), copy.isAbsent());
	}

	/**
	 * Test of copyOf method from absent and Maybe parameter.
	 */
	@Test(expected=IllegalStateException.class)
	public void testCopyOf_07(){
		MaybeM copy=MaybeM.copyOf(absentI);
		copy.get();
	}

	/**
	 * Test of copyOf method from non absent and Maybe parameter.
	 */
	@Test
	public void testCopyOf_08(){
		MaybeM copy=MaybeM.copyOf(m1I);
		assertEquals(m1I.isAbsent(), copy.isAbsent());
	}

	/**
	 * Test of copyOf method from non absent and Maybe parameter.
	 */
	@Test
	public void testCopyOf_09(){
		MaybeM copy=MaybeM.copyOf(m1I);
		assertSame(m1I.get(), copy.get());
	}

	/**
	 * Test of or(Maybe) method with this absent and Maybe parameter.
	 */
	@Test
	public void testOr_Maybe_05(){
		assertSame(absent1.or(m1I), m1I);
	}

	/**
	 * Test of or(Maybe) method with this non absent and Maybe parameter.
	 */
	@Test
	public void testOr_Maybe_06(){
		assertSame(m1.or(m1I), m1);
	}

	/**
	 * Test of compareTo method with both absents and Maybe parameter.
	 */
	@Test
	public void testCompareTo_11(){
		assertTrue(absent1.compareTo(absentI) == 0);
		assertTrue(absentI.compareTo(absent1) == 0);
	}

	/**
	 * Test of compareTo method with first absent and Maybe parameter.
	 */
	@Test
	public void testCompareTo_12(){
		assertTrue(absent1.compareTo(m1I) < 0);
	}

	/**
	 * Test of compareTo method with second absent and Maybe parameter.
	 */
	@Test
	public void testCompareTo_13(){
		assertTrue(m1.compareTo(absentI) > 0);
	}

	/**
	 * Test of compareTo method with no absents and first null value and Maybe
	 * parameter.
	 */
	@Test
	public void testCompareTo_14(){
		assertTrue(null1.compareTo(m1I) < 0);
	}

	/**
	 * Test of compareTo method with no absents and second null value and
	 * Maybe parameter.
	 */
	@Test
	public void testCompareTo_15(){
		assertTrue(m1.compareTo(nullI) > 0);
	}

	/**
	 * Test of compareTo method with no absents and both null values and Maybe
	 * parameter.
	 */
	@Test
	public void testCompareTo_16(){
		assertTrue(null1.compareTo(nullI) == 0);
		assertTrue(nullI.compareTo(null1) == 0);
	}

	/**
	 * Test of compareTo method with non comparable and Maybe parameter.
	 */
	@Test(expected=ClassCastException.class)
	public void testCompareTo_17(){
		uncomparable.compareTo(m1I);
	}

	/**
	 * Test of compareTo method with non comparable and Maybe parameter.
	 */
	@Test(expected=ClassCastException.class)
	public void testCompareTo_18(){
		m1.compareTo(uncomparableI);
	}

	/**
	 * Test of equals method with absents and Maybe parameter.
	 */
	@Test
	public void testEquals_07(){
		assertTrue(absent1.equals(absentI));
		assertTrue(absentI.equals(absent1));
	}

	/**
	 * Test of equals method with absents and Maybe parameter.
	 */
	@Test
	public void testEquals_08(){
		assertFalse(m1.equals(absentI));
		assertFalse(absent1.equals(m1I));
	}

	/**
	 * Test of equals method with no absents and Maybe parameter.
	 */
	public void testEquals_09(){
		assertFalse(m1.equals(m2I));
		assertFalse(m2.equals(m1I));
	}

	/**
	 * Test of equals method with no absents and Maybe parameter.
	 */
	public void testEquals_10(){
		assertTrue(m1.equals(m1I));
		assertTrue(m1I.equals(m1));
	}

	/**
	 * Test of equals method with no absents and one null value and Maybe
	 * parameter.
	 */
	@Test
	public void testEquals_11(){
		assertFalse(m1.equals(nullI));
		assertFalse(null1.equals(m1I));
	}

	/**
	 * Test of equals method with no absents and both null values and Maybe
	 * parameter.
	 */
	@Test
	public void testEquals_12(){
		assertTrue(null1.equals(nullI));
		assertTrue(nullI.equals(null1));
	}

	/**
	 * Test of hashCode method of equals MaybeM and Maybe.
	 */
	@Test
	public void testHashCode_04(){
		assertEquals(m1.hashCode(), m1I.hashCode());
	}

	/**
	 * Test of set method.
	 */
	@Test
	public void testSet_01(){
		MaybeM maybe=MaybeM.absent();
		int new_value=1;
		maybe.set(new_value);
		assertEquals(maybe.get(), new_value);
	}

	/**
	 * Test of set method.
	 */
	@Test
	public void testSet_02(){
		MaybeM maybe=MaybeM.absent();
		int new_value=1;
		maybe.set(new_value);
		assertTrue(maybe.isPresent());
	}

	/**
	 * Test of set method.
	 */
	@Test
	public void testSet_03(){
		MaybeM maybe=MaybeM.absent();
		int new_value=1;
		maybe.set(new_value);
		assertFalse(maybe.isAbsent());
	}

	/**
	 * Test of set method.
	 */
	@Test
	public void testSet_04(){
		MaybeM maybe=MaybeM.from(2);
		int new_value=1;
		maybe.set(new_value);
		assertEquals(maybe.get(), new_value);
	}

	/**
	 * Test of set method.
	 */
	@Test
	public void testSet_05(){
		MaybeM maybe=MaybeM.from(2);
		int new_value=1;
		maybe.set(new_value);
		assertTrue(maybe.isPresent());
	}

	/**
	 * Test of set method.
	 */
	@Test
	public void testSet_06(){
		MaybeM maybe=MaybeM.from(2);
		int new_value=1;
		maybe.set(new_value);
		assertFalse(maybe.isAbsent());
	}

	/**
	 * Test of setAbsent method.
	 */
	@Test(expected=IllegalStateException.class)
	public void testSetAbsent_01(){
		MaybeM maybe=MaybeM.absent();
		maybe.setAbsent();
		maybe.get();
	}

	/**
	 * Test of setAbsent method.
	 */
	@Test
	public void testSetAbsent_02(){
		MaybeM maybe=MaybeM.absent();
		maybe.setAbsent();
		assertFalse(maybe.isPresent());
	}

	/**
	 * Test of setAbsent method.
	 */
	@Test
	public void testSetAbsent_03(){
		MaybeM maybe=MaybeM.absent();
		maybe.setAbsent();
		assertTrue(maybe.isAbsent());
	}

	/**
	 * Test of setAbsent method.
	 */
	@Test(expected=IllegalStateException.class)
	public void testSetAbsent_04(){
		MaybeM maybe=MaybeM.from(2);
		maybe.setAbsent();
		maybe.get();
	}

	/**
	 * Test of setAbsent method.
	 */
	@Test
	public void testSetAbsent_05(){
		MaybeM maybe=MaybeM.from(2);
		maybe.setAbsent();
		assertFalse(maybe.isPresent());
	}

	/**
	 * Test of setAbsent method.
	 */
	@Test
	public void testSetAbsent_06(){
		MaybeM maybe=MaybeM.from(2);
		maybe.setAbsent();
		assertTrue(maybe.isAbsent());
	}
}