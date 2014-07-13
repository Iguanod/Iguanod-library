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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Unit tests for all classes in {@code es.iguanod.util.tuples}.
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1.b
 * @version 1.0.1.b
 */
@RunWith(value=Parameterized.class)
public class TuplesTest{

	private static LinkedList<Object[]> candidates;
	//************
	private Object tuple;
	private Integer[] values;
	private Object[] ascendants;
	//************
	private final static int NUM_CANDIDATES=222;

	@BeforeClass
	public static void beforeClass(){
		candidates=new LinkedList();
		Integer[] v;

		v=new Integer[]{1, 2, 3, 4, 5, 6};
		candidates.addFirst(new Object[]{new Tuple2(v[0], v[1]), v, null});
		candidates.addFirst(new Object[]{new Tuple3(v[0], v[1], v[2]), v, null});
		candidates.addFirst(new Object[]{new Tuple4(v[0], v[1], v[2], v[3]), v, null});
		candidates.addFirst(new Object[]{new Tuple5(v[0], v[1], v[2], v[3], v[4]), v, null});
		candidates.addFirst(new Object[]{new Tuple6(v[0], v[1], v[2], v[3], v[4], v[5]), v, null});

		candidates.addFirst(new Object[]{new Tuple2M(v[0], v[1]), v, null});
		candidates.addFirst(new Object[]{new Tuple3M(v[0], v[1], v[2]), v, null});
		candidates.addFirst(new Object[]{new Tuple4M(v[0], v[1], v[2], v[3]), v, null});
		candidates.addFirst(new Object[]{new Tuple5M(v[0], v[1], v[2], v[3], v[4]), v, null});
		candidates.addFirst(new Object[]{new Tuple6M(v[0], v[1], v[2], v[3], v[4], v[5]), v, null});

		v=new Integer[]{null, null, null, null, null, null};
		candidates.addFirst(new Object[]{new Tuple2(v[0], v[1]), v, null});
		candidates.addFirst(new Object[]{new Tuple3(v[0], v[1], v[2]), v, null});
		candidates.addFirst(new Object[]{new Tuple4(v[0], v[1], v[2], v[3]), v, null});
		candidates.addFirst(new Object[]{new Tuple5(v[0], v[1], v[2], v[3], v[4]), v, null});
		candidates.addFirst(new Object[]{new Tuple6(v[0], v[1], v[2], v[3], v[4], v[5]), v, null});

		candidates.addFirst(new Object[]{new Tuple2M(v[0], v[1]), v, null});
		candidates.addFirst(new Object[]{new Tuple3M(v[0], v[1], v[2]), v, null});
		candidates.addFirst(new Object[]{new Tuple4M(v[0], v[1], v[2], v[3]), v, null});
		candidates.addFirst(new Object[]{new Tuple5M(v[0], v[1], v[2], v[3], v[4]), v, null});
		candidates.addFirst(new Object[]{new Tuple6M(v[0], v[1], v[2], v[3], v[4], v[5]), v, null});
	}

	private Object shallowCopyTuple(){
		if(tuple instanceof Tuple2){
			return new Tuple2(values[0], values[1]);
		}else if(tuple instanceof Tuple3){
			return new Tuple3(values[0], values[1], values[2]);
		}else if(tuple instanceof Tuple4){
			return new Tuple4(values[0], values[1], values[2], values[3]);
		}else if(tuple instanceof Tuple5){
			return new Tuple5(values[0], values[1], values[2], values[3], values[4]);
		}else if(tuple instanceof Tuple6){
			return new Tuple6(values[0], values[1], values[2], values[3], values[4], values[5]);
		}else if(tuple instanceof Tuple2M){
			return new Tuple2M(values[0], values[1]);
		}else if(tuple instanceof Tuple3M){
			return new Tuple3M(values[0], values[1], values[2]);
		}else if(tuple instanceof Tuple4M){
			return new Tuple4M(values[0], values[1], values[2], values[3]);
		}else if(tuple instanceof Tuple5M){
			return new Tuple5M(values[0], values[1], values[2], values[3], values[4]);
		}else/* if(from instanceof Tuple6M)*/{
			return new Tuple6M(values[0], values[1], values[2], values[3], values[4], values[5]);
		}
	}

	private Object deepCopyTuple(int modif1, int modif2){
		if(tuple instanceof Tuple2){
			return new Tuple2(values[0].intValue() + modif1, values[1].intValue() + modif2);
		}else if(tuple instanceof Tuple3){
			return new Tuple3(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue());
		}else if(tuple instanceof Tuple4){
			return new Tuple4(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue(), values[3].intValue());
		}else if(tuple instanceof Tuple5){
			return new Tuple5(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue(), values[3].intValue(), values[4].intValue());
		}else if(tuple instanceof Tuple6){
			return new Tuple6(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue(), values[3].intValue(), values[4].intValue(), values[5].intValue());
		}else if(tuple instanceof Tuple2M){
			return new Tuple2M(values[0].intValue() + modif1, values[1].intValue() + modif2);
		}else if(tuple instanceof Tuple3M){
			return new Tuple3M(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue());
		}else if(tuple instanceof Tuple4M){
			return new Tuple4M(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue(), values[3].intValue());
		}else if(tuple instanceof Tuple5M){
			return new Tuple5M(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue(), values[3].intValue(), values[4].intValue());
		}else/* if(from instanceof Tuple6M)*/{
			return new Tuple6M(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue(), values[3].intValue(), values[4].intValue(), values[5].intValue());
		}
	}

	private Object contraryMutability(int modif1, int modif2){
		if(tuple instanceof Tuple2){
			return new Tuple2M(values[0].intValue() + modif1, values[1].intValue() + modif2);
		}else if(tuple instanceof Tuple3){
			return new Tuple3M(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue());
		}else if(tuple instanceof Tuple4){
			return new Tuple4M(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue(), values[3].intValue());
		}else if(tuple instanceof Tuple5){
			return new Tuple5M(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue(), values[3].intValue(), values[4].intValue());
		}else if(tuple instanceof Tuple6){
			return new Tuple6M(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue(), values[3].intValue(), values[4].intValue(), values[5].intValue());
		}else if(tuple instanceof Tuple2M){
			return new Tuple2(values[0].intValue() + modif1, values[1].intValue() + modif2);
		}else if(tuple instanceof Tuple3M){
			return new Tuple3(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue());
		}else if(tuple instanceof Tuple4M){
			return new Tuple4(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue(), values[3].intValue());
		}else if(tuple instanceof Tuple5M){
			return new Tuple5(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue(), values[3].intValue(), values[4].intValue());
		}else/* if(from instanceof Tuple6M)*/{
			return new Tuple6(values[0].intValue() + modif1, values[1].intValue() + modif2, values[2].intValue(), values[3].intValue(), values[4].intValue(), values[5].intValue());
		}
	}

	private Object contraryMutabilityNull(){
		if(tuple instanceof Tuple2){
			return new Tuple2M(values[0], values[1]);
		}else if(tuple instanceof Tuple3){
			return new Tuple3M(values[0], values[1], values[2]);
		}else if(tuple instanceof Tuple4){
			return new Tuple4M(values[0], values[1], values[2], values[3]);
		}else if(tuple instanceof Tuple5){
			return new Tuple5M(values[0], values[1], values[2], values[3], values[4]);
		}else if(tuple instanceof Tuple6){
			return new Tuple6M(values[0], values[1], values[2], values[3], values[4], values[5]);
		}else if(tuple instanceof Tuple2M){
			return new Tuple2(values[0], values[1]);
		}else if(tuple instanceof Tuple3M){
			return new Tuple3(values[0], values[1], values[2]);
		}else if(tuple instanceof Tuple4M){
			return new Tuple4(values[0], values[1], values[2], values[3]);
		}else if(tuple instanceof Tuple5M){
			return new Tuple5(values[0], values[1], values[2], values[3], values[4]);
		}else/* if(from instanceof Tuple6M)*/{
			return new Tuple6(values[0], values[1], values[2], values[3], values[4], values[5]);
		}
	}

