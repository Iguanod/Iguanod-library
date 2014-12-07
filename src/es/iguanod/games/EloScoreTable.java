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

	private static final long serialVersionUID=818005879329498518L;
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

	public static enum GameType{

		SINGLE,
		TEAM,
		ALL;
	}

	private class Stats implements Serializable{

		private static final long serialVersionUID=469496794623962393L;
		//************
		private LinkedFixedCapacityQueue<Double> queue=new LinkedFixedCapacityQueue<>(RECORD_SIZE);
		private double k_factor=0;
		//************
		private Counter<Integer, Integer> games=new IntHashCounter<>();
		private Counter<Integer, Integer> wins=new IntHashCounter<>();
		private Counter<Integer, Integer> losses=new IntHashCounter<>();
		private Counter<Integer, Integer> ties=new IntHashCounter<>();
		private List<List<Integer>> winners_count=new ArrayList<>();
		private List<Integer> players_count=new ArrayList<>();
		//************
		private Counter<Integer, Integer> games_team=new IntHashCounter<>();
		private Counter<Integer, Integer> wins_team=new IntHashCounter<>();
		private Counter<Integer, Integer> losses_team=new IntHashCounter<>();
		private Counter<Integer, Integer> ties_team=new IntHashCounter<>();
		private List<List<Integer>> winners_count_team=new ArrayList<>();
		private List<Integer> players_count_team=new ArrayList<>();
		//************
		private List<List<Integer>> winners_count_total=new ArrayList<>();
		private List<Integer> players_count_total=new ArrayList<>();

		private void resizeListList(List<List<Integer>> list, int threshold){
			for(int i=list.size(); i <= threshold; i++){
				ArrayList<Integer> next=new ArrayList<>();
				list.add(next);
				for(int j=0; j <= i; j++){
					next.add(0);
				}
			}
		}

		private void resizeList(List<Integer> list, int threshold){
			for(int i=list.size(); i <= threshold; i++){
				list.add(0);
			}
		}

		public void addGame(int num_players, boolean win, int num_winners, boolean single){

			List<List<Integer>> wc;
			List<Integer> pc;
			Counter<Integer, Integer> g;
			Counter<Integer, Integer> w;
			Counter<Integer, Integer> t;
			Counter<Integer, Integer> l;

			if(single){
				wc=winners_count;
				pc=players_count;
				g=games;
				w=wins;
				t=ties;
				l=losses;
			}else{
				wc=winners_count_team;
				pc=players_count_team;
				g=games_team;
				w=wins_team;
				t=ties_team;
				l=losses_team;
			}

			resizeListList(wc, num_players);
			resizeListList(winners_count_total, num_players);
			resizeList(pc, num_players);
			resizeList(players_count_total, num_players);

			g.sum(num_players);
			g.sum(0);
			if(win){
				if(num_winners != num_players){
					w.sum(num_players);
					w.sum(0);
				}
				if(num_winners > 1){
					t.sum(num_players);
					t.sum(0);
				}
			}else{
				l.sum(num_players);
				l.sum(0);
			}

			wc.get(num_players).set(num_winners, wc.get(num_players).get(num_winners) + 1);
			winners_count_total.get(num_players).set(num_winners, winners_count_total.get(num_players).get(num_winners) + 1);
			pc.set(num_players, pc.get(num_players) + 1);
			players_count_total.set(num_players, players_count_total.get(num_players) + 1);
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

			if(games.get(0) > positioning_games){
				return Math.abs((k_factor / positioning_games) + Math.signum(score));
			}else{
				return INITIAL_K_FACTOR;
			}
		}
	}
	
	private EloScoreTable(boolean use_k_factor, int positioning_games){
		this.use_k_factor=use_k_factor;
		this.positioning_games=positioning_games;
		table=new DoubleTreeCounterBuilder<T>().reverse(true).build();
		stats=new HashMap<>();
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

	public boolean contains(Object obj){
		return stats.containsKey(obj);
	}

	public int gamesPlayed(){
		return games;
	}

	private int pvtGames(T player, GameType type, int num_players){
		Stats s=stats.get(player);
		if(s == null){
			return 0;
		}

		Integer ret;
		Integer ret2=0;
		if(type == SINGLE){
			ret=s.games.get(num_players);
		}else if(type == TEAM){
			ret=s.games_team.get(num_players);
		}else{
			ret=s.games.get(num_players);
			ret2=s.games_team.get(num_players);
		}
		return (ret != null?ret:0) + (ret2 != null?ret2:0);
	}

	private int pvtWins(T player, GameType type, int num_players){
		Stats s=stats.get(player);
		if(s == null){
			return 0;
		}

		Integer ret;
		Integer ret2=0;
		if(type == SINGLE){
			ret=s.wins.get(num_players);
		}else if(type == TEAM){
			ret=s.wins_team.get(num_players);
		}else{
			ret=s.wins.get(num_players);
			ret2=s.wins_team.get(num_players);
		}
		return (ret != null?ret:0) + (ret2 != null?ret2:0);
	}

	private int pvtLosses(T player, GameType type, int num_players){
		Stats s=stats.get(player);
		if(s == null){
			return 0;
		}

		Integer ret;
		Integer ret2=0;
		if(type == SINGLE){
			ret=s.losses.get(num_players);
		}else if(type == TEAM){
			ret=s.losses_team.get(num_players);
		}else{
			ret=s.losses.get(num_players);
			ret2=s.losses_team.get(num_players);
		}
		return (ret != null?ret:0) + (ret2 != null?ret2:0);
	}

	private int pvtTies(T player, GameType type, int num_players){
		Stats s=stats.get(player);
		if(s == null){
			return 0;
		}

		Integer ret;
		Integer ret2=0;
		if(type == SINGLE){
			ret=s.ties.get(num_players);
		}else if(type == TEAM){
			ret=s.ties_team.get(num_players);
		}else{
			ret=s.ties.get(num_players);
			ret2=s.ties_team.get(num_players);
		}
		return (ret != null?ret:0) + (ret2 != null?ret2:0);
	}

	public int games(T player, GameType type){
		return pvtGames(player, type, 0);
	}

	public int wins(T player, GameType type){
		return pvtWins(player, type, 0);
	}

	public int losses(T player, GameType type){
		return pvtLosses(player, type, 0);
	}

	public int ties(T player, GameType type){
		return pvtTies(player, type, 0);
	}

	public int games(T player, GameType type, int num_players){
		if(num_players < 2){
			throw new IllegalArgumentException("The number of players has to be equal or greater than 2");
		}
		return pvtGames(player, type, num_players);
	}

	public int wins(T player, GameType type, int num_players){
		if(num_players < 2){
			throw new IllegalArgumentException("The number of players has to be equal or greater than 2");
		}
		return pvtWins(player, type, num_players);
	}

	public int losses(T player, GameType type, int num_players){
		if(num_players < 2){
			throw new IllegalArgumentException("The number of players has to be equal or greater than 2");
		}
		return pvtLosses(player, type, num_players);
	}

	public int ties(T player, GameType type, int num_players){
		if(num_players < 2){
			throw new IllegalArgumentException("The number of players has to be equal or greater than 2");
		}
		return pvtTies(player, type, num_players);
	}

	public List<List<Integer>> winnersCount(T player, GameType type){
		Stats s=stats.get(player);
		if(s == null){
			return Collections.EMPTY_LIST;
		}
		if(type == SINGLE){
			return CollectionsIg.<List<Integer>>deepUnmodifiableList(s.winners_count);
		}else if(type == TEAM){
			return CollectionsIg.<List<Integer>>deepUnmodifiableList(s.winners_count_team);
		}else{
			return CollectionsIg.<List<Integer>>deepUnmodifiableList(s.winners_count_total);
		}
	}

	public List<Integer> playersCount(T player, GameType type){
		Stats s=stats.get(player);
		if(s == null){
			return Collections.EMPTY_LIST;
		}
		if(type == SINGLE){
			return Collections.<Integer>unmodifiableList(s.players_count);
		}else if(type == TEAM){
			return Collections.<Integer>unmodifiableList(s.players_count_team);
		}else{
			return Collections.<Integer>unmodifiableList(s.players_count_total);
		}
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
