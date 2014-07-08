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

import es.iguanod.util.tuples.Tuple2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO: javadoc
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.8.1.a
 * @version 1.0.1.1.b
 */
public final class CYK{

	private CYK(){
	}

	public static <NT, T> Set<NT> CYK(Collection<? extends Tuple2<NT, ? extends Tuple2<NT, NT>>> non_terminals, Collection<? extends Tuple2<NT, T>> terminals, Collection<NT> starts, List<T> sentence){

		if(sentence.isEmpty() || starts.isEmpty() || terminals.isEmpty() || (non_terminals.isEmpty() && sentence.size() > 1)){
			return new HashSet<>();
		}

		HashSet<NT> symbols_set=new HashSet<>();

		for(Tuple2<NT, ? extends Tuple2<NT, NT>> rule:non_terminals){
			symbols_set.add(rule.getFirst());
			symbols_set.add(rule.getSecond().getFirst());
			symbols_set.add(rule.getSecond().getSecond());
		}

		for(Tuple2<NT, T> rule:terminals){
			symbols_set.add(rule.getFirst());
		}

		HashMap<NT, Integer> symbols=new HashMap<>();
		int count=0;
		for(NT sym:symbols_set){
			symbols.put(sym, count);
			count++;
		}

		boolean[][][] recognize=new boolean[sentence.size()][sentence.size()][symbols.size()];

		boolean flag=false;
		for(int i=0; i < sentence.size(); i++){
			for(Tuple2<NT, T> terminal:terminals){
				if(terminal.getSecond().equals(sentence.get(i))){
					recognize[i][0][symbols.get(terminal.getFirst())]=true;
					flag=true;
				}
			}
		}

		if(!flag){
			return new HashSet<>();
		}

		for(int i=2; i <= sentence.size(); i++){
			for(int j=0; j < sentence.size() - i + 1; j++){
				for(int k=1; k < i; k++){
					for(Tuple2<NT, ? extends Tuple2<NT, NT>> nt:non_terminals){
						if(recognize[j][k - 1][symbols.get(nt.getSecond().getFirst())] && recognize[j + k][i - k - 1][symbols.get(nt.getSecond().getSecond())]){
							recognize[j][i - 1][symbols.get(nt.getFirst())]=true;
						}
					}
				}
			}
		}

		HashSet<NT> found=new HashSet<>();
		for(NT start:starts){
			if(recognize[0][sentence.size() - 1][symbols.get(start)]){
				found.add(start);
			}
		}

		return found;
	}

