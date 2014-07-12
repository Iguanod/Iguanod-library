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

/**
 * An {@link java.lang.Enum Enum} representing different types of decks in
 * playing cards.
 *
 * @see es.iguanod.games.cards.Card
 * @see es.iguanod.games.cards.CardSuit
 * @see es.iguanod.games.cards.CardSymbol
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.1.1.a
 * @version 1.0.1.b
 */
public enum DeckType{

	/**
	 * The traditional Spanish deck.
	 */
	SPANISH,
	/**
	 * The extended Spanish deck, including numbers eight to ten.
	 */
	SPANISH_EXTENDED,
	/**
	 * The traditional French deck.
	 */
	FRENCH;

	/**
	 * Returns the name of this {@code DeckType} constant. The first letter is
	 * uppercase, and all the others are lowercase.
	 * 
	 * @return the name of this {@code DeckType}
	 */
	@Override
	public String toString(){
		return name().charAt(0) + name().substring(1).toLowerCase();
	}
}
