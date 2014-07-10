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
package es.iguanod.algorithms;

import es.iguanod.base.Objects;
import es.iguanod.util.tuples.Tuple2;
import es.iguanod.util.tuples.Tuple3;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Unit tests for all classes in {@code es.iguanod.util.tuples}.
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1.1.cb
 * @version 1.0.1.b
 */
@RunWith(Enclosed.class)
public class KnapsackTest{

	public static class ShortSingleRunTests{

		/**
		 * Test of knapsack function.
		 */
		@Test
		public void testKnapsack1(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			assertTrue(Knapsack.knapsack(items, 15) == 16.0);
		}

		/**
		 * Test of knapsack function with empty items.
		 */
		@Test
		public void testKnapsack2(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();

			assertTrue(Knapsack.knapsack(items, 15) == 0.0);
		}

		/**
		 * Test of knapsack function with weight zero.
		 */
		@Test
		public void testKnapsack3(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			assertTrue(Knapsack.knapsack(items, 0) == 0.0);
		}

		/**
		 * Test of knapsack function with empty items and zero weight.
		 */
		@Test
		public void testKnapsack4(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();

			assertTrue(Knapsack.knapsack(items, 0) == 0.0);
		}

		/**
		 * Test of knapsack function with nulls.
		 */
		@Test(expected=NullPointerException.class)
		public void testKnapsack5(){
			Knapsack.knapsack(null, 15);
		}

		/**
		 * Test of knapsack function with nulls.
		 */
		@Test(expected=NullPointerException.class)
		public void testKnapsack6(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(null);
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsack(items, 15);
		}

