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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Class representing a playing card. A card consists of a
 * {@link es.iguanod.games.cards.CardSuit suit} and a
 * {@link es.iguanod.games.cards.CardSymbol symbol}, and both have to belong to
 * same {@link es.iguanod.games.cards.DeckType deck type}.
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.1.1.a
 * @version 1.0.1.b
 */
public class Card implements Comparable<Card>, Serializable{

	private static final long serialVersionUID=2411911287533715178L;
	//
	//************
	//
	@SuppressWarnings("serial") // Because is static and so transient
	private static final Map<Locale, String> translations=new HashMap<Locale, String>(){
		{
			put(Locale.ENGLISH, " of ");
			put(new Locale("ES"), " de ");
			put(Locale.FRENCH, " du ");
		}
	};
	/**
	 * List containing all the {@link java.util.Locale Locales} to whose
	 * languages the preposition "of" used to get a {@code String}
	 * representation of a {@code Card} can be translated. (For example: "Ace
	 * <b>of</b> spades").
	 */
	public static final List<Locale> supported_locale=Collections.unmodifiableList(Arrays.asList(new Locale[]{
		Locale.ENGLISH,
		new Locale("ES"),
		Locale.FRENCH
	}));
	//
	//************
	//
	/**
	 * The suit of the card.
	 */
	public final CardSuit suit;
	/**
	 * The symbol of the card.
	 */
	public final CardSymbol symbol;

	/**
	 * Constructs a {@code Card} of the specified type of deck and with the
	 * specified suit and symbol.
	 *
	 * @param type the type of the {@code Card}
	 * @param symbol the symbol of the {@code Card}
	 * @param suit the suit of the {@code Card}
	 *
	 * @throws NullPointerException if {@code type}, {@code symbol} or
	 * {@code suit} is {@code null}
	 * @throws IllegalArgumentException if {@code symbol} or {@code suit}
	 * don't belong to the deck type {@code type}
	 */
	public Card(DeckType type, CardSymbol symbol, CardSuit suit) throws IllegalArgumentException{
		if(type == null || symbol == null || suit == null){
			throw new NullPointerException("The constructor doesn't accept null values");
		}
		if(!symbol.types.contains(type) || !suit.types.contains(type)){
			throw new IllegalArgumentException("The card symbol \"" + symbol + "\" or the card suit \"" + suit + "\" doesn't have the type \"" + type + "\" as one of their types.");
		}
		this.symbol=symbol;
		this.suit=suit;
	}

	/**
	 * Constructs a {@code Card} with the same suit and symbol of another
	 * {@code Card}.
	 *
	 * @param card the {@code Card} from which this will be constructed
	 *
	 * @throws NullPointerException if {@code card} is {@code null}
	 */
	public Card(Card card){
		this.suit=card.suit;
		this.symbol=card.symbol;
	}

	/**
	 * Compares this object with the specified object for order. Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 * <p>
	 * The suits are compared first, and in case they are equal the symbols
	 * are compared.</p>
	 *
	 * @param card the {@code Card} to be compared to {@code this}
	 *
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object
	 *
	 * @throws NullPointerException if {@code card} is {@code null}
	 *
	 * @see java.lang.Enum#compareTo(Object)
	 */
	@Override
	public int compareTo(Card card){

		int cmp;
		if((cmp=this.suit.compareTo(card.suit)) == 0){
			return this.symbol.compareTo(card.symbol);
		}else{
			return cmp;
		}
	}

	/**
	 * Returns a {@code String} representation of this {@code Card}. Said
	 * representation is equivalent to the one obtained with
	 * {@code symbol.toString() + " of " + suit.toString()}.
	 * <p>
	 * To obtain the representation in a different language, use
	 * {@link #toString(Locale)} instead.</p>
	 *
	 * @return the {@code String} representation
	 *
	 * @see es.iguanod.games.cards.CardSuit#toString
	 * @see es.iguanod.games.cards.CardSymbol#toString
	 */
	@Override
	public String toString(){
		return symbol + " of " + suit;
	}

	/**
	 * Returns a {@code String} representation of this {@code Card}. Said
	 * representation is obtained by calling {@code toString(locale)} on the
	 * symbol and the suite, and separating them with the appropriate
	 * preposition for the {@code Locale}, or "of" if there isn't one.
	 *
	 * @param locale the {code Locale} use to translate the {@code Card}
	 *
	 * @return the {@code String} representation
	 *
	 * @throws IllegalArgumentException if {@code locale} is not supported
	 * @throws NullPointerException if {@code locale} is {@code null}
	 *
	 * @see es.iguanod.games.cards.CardSuit#toString(Locale)
	 * @see es.iguanod.games.cards.CardSymbol#toString(Locale)
	 * @see es.iguanod.games.cards.CardSuit#supported_locale
	 * @see es.iguanod.games.cards.CardSymbol#supported_locale
	 */
	public String toString(Locale locale) throws IllegalArgumentException{
		return symbol.toString(locale) + (supported_locale.contains(locale)?translations.get(locale):" of ") + suit.toString(locale);
	}

	/**
	 * Indicates whether some other object is "equal to" this one. Two
	 * {@code Cards} are considered equals if both have the same suit and
	 * symbol.
	 * <p>
	 * In every other aspect this method follows the general contract of
	 * {@link java.lang.Object#equals(Object) Object.equals}.</p>
	 *
	 * @param obj the object to be tested for equality
	 *
	 * @return {@code true} if the passed argument is equal to {@code this},
	 * {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj){

		if(obj == null){
			return false;
		}

		if(obj == this){
			return true;
		}

		if(!(obj instanceof Card)){
			return false;
		}

		Card card=(Card)obj;
		return this.suit == card.suit
		&& this.symbol == card.symbol;
	}

	/**
	 * Returns a hashCode value for this {@code Card}.
	 * <p>
	 * This method follows the general contract of
	 * {@link java.lang.Object#hashCode() Object.hashCode}.</p>
	 *
	 * @return the hashCode of this object
	 */
	@Override
	public int hashCode(){
		int hash=5;
		hash=97 * hash + (this.suit != null?this.suit.hashCode():0);
		hash=97 * hash + (this.symbol != null?this.symbol.hashCode():0);
		return hash;
	}
}
