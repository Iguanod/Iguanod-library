package es.iguanod.games;

import es.iguanod.collect.DoubleTreeCounter.DoubleTreeCounterBuilder;
import es.iguanod.collect.SortedCounter;
import es.iguanod.util.tuples.Tuple2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since
 * @version
 */
public class EloScoreTable<T> implements Iterable<Tuple2<T, Integer>>, Serializable{

	private static final long serialVersionUID=818005879329498514L;
	//************
	private static final int TIMES_BETTER=2;
	private static final int DIFFERENCE_BETTER=150;
	//************
	private static final int BASE_CHANGE=30;
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

		private static final long serialVersionUID=-469496794623962387L;
		//************
		private LinkedList<Double> games=new LinkedList<>();
		private double k_factor;
		private int wins=0;
		private int losses=0;

		public double addGame(int num_players, boolean win){

			if(win){
				wins++;
			}else{
				losses++;
			}

			if(!use_k_factor){
				return 1;
			}

			double next=win?1.0:-1.0 / (num_players - 1);
			games.addFirst(next);
			k_factor+=next;

			if(games.size() > RECORD_SIZE){
				k_factor-=games.removeLast();
			}

			if(wins + losses > positioning_games){
				return Math.abs((k_factor / positioning_games) + (win?1.1:-1.1));
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
			updatePlayer(player, scores_sum, 1.0 / winner_players.size(), winner_players.size() + loser_players.size(), acc, BASE_CHANGE);
		}

		for(T player:loser_players){
			updatePlayer(player, scores_sum, 0, winner_players.size() + loser_players.size(), acc, BASE_CHANGE);
		}

		for(Entry<T, Double> change:acc.entrySet()){
			table.sum(change.getKey(), change.getValue());
			mean+=change.getValue() / table.size();
		}
		
		games++;
	}
	
	private void updatePlayer(T player, double scores_sum, double actual_score, int num_players, Map<T, Double> acc, double change){

		double k_factor=stats.get(player).addGame(num_players, actual_score > 0);
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
			updateTeam(team, winner_elos.get(i), scores_sum, 1.0 / winner_teams.size(), winner_teams.size() + loser_teams.size(), acc);
			i++;
		}

		i=0;
		for(Collection<? extends T> team:loser_teams){
			updateTeam(team, loser_elos.get(i), scores_sum, 1.0 / winner_teams.size(), winner_teams.size() + loser_teams.size(), acc);
			i++;
		}

		for(Entry<T, Double> change:acc.entrySet()){
			table.sum(change.getKey(), change.getValue());
			mean+=change.getValue() / table.size();
		}
		
		games++;
	}

	private void updateTeam(Collection<? extends T> team, double team_elo, double scores_sum, double actual_score, int num_teams, Map<T, Double> acc){

		double expected_score=Math.pow(TIMES_BETTER, team_elo / DIFFERENCE_BETTER) / scores_sum;

		double scores_sum_players=0;
		for(T player:team){
			scores_sum_players+=Math.pow(TIMES_BETTER, table.get(player) / DIFFERENCE_BETTER);
		}
		for(T player:team){
			updatePlayer(player, scores_sum_players, actual_score, num_teams, acc, (actual_score - expected_score) * BASE_CHANGE * team.size());
		}
	}
	
	public int totalGames(){
		return games;
	}

	public int wins(T player){
		Stats s=stats.get(player);
		return s == null?0:s.wins;
	}

	public int losses(T player){
		Stats s=stats.get(player);
		return s == null?0:s.losses;
	}

	public int numGames(T player){
		Stats s=stats.get(player);
		return s == null?0:s.wins + s.losses;
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