	/**
	 * If a symbol can be derived in more than one way, the one resulting in
	 * the highest probability is used.
	 *
	 * @param <NT>
	 * @param <T>
	 * @param non_terminals
	 * @param terminals
	 * @param starts
	 * @param sentence
	 *
	 * @return
	 *
	 * @throws IllegalArgumentException
	 */
	public static <NT, T> Set<Tuple2<NT, Double>> CYKprobGrammar(Collection<? extends Tuple2<NT, Collection<? extends Tuple2<? extends Tuple2<NT, NT>, Double>>>> non_terminals, Collection<? extends Tuple2<NT, Collection<? extends Tuple2<T, Double>>>> terminals, Collection<NT> starts, List<T> sentence) throws IllegalArgumentException{

		for(Tuple2<NT, Collection<? extends Tuple2<? extends Tuple2<NT, NT>, Double>>> rule:non_terminals){
			if(rule.getSecond().isEmpty()){
				throw new IllegalArgumentException("All rules must have derivations");
			}
			for(Tuple2<? extends Tuple2<NT, NT>, Double> derivation:rule.getSecond()){
				if(derivation.getSecond() < 0 || derivation.getSecond() > 1){
					throw new IllegalArgumentException("Probabilities must be between 0 and 1");
				}
			}
		}

		for(Tuple2<NT, Collection<? extends Tuple2<T, Double>>> rule:terminals){
			if(rule.getSecond().isEmpty()){
				throw new IllegalArgumentException("All rules must have derivations");
			}
			for(Tuple2<T, Double> derivation:rule.getSecond()){
				if(derivation.getSecond() < 0 || derivation.getSecond() > 1){
					throw new IllegalArgumentException("Probabilities must be between 0 and 1");
				}
			}
		}

		if(sentence.isEmpty() || starts.isEmpty() || terminals.isEmpty() || (non_terminals.isEmpty() && sentence.size() > 1)){
			return new HashSet<>();
		}

		HashSet<NT> symbols_set=new HashSet<>();

		for(Tuple2<NT, Collection<? extends Tuple2<T, Double>>> rule:terminals){
			boolean flag=false;
			for(Tuple2<T, Double> derivation:rule.getSecond()){
				if(derivation.getSecond() != 0){
					flag=true;
					break;
				}
			}
			if(flag){
				symbols_set.add(rule.getFirst());
			}
		}

		int size=symbols_set.size();

		if(size == 0){
			return new HashSet<>();
		}

		for(Tuple2<NT, Collection<? extends Tuple2<? extends Tuple2<NT, NT>, Double>>> rule:non_terminals){
			boolean flag=false;
			for(Tuple2<? extends Tuple2<NT, NT>, Double> derivation:rule.getSecond()){
				if(derivation.getSecond() != 0){
					flag=true;
					symbols_set.add(derivation.getFirst().getFirst());
					symbols_set.add(derivation.getFirst().getSecond());
				}
			}
			if(flag){
				symbols_set.add(rule.getFirst());
			}
		}

		if(size == symbols_set.size() && sentence.size() > 1){
			return new HashSet<>();
		}

		HashMap<NT, Integer> symbols=new HashMap<>();
		int count=0;
		for(NT sym:symbols_set){
			symbols.put(sym, count);
			count++;
		}

		double[][][] recognize=new double[sentence.size()][sentence.size()][symbols.size()];

		boolean flag=false;
		for(int i=0; i < sentence.size(); i++){
			for(int j=0; j < sentence.size(); j++){
				for(int k=0; k < symbols.size(); k++){
					recognize[i][j][k]=1;
					flag=true;
				}
			}
		}

		if(!flag){
			return new HashSet<>();
		}

		for(int i=0; i < sentence.size(); i++){
			for(Tuple2<NT, Collection<? extends Tuple2<T, Double>>> terminal:terminals){
				for(Tuple2<T, Double> derivation:terminal.getSecond()){
					if(derivation.getFirst().equals(sentence.get(i))){
						if(recognize[i][0][symbols.get(terminal.getFirst())] > 0){
							recognize[i][0][symbols.get(terminal.getFirst())]=Math.log(derivation.getSecond());
						}else{
							recognize[i][0][symbols.get(terminal.getFirst())]=Math.max(Math.log(derivation.getSecond()), recognize[i][0][symbols.get(terminal.getFirst())]);
						}
					}
				}
			}
		}

		for(int i=2; i <= sentence.size(); i++){
			for(int j=0; j < sentence.size() - i + 1; j++){
				for(int k=1; k < i; k++){
					for(Tuple2<NT, Collection<? extends Tuple2<? extends Tuple2<NT, NT>, Double>>> nt:non_terminals){
						for(Tuple2<? extends Tuple2<NT, NT>, Double> derivation:nt.getSecond()){
							if(recognize[j][k - 1][symbols.get(derivation.getFirst().getFirst())] <= 0 && recognize[j + k][i - k - 1][symbols.get(derivation.getFirst().getSecond())] <= 0){
								if(recognize[j][i - 1][symbols.get(nt.getFirst())] > 0){
									recognize[j][i - 1][symbols.get(nt.getFirst())]=recognize[j][k - 1][symbols.get(derivation.getFirst().getFirst())] + recognize[j + k][i - k - 1][symbols.get(derivation.getFirst().getSecond())] + Math.log(derivation.getSecond());
								}else{
									recognize[j][i - 1][symbols.get(nt.getFirst())]=Math.max(recognize[j][i - 1][symbols.get(nt.getFirst())], recognize[j][k - 1][symbols.get(derivation.getFirst().getFirst())] + recognize[j + k][i - k - 1][symbols.get(derivation.getFirst().getSecond())] + Math.log(derivation.getSecond()));
								}
							}
						}
					}
				}
			}
		}

		ArrayList<Tuple2<Double, NT>> found=new ArrayList<>();

		for(NT start:starts){
			if(recognize[0][sentence.size() - 1][symbols.get(start)] <= 0){
				found.add(new Tuple2<>(Math.exp(recognize[0][sentence.size() - 1][symbols.get(start)]), start));
			}
		}

		if(found.isEmpty()){
			return new HashSet<>();
		}else{

			Collections.sort(found, Collections.reverseOrder());

			Set<Tuple2<NT, Double>> found2=new HashSet<>();

			for(Tuple2<Double, NT> pair:found){
				found2.add(new Tuple2<>(pair.getSecond(), pair.getFirst()));
			}

			return found2;
		}
	}

