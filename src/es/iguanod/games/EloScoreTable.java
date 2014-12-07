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
import es.iguanod.collect.Counter;
import es.iguanod.collect.DoubleTreeCounter.DoubleTreeCounterBuilder;
import es.iguanod.collect.IntHashCounter;
import es.iguanod.collect.LinkedFixedCapacityQueue;
import es.iguanod.collect.SortedCounter;
import static es.iguanod.games.EloScoreTable.GameType.*;
import es.iguanod.util.Maybe;
import es.iguanod.util.tuples.Tuple2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class EloScoreTable<T> implements Iterable<Tuple2<T, Integer>>, Serializable{

	private static final long serialVersionUID=818005879329498519L;
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

	private int games=0;
	private double mean=INITIAL_SCORE;
	private final boolean use_k_factor;
	private final int positioning_games;
	//************
	private SortedCounter<T, Double> table;
	private Map<T, Stats> stats;
	private Map<GameType, Map<Integer, Counter<Integer, Integer>>> ties_total=new EnumMap<>(GameType.class);

	private EloScoreTable(boolean use_k_factor, int positioning_games){
		this.use_k_factor=use_k_factor;
		this.positioning_games=positioning_games;
		table=new DoubleTreeCounterBuilder<T>().reverse(true).build();
		stats=new HashMap<>();
		ties_total.put(SINGLE, new HashMap<Integer, Counter<Integer, Integer>>());
		ties_total.put(TEAM, new HashMap<Integer, Counter<Integer, Integer>>());
	}

	public EloScoreTable(int initial_positioning_games){
		this(true, initial_positioning_games);
	}

	public EloScoreTable(boolean fast_repositioning){
		this(fast_repositioning, DEFAULT_POSITIONING_GAMES);
	}

	public EloScoreTable(){
		this(false, 0);
	}

	public static enum GameType{

		SINGLE,
		TEAM,
		ALL;
	}

	private static enum StatType{

		GAMES,
		WINS,
		LOSSES,
		TIES;
	}

	private class Stats implements Serializable{

		private static final long serialVersionUID=469496794623962394L;
		//************
		private LinkedFixedCapacityQueue<Double> queue=new LinkedFixedCapacityQueue<>(RECORD_SIZE);
		private double k_factor=0;
		//************
		private Map<GameType, Map<StatType, Counter<Integer, Integer>>> stats=new EnumMap<>(GameType.class);
		private Map<GameType, Map<Integer, Counter<Integer, Integer>>> ties_count=new EnumMap<>(GameType.class);

		public Stats(){
			Map<StatType, Counter<Integer, Integer>> single=new EnumMap<>(StatType.class);
			Map<StatType, Counter<Integer, Integer>> team=new EnumMap<>(StatType.class);
			stats.put(SINGLE, single);
			stats.put(TEAM, team);
			for(StatType type:StatType.values()){
				single.put(type, new IntHashCounter<Integer>());
				team.put(type, new IntHashCounter<Integer>());
			}

			ties_count.put(SINGLE, new HashMap<Integer, Counter<Integer, Integer>>());
			ties_count.put(TEAM, new HashMap<Integer, Counter<Integer, Integer>>());
		}

		public void addGame(int num_players, boolean win, int num_winners, boolean single){

			GameType type=single ? SINGLE : TEAM;

			stats.get(type).get(StatType.GAMES).sum(num_players);
			stats.get(type).get(StatType.GAMES).sum(0);

			if(win){
				if(num_winners != num_players){
					stats.get(type).get(StatType.WINS).sum(num_players);
					stats.get(type).get(StatType.WINS).sum(0);
				}
				if(num_winners > 1){
					stats.get(type).get(StatType.TIES).sum(num_players);
					stats.get(type).get(StatType.TIES).sum(0);
				}
			}else{
				stats.get(type).get(StatType.LOSSES).sum(num_players);
				stats.get(type).get(StatType.LOSSES).sum(0);
			}

			Counter<Integer, Integer> ties=ties_count.get(type).get(num_players);
			if(ties == null){
				ties=new IntHashCounter<>();
				ties_count.get(type).put(num_players, ties);
			}
			ties.sum(num_winners);

			ties=ties_total.get(type).get(num_players);
			if(ties == null){
				ties=new IntHashCounter<>();
				ties_total.get(type).put(num_players, ties);
			}
			ties.sum(num_winners);
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

			if(stats.get(TEAM).get(StatType.GAMES).get(0) + stats.get(SINGLE).get(StatType.GAMES).get(0) > positioning_games){
				return Math.abs((k_factor / positioning_games) + Math.signum(score));
			}else{
				return INITIAL_K_FACTOR;
			}
		}
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
		s.addGame(num_players, actual_score > 0, num_winners, true);
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
		double team_change=(actual_score - expected_score) * BASE_CHANGE * ((4 * team.size()) / (4 * team.size() + 3.0));

		double scores_sum_players=0;
		for(T player:team){
			scores_sum_players+=Math.pow(TIMES_BETTER, table.get(player) / DIFFERENCE_BETTER);
		}
		for(T player:team){
			Stats s=stats.get(player);
			s.addGame(num_teams, actual_score > 0, num_winners, false);
			double k_factor=s.kFactor(num_teams, actual_score);
			double expected_in_team=1 - Math.pow(TIMES_BETTER, table.get(player) / DIFFERENCE_BETTER) / scores_sum_players;
			acc.put(player, expected_in_team * team_change * k_factor);
		}
	}

	public Set<T> players(){
		return table.keySet();
	}

	public boolean contains(T player){
		return stats.containsKey(player);
	}

	private int pvtStats(T player, GameType game_type, StatType stat_type, int num_players){
		Stats s=stats.get(player);
		if(s == null){
			return 0;
		}

		if(game_type == ALL){
			return pvtStats(player, SINGLE, stat_type, num_players) + pvtStats(player, TEAM, stat_type, num_players);
		}else{
			Integer ret=s.stats.get(game_type).get(stat_type).get(num_players);
			if(ret==null){
				return 0;
			}
			return ret;
		}
	}

	public int games(){
		return games;
	}

	public int games(T player, GameType type){
		return pvtStats(player, type, StatType.GAMES, 0);
	}

	public int wins(T player, GameType type){
		return pvtStats(player, type, StatType.WINS, 0);
	}

	public int losses(T player, GameType type){
		return pvtStats(player, type, StatType.LOSSES, 0);
	}

	public int ties(T player, GameType type){
		return pvtStats(player, type, StatType.TIES, 0);
	}

	public int games(T player, GameType type, int num_players){
		if(num_players < 2){
			throw new IllegalArgumentException("The number of players has to be equal or greater than 2");
		}
		return pvtStats(player, type, StatType.GAMES, num_players);
	}

	public int wins(T player, GameType type, int num_players){
		if(num_players < 2){
			throw new IllegalArgumentException("The number of players has to be equal or greater than 2");
		}
		return pvtStats(player, type, StatType.WINS, num_players);
	}

	public int losses(T player, GameType type, int num_players){
		if(num_players < 2){
			throw new IllegalArgumentException("The number of players has to be equal or greater than 2");
		}
		return pvtStats(player, type, StatType.LOSSES, num_players);
	}

	public int ties(T player, GameType type, int num_players){
		if(num_players < 2){
			throw new IllegalArgumentException("The number of players has to be equal or greater than 2");
		}
		return pvtStats(player, type, StatType.TIES, num_players);
	}

	protected int pvtTimesTied(Map<GameType, Map<Integer, Counter<Integer, Integer>>> ties_map, GameType type, int num_players, int num_winners){
		if(type == ALL){
			return timesTied(SINGLE, num_players, num_winners) + timesTied(TEAM, num_players, num_winners);
		}else{
			Counter<Integer, Integer> ties=ties_map.get(type).get(num_players);
			if(ties == null){
				return 0;
			}
			Integer ret=ties.get(num_winners);
			if(ret == null){
				return 0;
			}
			return ret;
		}
	}

	public int timesTied(T player, GameType type, int num_players, int num_winners){
		Stats s=stats.get(player);
		if(s == null){
			return 0;
		}

		return pvtTimesTied(s.ties_count, type, num_players, num_winners);
	}

	public int timesTied(GameType type, int num_players, int num_winners){

		return pvtTimesTied(ties_total, type, num_players, num_winners);
	}

	public int score(T player){
		Double x=table.get(player);
		return (int)(x == null ? mean : x);
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
