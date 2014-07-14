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
package es.iguanod.games.cards;

import es.iguanod.util.tuples.Tuple2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

/**
 * Class that represents a shoe to work with one or more decks of cards.
 * <p>
 * To use this class, typically a new {@code DealerShoe} is created, either with
 * some decks in it, or empty and then added manually the decks. Then, calls to
 * {@link #next()} or {@link #nextForced()} return
 * {@link es.iguanod.games.cards.Card Cards} and put them in the discard pile.
 * When the shoe is empty, the discard pile is restored and shuffled.</p>
 * <p>
 * {@code DealerShoe} accepts {@link java.util.Collection}{@code <Card>} as
 * parameters for the addition methods. It is important to note that, since
 * {@link es.iguanod.games.cards.Deck} extends {@code ArrayList<Card>}, those
 * methods accept {@code Decks} as well.</p>
 * <p>
 * Once a deck is added to the shoe, its {@code Cards} are actually copied into
 * the shoe, so modifications to the {@code ArrayList<Card>} or {@code Deck} are
 * not reflected. However, the shoe keeps track of the decks added to it, so
 * {@link #retrieveDecks()} will return the {@code Cards} in separate
 * {@code Decks} according to how they were added (except if they were removed
 * with {@link #remove} or {@link #removeNext}).</p>
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.1.1.a
 * @version 1.0.1.b
 */
public class DealerShoe implements Iterator<Card>, Serializable{

	private static final long serialVersionUID=7915964897261358771L;
	//
	//************
	//
	/**
	 * Cards ready to be dealt.
	 */
	private LinkedList<Tuple2<Card, Long>> cards=new LinkedList<>();
	/**
	 * Cards in the discard pile.
	 */
	private LinkedList<Tuple2<Card, Long>> discarded=new LinkedList<>();
	/**
	 * Id for the decks.
	 */
	private long count=0;
	/**
	 * Correct state to call remove method.
	 */
	private boolean remove_allowed=false;

	/**
	 * Constructs a new empty {@code DealerShoe}.
	 */
	public DealerShoe(){
	}

	/**
	 * Constructs a {@code DealerShoe} with a number of decks equal to
	 * {@code num_decks} of the type {@code type}. If {@code num_decks} is
	 * non-possitive the shoe is created empty.
	 *
	 * @param num_decks number of decks to be added
	 * @param type type of the decks
	 *
	 * @throws NullPointerException if {@code type} is {@code null}
	 *
	 * @see es.iguanod.games.cards.Deck#Deck(DeckType)
	 */
	public DealerShoe(int num_decks, DeckType type){
		Deck deck=new Deck(type);
		for(int i=0; i < num_decks; i++){
			for(Card card:deck){
				cards.add(new Tuple2<>(card, count));
			}
			count++;
		}
	}

	/**
	 * Constructs a {@code DealerShoe} with all the cards of the specified
	 * decks. {@code Cards} in different decks will be retrieved separatedly
	 * by the {@link #retrieveDecks()} method.
	 *
	 * @param decks the decks to be added
	 *
	 * @throws NullPointerException if {@code decks} or any of the
	 * sub-{@code Collection} or {@code Card} is {@code null}
	 */
	public DealerShoe(Collection<? extends Collection<? extends Card>> decks){
		for(Collection<? extends Card> deck:decks){
			for(Card card:deck){
				if(card == null){
					throw new NullPointerException("Cards cannot be null");
				}
				cards.add(new Tuple2<>(card, count));
			}
			count++;
		}
	}

	/**
	 * Constructs a new {@code DealerShoe} with all the decks from another.
	 * {@code Cards} in the discard pile of the original shoe will be added to
	 * the discard pile of this one.
	 *
	 * @param shoe the {@code DealerShoe} whose decks are to be added to this
	 *
	 * @throws NullPointerException if {@code shoe} is {@code null}
	 */
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

	/**
	 * Adds all the {@code Cards} of {@code deck} to this {@code DealerShoe}.
	 * The {@code Cards} are added to the end of the shoe.
	 *
	 * @param deck {@code Collection} of all the {@code Cards} to be added
	 *
	 * @return {@code this} (for method call concatenation)
	 *
	 * @throws NullPointerException if {@code deck} or any of its
	 * {@code Cards} is {@code null}
	 */
	public DealerShoe add(Collection<? extends Card> deck){
		for(Card card:deck){
			if(card == null){
				throw new NullPointerException("Cards cannot be null");
			}
			cards.add(new Tuple2<>(card, count));
		}
		count++;
		remove_allowed=false;
		return this;
	}

	/**
	 * Adds all the {@code Cards} of {@code deck} to this {@code DealerShoe}
	 * discard pile.
	 *
	 * @param deck {@code Collection} of all the {@code Cards} to be added
	 *
	 * @return {@code this} (for method call concatenation)
	 *
	 * @throws NullPointerException if {@code deck} or any of its
	 * {@code Cards} is {@code null}
	 */
	public DealerShoe addToDiscardPile(Collection<? extends Card> deck){
		for(Card card:deck){
			if(card == null){
				throw new NullPointerException("Cards cannot be null");
			}
			discarded.add(new Tuple2<>(card, count));
		}
		count++;
		remove_allowed=false;
		return this;
	}

