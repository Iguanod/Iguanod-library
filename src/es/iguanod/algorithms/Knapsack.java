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
package es.iguanod.algorithms;

import es.iguanod.util.tuples.Tuple2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class provides a dynamic pogramming implementation of the <a
 * href="http://en.wikipedia.org/wiki/Knapsack_problem">knapsack problem</a>.
 * The goal of this problem is, given a set of items, each with a mass and a
 * value, to determine the number of each item to include in a collection so
 * that the total weight is less than or equal to a given limit and the total
 * value is as large as possible.
 *
 * <p>
 * Implementation is provided for two variants of the problem: the bounded
 * variant, in which every item can be added as much once; and the unbounded
 * variant, in which each item can be used as many times as wanted.
 * Additionally, for both variants there are methods that return only the best
 * achievable profit or the number of each item needed to get said profit.</p>
 *
 * @see <a href="http://en.wikipedia.org/wiki/Knapsack_problem">Wikipedia
 * article</a> about the knapsack problem
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public final class Knapsack{

	/**
	 * Uninstantiable class.
	 */
	private Knapsack(){
	}

	/**
	 * Calculates the greatest common divisor of two ints.
	 */
	private static int gcd(int u, int v){

		int shift;
		for(shift=0; ((u | v) & 1) == 0; shift++){
			u>>=1;
			v>>=1;
		}

		while((u & 1) == 0){
			u>>=1;
		}

		do{
			while((v & 1) == 0){
				v>>=1;
			}
			if(u > v){
				int t=v;
				v=u;
				u=t;
			}
			v=v - u;
		}while(v != 0);

		return u << shift;
	}

	/**
	 * Given some items and the max weight, divides the weight of each item
	 * and the max weight by their gcd.
	 *
	 * @param items collection of tuples value-weight
	 * @param max_weight maximum weight
	 *
	 * @return tuple with the new list of items in the first place, and the
	 * new max weight in the second place
	 */
	private static Tuple2<List<Tuple2<Double, Integer>>, Integer> gcdItems(Collection<? extends Tuple2<Double, Integer>> items, int max_weight){

		int gcd=max_weight;

		for(Tuple2<Double, Integer> item:items){
			if(item.getSecond() == 0){
				continue;
			}
			gcd=gcd(gcd, item.getSecond());
			if(gcd == 1){
				break;
			}
		}

		if(gcd == 1){
			return new Tuple2<List<Tuple2<Double, Integer>>, Integer>(new ArrayList<>(items), max_weight);
		}else{
			ArrayList<Tuple2<Double, Integer>> ret=new ArrayList<>();
			for(Tuple2<Double, Integer> item:items){
				ret.add(new Tuple2<>(item.getFirst(), item.getSecond() / gcd));
			}
			return new Tuple2<List<Tuple2<Double, Integer>>, Integer>(ret, max_weight / gcd);
		}
	}

	/**
	 * Solves the bounded knapsack problem, returning only the optimized
	 * value.
	 *
	 * @param items {@code Collection} of tuples containing all the possible
	 * items. Each tuple of the collection is composed of the item's value in
	 * its first place, and the item's weight in its second place
	 * @param max_weight the maximum weight the chosen items can weight
	 * together
	 *
	 * @return the optimized value for the problem, i.e. the sum of the values
	 * of all the chosen items
	 *
	 * @throws IllegalArgumentException if the weight of any item or
	 * {@code max_weight} is negative
	 * @throws NullPointerException if {@code items} is {@code null}, or any
	 * {@code Tuple2} in {@code items} is {@code null} or any {@code Double}
	 * or {@code Integer} in any {@code Tuple2} is {@code null}
	 */
	public static double knapsack(Collection<? extends Tuple2<Double, Integer>> items, int max_weight){

		if(max_weight == 0){
			return 0;
		}else if(max_weight < 0){
			throw new IllegalArgumentException("Max weight must be non-negative");
		}
		for(Tuple2<Double, Integer> item:items){
			if(item.getSecond() < 0){
				throw new IllegalArgumentException("All weights must be non-negative");
			}
		}

		Tuple2<? extends List<? extends Tuple2<Double, Integer>>, Integer> gcd=gcdItems(items, max_weight);
		List<? extends Tuple2<Double, Integer>> it=gcd.getFirst();
		max_weight=gcd.getSecond();

		double[][] best=new double[it.size() + 1][max_weight + 1];

		for(int i=1; i < items.size() + 1; i++){
			for(int j=1; j <= max_weight; j++){
				if(j >= it.get(i - 1).getSecond()){
					best[i][j]=Math.max(best[i - 1][j], best[i - 1][j - it.get(i - 1).getSecond()] + it.get(i - 1).getFirst());
				}else{
					best[i][j]=best[i - 1][j];
				}
			}
		}

		return best[items.size()][max_weight];
	}

	/**
	 * Solves the bounded knapsack problem, returning both the optimized value
	 * and the count of each item needed to achieve that value.
	 * <p>
	 * Note that since it is the bounded version of the problem, each element
	 * can be only selected either one or zero times, so the count of the
	 * items is returned as a {@code List} of {@code Booleans}.</p>
	 * <p>
	 * <b>Warning</b>: the returned {@code List} of {@code Booleans} may be
	 * immutable.</p>
	 *
	 * @param items {@code List} of tuples containing all the possible items.
	 * Each tuple of the list is composed of the item's value in its first
	 * place, and the item's weight in its second place
	 * @param max_weight the maximum weight the chosen items can weight
	 * together
	 *
	 * @return a {@code Tuple2} with the optimized value for the problem (i.e.
	 * the sum of the values of all the chosen items) in its first place, and
	 * a {@code List} of {@code Booleans} in which the ith element is
	 * {@code true} if the ith element of the list of items is chosen, and
	 * false otherwise
	 *
	 * @throws IllegalArgumentException if the weight of any item or
	 * {@code max_weight} is negative
	 * @throws NullPointerException if {@code items} is {@code null}, or any
	 * {@code Tuple2} in {@code items} is {@code null} or any {@code Double}
	 * or {@code Integer} in any {@code Tuple2} is {@code null}
	 */
	public static Tuple2<Double, List<Boolean>> knapsackCount(List<? extends Tuple2<Double, Integer>> items, int max_weight){

		if(max_weight == 0){
			return new Tuple2<>(0.0, Collections.<Boolean>nCopies(items.size(), false));
		}else if(max_weight < 0){
			throw new IllegalArgumentException("Max weight must be non-negative");
		}
		for(Tuple2<Double, Integer> item:items){
			if(item.getSecond() < 0){
				throw new IllegalArgumentException("All weights must be non-negative");
			}
		}

		Tuple2<List<Tuple2<Double, Integer>>, Integer> gcd=gcdItems(items, max_weight);
		items=gcd.getFirst();
		max_weight=gcd.getSecond();

		double[][] best=new double[items.size() + 1][max_weight + 1];
		boolean[][][] track=new boolean[items.size() + 1][max_weight + 1][items.size()];

		for(int i=1; i < items.size() + 1; i++){
			for(int j=1; j <= max_weight; j++){
				if(j >= items.get(i - 1).getSecond()){
					double add=best[i - 1][j - items.get(i - 1).getSecond()] + items.get(i - 1).getFirst();
					if(best[i - 1][j] >= add){
						best[i][j]=best[i - 1][j];
						System.arraycopy(track[i - 1][j], 0, track[i][j], 0, items.size());
					}else{
						best[i][j]=add;
						System.arraycopy(track[i - 1][j - items.get(i - 1).getSecond()], 0, track[i][j], 0, items.size());
						track[i][j][i - 1]=true;
					}
				}else{
					best[i][j]=best[i - 1][j];
					System.arraycopy(track[i - 1][j], 0, track[i][j], 0, items.size());
				}
			}
		}

		// Since Arrays.asList can't be used with primitives, creating an ArrayList is less expensive than operating with Boolean in the whole function
		ArrayList<Boolean> list=new ArrayList<>();
		for(boolean b:track[items.size()][max_weight]){
			list.add(b);
		}
		return new Tuple2<Double, List<Boolean>>(best[items.size()][max_weight], list);
	}

	/**
	 * Solves the unbounded knapsack problem, returning only the optimized
	 * value.
	 *
	 * @param items {@code Collection} of tuples containing all the possible
	 * items. Each tuple of the collection is composed of the item's value in
	 * its first place, and the item's weight in its second place
	 * @param max_weight the maximum weight the chosen items can weight
	 * together
	 *
	 * @return the optimized value for the problem, i.e. the sum of the values
	 * of all the chosen items
	 *
	 * @throws IllegalArgumentException if the weight of any item is
	 * non-possitive or {@code max_weight} is negative
	 * @throws NullPointerException if {@code items} is {@code null}, or any
	 * {@code Tuple2} in {@code items} is {@code null} or any {@code Double}
	 * or {@code Integer} in any {@code Tuple2} is {@code null}
	 */
	public static double knapsackUnbounded(Collection<? extends Tuple2<Double, Integer>> items, int max_weight){

		if(max_weight == 0){
			return 0;
		}else if(max_weight < 0){
			throw new IllegalArgumentException("Max weight must be non-negative");
		}
		for(Tuple2<Double, Integer> item:items){
			if(item.getSecond() <= 0){
				throw new IllegalArgumentException("All weights must be possitive");
			}
		}

		Tuple2<? extends List<? extends Tuple2<Double, Integer>>, Integer> gcd=gcdItems(items, max_weight);
		items=gcd.getFirst();
		max_weight=gcd.getSecond();

		double[] best=new double[max_weight + 1];

		for(int i=1; i < max_weight + 1; i++){
			best[i]=best[i - 1];
			for(Tuple2<Double, Integer> item:items){
				if(i >= item.getSecond()){
					best[i]=Math.max(best[i], best[i - item.getSecond()] + item.getFirst());
				}
			}
		}

		return best[max_weight];
	}

	/**
	 * Solves the bounded knapsack problem, returning both the optimized value
	 * and the count of each item needed to achieve that value.
	 *
	 * <p>
	 * <b>Warning</b>: the returned {@code List} of {@code Integers} may be
	 * immutable</p>
	 *
	 * @param items {@code List} of tuples containing all the possible items.
	 * Each tuple of the list is composed of the item's value in its first
	 * place, and the item's weight in its second place
	 * @param max_weight the maximum weight the chosen items can weight
	 * together
	 *
	 * @return a {@code Tuple2} with the optimized value for the problem (i.e.
	 * the sum of the values of all the chosen items) in its first place, and
	 * a {@code List} of {@code Integers} in which the ith element is the
	 * number of times the ith element of the list of items is chosen
	 *
	 * @throws IllegalArgumentException if the weight of any item is
	 * non-possitive or {@code max_weight} is negative
	 * @throws NullPointerException if {@code items} is {@code null}, or any
	 * {@code Tuple2} in {@code items} is {@code null} or any {@code Double}
	 * or {@code Integer} in any {@code Tuple2} is {@code null}
	 */
	public static Tuple2<Double, List<Integer>> knapsackUnboundedCount(List<? extends Tuple2<Double, Integer>> items, int max_weight) throws IllegalArgumentException{

		if(max_weight == 0){
			return new Tuple2<>(0.0, Collections.<Integer>nCopies(items.size(), 0));
		}else if(max_weight < 0){
			throw new IllegalArgumentException("Max weight must be non-negative");
		}
		for(Tuple2<Double, Integer> item:items){
			if(item.getSecond() <= 0){
				throw new IllegalArgumentException("All weights must be possitive");
			}
		}

		Tuple2<? extends List<? extends Tuple2<Double, Integer>>, Integer> gcd=gcdItems(items, max_weight);
		items=gcd.getFirst();
		max_weight=gcd.getSecond();

		double[] best=new double[max_weight + 1];
		int[][] track=new int[max_weight + 1][items.size()];

		for(int i=1; i < max_weight + 1; i++){
			best[i]=best[i - 1];
			int max=-1;
			for(int j=0; j < items.size(); j++){
				if(i >= items.get(j).getSecond()){
					double next=best[i - items.get(j).getSecond()] + items.get(j).getFirst();
					if(next > best[i]){
						best[i]=next;
						max=j;
					}
				}
			}
			if(max == -1){
				System.arraycopy(track[i - 1], 0, track[i], 0, items.size());
			}else{
				System.arraycopy(track[i - items.get(max).getSecond()], 0, track[i], 0, items.size());
				track[i][max]++;
			}
		}

		// Since Arrays.asList can't be used with primitives, creating an ArrayList is less expensive than operating with Integer in the whole function
		ArrayList<Integer> list=new ArrayList<>();
		for(int i:track[max_weight]){
			list.add(i);
		}
		return new Tuple2<Double, List<Integer>>(best[max_weight], list);
	}
}