		/**
		 * Test of knapsack function with nulls.
		 */
		@Test(expected=NullPointerException.class)
		public void testKnapsack7(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, (Integer)null));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsack(items, 15);
		}

		/**
		 * Test of knapsack function with negative weights.
		 */
		@Test(expected=IllegalArgumentException.class)
		public void testKnapsack8(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsack(items, -1);
		}

		/**
		 * Test of knapsack function with negative weights.
		 */
		@Test(expected=IllegalArgumentException.class)
		public void testKnapsack9(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, -12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsack(items, 15);
		}

		/**
		 * Test of knapsack function with negative profits.
		 */
		@Test
		public void testKnapsack10(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(new Tuple2<>(-1.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			assertTrue(Knapsack.knapsack(items, 15) == 9.0);
		}

		/**
		 * Test of knapsack function with all negative profits.
		 */
		@Test
		public void testKnapsack11(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(-4.0, 12));
			items.add(new Tuple2<>(-3.0, 1));
			items.add(new Tuple2<>(-1.0, 4));
			items.add(new Tuple2<>(-2.0, 2));
			items.add(new Tuple2<>(-1.0, 1));

			assertTrue(Knapsack.knapsack(items, 15) == 0.0);
		}

		/**
		 * Test of knapsackCount function.
		 */
		@Test
		public void testKnapsackCount1(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Tuple2<Double, List<Boolean>> result=Knapsack.knapsackCount(items, 15);
			assertTrue(result.getFirst().equals(16.0));
			assertTrue(Objects.equals(result.getSecond().toArray(new Boolean[]{}), new Boolean[]{false, true, true, true, true}));
		}

		/**
		 * Test of knapsackCount function with empty items.
		 */
		@Test
		public void testKnapsackCount2(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();

			Tuple2<Double, List<Boolean>> result=Knapsack.knapsackCount(items, 15);
			assertTrue(result.getFirst().equals(0.0));
			assertTrue(result.getSecond().isEmpty());
		}

		/**
		 * Test of knapsackCount function with weight zero.
		 */
		@Test
		public void testKnapsackCount3(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Tuple2<Double, List<Boolean>> result=Knapsack.knapsackCount(items, 0);
			assertTrue(result.getFirst().equals(0.0));
			assertTrue(Objects.equals(result.getSecond().toArray(new Boolean[]{}), new Boolean[]{false, false, false, false, false}));
		}

		/**
		 * Test of knapsackCount function with empty items and zero weight.
		 */
		@Test
		public void testKnapsackCount4(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();

			Tuple2<Double, List<Boolean>> result=Knapsack.knapsackCount(items, 0);
			assertTrue(result.getFirst() == 0.0);
			assertTrue(result.getSecond().isEmpty());
		}

		/**
		 * Test of knapsackCount function with nulls.
		 */
		@Test(expected=NullPointerException.class)
		public void testKnapsackCount5(){
			Knapsack.knapsackCount(null, 15);
		}

		/**
		 * Test of knapsackCount function with nulls.
		 */
		@Test(expected=NullPointerException.class)
		public void testKnapsackCount6(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(null);
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsackCount(items, 15);
		}

		/**
		 * Test of knapsackCount function with nulls.
		 */
		@Test(expected=NullPointerException.class)
		public void testKnapsackCount7(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, (Integer)null));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsackCount(items, 15);
		}

		/**
		 * Test of knapsackCount function with negative weights.
		 */
		@Test(expected=IllegalArgumentException.class)
		public void testKnapsackCount8(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsackCount(items, -1);
		}

		/**
		 * Test of knapsackCount function with negative weights.
		 */
		@Test(expected=IllegalArgumentException.class)
		public void testKnapsackCount9(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, -12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsackCount(items, 15);
		}

		/**
		 * Test of knapsackCount function with negative profits.
		 */
		@Test
		public void testKnapsackCount10(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(3.0, 1));
			items.add(new Tuple2<>(-1.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Tuple2<Double, List<Boolean>> result=Knapsack.knapsackCount(items, 15);
			assertTrue(result.getFirst().equals(9.0));
			assertTrue(Objects.equals(result.getSecond().toArray(new Boolean[]{}), new Boolean[]{true, true, false, true, false}));
		}

		/**
		 * Test of knapsackCount function with all negative profits.
		 */
		@Test
		public void testKnapsackCount11(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(-4.0, 12));
			items.add(new Tuple2<>(-3.0, 1));
			items.add(new Tuple2<>(-1.0, 4));
			items.add(new Tuple2<>(-2.0, 2));
			items.add(new Tuple2<>(-1.0, 1));

			Tuple2<Double, List<Boolean>> result=Knapsack.knapsackCount(items, 15);
			assertTrue(result.getFirst().equals(0.0));
			assertTrue(Objects.equals(result.getSecond().toArray(new Boolean[]{}), new Boolean[]{false, false, false, false, false}));
		}

		/**
		 * Test of knapsackUnbounded function.
		 */
		@Test
		public void testKnapsackUnbounded1(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(2.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			assertTrue(Knapsack.knapsackUnbounded(items, 15) == 36.0);
		}

		/**
		 * Test of knapsackUnbounded function with empty items.
		 */
		@Test
		public void testKnapsackUnbounded2(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			assertTrue(Knapsack.knapsackUnbounded(items, 15) == 0.0);
		}

		/**
		 * Test of knapsackUnbounded function with weight zero.
		 */
		@Test
		public void testKnapsackUnbounded3(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(2.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			assertTrue(Knapsack.knapsackUnbounded(items, 0) == 0.0);
		}

		/**
		 * Test of knapsackUnbounded function with empty items and zero
		 * weight.
		 */
		@Test
		public void testKnapsackUnbounded4(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();

			assertTrue(Knapsack.knapsackUnbounded(items, 0) == 0.0);
		}

		/**
		 * Test of knapsackUnbounded function with nulls.
		 */
		@Test(expected=NullPointerException.class)
		public void testKnapsackUnbounded5(){
			Knapsack.knapsackUnbounded(null, 15);
		}

		/**
		 * Test of knapsackUnbounded function with nulls.
		 */
		@Test(expected=NullPointerException.class)
		public void testKnapsackUnbounded6(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(2.0, 1));
			items.add(null);
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsackUnbounded(items, 15);
		}

		/**
		 * Test of knapsackUnbounded function with nulls.
		 */
		@Test(expected=NullPointerException.class)
		public void testKnapsackUnbounded7(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(2.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, (Integer)null));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsackUnbounded(items, 15);
		}

		/**
		 * Test of knapsackUnbounded function with negative weights.
		 */
		@Test(expected=IllegalArgumentException.class)
		public void testKnapsackUnbounded8(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(2.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsackUnbounded(items, -1);
		}

		/**
		 * Test of knapsackUnbounded function with negative weights.
		 */
		@Test(expected=IllegalArgumentException.class)
		public void testKnapsackUnbounded9(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, -12));
			items.add(new Tuple2<>(2.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsackUnbounded(items, 15);
		}

		/**
		 * Test of knapsackUnbounded function with negative profits.
		 */
		@Test
		public void testKnapsackUnbounded10(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(-2.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			assertTrue(Knapsack.knapsackUnbounded(items, 15) == 33.0);
		}

		/**
		 * Test of knapsackUnbounded function with all negative profits.
		 */
		@Test
		public void testKnapsackUnbounded11(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(-4.0, 12));
			items.add(new Tuple2<>(-2.0, 1));
			items.add(new Tuple2<>(-10.0, 4));
			items.add(new Tuple2<>(-2.0, 2));
			items.add(new Tuple2<>(-1.0, 1));

			assertTrue(Knapsack.knapsackUnbounded(items, 15) == 0.0);
		}

		/**
		 * Test of knapsackUnboundedCount function.
		 */
		@Test
		public void testKnapsackUnboundedCount1(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(2.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Tuple2<Double, List<Integer>> result=Knapsack.knapsackUnboundedCount(items, 15);
			assertTrue(result.getFirst().equals(36.0));
			assertTrue(Objects.equals(result.getSecond().toArray(new Integer[]{}), new Integer[]{0, 3, 3, 0, 0}));
		}

		/**
		 * Test of knapsackUnboundedCount function with empty items.
		 */
		@Test
		public void testKnapsackUnboundedCount2(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();

			Tuple2<Double, List<Integer>> result=Knapsack.knapsackUnboundedCount(items, 15);
			assertTrue(result.getFirst().equals(0.0));
			assertTrue(result.getSecond().isEmpty());
		}

		/**
		 * Test of knapsackUnboundedCount function with weight zero.
		 */
		@Test
		public void testKnapsackUnboundedCount3(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(2.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Tuple2<Double, List<Integer>> result=Knapsack.knapsackUnboundedCount(items, 0);
			assertTrue(result.getFirst().equals(0.0));
			assertTrue(Objects.equals(result.getSecond().toArray(new Integer[]{}), new Integer[]{0, 0, 0, 0, 0}));
		}

		/**
		 * Test of knapsackUnboundedCount function with empty items and zero
		 * weight.
		 */
		@Test
		public void testKnapsackUnboundedCount4(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();

			Tuple2<Double, List<Integer>> result=Knapsack.knapsackUnboundedCount(items, 0);
			assertTrue(result.getFirst().equals(0.0));
			assertTrue(result.getSecond().isEmpty());
		}

		/**
		 * Test of knapsackUnboundedCount function with nulls.
		 */
		@Test(expected=NullPointerException.class)
		public void testKnapsackUnboundedCount5(){
			Knapsack.knapsackUnboundedCount(null, 15);
		}

		/**
		 * Test of knapsackUnboundedCount function with nulls.
		 */
		@Test(expected=NullPointerException.class)
		public void testKnapsackUnboundedCount6(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(2.0, 1));
			items.add(null);
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsackUnboundedCount(items, 15);
		}

		/**
		 * Test of knapsackUnboundedCount function with nulls.
		 */
		@Test(expected=NullPointerException.class)
		public void testKnapsackUnboundedCount7(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(2.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, (Integer)null));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsackUnboundedCount(items, 15);
		}

		/**
		 * Test of knapsackUnboundedCount function with negative weights.
		 */
		@Test(expected=IllegalArgumentException.class)
		public void testKnapsackUnboundedCount8(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(2.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsackUnboundedCount(items, -1);
		}

		/**
		 * Test of knapsackUnboundedCount function with negative weights.
		 */
		@Test(expected=IllegalArgumentException.class)
		public void testKnapsackUnboundedCount9(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, -12));
			items.add(new Tuple2<>(2.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.0, 2));
			items.add(new Tuple2<>(1.0, 1));

			Knapsack.knapsackUnboundedCount(items, 15);
		}

		/**
		 * Test of knapsackUnboundedCount function with negative profits.
		 */
		@Test
		public void testKnapsackUnboundedCount10(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(4.0, 12));
			items.add(new Tuple2<>(-2.0, 1));
			items.add(new Tuple2<>(10.0, 4));
			items.add(new Tuple2<>(2.5, 2));
			items.add(new Tuple2<>(1.0, 1));

			Tuple2<Double, List<Integer>> result=Knapsack.knapsackUnboundedCount(items, 15);
			assertTrue(result.getFirst().equals(33.5));
			assertTrue(Objects.equals(result.getSecond().toArray(new Integer[]{}), new Integer[]{0, 0, 3, 1, 1}));
		}

		/**
		 * Test of knapsackUnboundedCount function with all negative
		 * profits.
		 */
		@Test
		public void testKnapsackUnboundedCount11(){

			ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
			items.add(new Tuple2<>(-4.0, 12));
			items.add(new Tuple2<>(-2.0, 1));
			items.add(new Tuple2<>(-10.0, 4));
			items.add(new Tuple2<>(-2.0, 2));
			items.add(new Tuple2<>(-1.0, 1));

			Tuple2<Double, List<Integer>> result=Knapsack.knapsackUnboundedCount(items, 15);
			assertTrue(result.getFirst().equals(0.0));
			assertTrue(Objects.equals(result.getSecond().toArray(new Integer[]{}), new Integer[]{0, 0, 0, 0, 0}));
		}
	}

	public static class LongSingleRunTests{

		@Test
		public void testKnapsack1() throws FileNotFoundException, IOException{

			try(BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream("resources/test/knapsack.in")))){
				try(BufferedReader out=new BufferedReader(new InputStreamReader(new FileInputStream("resources/test/knapsack.out")))){
					int num_items=Integer.parseInt(in.readLine());
					String[] values=in.readLine().split(" ");
					String[] weights=in.readLine().split(" ");
					int capacity=Integer.parseInt(in.readLine());
					ArrayList<Tuple2<Double, Integer>> items=new ArrayList();
					for(int i=0; i < num_items; i++){
						items.add(new Tuple2<>(Double.parseDouble(values[i]), Integer.parseInt(weights[i])));
					}
					double result=Knapsack.knapsack(items, capacity);
					result=Math.round(result * 1000) / 1000.0;
					double expected=Double.parseDouble(out.readLine());
					assertTrue(result == expected);
				}
			}
		}

		@Test
		public void testKnapsackCount1() throws FileNotFoundException, IOException{

			try(BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream("resources/test/knapsackCount.in")))){
				try(BufferedReader out=new BufferedReader(new InputStreamReader(new FileInputStream("resources/test/knapsackCount.out")))){
					int num_items=Integer.parseInt(in.readLine());
					String[] values=in.readLine().split(" ");
					String[] weights=in.readLine().split(" ");
					int capacity=Integer.parseInt(in.readLine());
					ArrayList<Tuple2<Double, Integer>> items=new ArrayList();
					for(int i=0; i < num_items; i++){
						items.add(new Tuple2<>(Double.parseDouble(values[i]), Integer.parseInt(weights[i])));
					}
					Tuple2<Double, List<Boolean>> result=Knapsack.knapsackCount(items, capacity);
					double expected_profit=Double.parseDouble(out.readLine());
					assertTrue(Math.round(result.getFirst() * 1000) / 1000.0 == expected_profit);
					String expected_items=out.readLine();
					assertTrue(result.getSecond().size()==expected_items.length());
					for(int i=0;i<expected_items.length();i++){
						assertTrue(expected_items.charAt(i)==(result.getSecond().get(i)?'1':'0'));
					}
				}
			}
		}

		@Test
		public void testKnapsackUnbounded1() throws FileNotFoundException, IOException{

			try(BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream("resources/test/knapsackUnbounded.in")))){
				try(BufferedReader out=new BufferedReader(new InputStreamReader(new FileInputStream("resources/test/knapsackUnbounded.out")))){
					int num_items=Integer.parseInt(in.readLine());
					String[] values=in.readLine().split(" ");
					String[] weights=in.readLine().split(" ");
					int capacity=Integer.parseInt(in.readLine());
					ArrayList<Tuple2<Double, Integer>> items=new ArrayList();
					for(int i=0; i < num_items; i++){
						items.add(new Tuple2<>(Double.parseDouble(values[i]), Integer.parseInt(weights[i])));
					}
					double result=Knapsack.knapsackUnbounded(items, capacity);
					result=Math.round(result * 1000) / 1000.0;
					double expected=Double.parseDouble(out.readLine());
					assertTrue(result == expected);
				}
			}
		}

		@Test
		public void testKnapsackUnboundedCount1() throws FileNotFoundException, IOException{

			try(BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream("resources/test/knapsackUnboundedCount.in")))){
				try(BufferedReader out=new BufferedReader(new InputStreamReader(new FileInputStream("resources/test/knapsackUnboundedCount.out")))){
					int num_items=Integer.parseInt(in.readLine());
					String[] values=in.readLine().split(" ");
					String[] weights=in.readLine().split(" ");
					int capacity=Integer.parseInt(in.readLine());
					ArrayList<Tuple2<Double, Integer>> items=new ArrayList();
					for(int i=0; i < num_items; i++){
						items.add(new Tuple2<>(Double.parseDouble(values[i]), Integer.parseInt(weights[i])));
					}
					Tuple2<Double, List<Integer>> result=Knapsack.knapsackUnboundedCount(items, capacity);
					double expected_profit=Double.parseDouble(out.readLine());
					assertTrue(Math.round(result.getFirst() * 1000) / 1000.0 == expected_profit);
					String[] expected_items=out.readLine().split(" ");
					assertTrue(result.getSecond().size()==expected_items.length);
					for(int i=0;i<expected_items.length;i++){
						assertTrue(Integer.parseInt(expected_items[i])==result.getSecond().get(i));
					}
				}
			}
		}
	}
	
	@RunWith(value=Parameterized.class)
	public static class MultipleRunTests{

		private static boolean interrupt;
		private static final long TIMEOUT=3000;

		private static Tuple2<Double, List<List<Boolean>>> bruteForce(List<? extends Tuple2<Double, Integer>> items, int max_weight){

			interrupt=false;

			boolean[] chosen=new boolean[items.size()];
			double max_achieved=-1;
			ArrayList<List<Boolean>> best=new ArrayList<>();

			do{
				if(interrupt){
					return null;
				}
				int weight_acc=0;
				double value_acc=0;
				for(int i=0; i < chosen.length; i++){
					if(chosen[i]){
						weight_acc+=items.get(i).getSecond();
						if(weight_acc > max_weight || items.get(i).getFirst() <= 0){
							value_acc=-2;
							break;
						}
						value_acc+=items.get(i).getFirst();
					}
				}

				if(value_acc > max_achieved){
					best.clear();
					max_achieved=value_acc;
				}
				if(value_acc == max_achieved){
					ArrayList<Boolean> add=new ArrayList<>();
					for(boolean b:chosen){
						add.add(b);
					}
					best.add(add);
				}

				int i;
				for(i=chosen.length - 1; i >= 0; i--){
					chosen[i]=!chosen[i];
					if(chosen[i]){
						break;
					}
				}
				if(i < 0){
					break;
				}
			}while(true);

			return new Tuple2<Double, List<List<Boolean>>>(max_achieved, best);
		}

		private static Tuple2<Double, List<List<Integer>>> bruteForceUnbounded(List<? extends Tuple2<Double, Integer>> items, int max_weight){

			interrupt=false;

			double max_achieved=-1;
			ArrayList<List<Integer>> best=new ArrayList<>();

			LinkedList<Tuple3<List<Integer>, Double, Integer>> candidates=new LinkedList<>();
			candidates.push(new Tuple3<>(Collections.nCopies(items.size(), 0), 0.0, 0));

			while(!candidates.isEmpty()){
				if(interrupt){
					return null;
				}
				Tuple3<List<Integer>, Double, Integer> candidate=candidates.pop();

				if(candidate.getSecond() > max_achieved){
					best.clear();
					max_achieved=candidate.getSecond();
				}
				if(candidate.getSecond() == max_achieved){
					best.add(candidate.getFirst());
				}

				for(int i=0; i < items.size(); i++){
					if(items.get(i).getFirst() > 0 && candidate.getThird() + items.get(i).getSecond() <= max_weight){
						ArrayList<Integer> next=new ArrayList<>(candidate.getFirst());
						next.set(i, next.get(i) + 1);
						candidates.push(new Tuple3<List<Integer>, Double, Integer>(next, candidate.getSecond() + items.get(i).getFirst(), candidate.getThird() + items.get(i).getSecond()));
					}
				}
			}

			return new Tuple2<Double, List<List<Integer>>>(max_achieved, best);
		}

		private static boolean checkKnapsack(List<? extends Tuple2<Double, Integer>> items, int max_weight){

			double result=Knapsack.knapsack(items, max_weight);
			Tuple2<Double, List<List<Boolean>>> bf=bruteForce(items, max_weight);

			return bf == null || result == bf.getFirst();
		}

		private static boolean checkKnapsackCount(List<? extends Tuple2<Double, Integer>> items, int max_weight){

			Tuple2<Double, List<Boolean>> result=Knapsack.knapsackCount(items, max_weight);
			Tuple2<Double, List<List<Boolean>>> bf=bruteForce(items, max_weight);

			return bf == null || bf.getFirst().equals(result.getFirst()) && bf.getSecond().contains(result.getSecond());
		}

		private static boolean checkKnapsackUnbounded(List<? extends Tuple2<Double, Integer>> items, int max_weight){

			double result=Knapsack.knapsackUnbounded(items, max_weight);
			Tuple2<Double, List<List<Integer>>> bf=bruteForceUnbounded(items, max_weight);

			return bf == null || result == bf.getFirst();
		}

		private static boolean checkKnapsackUnboundedCount(List<? extends Tuple2<Double, Integer>> items, int max_weight){

			Tuple2<Double, List<Integer>> result=Knapsack.knapsackUnboundedCount(items, max_weight);
			Tuple2<Double, List<List<Integer>>> bf=bruteForceUnbounded(items, max_weight);

			return bf == null || bf.getFirst().equals(result.getFirst()) && bf.getSecond().contains(result.getSecond());
		}
		private List<? extends Tuple2<Double, Integer>> items;
		private int max_weight;

		public MultipleRunTests(List<? extends Tuple2<Double, Integer>> items, int max_weight){
			this.items=items;
			this.max_weight=max_weight;
		}

		@Parameters
		public static List<Object[]> data(){
			Random rand=new Random();
			ArrayList<Object[]> ret=new ArrayList<>();

			for(int i=1; i <= 200; i++){
				ArrayList<Tuple2<Double, Integer>> items=new ArrayList<>();
				for(int j=0; j < 10; j++){
					items.add(new Tuple2<>(rand.nextInt() + rand.nextDouble() + 25, rand.nextInt(100) + 1));
				}
				ret.add(new Object[]{items, rand.nextInt(200) + 1});
			}

			return ret;
		}

		/**
		 * Test of knapsack function.
		 */
		@Test
		public void testKnapsack1(){

			new Thread(){
				@Override
				public void run(){
					try{
						Thread.sleep(TIMEOUT);
						interrupt=true;
					}catch(InterruptedException ex){
					}
				}
			}.start();
			assertTrue(checkKnapsack(items, max_weight));
		}

		/**
		 * Test of knapsackCount function.
		 */
		@Test
		public void testKnapsackCount1(){

			new Thread(){
				@Override
				public void run(){
					try{
						Thread.sleep(TIMEOUT);
						interrupt=true;
					}catch(InterruptedException ex){
					}
				}
			}.start();
			assertTrue(checkKnapsackCount(items, max_weight));
		}

		/**
		 * Test of knapsackUnbounded function.
		 */
		@Test
		public void testKnapsackUnbounded1() throws InterruptedException{

			new Thread(){
				@Override
				public void run(){
					try{
						Thread.sleep(TIMEOUT);
						interrupt=true;
					}catch(InterruptedException ex){
					}
				}
			}.start();
			assertTrue(checkKnapsackUnbounded(items, max_weight));
		}

		/**
		 * Test of knapsackUnboundedCount function.
		 */
		@Test
		public void testKnapsackUnboundedCount1() throws InterruptedException{

			new Thread(){
				@Override
				public void run(){
					try{
						Thread.sleep(TIMEOUT);
						interrupt=true;
					}catch(InterruptedException ex){
					}
				}
			}.start();
			assertTrue(checkKnapsackUnboundedCount(items, max_weight));
		}
	}
}