	/**
	 * Retrieves all the {@code Cards} of this {@code DealerShoe} orgainzed in
	 * {@code Decks} according to how they were added. Note that even if a
	 * deck was added being any kind of {@code Collection<Card>}, it will be
	 * returned by this method as {@code Deck}.
	 *
	 * @return the {@code Decks}
	 */
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

	/**
	 * Moves all the cards from the discard pile to the shoe.
	 *
	 * @return {@code this} (for method call concatenation)
	 */
	public DealerShoe restore(){

		cards.addAll(discarded);
		discarded.clear();
		remove_allowed=false;
		return this;
	}

	/**
	 * Shuffles all the cards in the shoe. This does <b>not</b> restores the
	 * discard pile, in order to shuffle all the cards do
	 * {@code restore().shuffle()}.
	 *
	 * @return {@code this} (for method call concatenation)
	 */
	public DealerShoe shuffle(){

		Collections.shuffle(cards);
		remove_allowed=false;
		return this;
	}

	/**
	 * Removes all the cards in the shoe and in the discard pile.
	 *
	 * @return {@code this} (for method call concatenation)
	 */
	public DealerShoe clear(){

		cards.clear();
		discarded.clear();
		count=0;
		remove_allowed=false;
		return this;
	}

	/**
	 * Skips one card from the shoe, putting it in the discard pile. If
	 * neccessary, cards on the discard pile are restored and shuffled.
	 *
	 * @return {@code this} (for method call concatenation)
	 *
	 * @throws NoSuchElementException if this {@code DealerShoe} has no cards
	 */
	public DealerShoe skip(){

		return skip(1);
	}

	/**
	 * Skips {@code n} cards from the shoe, putting them in the discard pile.
	 * If neccessary, cards on the discard pile are {@link #restore restored}
	 * and {@link #shuffle shuffled}.
	 *
	 * @param n number of cards to be skipped
	 *
	 * @return {@code this} (for method call concatenation)
	 *
	 * @throws IllegalArgumentException if {@code n} is negative
	 * @throws NoSuchElementException if this {@code DealerShoe} has no cards
	 */
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
	 * Returns wether this {@code DealerShoe} has more cards to deal (not in
	 * the discard pile). If this method returns {@code true}, then
	 * {@link #next()} will return an element rather than throwing an
	 * exception.
	 *
	 * @return {@code true} if there are more cards, {@code false} otherwise
	 */
	@Override
	public boolean hasNext(){
		return !cards.isEmpty();
	}

	/**
	 * Returns the next {@code Card} in the shoe and puts it in the discard
	 * pile. To get the next {@code Card} even if they are all in the discard
	 * pile, use {@link #nextForced()}.
	 *
	 * @return the next {@code Card} in the shoe.
	 *
	 * @throws NoSuchElementException if there are no more {@code Cards} in
	 * the shoe
	 */
	@Override
	public Card next() throws NoSuchElementException{

		if(cards.isEmpty()){
			throw new NoSuchElementException();
		}

		remove_allowed=true;

		Tuple2<Card, Long> next=cards.pop();
		discarded.add(next);
		return next.getFirst();
	}

	/**
	 * Returns the next {@code Card} in the shoe (or {@link #restore restores}
	 * the discard pile and {@link #shuffle shuffles}, then takes one) and
	 * puts it in the discard pile.
	 *
	 * @return the next {@code Card}
	 *
	 * @throws NoSuchElementException if this {@code DealerShoe} has no cards
	 */
	public Card nextForced() throws NoSuchElementException{

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
	 * Removes the last {@code Card} returned by {@link #next} or
	 * {@link #nextForced} from this {@code DealerShoe}. This method can be
	 * called only once per call to {@link #next} or {@link #nextForced}, and
	 * calling any other method that modifies the state of the
	 * {@code DealerShoe} in between will cause this method to throw an
	 * {@code IllegalStateException}.
	 * <p>
	 * If a {@code Card} is removed, it won't be retrieved by the
	 * {@link #retrieveDecks} method.</p>
	 *
	 * @throws IllegalStateException if this call doesn't follow a successfull
	 * call to either {@link #next} or {@link #nextForced}
	 */
	@Override
	public void remove(){
		if(!remove_allowed){
			throw new IllegalStateException();
		}
		discarded.removeLast();
		remove_allowed=false;
	}

	/**
	 * Returns and removes the next {@code Card} that would be returned by
	 * {@link #nextForced} from this {@code DealerShoe}.
	 * <p>
	 * If a {@code Card} is removed, it won't be retrieved by the
	 * {@link #retrieveDecks} method.</p>
	 *
	 * @return the removed {@code Card}
	 */
	public Card removeNext(){

		Card ret=nextForced();
		discarded.removeLast();
		remove_allowed=false;
		return ret;
	}
}
