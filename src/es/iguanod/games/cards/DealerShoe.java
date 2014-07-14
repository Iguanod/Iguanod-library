package es.iguanod.games.cards;

import es.iguanod.util.tuples.Tuple2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.1.1.a
 * @version
 */
public class DealerShoe implements Iterator<Card>, Serializable{

	private static final long serialVersionUID=7915964897261358771L;
	//************
	private LinkedList<Tuple2<Card, Long>> cards=new LinkedList<>();
	private LinkedList<Tuple2<Card, Long>> discarded=new LinkedList<>();
	private long count=0;
	private boolean remove_allowed=false;
	
	// DOC REMAINDER: Decks are copied, modifications dont get reflected

	public DealerShoe(){
	}

	public DealerShoe(int decks, DeckType type){
		Deck deck=new Deck(type);
		for(int i=0; i < decks; i++){
			for(Card card:deck){
				cards.add(new Tuple2<>(card, count));
			}
			count++;
		}
	}

	public DealerShoe(Collection<? extends List<? extends Card>> decks) throws IllegalArgumentException{
		for(List<? extends Card> deck:decks){
			for(Card card:deck){
				cards.add(new Tuple2<>(card, count));
			}
			count++;
		}
	}

	public DealerShoe(DealerShoe shoe){

		for(Tuple2<Card, Long> tuple:shoe.cards){
			cards.add(tuple);
			count=Math.max(count, tuple.getSecond());
		}
		for(Tuple2<Card, Long> tuple:shoe.discarded){
			discarded.add(tuple);
			count=Math.max(count, tuple.getSecond());
		}
	}

	public DealerShoe add(List<? extends Card> deck){
		for(Card card:deck){
			cards.add(new Tuple2<>(card, count));
		}
		count++;
		remove_allowed=false;
		return this;
	}

	public DealerShoe add(Collection<? extends List<? extends Card>> decks){
		for(List<? extends Card> deck:decks){
			add(deck);
		}
		return this;
	}

	public DealerShoe addToDiscardPile(List<? extends Card> deck){
		for(Card card:deck){
			discarded.add(new Tuple2<>(card, count));
		}
		count++;
		remove_allowed=false;
		return this;
	}

	public DealerShoe addToDiscardPile(Collection<? extends List<? extends Card>> decks){
		for(List<? extends Card> deck:decks){
			addToDiscardPile(deck);
		}
		return this;
	}

	public Collection<Deck> retrieveDecks(){

		restore();

		HashMap<Long, ArrayList<Card>> map=new HashMap<>();

		for(Tuple2<Card, Long> tuple:cards){
			if(map.containsKey(tuple.getSecond())){
				map.get(tuple.getSecond()).add(tuple.getFirst());
			}else{
				ArrayList<Card> list=new ArrayList<>();
				list.add(tuple.getFirst());
				map.put(tuple.getSecond(), list);
			}
		}

		ArrayList<Deck> ret=new ArrayList<>();
		for(Entry<Long, ArrayList<Card>> entry:map.entrySet()){
			ret.add(new Deck(entry.getValue()));
		}

		this.clear();
		
		remove_allowed=false;

		return ret;
	}

	public DealerShoe restore(){

		cards.addAll(discarded);
		discarded.clear();
		remove_allowed=false;
		return this;
	}

	public DealerShoe shuffle(){

		Collections.shuffle(cards);
		remove_allowed=false;
		return this;
	}

	public DealerShoe clear(){

		cards.clear();
		discarded.clear();
		count=0;
		remove_allowed=false;
		return this;
	}

	public DealerShoe skip(){

		return skip(1);
	}

	public DealerShoe skip(int n) throws IllegalArgumentException{

		if(n < 0){
			throw new IllegalArgumentException("The number must be non-negative");
		}

		for(int i=0; i < n; i++){
			nextForced();
		}
		
		remove_allowed=false;

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext(){
		return !cards.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Card next(){

		if(cards.isEmpty()){
			throw new NoSuchElementException();
		}

		remove_allowed=true;

		Tuple2<Card, Long> next=cards.pop();
		discarded.add(next);
		return next.getFirst();
	}

	public Card nextForced(){

		if(cards.isEmpty()){
			if(discarded.isEmpty()){
				throw new NoSuchElementException();
			}
			restore().shuffle();
		}

		remove_allowed=true;

		Tuple2<Card, Long> next=cards.pop();
		discarded.add(next);
		return next.getFirst();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(){
		if(!remove_allowed){
			throw new IllegalStateException();
		}
		discarded.removeLast();
		remove_allowed=false;
	}

	public Card removeNext(){

		Card ret=nextForced();
		discarded.removeLast();
		remove_allowed=false;
		return ret;
	}
}