	public static <NT, T> Set<Tuple2<NT, Double>> CYKprobInput(Collection<? extends Tuple2<NT, ? extends Tuple2<NT, NT>>> non_terminals, Collection<? extends Tuple2<NT, T>> terminals, Collection<NT> starts, List<Collection<? extends Tuple2<T, Double>>> sentence){

		for(Collection<? extends Tuple2<T, Double>> word:sentence){
			for(Tuple2<T, Double> pair:word){
				if(pair.getSecond() < 0 || pair.getSecond() > 1){
					throw new IllegalArgumentException("Probabilities must be between 0 and 1");
				}
			}
		}

		if(sentence.isEmpty() || starts.isEmpty() || terminals.isEmpty() || (non_terminals.isEmpty() && sentence.size() > 1)){
			return new HashSet<>();
		}

		HashSet<NT> symbols_set=new HashSet<>();

		for(Tuple2<NT, ? extends Tuple2<NT, NT>> rule:non_terminals){
			symbols_set.add(rule.getFirst());
			symbols_set.add(rule.getSecond().getFirst());
			symbols_set.add(rule.getSecond().getSecond());
		}

		for(Tuple2<NT, T> rule:terminals){
			symbols_set.add(rule.getFirst());
		}

		HashMap<NT, Integer> symbols=new HashMap<>();
		int count=0;
		for(NT sym:symbols_set){
			symbols.put(sym, count);
			count++;
		}

		double[][][] recognize=new double[sentence.size()][sentence.size()][symbols.size()];

		for(int i=0; i < sentence.size(); i++){
			for(int j=0; j < sentence.size(); j++){
				for(int k=0; k < symbols.size(); k++){
					recognize[i][j][k]=1;
				}
			}
		}

		boolean flag=false;
		for(int i=0; i < sentence.size(); i++){
			for(Tuple2<NT, T> terminal:terminals){
				for(Tuple2<T, Double> sent:sentence.get(i)){
					if(terminal.getSecond().equals(sent.getFirst())){
						if(recognize[i][0][symbols.get(terminal.getFirst())] > 0){
							recognize[i][0][symbols.get(terminal.getFirst())]=Math.log(sent.getSecond());
						}else{
							recognize[i][0][symbols.get(terminal.getFirst())]=Math.max(Math.log(sent.getSecond()), recognize[i][0][symbols.get(terminal.getFirst())]);
						}
						flag=true;
					}
				}
			}
		}

		if(!flag){
			return new HashSet<>();
		}

		for(int i=2; i <= sentence.size(); i++){
			for(int j=0; j < sentence.size() - i + 1; j++){
				for(int k=1; k < i; k++){
					for(Tuple2<NT, ? extends Tuple2<NT, NT>> nt:non_terminals){
						if(recognize[j][k - 1][symbols.get(nt.getSecond().getFirst())] <= 0 && recognize[j + k][i - k - 1][symbols.get(nt.getSecond().getSecond())] <= 0){
							if(recognize[j][i - 1][symbols.get(nt.getFirst())] > 0){
								recognize[j][i - 1][symbols.get(nt.getFirst())]=recognize[j][k - 1][symbols.get(nt.getSecond().getFirst())] + recognize[j + k][i - k - 1][symbols.get(nt.getSecond().getSecond())];
							}else{
								recognize[j][i - 1][symbols.get(nt.getFirst())]=Math.max(recognize[j][k - 1][symbols.get(nt.getSecond().getFirst())] + recognize[j + k][i - k - 1][symbols.get(nt.getSecond().getSecond())], recognize[j][i - 1][symbols.get(nt.getFirst())]);
							}
						}
					}
				}
			}
		}

		ArrayList<Tuple2<Double, NT>> found=new ArrayList<>();

		for(NT start:starts){
			if(recognize[0][sentence.size() - 1][symbols.get(start)] <= 0){
				found.add(new Tuple2<>(Math.exp(recognize[0][sentence.size() - 1][symbols.get(start)]), start));
			}
		}

		if(found.isEmpty()){
			return new HashSet<>();
		}else{

			Collections.sort(found);

			Set<Tuple2<NT, Double>> found2=new HashSet<>();

			for(Tuple2<Double, NT> pair:found){
				found2.add(new Tuple2<>(pair.getSecond(), pair.getFirst()));
			}

			return found2;
		}
	}
}