	private Object nonNullTuple(Integer first){
		if(tuple instanceof Tuple2){
			return new Tuple2(first, 1);
		}else if(tuple instanceof Tuple3){
			return new Tuple3(first, 1, 1);
		}else if(tuple instanceof Tuple4){
			return new Tuple4(first, 1, 1, 1);
		}else if(tuple instanceof Tuple5){
			return new Tuple5(first, 1, 1, 1, 1);
		}else if(tuple instanceof Tuple6){
			return new Tuple6(first, 1, 1, 1, 1, 1);
		}else if(tuple instanceof Tuple2M){
			return new Tuple2M(first, 1);
		}else if(tuple instanceof Tuple3M){
			return new Tuple3M(first, 1, 1);
		}else if(tuple instanceof Tuple4M){
			return new Tuple4M(first, 1, 1, 1);
		}else if(tuple instanceof Tuple5M){
			return new Tuple5M(first, 1, 1, 1, 1);
		}else/* if(from instanceof Tuple6M)*/{
			return new Tuple6M(first, 1, 1, 1, 1, 1);
		}
	}

	private Object uncomparableTuple(){
		if(tuple instanceof Tuple2){
			return new Tuple2(new Object(), new Object());
		}else if(tuple instanceof Tuple3){
			return new Tuple3(new Object(), new Object(), new Object());
		}else if(tuple instanceof Tuple4){
			return new Tuple4(new Object(), new Object(), new Object(), new Object());
		}else if(tuple instanceof Tuple5){
			return new Tuple5(new Object(), new Object(), new Object(), new Object(), new Object());
		}else if(tuple instanceof Tuple6){
			return new Tuple6(new Object(), new Object(), new Object(), new Object(), new Object(), new Object());
		}else if(tuple instanceof Tuple2M){
			return new Tuple2M(new Object(), new Object());
		}else if(tuple instanceof Tuple3M){
			return new Tuple3M(new Object(), new Object(), new Object());
		}else if(tuple instanceof Tuple4M){
			return new Tuple4M(new Object(), new Object(), new Object(), new Object());
		}else if(tuple instanceof Tuple5M){
			return new Tuple5M(new Object(), new Object(), new Object(), new Object(), new Object());
		}else/* if(from instanceof Tuple6M)*/{
			return new Tuple6M(new Object(), new Object(), new Object(), new Object(), new Object(), new Object());
		}
	}

	public TuplesTest(Object tuple, Integer[] values, Object[] ascendants){
		this.tuple=tuple;
		this.values=values;
		this.ascendants=ascendants;
	}

