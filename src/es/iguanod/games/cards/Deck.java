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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class representing a deck of playing cards. A deck is essentially an
 * {@link java.util.ArrayList} of {@link es.iguanod.games.cards.Card Cards} that
 * offers extra funtionality for custom sorting and
 * {@link #Deck(DeckType) creation} of a {@code Deck} with all the {@code Cards}
 * of a certain {@link es.iguanod.games.cards.DeckType}.
 * <p>
 * Regular sorting and shuffling can be achieved with
 * {@link java.util.Collections#sort(List) Collections.sort(this)} and
 * {@link java.util.Collections#shuffle(List) Collections.shuffle(this)}.</p>
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class Deck extends ArrayList<Card>{

	private static final long serialVersionUID=1514154583449501651L;

	/**
	 * Constructs a {@code Deck} that contains all {@code Cards} from a
	 * {@code Collection}.
	 *
	 * @param cards the {@code Cards} making up the {@code Deck}
	 *
	 * @throws NullPointerException if {@code cards} is {@code null}
	 */
	public Deck(Collection<? extends Card> cards){
		super(cards);
	}

	/**
	 * Constructs a {@code Deck} that contains all the @{code Cards} of the
	 * specified {@code DeckType}.
	 *
	 * @param type the {@code DeckType} of all the {@code Cards} of this
	 * {@code Deck}
	 *
	 * @throws NullPointerException if {@code type} is {@code null}
	 */
	public Deck(DeckType type){

		if(type == null){
			throw new NullPointerException("The type can't be null");
		}

		ArrayList<Card> cards=new ArrayList<>();

		for(CardSuit suit:CardSuit.values()){
			if(suit.types.contains(type)){
				for(CardSymbol symbol:CardSymbol.values()){
					if(symbol.types.contains(type)){
						cards.add(new Card(type, symbol, suit));
					}
				}
			}
		}

		super.addAll(cards);
	}

	/**
	 * Sorts the {@code Deck} according to the order specified by
	 * {@code suits} and {@code symbols}. {@code Cards} are compared first for
	 * their suit and then for their symbol. The lower the index of a suit or
	 * symbol in {@code suits} or {@code symbols}, the closer to the begining
	 * of the {@code Deck} will a {@code Card} be placed.
	 * <p>
	 * If a {@code Card}'s suit isn't contained in {@code suits}, it will be
	 * placed after all other suits. If a {@code Card}'s symbol isn't
	 * contained in {@code symbols}, it will be placed after all other symbols
	 * with its suit.</p>
	 *
	 * @param suits a {@code List} specifying the order of the suits
	 * @param symbols a {@code List} specifying the order of the symbols
	 *
	 * @return {@code this} (for operation concatenation)
	 */
	public Deck sort(final List<CardSuit> suits, final List<CardSymbol> symbols){
		Collections.sort(this, new Comparator<Card>(){
			@Override
			public int compare(Card o1, Card o2){
				int ind1=suits.indexOf(o1.suit);
				int ind2=suits.indexOf(o2.suit);
				int cmp=(ind1 != -1 ? ind1 : suits.size()) - (ind2 != -1 ? ind2 : suits.size());
				if(cmp != 0){
					return cmp;
				}else{
					ind1=symbols.indexOf(o1.symbol);
					ind2=symbols.indexOf(o2.symbol);
					ind1=ind1 != -1 ? ind1 : suits.size();
					ind2=ind2 != -1 ? ind2 : suits.size();
					return (ind1 != -1 ? ind1 : symbols.size()) - (ind2 != -1 ? ind2 : symbols.size());
				}
			}
		});
		return this;
	}
}
