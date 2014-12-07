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
package es.iguanod.games;

import es.iguanod.collect.CollectionsIg;
import es.iguanod.collect.DoubleTreeCounter.DoubleTreeCounterBuilder;
import es.iguanod.collect.LinkedFixedCapacityQueue;
import es.iguanod.collect.SortedCounter;
import es.iguanod.util.Maybe;
import es.iguanod.util.tuples.Tuple2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class EloScoreTable<T> implements Iterable<Tuple2<T, Integer>>, Serializable{

	private static final long serialVersionUID=818005879329498515L;
	//************
	private static final int TIMES_BETTER=2;
	private static final int DIFFERENCE_BETTER=200;
	private static final int BASE_CHANGE=50;
	//************
	private static final double INITIAL_K_FACTOR=1.66;
	private static final int DEFAULT_POSITIONING_GAMES=8;
	private static final int RECORD_SIZE=16;
	//************
	public static final double INITIAL_SCORE=1000;
	//************
	private SortedCounter<T, Double> table;
	private Map<T, Stats> stats;
	private int games=0;
	private double mean=INITIAL_SCORE;
	private final boolean use_k_factor;
	private final int positioning_games;

	private class Stats implements Serializable{

		private static final long serialVersionUID=469496794623962390L;
		//************
		private LinkedFixedCapacityQueue<Double> queue=new LinkedFixedCapacityQueue<>(RECORD_SIZE);
		private double k_factor=0;
		//************
		private int games=0;
		private int wins=0;
		private int losses=0;
		private int ties=0;
		private List<List<Integer>> ties_table=new ArrayList<>();
		//************
		private int games_team=0;
		private int wins_team=0;
		private int losses_team=0;
		private int ties_team=0;
		private List<List<Integer>> ties_table_team=new ArrayList<>();
		//************
		private List<List<Integer>> ties_table_total=new ArrayList<>();

		public void addGame(int num_players, boolean win, int num_winners){

			if(ties_table.size() < num_players){
				for(int i=ties_table.size(); i <= num_players; i++){
					ArrayList<Integer> next=new ArrayList<>();
					ties_table.add(next);
					for(int j=0; j <= i; j++){
						next.add(0);
					}
				}
			}

			if(ties_table_total.size() < num_players){
				for(int i=ties_table_total.size(); i <= num_players; i++){
					ArrayList<Integer> next=new ArrayList<>();
					ties_table_total.add(next);
					for(int j=0; j <= i; j++){
						next.add(0);
					}
				}
			}

			games++;
			if(win){
				wins++;
			}else{
				losses++;
			}
			if(num_winners > 1){
				ties++;
				ties_table.get(num_players).set(num_winners, ties_table.get(num_players).get(num_winners) + 1);
				ties_table_total.get(num_players).set(num_winners, ties_table_total.get(num_players).get(num_winners) + 1);
			}
		}

		public void addGameTeam(int num_teams, boolean win, int num_winners){

			if(ties_table_team.size() < num_teams){
				for(int i=ties_table_team.size(); i <= num_teams; i++){
					ArrayList<Integer> next=new ArrayList<>();
					ties_table_team.add(next);
					for(int j=0; j <= i; j++){
						next.add(0);
					}
				}
			}

			if(ties_table_total.size() < num_teams){
				for(int i=ties_table_total.size(); i <= num_teams; i++){
					ArrayList<Integer> next=new ArrayList<>();
					ties_table_total.add(next);
					for(int j=0; j <= i; j++){
						next.add(0);
					}
				}
			}

			games_team++;
			if(win){
				wins_team++;
			}else{
				losses_team++;
			}
			if(num_winners > 1){
				ties_team++;
				ties_table_team.get(num_teams).set(num_winners, ties_table_team.get(num_teams).get(num_winners) + 1);
				ties_table_total.get(num_teams).set(num_winners, ties_table_total.get(num_teams).get(num_winners) + 1);
			}
		}

		public double kFactor(int num_players, double score){

			if(!use_k_factor){
				return 1;
			}

			double next;
			if(score >= 0){
				next=1 - 1.0 / (num_players * score);
			}else{
				next=-1.0 / num_players;
			}
			k_factor+=next;
			Maybe<Double> popped=queue.push(next);
			if(popped.isPresent()){
				k_factor-=popped.get();
			}

			if(wins + ties + losses > positioning_games){
				return Math.abs((k_factor / positioning_games) + Math.signum(score));
			}else{
				return INITIAL_K_FACTOR;
			}
		}
	}

	public EloScoreTable(boolean fast_repositioning, int initial_positioning_games){
		use_k_factor=fast_repositioning;
		positioning_games=initial_positioning_games;
		table=new DoubleTreeCounterBuilder<T>().reverse(true).build();
		stats=new HashMap<>();
	}

	public EloScoreTable(boolean fast_repositioning){
		this(fast_repositioning, DEFAULT_POSITIONING_GAMES);
	}

	public EloScoreTable(){
		this(false);
	}

	public void submitGame(Collection<? extends T> winner_players, Collection<? extends T> loser_players){

		if(winner_players.isEmpty()){
			throw new IllegalArgumentException("At least one winner player needed");
		}

		HashSet<T> set=new HashSet<>();

		set.addAll(winner_players);
		set.addAll(loser_players);

		if(set.size() != winner_players.size() + loser_players.size()){
			throw new IllegalArgumentException("Repeated players not allowed");
		}

		Map<T, Double> acc=new HashMap<>();
		double scores_sum=0;

		for(T player:winner_players){
			if(!table.containsKey(player)){
				table.put(player, mean);
				stats.put(player, new Stats());
			}
			scores_sum+=Math.pow(TIMES_BETTER, table.get(player) / DIFFERENCE_BETTER);
		}

		for(T player:loser_players){
			if(!table.containsKey(player)){
				table.put(player, mean);
				stats.put(player, new Stats());
			}
			scores_sum+=Math.pow(TIMES_BETTER, table.get(player) / DIFFERENCE_BETTER);
		}

		for(T player:winner_players){
			updatePlayer(player, scores_sum, 1.0 / winner_players.size(), winner_players.size() + loser_players.size(), acc, BASE_CHANGE, winner_players.size());
		}

		for(T player:loser_players){
			updatePlayer(player, scores_sum, 0, winner_players.size() + loser_players.size(), acc, BASE_CHANGE, winner_players.size());
		}

		for(Entry<T, Double> change:acc.entrySet()){
			table.sum(change.getKey(), change.getValue());
			mean+=change.getValue() / table.size();
		}

		games++;
	}

	private void updatePlayer(T player, double scores_sum, double actual_score, int num_players, Map<T, Double> acc, double change, int num_winners){

		Stats s=stats.get(player);
		s.addGame(num_players, actual_score > 0, num_winners);
		double k_factor=s.kFactor(num_players, actual_score);
		double expected_score=Math.pow(TIMES_BETTER, table.get(player) / DIFFERENCE_BETTER) / scores_sum;

		acc.put(player, (actual_score - expected_score) * change * k_factor);
	}

	public void submitGameTeams(Collection<? extends Collection<? extends T>> winner_teams, Collection<? extends Collection<? extends T>> loser_teams){

		if(winner_teams.isEmpty()){
			throw new IllegalArgumentException("At least one winner team needed");
		}

		HashSet<T> set=new HashSet<>();
		int size=0;
		int team_size=-1;

		for(Collection<? extends T> team:winner_teams){
			set.addAll(team);
			size+=team.size();
			if(team_size < 0){
				team_size=team.size();
			}else{
				if(team_size != team.size()){
					throw new IllegalArgumentException("All teams must have the same number of players");
				}
			}
		}

		if(size == 0){
			throw new IllegalArgumentException("At least one winner player needed");
		}

		for(Collection<? extends T> team:loser_teams){
			set.addAll(team);
			size+=team.size();
			if(team_size != team.size()){
				throw new IllegalArgumentException("All teams must have the same number of players");
			}
		}

		if(set.size() != size){
			throw new IllegalArgumentException("Repeated players not allowed");
		}

		double scores_sum=0;

		ArrayList<Double> winner_elos=new ArrayList<>();
		for(Collection<? extends T> team:winner_teams){
			double acc=0;
			for(T player:team){
				if(!table.containsKey(player)){
					table.put(player, mean);
					stats.put(player, new Stats());
				}
				acc+=table.get(player);
			}
			acc/=team.size();
			winner_elos.add(acc);
			scores_sum+=Math.pow(TIMES_BETTER, acc / DIFFERENCE_BETTER);
		}

		ArrayList<Double> loser_elos=new ArrayList<>();
		for(Collection<? extends T> team:loser_teams){
			double acc=0;
			for(T player:team){
				if(!table.containsKey(player)){
					table.put(player, mean);
					stats.put(player, new Stats());
				}
				acc+=table.get(player);
			}
			acc/=team.size();
			loser_elos.add(acc);
			scores_sum+=Math.pow(TIMES_BETTER, acc / DIFFERENCE_BETTER);
		}

		Map<T, Double> acc=new HashMap<>();

		int i=0;
		for(Collection<? extends T> team:winner_teams){
			updateTeam(team, winner_elos.get(i), scores_sum, 1.0 / winner_teams.size(), winner_teams.size() + loser_teams.size(), acc, winner_teams.size());
			i++;
		}

		i=0;
		for(Collection<? extends T> team:loser_teams){
			updateTeam(team, loser_elos.get(i), scores_sum, 0, winner_teams.size() + loser_teams.size(), acc, winner_teams.size());
			i++;
		}

		for(Entry<T, Double> change:acc.entrySet()){
			table.sum(change.getKey(), change.getValue());
			mean+=change.getValue() / table.size();
		}

		games++;
	}

	private void updateTeam(Collection<? extends T> team, double team_elo, double scores_sum, double actual_score, int num_teams, Map<T, Double> acc, int num_winners){

		double expected_score=Math.pow(TIMES_BETTER, team_elo / DIFFERENCE_BETTER) / scores_sum;
		double team_change=(actual_score - expected_score) * BASE_CHANGE * team.size();

		double scores_sum_players=0;
		for(T player:team){
			scores_sum_players+=Math.pow(TIMES_BETTER, table.get(player) / DIFFERENCE_BETTER);
		}
		for(T player:team){
			Stats s=stats.get(player);
			s.addGameTeam(num_teams, actual_score > 0, num_winners);
			double k_factor=s.kFactor(num_teams, actual_score);
			double expected_in_team=1 - Math.pow(TIMES_BETTER, table.get(player) / DIFFERENCE_BETTER) / scores_sum_players;
			acc.put(player, expected_in_team * team_change * k_factor);
		}
	}

	public int gamesPlayed(){
		return games;
	}

	public int games(T player){
		Stats s=stats.get(player);
		return s == null?0:s.games + s.games_team;
	}

	public int wins(T player){
		Stats s=stats.get(player);
		return s == null?0:s.wins + s.wins_team;
	}

	public int losses(T player){
		Stats s=stats.get(player);
		return s == null?0:s.losses + s.losses_team;
	}

	public int ties(T player){
		Stats s=stats.get(player);
		return s == null?0:s.ties + s.ties_team;
	}

	public List<List<Integer>> detailedTies(T player){
		Stats s=stats.get(player);
		return s == null?Collections.EMPTY_LIST:CollectionsIg.<List<Integer>>deepUnmodifiableList(s.ties_table_total);
	}

	public int gamesSingle(T player){
		Stats s=stats.get(player);
		return s == null?0:s.games;
	}

	public int winsSingle(T player){
		Stats s=stats.get(player);
		return s == null?0:s.wins;
	}

	public int lossesSingle(T player){
		Stats s=stats.get(player);
		return s == null?0:s.losses;
	}

	public int tiesSingle(T player){
		Stats s=stats.get(player);
		return s == null?0:s.ties;
	}

	public List<List<Integer>> detailedTiesSingle(T player){
		Stats s=stats.get(player);
		return s == null?Collections.EMPTY_LIST:CollectionsIg.<List<Integer>>deepUnmodifiableList(s.ties_table);
	}

	public int gamesTeam(T player){
		Stats s=stats.get(player);
		return s == null?0:s.games_team;
	}

	public int winsTeam(T player){
		Stats s=stats.get(player);
		return s == null?0:s.wins_team;
	}

	public int lossesTeam(T player){
		Stats s=stats.get(player);
		return s == null?0:s.losses_team;
	}

	public int tiesTeam(T player){
		Stats s=stats.get(player);
		return s == null?0:s.ties_team;
	}

	public List<List<Integer>> detailedTiesTeam(T player){
		Stats s=stats.get(player);
		return s == null?Collections.EMPTY_LIST:CollectionsIg.<List<Integer>>deepUnmodifiableList(s.ties_table_team);
	}

	public int score(T player){
		Double x=table.get(player);
		return (int)(x == null?mean:x);
	}

	@Override
	public Iterator<Tuple2<T, Integer>> iterator(){
		return new Iterator<Tuple2<T, Integer>>(){
			private Iterator<Entry<T, Double>> iter=table.entrySet().iterator();

			@Override
			public boolean hasNext(){
				return iter.hasNext();
			}

			@Override
			public Tuple2<T, Integer> next(){
				Entry<T, Double> next=iter.next();
				return new Tuple2<>(next.getKey(), next.getValue().intValue());
			}

			@Override
			public void remove(){
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public String toString(){
		if(table.isEmpty()){
			return "{}";
		}else{
			String str="{";
			for(Entry<T, Double> entry:table.entrySet()){
				str+=entry.getKey() + "=>" + entry.getValue().intValue() + ", ";
			}
			return str.substring(0, str.length() - 2) + "}";
		}
	}
}