	@Parameters
	public static List<Object[]> data(){
		return new List<Object[]>(){
			private int last_requested=-1;
			private Object[] last_given;

			@Override
			public int size(){
				return NUM_CANDIDATES;
			}

			@Override
			public Object[] get(int index){
				if(index != last_requested){
					last_requested=index;
					last_given=candidates.removeLast();
				}
				return last_given;
			}

			@Override
			public boolean isEmpty(){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public boolean contains(Object o){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public Iterator<Object[]> iterator(){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public Object[] toArray(){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public <T> T[] toArray(T[] a){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public boolean add(Object[] e){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public boolean remove(Object o){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public boolean containsAll(Collection<?> c){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public boolean addAll(Collection<? extends Object[]> c){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public boolean addAll(int index, Collection<? extends Object[]> c){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public boolean removeAll(Collection<?> c){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public boolean retainAll(Collection<?> c){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void clear(){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public Object[] set(int index, Object[] element){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void add(int index, Object[] element){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public Object[] remove(int index){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public int indexOf(Object o){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public int lastIndexOf(Object o){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public ListIterator<Object[]> listIterator(){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public ListIterator<Object[]> listIterator(int index){
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public List<Object[]> subList(int fromIndex, int toIndex){
				throw new UnsupportedOperationException("Not supported yet.");
			}
		};
	}

	/**
	 * Tests wether the construction of a new tuple gives a non null object.
	 */
	@Test
	public void testConstructor(){
		assertNotNull(tuple);
	}

	/**
	 * Test of getFirst method.
	 */
	@Test
	public void testGetFirst1(){
		if(tuple instanceof Tuple2){
			assertSame(((Tuple2)tuple).getFirst(), values[0]);
		}else if(tuple instanceof Tuple3){
			assertSame(((Tuple3)tuple).getFirst(), values[0]);
		}else if(tuple instanceof Tuple4){
			assertSame(((Tuple4)tuple).getFirst(), values[0]);
		}else if(tuple instanceof Tuple5){
			assertSame(((Tuple5)tuple).getFirst(), values[0]);
		}else if(tuple instanceof Tuple6){
			assertSame(((Tuple6)tuple).getFirst(), values[0]);
		}
	}

	/**
	 * Test of getSecond method.
	 */
	@Test
	public void testGetSecond1(){
		if(tuple instanceof Tuple2){
			assertSame(((Tuple2)tuple).getSecond(), values[1]);
		}else if(tuple instanceof Tuple3){
			assertSame(((Tuple3)tuple).getSecond(), values[1]);
		}else if(tuple instanceof Tuple4){
			assertSame(((Tuple4)tuple).getSecond(), values[1]);
		}else if(tuple instanceof Tuple5){
			assertSame(((Tuple5)tuple).getSecond(), values[1]);
		}else if(tuple instanceof Tuple6){
			assertSame(((Tuple6)tuple).getSecond(), values[1]);
		}
	}

	/**
	 * Test of getThird method.
	 */
	@Test
	public void testGetThird1(){
		if(tuple instanceof Tuple3){
			assertSame(((Tuple3)tuple).getThird(), values[2]);
		}else if(tuple instanceof Tuple4){
			assertSame(((Tuple4)tuple).getThird(), values[2]);
		}else if(tuple instanceof Tuple5){
			assertSame(((Tuple5)tuple).getThird(), values[2]);
		}else if(tuple instanceof Tuple6){
			assertSame(((Tuple6)tuple).getThird(), values[2]);
		}
	}

	/**
	 * Test of getFourth method.
	 */
	@Test
	public void testGetFourth1(){
		if(tuple instanceof Tuple4){
			assertSame(((Tuple4)tuple).getFourth(), values[3]);
		}else if(tuple instanceof Tuple5){
			assertSame(((Tuple5)tuple).getFourth(), values[3]);
		}else if(tuple instanceof Tuple6){
			assertSame(((Tuple6)tuple).getFourth(), values[3]);
		}
	}

	/**
	 * Test of getFifth method.
	 */
	@Test
	public void testGetFifth1(){
		if(tuple instanceof Tuple5){
			assertSame(((Tuple5)tuple).getFifth(), values[4]);
		}else if(tuple instanceof Tuple6){
			assertSame(((Tuple6)tuple).getFifth(), values[4]);
		}
	}

	/**
	 * Test of getSixth method.
	 */
	@Test
	public void testGetSixth1(){
		if(tuple instanceof Tuple6){
			assertSame(((Tuple6)tuple).getSixth(), values[5]);
		}
	}

	/**
	 * Test of setFirst method.
	 */
	@Test
	public void testSetFirst1(){
		Integer new_value=7;
		Integer old_value=null;
		if(tuple instanceof Tuple2M){
			old_value=(Integer)((Tuple2M)tuple).getFirst();
			assertSame(((Tuple2M)tuple).setFirst(new_value), values[0]);
			assertSame(((Tuple2M)tuple).getFirst(), new_value);
		}else if(tuple instanceof Tuple3M){
			old_value=(Integer)((Tuple3M)tuple).getFirst();
			assertSame(((Tuple3M)tuple).setFirst(new_value), values[0]);
			assertSame(((Tuple3M)tuple).getFirst(), new_value);
		}else if(tuple instanceof Tuple4M){
			old_value=(Integer)((Tuple4M)tuple).getFirst();
			assertSame(((Tuple4M)tuple).setFirst(new_value), values[0]);
			assertSame(((Tuple4M)tuple).getFirst(), new_value);
		}else if(tuple instanceof Tuple5M){
			old_value=(Integer)((Tuple5M)tuple).getFirst();
			assertSame(((Tuple5M)tuple).setFirst(new_value), values[0]);
			assertSame(((Tuple5M)tuple).getFirst(), new_value);
		}else if(tuple instanceof Tuple6M){
			old_value=(Integer)((Tuple6M)tuple).getFirst();
			assertSame(((Tuple6M)tuple).setFirst(new_value), values[0]);
			assertSame(((Tuple6M)tuple).getFirst(), new_value);
		}

		if(ascendants != null){
			for(Object ascendant:ascendants){
				if(ascendant instanceof Tuple3M){
					assertSame(((Tuple3M)ascendant).getFirst(), new_value);
				}else if(ascendant instanceof Tuple4M){
					assertSame(((Tuple4M)ascendant).getFirst(), new_value);
				}else if(ascendant instanceof Tuple5M){
					assertSame(((Tuple5M)ascendant).getFirst(), new_value);
				}else if(ascendant instanceof Tuple6M){
					assertSame(((Tuple6M)ascendant).getFirst(), new_value);
				}
			}
		}
		
		if(tuple instanceof Tuple2M){
			((Tuple2M)tuple).setFirst(old_value);
		}else if(tuple instanceof Tuple3M){
			((Tuple3M)tuple).setFirst(old_value);
		}else if(tuple instanceof Tuple4M){
			((Tuple4M)tuple).setFirst(old_value);
		}else if(tuple instanceof Tuple5M){
			((Tuple5M)tuple).setFirst(old_value);
		}else if(tuple instanceof Tuple6M){
			((Tuple6M)tuple).setFirst(old_value);
		}
	}

	/**
	 * Test of setFirst method with null new value.
	 */
	@Test
	public void testSetFirst2(){
		Integer new_value=null;
		Integer old_value=null;
		if(tuple instanceof Tuple2M){
			old_value=(Integer)((Tuple2M)tuple).getFirst();
			assertSame(((Tuple2M)tuple).setFirst(new_value), values[0]);
			assertSame(((Tuple2M)tuple).getFirst(), new_value);
		}else if(tuple instanceof Tuple3M){
			old_value=(Integer)((Tuple3M)tuple).getFirst();
			assertSame(((Tuple3M)tuple).setFirst(new_value), values[0]);
			assertSame(((Tuple3M)tuple).getFirst(), new_value);
		}else if(tuple instanceof Tuple4M){
			old_value=(Integer)((Tuple4M)tuple).getFirst();
			assertSame(((Tuple4M)tuple).setFirst(new_value), values[0]);
			assertSame(((Tuple4M)tuple).getFirst(), new_value);
		}else if(tuple instanceof Tuple5M){
			old_value=(Integer)((Tuple5M)tuple).getFirst();
			assertSame(((Tuple5M)tuple).setFirst(new_value), values[0]);
			assertSame(((Tuple5M)tuple).getFirst(), new_value);
		}else if(tuple instanceof Tuple6M){
			old_value=(Integer)((Tuple6M)tuple).getFirst();
			assertSame(((Tuple6M)tuple).setFirst(new_value), values[0]);
			assertSame(((Tuple6M)tuple).getFirst(), new_value);
		}

		if(ascendants != null){
			for(Object ascendant:ascendants){
				if(ascendant instanceof Tuple3M){
					assertSame(((Tuple3M)ascendant).getFirst(), new_value);
				}else if(ascendant instanceof Tuple4M){
					assertSame(((Tuple4M)ascendant).getFirst(), new_value);
				}else if(ascendant instanceof Tuple5M){
					assertSame(((Tuple5M)ascendant).getFirst(), new_value);
				}else if(ascendant instanceof Tuple6M){
					assertSame(((Tuple6M)ascendant).getFirst(), new_value);
				}
			}
		}
		
		if(tuple instanceof Tuple2M){
			((Tuple2M)tuple).setFirst(old_value);
		}else if(tuple instanceof Tuple3M){
			((Tuple3M)tuple).setFirst(old_value);
		}else if(tuple instanceof Tuple4M){
			((Tuple4M)tuple).setFirst(old_value);
		}else if(tuple instanceof Tuple5M){
			((Tuple5M)tuple).setFirst(old_value);
		}else if(tuple instanceof Tuple6M){
			((Tuple6M)tuple).setFirst(old_value);
		}
	}

	/**
	 * Test of setSecond method.
	 */
	@Test
	public void testSetSecond1(){
		Integer new_value=7;
		Integer old_value=null;
		if(tuple instanceof Tuple2M){
			old_value=(Integer)((Tuple2M)tuple).getSecond();
			assertSame(((Tuple2M)tuple).setSecond(new_value), values[1]);
			assertSame(((Tuple2M)tuple).getSecond(), new_value);
		}else if(tuple instanceof Tuple3M){
			old_value=(Integer)((Tuple3M)tuple).getSecond();
			assertSame(((Tuple3M)tuple).setSecond(new_value), values[1]);
			assertSame(((Tuple3M)tuple).getSecond(), new_value);
		}else if(tuple instanceof Tuple4M){
			old_value=(Integer)((Tuple4M)tuple).getSecond();
			assertSame(((Tuple4M)tuple).setSecond(new_value), values[1]);
			assertSame(((Tuple4M)tuple).getSecond(), new_value);
		}else if(tuple instanceof Tuple5M){
			old_value=(Integer)((Tuple5M)tuple).getSecond();
			assertSame(((Tuple5M)tuple).setSecond(new_value), values[1]);
			assertSame(((Tuple5M)tuple).getSecond(), new_value);
		}else if(tuple instanceof Tuple6M){
			old_value=(Integer)((Tuple6M)tuple).getSecond();
			assertSame(((Tuple6M)tuple).setSecond(new_value), values[1]);
			assertSame(((Tuple6M)tuple).getSecond(), new_value);
		}

		if(ascendants != null){
			for(Object ascendant:ascendants){
				if(ascendant instanceof Tuple3M){
					assertSame(((Tuple3M)ascendant).getSecond(), new_value);
				}else if(ascendant instanceof Tuple4M){
					assertSame(((Tuple4M)ascendant).getSecond(), new_value);
				}else if(ascendant instanceof Tuple5M){
					assertSame(((Tuple5M)ascendant).getSecond(), new_value);
				}else if(ascendant instanceof Tuple6M){
					assertSame(((Tuple6M)ascendant).getSecond(), new_value);
				}
			}
		}
		
		if(tuple instanceof Tuple2M){
			((Tuple2M)tuple).setSecond(old_value);
		}else if(tuple instanceof Tuple3M){
			((Tuple3M)tuple).setSecond(old_value);
		}else if(tuple instanceof Tuple4M){
			((Tuple4M)tuple).setSecond(old_value);
		}else if(tuple instanceof Tuple5M){
			((Tuple5M)tuple).setSecond(old_value);
		}else if(tuple instanceof Tuple6M){
			((Tuple6M)tuple).setSecond(old_value);
		}
	}

	/**
	 * Test of setSecond method with null new value.
	 */
	@Test
	public void testSetSecond2(){
		Integer new_value=null;
		Integer old_value=null;
		if(tuple instanceof Tuple2M){
			old_value=(Integer)((Tuple2M)tuple).getSecond();
			assertSame(((Tuple2M)tuple).setSecond(new_value), values[1]);
			assertSame(((Tuple2M)tuple).getSecond(), new_value);
		}else if(tuple instanceof Tuple3M){
			old_value=(Integer)((Tuple3M)tuple).getSecond();
			assertSame(((Tuple3M)tuple).setSecond(new_value), values[1]);
			assertSame(((Tuple3M)tuple).getSecond(), new_value);
		}else if(tuple instanceof Tuple4M){
			old_value=(Integer)((Tuple4M)tuple).getSecond();
			assertSame(((Tuple4M)tuple).setSecond(new_value), values[1]);
			assertSame(((Tuple4M)tuple).getSecond(), new_value);
		}else if(tuple instanceof Tuple5M){
			old_value=(Integer)((Tuple5M)tuple).getSecond();
			assertSame(((Tuple5M)tuple).setSecond(new_value), values[1]);
			assertSame(((Tuple5M)tuple).getSecond(), new_value);
		}else if(tuple instanceof Tuple6M){
			old_value=(Integer)((Tuple6M)tuple).getSecond();
			assertSame(((Tuple6M)tuple).setSecond(new_value), values[1]);
			assertSame(((Tuple6M)tuple).getSecond(), new_value);
		}

		if(ascendants != null){
			for(Object ascendant:ascendants){
				if(ascendant instanceof Tuple3M){
					assertSame(((Tuple3M)ascendant).getSecond(), new_value);
				}else if(ascendant instanceof Tuple4M){
					assertSame(((Tuple4M)ascendant).getSecond(), new_value);
				}else if(ascendant instanceof Tuple5M){
					assertSame(((Tuple5M)ascendant).getSecond(), new_value);
				}else if(ascendant instanceof Tuple6M){
					assertSame(((Tuple6M)ascendant).getSecond(), new_value);
				}
			}
		}
		
		if(tuple instanceof Tuple2M){
			((Tuple2M)tuple).setSecond(old_value);
		}else if(tuple instanceof Tuple3M){
			((Tuple3M)tuple).setSecond(old_value);
		}else if(tuple instanceof Tuple4M){
			((Tuple4M)tuple).setSecond(old_value);
		}else if(tuple instanceof Tuple5M){
			((Tuple5M)tuple).setSecond(old_value);
		}else if(tuple instanceof Tuple6M){
			((Tuple6M)tuple).setSecond(old_value);
		}
	}

	/**
	 * Test of setThird method.
	 */
	@Test
	public void testSetThird1(){
		Integer new_value=7;
		Integer old_value;
		if(tuple instanceof Tuple3M){
			old_value=(Integer)((Tuple3M)tuple).getThird();
			assertSame(((Tuple3M)tuple).setThird(new_value), values[2]);
			assertSame(((Tuple3M)tuple).getThird(), new_value);
		}else if(tuple instanceof Tuple4M){
			old_value=(Integer)((Tuple4M)tuple).getThird();
			assertSame(((Tuple4M)tuple).setThird(new_value), values[2]);
			assertSame(((Tuple4M)tuple).getThird(), new_value);
		}else if(tuple instanceof Tuple5M){
			old_value=(Integer)((Tuple5M)tuple).getThird();
			assertSame(((Tuple5M)tuple).setThird(new_value), values[2]);
			assertSame(((Tuple5M)tuple).getThird(), new_value);
		}else if(tuple instanceof Tuple6M){
			old_value=(Integer)((Tuple6M)tuple).getThird();
			assertSame(((Tuple6M)tuple).setThird(new_value), values[2]);
			assertSame(((Tuple6M)tuple).getThird(), new_value);
		}else{
			return;
		}

		if(ascendants != null){
			for(Object ascendant:ascendants){
				if(ascendant instanceof Tuple4M){
					assertSame(((Tuple4M)ascendant).getThird(), new_value);
				}else if(ascendant instanceof Tuple5M){
					assertSame(((Tuple5M)ascendant).getThird(), new_value);
				}else if(ascendant instanceof Tuple6M){
					assertSame(((Tuple6M)ascendant).getThird(), new_value);
				}
			}
		}
		
		if(tuple instanceof Tuple3M){
			((Tuple3M)tuple).setThird(old_value);
		}else if(tuple instanceof Tuple4M){
			((Tuple4M)tuple).setThird(old_value);
		}else if(tuple instanceof Tuple5M){
			((Tuple5M)tuple).setThird(old_value);
		}else if(tuple instanceof Tuple6M){
			((Tuple6M)tuple).setThird(old_value);
		}
	}

	/**
	 * Test of setThird method with null value.
	 */
	@Test
	public void testSetThird2(){
		Integer new_value=null;
		Integer old_value;
		if(tuple instanceof Tuple3M){
			old_value=(Integer)((Tuple3M)tuple).getThird();
			assertSame(((Tuple3M)tuple).setThird(new_value), values[2]);
			assertSame(((Tuple3M)tuple).getThird(), new_value);
		}else if(tuple instanceof Tuple4M){
			old_value=(Integer)((Tuple4M)tuple).getThird();
			assertSame(((Tuple4M)tuple).setThird(new_value), values[2]);
			assertSame(((Tuple4M)tuple).getThird(), new_value);
		}else if(tuple instanceof Tuple5M){
			old_value=(Integer)((Tuple5M)tuple).getThird();
			assertSame(((Tuple5M)tuple).setThird(new_value), values[2]);
			assertSame(((Tuple5M)tuple).getThird(), new_value);
		}else if(tuple instanceof Tuple6M){
			old_value=(Integer)((Tuple6M)tuple).getThird();
			assertSame(((Tuple6M)tuple).setThird(new_value), values[2]);
			assertSame(((Tuple6M)tuple).getThird(), new_value);
		}else{
			return;
		}

		if(ascendants != null){
			for(Object ascendant:ascendants){
				if(ascendant instanceof Tuple4M){
					assertSame(((Tuple4M)ascendant).getThird(), new_value);
				}else if(ascendant instanceof Tuple5M){
					assertSame(((Tuple5M)ascendant).getThird(), new_value);
				}else if(ascendant instanceof Tuple6M){
					assertSame(((Tuple6M)ascendant).getThird(), new_value);
				}
			}
		}
		
		if(tuple instanceof Tuple3M){
			((Tuple3M)tuple).setThird(old_value);
		}else if(tuple instanceof Tuple4M){
			((Tuple4M)tuple).setThird(old_value);
		}else if(tuple instanceof Tuple5M){
			((Tuple5M)tuple).setThird(old_value);
		}else if(tuple instanceof Tuple6M){
			((Tuple6M)tuple).setThird(old_value);
		}
	}

	/**
	 * Test of setFourth method.
	 */
	@Test
	public void testSetFourth1(){
		Integer new_value=7;
		Integer old_value;
		if(tuple instanceof Tuple4M){
			old_value=(Integer)((Tuple4M)tuple).getFourth();
			assertSame(((Tuple4M)tuple).setFourth(new_value), values[3]);
			assertSame(((Tuple4M)tuple).getFourth(), new_value);
		}else if(tuple instanceof Tuple5M){
			old_value=(Integer)((Tuple5M)tuple).getFourth();
			assertSame(((Tuple5M)tuple).setFourth(new_value), values[3]);
			assertSame(((Tuple5M)tuple).getFourth(), new_value);
		}else if(tuple instanceof Tuple6M){
			old_value=(Integer)((Tuple6M)tuple).getFourth();
			assertSame(((Tuple6M)tuple).setFourth(new_value), values[3]);
			assertSame(((Tuple6M)tuple).getFourth(), new_value);
		}else{
			return;
		}

		if(ascendants != null){
			for(Object ascendant:ascendants){
				if(ascendant instanceof Tuple5M){
					assertSame(((Tuple5M)ascendant).getFourth(), new_value);
				}else if(ascendant instanceof Tuple6M){
					assertSame(((Tuple6M)ascendant).getFourth(), new_value);
				}
			}
		}
		
		if(tuple instanceof Tuple4M){
			((Tuple4M)tuple).setFourth(old_value);
		}else if(tuple instanceof Tuple5M){
			((Tuple5M)tuple).setFourth(old_value);
		}else if(tuple instanceof Tuple6M){
			((Tuple6M)tuple).setFourth(old_value);
		}
	}

	/**
	 * Test of setFourth method with null new value.
	 */
	@Test
	public void testSetFourth2(){
		Integer new_value=null;
		Integer old_value;
		if(tuple instanceof Tuple4M){
			old_value=(Integer)((Tuple4M)tuple).getFourth();
			assertSame(((Tuple4M)tuple).setFourth(new_value), values[3]);
			assertSame(((Tuple4M)tuple).getFourth(), new_value);
		}else if(tuple instanceof Tuple5M){
			old_value=(Integer)((Tuple5M)tuple).getFourth();
			assertSame(((Tuple5M)tuple).setFourth(new_value), values[3]);
			assertSame(((Tuple5M)tuple).getFourth(), new_value);
		}else if(tuple instanceof Tuple6M){
			old_value=(Integer)((Tuple6M)tuple).getFourth();
			assertSame(((Tuple6M)tuple).setFourth(new_value), values[3]);
			assertSame(((Tuple6M)tuple).getFourth(), new_value);
		}else{
			return;
		}

		if(ascendants != null){
			for(Object ascendant:ascendants){
				if(ascendant instanceof Tuple5M){
					assertSame(((Tuple5M)ascendant).getFourth(), new_value);
				}else if(ascendant instanceof Tuple6M){
					assertSame(((Tuple6M)ascendant).getFourth(), new_value);
				}
			}
		}
		
		if(tuple instanceof Tuple4M){
			((Tuple4M)tuple).setFourth(old_value);
		}else if(tuple instanceof Tuple5M){
			((Tuple5M)tuple).setFourth(old_value);
		}else if(tuple instanceof Tuple6M){
			((Tuple6M)tuple).setFourth(old_value);
		}
	}

	/**
	 * Test of setFifth method.
	 */
	@Test
	public void testSetFifth1(){
		Integer new_value=7;
		Integer old_value;
		if(tuple instanceof Tuple5M){
			old_value=(Integer)((Tuple5M)tuple).getFifth();
			assertSame(((Tuple5M)tuple).setFifth(new_value), values[4]);
			assertSame(((Tuple5M)tuple).getFifth(), new_value);
		}else if(tuple instanceof Tuple6M){
			old_value=(Integer)((Tuple6M)tuple).getFifth();
			assertSame(((Tuple6M)tuple).setFifth(new_value), values[4]);
			assertSame(((Tuple6M)tuple).getFifth(), new_value);
		}else{
			return;
		}

		if(ascendants != null){
			for(Object ascendant:ascendants){
				if(ascendant instanceof Tuple6M){
					assertSame(((Tuple6M)ascendant).getFifth(), new_value);
				}
			}
		}
		
		
		if(tuple instanceof Tuple5M){
			((Tuple5M)tuple).setFifth(old_value);
		}else if(tuple instanceof Tuple6M){
			((Tuple6M)tuple).setFifth(old_value);
		}
	}

	/**
	 * Test of setFifth method with null new value.
	 */
	@Test
	public void testSetFifth2(){
		Integer new_value=null;
		Integer old_value;
		if(tuple instanceof Tuple5M){
			old_value=(Integer)((Tuple5M)tuple).getFifth();
			assertSame(((Tuple5M)tuple).setFifth(new_value), values[4]);
			assertSame(((Tuple5M)tuple).getFifth(), new_value);
		}else if(tuple instanceof Tuple6M){
			old_value=(Integer)((Tuple6M)tuple).getFifth();
			assertSame(((Tuple6M)tuple).setFifth(new_value), values[4]);
			assertSame(((Tuple6M)tuple).getFifth(), new_value);
		}else{
			return;
		}

		if(ascendants != null){
			for(Object ascendant:ascendants){
				if(ascendant instanceof Tuple6M){
					assertSame(((Tuple6M)ascendant).getFifth(), new_value);
				}
			}
		}
		
		
		if(tuple instanceof Tuple5M){
			((Tuple5M)tuple).setFifth(old_value);
		}else if(tuple instanceof Tuple6M){
			((Tuple6M)tuple).setFifth(old_value);
		}
	}

	/**
	 * Test of setSixth method.
	 */
	@Test
	public void testSetSixth1(){
		Integer new_value=7;
		Integer old_value;
		if(tuple instanceof Tuple6M){
			old_value=(Integer)((Tuple6M)tuple).getSixth();
			assertSame(((Tuple6M)tuple).setSixth(new_value), values[5]);
			assertSame(((Tuple6M)tuple).getSixth(), new_value);
		}else{
			return;
		}
		
		if(tuple instanceof Tuple6M){
			((Tuple6M)tuple).setSixth(old_value);
		}
	}

	/**
	 * Test of setSixth method with null new value.
	 */
	@Test
	public void testSetSixth2(){
		Integer new_value=null;
		Integer old_value;
		if(tuple instanceof Tuple6M){
			old_value=(Integer)((Tuple6M)tuple).getSixth();
			assertSame(((Tuple6M)tuple).setSixth(new_value), values[5]);
			assertSame(((Tuple6M)tuple).getSixth(), new_value);
		}else{
			return;
		}
		
		if(tuple instanceof Tuple6M){
			((Tuple6M)tuple).setSixth(old_value);
		}
	}

	/**
	 * Test of compareTo with an equal tuple.
	 */
	@Test
	public void testCompareTo1(){
		if(values[0] == null){
			return;
		}
		Object copy=deepCopyTuple(0, 0);
		if(copy instanceof Tuple2){
			assertTrue(((Tuple2)tuple).compareTo((Tuple2)copy) == 0);
			assertTrue(((Tuple2)copy).compareTo((Tuple2)tuple) == 0);
		}else if(copy instanceof Tuple3){
			assertTrue(((Tuple3)tuple).compareTo((Tuple3)copy) == 0);
			assertTrue(((Tuple3)copy).compareTo((Tuple3)tuple) == 0);
		}else if(copy instanceof Tuple4){
			assertTrue(((Tuple4)tuple).compareTo((Tuple4)copy) == 0);
			assertTrue(((Tuple4)copy).compareTo((Tuple4)tuple) == 0);
		}else if(copy instanceof Tuple5){
			assertTrue(((Tuple5)tuple).compareTo((Tuple5)copy) == 0);
			assertTrue(((Tuple5)copy).compareTo((Tuple5)tuple) == 0);
		}else if(copy instanceof Tuple6){
			assertTrue(((Tuple6)tuple).compareTo((Tuple6)copy) == 0);
			assertTrue(((Tuple6)copy).compareTo((Tuple6)tuple) == 0);
		}
	}

	/**
	 * Test of compareTo with a lesser tuple (first different).
	 */
	@Test
	public void testCompareTo2(){
		if(values[0] == null){
			return;
		}
		Object copy=deepCopyTuple(-1, 0);
		if(copy instanceof Tuple2){
			assertTrue(((Tuple2)tuple).compareTo((Tuple2)copy) > 0);
			assertTrue(((Tuple2)copy).compareTo((Tuple2)tuple) < 0);
		}else if(copy instanceof Tuple3){
			assertTrue(((Tuple3)tuple).compareTo((Tuple3)copy) > 0);
			assertTrue(((Tuple3)copy).compareTo((Tuple3)tuple) < 0);
		}else if(copy instanceof Tuple4){
			assertTrue(((Tuple4)tuple).compareTo((Tuple4)copy) > 0);
			assertTrue(((Tuple4)copy).compareTo((Tuple4)tuple) < 0);
		}else if(copy instanceof Tuple5){
			assertTrue(((Tuple5)tuple).compareTo((Tuple5)copy) > 0);
			assertTrue(((Tuple5)copy).compareTo((Tuple5)tuple) < 0);
		}else if(copy instanceof Tuple6){
			assertTrue(((Tuple6)tuple).compareTo((Tuple6)copy) > 0);
			assertTrue(((Tuple6)copy).compareTo((Tuple6)tuple) < 0);
		}
	}

	/**
	 * Test of compareTo with a lesser tuple (second different).
	 */
	@Test
	public void testCompareTo3(){
		if(values[0] == null){
			return;
		}
		Object copy=deepCopyTuple(0, -1);
		if(copy instanceof Tuple2){
			assertTrue(((Tuple2)tuple).compareTo((Tuple2)copy) > 0);
			assertTrue(((Tuple2)copy).compareTo((Tuple2)tuple) < 0);
		}else if(copy instanceof Tuple3){
			assertTrue(((Tuple3)tuple).compareTo((Tuple3)copy) > 0);
			assertTrue(((Tuple3)copy).compareTo((Tuple3)tuple) < 0);
		}else if(copy instanceof Tuple4){
			assertTrue(((Tuple4)tuple).compareTo((Tuple4)copy) > 0);
			assertTrue(((Tuple4)copy).compareTo((Tuple4)tuple) < 0);
		}else if(copy instanceof Tuple5){
			assertTrue(((Tuple5)tuple).compareTo((Tuple5)copy) > 0);
			assertTrue(((Tuple5)copy).compareTo((Tuple5)tuple) < 0);
		}else if(copy instanceof Tuple6){
			assertTrue(((Tuple6)tuple).compareTo((Tuple6)copy) > 0);
			assertTrue(((Tuple6)copy).compareTo((Tuple6)tuple) < 0);
		}
	}

	/**
	 * Test of compareTo with a greater tuple (first different).
	 */
	@Test
	public void testCompareTo4(){
		if(values[0] == null){
			return;
		}
		Object copy=deepCopyTuple(1, 0);
		if(copy instanceof Tuple2){
			assertTrue(((Tuple2)tuple).compareTo((Tuple2)copy) < 0);
			assertTrue(((Tuple2)copy).compareTo((Tuple2)tuple) > 0);
		}else if(copy instanceof Tuple3){
			assertTrue(((Tuple3)tuple).compareTo((Tuple3)copy) < 0);
			assertTrue(((Tuple3)copy).compareTo((Tuple3)tuple) > 0);
		}else if(copy instanceof Tuple4){
			assertTrue(((Tuple4)tuple).compareTo((Tuple4)copy) < 0);
			assertTrue(((Tuple4)copy).compareTo((Tuple4)tuple) > 0);
		}else if(copy instanceof Tuple5){
			assertTrue(((Tuple5)tuple).compareTo((Tuple5)copy) < 0);
			assertTrue(((Tuple5)copy).compareTo((Tuple5)tuple) > 0);
		}else if(copy instanceof Tuple6){
			assertTrue(((Tuple6)tuple).compareTo((Tuple6)copy) < 0);
			assertTrue(((Tuple6)copy).compareTo((Tuple6)tuple) > 0);
		}
	}

	/**
	 * Test of compareTo with a greater tuple (second different).
	 */
	@Test
	public void testCompareTo5(){
		if(values[0] == null){
			return;
		}
		Object copy=deepCopyTuple(0, 1);
		if(copy instanceof Tuple2){
			assertTrue(((Tuple2)tuple).compareTo((Tuple2)copy) < 0);
			assertTrue(((Tuple2)copy).compareTo((Tuple2)tuple) > 0);
		}else if(copy instanceof Tuple3){
			assertTrue(((Tuple3)tuple).compareTo((Tuple3)copy) < 0);
			assertTrue(((Tuple3)copy).compareTo((Tuple3)tuple) > 0);
		}else if(copy instanceof Tuple4){
			assertTrue(((Tuple4)tuple).compareTo((Tuple4)copy) < 0);
			assertTrue(((Tuple4)copy).compareTo((Tuple4)tuple) > 0);
		}else if(copy instanceof Tuple5){
			assertTrue(((Tuple5)tuple).compareTo((Tuple5)copy) < 0);
			assertTrue(((Tuple5)copy).compareTo((Tuple5)tuple) > 0);
		}else if(copy instanceof Tuple6){
			assertTrue(((Tuple6)tuple).compareTo((Tuple6)copy) < 0);
			assertTrue(((Tuple6)copy).compareTo((Tuple6)tuple) > 0);
		}
	}

	/**
	 * Test of compareTo with two null tuples.
	 */
	@Test
	public void testCompareTo6(){
		if(values[0] != null){
			return;
		}
		Object cmp=shallowCopyTuple();
		if(cmp instanceof Tuple2){
			assertTrue(((Tuple2)tuple).compareTo((Tuple2)cmp) == 0);
			assertTrue(((Tuple2)cmp).compareTo((Tuple2)tuple) == 0);
		}else if(cmp instanceof Tuple3){
			assertTrue(((Tuple3)tuple).compareTo((Tuple3)cmp) == 0);
			assertTrue(((Tuple3)cmp).compareTo((Tuple3)tuple) == 0);
		}else if(cmp instanceof Tuple4){
			assertTrue(((Tuple4)tuple).compareTo((Tuple4)cmp) == 0);
			assertTrue(((Tuple4)cmp).compareTo((Tuple4)tuple) == 0);
		}else if(cmp instanceof Tuple5){
			assertTrue(((Tuple5)tuple).compareTo((Tuple5)cmp) == 0);
			assertTrue(((Tuple5)cmp).compareTo((Tuple5)tuple) == 0);
		}else if(cmp instanceof Tuple6){
			assertTrue(((Tuple6)tuple).compareTo((Tuple6)cmp) == 0);
			assertTrue(((Tuple6)cmp).compareTo((Tuple6)tuple) == 0);
		}
	}

	/**
	 * Test of compareTo with one null tuple (first different).
	 */
	@Test
	public void testCompareTo7(){
		if(values[0] != null){
			return;
		}
		Object cmp=nonNullTuple(1);
		if(cmp instanceof Tuple2){
			assertTrue(((Tuple2)tuple).compareTo((Tuple2)cmp) < 0);
			assertTrue(((Tuple2)cmp).compareTo((Tuple2)tuple) > 0);
		}else if(cmp instanceof Tuple3){
			assertTrue(((Tuple3)tuple).compareTo((Tuple3)cmp) < 0);
			assertTrue(((Tuple3)cmp).compareTo((Tuple3)tuple) > 0);
		}else if(cmp instanceof Tuple4){
			assertTrue(((Tuple4)tuple).compareTo((Tuple4)cmp) < 0);
			assertTrue(((Tuple4)cmp).compareTo((Tuple4)tuple) > 0);
		}else if(cmp instanceof Tuple5){
			assertTrue(((Tuple5)tuple).compareTo((Tuple5)cmp) < 0);
			assertTrue(((Tuple5)cmp).compareTo((Tuple5)tuple) > 0);
		}else if(cmp instanceof Tuple6){
			assertTrue(((Tuple6)tuple).compareTo((Tuple6)cmp) < 0);
			assertTrue(((Tuple6)cmp).compareTo((Tuple6)tuple) > 0);
		}
	}

	/**
	 * Test of compareTo with one null tuple (second different).
	 */
	@Test
	public void testCompareTo8(){
		if(values[0] != null){
			return;
		}
		Object cmp=nonNullTuple(null);
		if(cmp instanceof Tuple2){
			assertTrue(((Tuple2)tuple).compareTo((Tuple2)cmp) < 0);
			assertTrue(((Tuple2)cmp).compareTo((Tuple2)tuple) > 0);
		}else if(cmp instanceof Tuple3){
			assertTrue(((Tuple3)tuple).compareTo((Tuple3)cmp) < 0);
			assertTrue(((Tuple3)cmp).compareTo((Tuple3)tuple) > 0);
		}else if(cmp instanceof Tuple4){
			assertTrue(((Tuple4)tuple).compareTo((Tuple4)cmp) < 0);
			assertTrue(((Tuple4)cmp).compareTo((Tuple4)tuple) > 0);
		}else if(cmp instanceof Tuple5){
			assertTrue(((Tuple5)tuple).compareTo((Tuple5)cmp) < 0);
			assertTrue(((Tuple5)cmp).compareTo((Tuple5)tuple) > 0);
		}else if(cmp instanceof Tuple6){
			assertTrue(((Tuple6)tuple).compareTo((Tuple6)cmp) < 0);
			assertTrue(((Tuple6)cmp).compareTo((Tuple6)tuple) > 0);
		}
	}

	/**
	 * Test of compareTo with an equal tuple and different mutability.
	 */
	@Test
	public void testCompareTo9(){
		Object copy;
		if(values[0] == null){
			copy=contraryMutabilityNull();
		}else{
			copy=contraryMutability(0, 0);
		}
		if(copy instanceof Tuple2){
			assertTrue(((Tuple2)tuple).compareTo((Tuple2)copy) == 0);
			assertTrue(((Tuple2)copy).compareTo((Tuple2)tuple) == 0);
		}else if(copy instanceof Tuple3){
			assertTrue(((Tuple3)tuple).compareTo((Tuple3)copy) == 0);
			assertTrue(((Tuple3)copy).compareTo((Tuple3)tuple) == 0);
		}else if(copy instanceof Tuple4){
			assertTrue(((Tuple4)tuple).compareTo((Tuple4)copy) == 0);
			assertTrue(((Tuple4)copy).compareTo((Tuple4)tuple) == 0);
		}else if(copy instanceof Tuple5){
			assertTrue(((Tuple5)tuple).compareTo((Tuple5)copy) == 0);
			assertTrue(((Tuple5)copy).compareTo((Tuple5)tuple) == 0);
		}else if(copy instanceof Tuple6){
			assertTrue(((Tuple6)tuple).compareTo((Tuple6)copy) == 0);
			assertTrue(((Tuple6)copy).compareTo((Tuple6)tuple) == 0);
		}
	}

	/**
	 * Test of compareTo with a lesser tuple (first different) and different
	 * mutability.
	 */
	@Test
	public void testCompareTo10(){
		if(values[0] == null){
			return;
		}
		Object copy=contraryMutability(-1, 0);
		if(copy instanceof Tuple2){
			assertTrue(((Tuple2)tuple).compareTo((Tuple2)copy) > 0);
			assertTrue(((Tuple2)copy).compareTo((Tuple2)tuple) < 0);
		}else if(copy instanceof Tuple3){
			assertTrue(((Tuple3)tuple).compareTo((Tuple3)copy) > 0);
			assertTrue(((Tuple3)copy).compareTo((Tuple3)tuple) < 0);
		}else if(copy instanceof Tuple4){
			assertTrue(((Tuple4)tuple).compareTo((Tuple4)copy) > 0);
			assertTrue(((Tuple4)copy).compareTo((Tuple4)tuple) < 0);
		}else if(copy instanceof Tuple5){
			assertTrue(((Tuple5)tuple).compareTo((Tuple5)copy) > 0);
			assertTrue(((Tuple5)copy).compareTo((Tuple5)tuple) < 0);
		}else if(copy instanceof Tuple6){
			assertTrue(((Tuple6)tuple).compareTo((Tuple6)copy) > 0);
			assertTrue(((Tuple6)copy).compareTo((Tuple6)tuple) < 0);
		}
	}

	/**
	 * Test of compareTo with a lesser tuple (second different) and different
	 * mutability.
	 */
	@Test
	public void testCompareTo11(){
		if(values[0] == null){
			return;
		}
		Object copy=contraryMutability(0, -1);
		if(copy instanceof Tuple2){
			assertTrue(((Tuple2)tuple).compareTo((Tuple2)copy) > 0);
			assertTrue(((Tuple2)copy).compareTo((Tuple2)tuple) < 0);
		}else if(copy instanceof Tuple3){
			assertTrue(((Tuple3)tuple).compareTo((Tuple3)copy) > 0);
			assertTrue(((Tuple3)copy).compareTo((Tuple3)tuple) < 0);
		}else if(copy instanceof Tuple4){
			assertTrue(((Tuple4)tuple).compareTo((Tuple4)copy) > 0);
			assertTrue(((Tuple4)copy).compareTo((Tuple4)tuple) < 0);
		}else if(copy instanceof Tuple5){
			assertTrue(((Tuple5)tuple).compareTo((Tuple5)copy) > 0);
			assertTrue(((Tuple5)copy).compareTo((Tuple5)tuple) < 0);
		}else if(copy instanceof Tuple6){
			assertTrue(((Tuple6)tuple).compareTo((Tuple6)copy) > 0);
			assertTrue(((Tuple6)copy).compareTo((Tuple6)tuple) < 0);
		}
	}

	/**
	 * Test of compareTo with a greater tuple (first different) and different
	 * mutability.
	 */
	@Test
	public void testCompareTo12(){
		if(values[0] == null){
			return;
		}
		Object copy=contraryMutability(1, 0);
		if(copy instanceof Tuple2){
			assertTrue(((Tuple2)tuple).compareTo((Tuple2)copy) < 0);
			assertTrue(((Tuple2)copy).compareTo((Tuple2)tuple) > 0);
		}else if(copy instanceof Tuple3){
			assertTrue(((Tuple3)tuple).compareTo((Tuple3)copy) < 0);
			assertTrue(((Tuple3)copy).compareTo((Tuple3)tuple) > 0);
		}else if(copy instanceof Tuple4){
			assertTrue(((Tuple4)tuple).compareTo((Tuple4)copy) < 0);
			assertTrue(((Tuple4)copy).compareTo((Tuple4)tuple) > 0);
		}else if(copy instanceof Tuple5){
			assertTrue(((Tuple5)tuple).compareTo((Tuple5)copy) < 0);
			assertTrue(((Tuple5)copy).compareTo((Tuple5)tuple) > 0);
		}else if(copy instanceof Tuple6){
			assertTrue(((Tuple6)tuple).compareTo((Tuple6)copy) < 0);
			assertTrue(((Tuple6)copy).compareTo((Tuple6)tuple) > 0);
		}
	}

	/**
	 * Test of compareTo with a greater tuple (second different) and different
	 * mutability.
	 */
	@Test
	public void testCompareTo13(){
		if(values[0] == null){
			return;
		}
		Object copy=contraryMutability(0, 1);
		if(copy instanceof Tuple2){
			assertTrue(((Tuple2)tuple).compareTo((Tuple2)copy) < 0);
			assertTrue(((Tuple2)copy).compareTo((Tuple2)tuple) > 0);
		}else if(copy instanceof Tuple3){
			assertTrue(((Tuple3)tuple).compareTo((Tuple3)copy) < 0);
			assertTrue(((Tuple3)copy).compareTo((Tuple3)tuple) > 0);
		}else if(copy instanceof Tuple4){
			assertTrue(((Tuple4)tuple).compareTo((Tuple4)copy) < 0);
			assertTrue(((Tuple4)copy).compareTo((Tuple4)tuple) > 0);
		}else if(copy instanceof Tuple5){
			assertTrue(((Tuple5)tuple).compareTo((Tuple5)copy) < 0);
			assertTrue(((Tuple5)copy).compareTo((Tuple5)tuple) > 0);
		}else if(copy instanceof Tuple6){
			assertTrue(((Tuple6)tuple).compareTo((Tuple6)copy) < 0);
			assertTrue(((Tuple6)copy).compareTo((Tuple6)tuple) > 0);
		}
	}

	/**
	 * Test of compareTo with null argument.
	 */
	@Test(expected=NullPointerException.class)
	public void testCompareTo14(){
		if(tuple instanceof Tuple2){
			((Tuple2)tuple).compareTo(null);
		}else if(tuple instanceof Tuple3){
			((Tuple3)tuple).compareTo(null);
		}else if(tuple instanceof Tuple4){
			((Tuple4)tuple).compareTo(null);
		}else if(tuple instanceof Tuple5){
			((Tuple5)tuple).compareTo(null);
		}else if(tuple instanceof Tuple6){
			((Tuple6)tuple).compareTo(null);
		}
	}

	/**
	 * Test of compareTo with an uncomparableTuple as first tuple.
	 */
	@Test(expected=ClassCastException.class)
	public void testCompareTo15(){
		if(values[0] == null){
			throw new ClassCastException("Forced exception");
		}
		Object cmp=uncomparableTuple();
		if(tuple instanceof Tuple2){
			((Tuple2)cmp).compareTo((Tuple2)tuple);
		}else if(tuple instanceof Tuple3){
			((Tuple3)cmp).compareTo((Tuple3)tuple);
		}else if(tuple instanceof Tuple4){
			((Tuple4)cmp).compareTo((Tuple4)tuple);
		}else if(tuple instanceof Tuple5){
			((Tuple5)cmp).compareTo((Tuple5)tuple);
		}else if(tuple instanceof Tuple6){
			((Tuple6)cmp).compareTo((Tuple6)tuple);
		}
	}

	/**
	 * Test of compareTo with an uncomparableTuple as second tuple.
	 */
	@Test(expected=ClassCastException.class)
	public void testCompareTo16(){
		if(values[0] == null){
			throw new ClassCastException("Forced exception");
		}
		Object cmp=uncomparableTuple();
		if(tuple instanceof Tuple2){
			((Tuple2)tuple).compareTo((Tuple2)cmp);
		}else if(tuple instanceof Tuple3){
			((Tuple3)tuple).compareTo((Tuple3)cmp);
		}else if(tuple instanceof Tuple4){
			((Tuple4)tuple).compareTo((Tuple4)cmp);
		}else if(tuple instanceof Tuple5){
			((Tuple5)tuple).compareTo((Tuple5)cmp);
		}else if(tuple instanceof Tuple6){
			((Tuple6)tuple).compareTo((Tuple6)cmp);
		}
	}

	/**
	 * Test of equals with an equal tuple.
	 */
	@Test
	public void testEquals1(){
		Object copy;
		if(values[0] == null){
			copy=shallowCopyTuple();
		}else{
			copy=deepCopyTuple(0, 0);
		}
		assertTrue(tuple.equals(copy));
		assertTrue(copy.equals(tuple));
	}

	/**
	 * Test of equals with a different tuple (first different).
	 */
	@Test
	public void testEquals2(){
		Object copy;
		if(values[0] == null){
			copy=uncomparableTuple();
		}else{
			copy=deepCopyTuple(1, 0);
		}
		assertFalse(tuple.equals(copy));
		assertFalse(copy.equals(tuple));
	}

	/**
	 * Test of equals with a different tuple (second different).
	 */
	@Test
	public void testEquals3(){
		Object copy;
		if(values[0] == null){
			copy=uncomparableTuple();
		}else{
			copy=deepCopyTuple(0, 1);
		}
		assertFalse(tuple.equals(copy));
		assertFalse(copy.equals(tuple));
	}

	/**
	 * Test of equals with an equal tuple and different mutability.
	 */
	@Test
	public void testEquals4(){
		Object copy;
		if(values[0] == null){
			copy=contraryMutabilityNull();
		}else{
			copy=contraryMutability(0, 0);
		}
		assertTrue(tuple.equals(copy));
		assertTrue(copy.equals(tuple));
	}

	/**
	 * Test of equals with a different tuple (first different) and different
	 * mutability.
	 */
	@Test
	public void testEquals5(){
		Object copy;
		if(values[0] == null){
			return;
		}else{
			copy=contraryMutability(1, 0);
		}
		assertFalse(tuple.equals(copy));
		assertFalse(copy.equals(tuple));
	}

	/**
	 * Test of equals with a different tuple (second different) and different
	 * mutability.
	 */
	@Test
	public void testEquals6(){
		Object copy;
		if(values[0] == null){
			return;
		}else{
			copy=contraryMutability(0, 1);
		}
		assertFalse(tuple.equals(copy));
		assertFalse(copy.equals(tuple));
	}

	/**
	 * Test of equals with non a tuple.
	 */
	@Test
	public void testEquals7(){
		assertFalse(tuple.equals(1));
	}

	/**
	 * Test of hashCode.
	 */
	@Test
	public void testHashCode1(){
		tuple.hashCode();
	}

	@Test
	public void testToString1(){
		if(tuple instanceof Tuple2){
			assertEquals(tuple.toString(), "(" + values[0] + ", " + values[1] + ")");
		}else if(tuple instanceof Tuple3){
			assertEquals(tuple.toString(), "(" + values[0] + ", " + values[1] + ", " + values[2] + ")");
		}else if(tuple instanceof Tuple4){
			assertEquals(tuple.toString(), "(" + values[0] + ", " + values[1] + ", " + values[2] + ", " + values[3] + ")");
		}else if(tuple instanceof Tuple5){
			assertEquals(tuple.toString(), "(" + values[0] + ", " + values[1] + ", " + values[2] + ", " + values[3] + ", " + values[4] + ")");
		}else if(tuple instanceof Tuple6){
			assertEquals(tuple.toString(), "(" + values[0] + ", " + values[1] + ", " + values[2] + ", " + values[3] + ", " + values[4] + ", " + values[5] + ")");
		}
	}

	@Test
	public void testAsTuple2(){
		Object as;
		if(tuple instanceof Tuple3){
			as=((Tuple3)tuple).asTuple2();
		}else if(tuple instanceof Tuple4){
			as=((Tuple4)tuple).asTuple2();
		}else if(tuple instanceof Tuple5){
			as=((Tuple5)tuple).asTuple2();
		}else if(tuple instanceof Tuple6){
			as=((Tuple6)tuple).asTuple2();
		}else{
			return;
		}
		assertNotNull(as);
		candidates.addFirst(new Object[]{as, new Integer[]{values[0], values[1]}, null});
	}

	@Test
	public void testAsTuple2M(){
		Object as;
		if(tuple instanceof Tuple3M){
			as=((Tuple3M)tuple).asTuple2M();
		}else if(tuple instanceof Tuple4M){
			as=((Tuple4M)tuple).asTuple2M();
		}else if(tuple instanceof Tuple5M){
			as=((Tuple5M)tuple).asTuple2M();
		}else if(tuple instanceof Tuple6M){
			as=((Tuple6M)tuple).asTuple2M();
		}else{
			return;
		}
		assertNotNull(as);

		ArrayList<Object> new_ascendants=new ArrayList<>();
		if(ascendants != null){
			new_ascendants.addAll(Arrays.asList(ascendants));
		}
		new_ascendants.add(tuple);
		candidates.addFirst(new Object[]{as, new Integer[]{values[0], values[1]}, new_ascendants.toArray()});
	}

	@Test
	public void testAsTuple3(){
		Object as;
		if(tuple instanceof Tuple4){
			as=((Tuple4)tuple).asTuple3();
		}else if(tuple instanceof Tuple5){
			as=((Tuple5)tuple).asTuple3();
		}else if(tuple instanceof Tuple6){
			as=((Tuple6)tuple).asTuple3();
		}else{
			return;
		}
		assertNotNull(as);
		candidates.addFirst(new Object[]{as, new Integer[]{values[0], values[1], values[2]}, null});
	}

	@Test
	public void testAsTuple3M(){
		Object as;
		if(tuple instanceof Tuple4M){
			as=((Tuple4M)tuple).asTuple3M();
		}else if(tuple instanceof Tuple5M){
			as=((Tuple5M)tuple).asTuple3M();
		}else if(tuple instanceof Tuple6M){
			as=((Tuple6M)tuple).asTuple3M();
		}else{
			return;
		}
		assertNotNull(as);

		ArrayList<Object> new_ascendants=new ArrayList<>();
		if(ascendants != null){
			new_ascendants.addAll(Arrays.asList(ascendants));
		}
		new_ascendants.add(tuple);
		candidates.addFirst(new Object[]{as, new Integer[]{values[0], values[1], values[2]}, new_ascendants.toArray()});
	}

	@Test
	public void testAsTuple4(){
		Object as;
		if(tuple instanceof Tuple5){
			as=((Tuple5)tuple).asTuple4();
		}else if(tuple instanceof Tuple6){
			as=((Tuple6)tuple).asTuple4();
		}else{
			return;
		}
		assertNotNull(as);
		candidates.addFirst(new Object[]{as, new Integer[]{values[0], values[1], values[2], values[3]}, null});
	}

	@Test
	public void testAsTuple4M(){
		Object as;
		if(tuple instanceof Tuple5M){
			as=((Tuple5M)tuple).asTuple4M();
		}else if(tuple instanceof Tuple6M){
			as=((Tuple6M)tuple).asTuple4M();
		}else{
			return;
		}
		assertNotNull(as);

		ArrayList<Object> new_ascendants=new ArrayList<>();
		if(ascendants != null){
			new_ascendants.addAll(Arrays.asList(ascendants));
		}
		new_ascendants.add(tuple);
		candidates.addFirst(new Object[]{as, new Integer[]{values[0], values[1], values[2], values[3]}, new_ascendants.toArray()});
	}

	@Test
	public void testAsTuple5(){
		Object as;
		if(tuple instanceof Tuple6){
			as=((Tuple6)tuple).asTuple5();
		}else{
			return;
		}
		assertNotNull(as);
		candidates.addFirst(new Object[]{as, new Integer[]{values[0], values[1], values[2], values[3], values[4]}, null});
	}

	@Test
	public void testAsTuple5M(){
		Object as;
		if(tuple instanceof Tuple6M){
			as=((Tuple6M)tuple).asTuple5M();
		}else{
			return;
		}
		assertNotNull(as);

		ArrayList<Object> new_ascendants=new ArrayList<>();
		if(ascendants != null){
			new_ascendants.addAll(Arrays.asList(ascendants));
		}
		new_ascendants.add(tuple);
		candidates.addFirst(new Object[]{as, new Integer[]{values[0], values[1], values[2], values[3], values[4]}, new_ascendants.toArray()});
	}
}