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

import static es.iguanod.games.cards.DeckType.*;
import es.iguanod.util.Maybe;
import es.iguanod.util.tuples.Tuple2;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * An {@link java.lang.Enum Enum} representing different symbols in playing
 * cards.
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public enum CardSymbol{

	/**
	 * French, Spanish, extended Spanish and German card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Ace"
	 *     Spanish: "As"
	 *     French: "As"
	 *     German: "As"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	ACE(new DeckType[]{
		FRENCH,
		SPANISH,
		SPANISH_EXTENDED,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Ace"),
		new Tuple2(new Locale("ES"), "As"),
		new Tuple2(Locale.FRENCH, "As"),
		new Tuple2(Locale.GERMAN, "As")
	}),
	/**
	 * French, Spanish, extended Spanish and German card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Two"
	 *     Spanish: "Dos"
	 *     French: "Deux"
	 *     German: "Zwei"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	TWO(new DeckType[]{
		FRENCH,
		SPANISH,
		SPANISH_EXTENDED,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Two"),
		new Tuple2(new Locale("ES"), "Dos"),
		new Tuple2(Locale.FRENCH, "Deux"),
		new Tuple2(Locale.GERMAN, "Zwei")
	}),
	/**
	 * French, Spanish, extended Spanish and German card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Three"
	 *     Spanish: "Tres"
	 *     French: "Trois"
	 *     German: "Drei"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	THREE(new DeckType[]{
		FRENCH,
		SPANISH,
		SPANISH_EXTENDED,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Three"),
		new Tuple2(new Locale("ES"), "Tres"),
		new Tuple2(Locale.FRENCH, "Trois"),
		new Tuple2(Locale.GERMAN, "Drei")
	}),
	/**
	 * French, Spanish, extended Spanish and German card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Four"
	 *     Spanish: "Cuatro"
	 *     French: "Quatre"
	 *     German: "Vier"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	FOUR(new DeckType[]{
		FRENCH,
		SPANISH,
		SPANISH_EXTENDED,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Four"),
		new Tuple2(new Locale("ES"), "Cuatro"),
		new Tuple2(Locale.FRENCH, "Quatre"),
		new Tuple2(Locale.GERMAN, "Vier")
	}),
	/**
	 * French, Spanish, extended Spanish and German card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Five"
	 *     Spanish: "Cinco"
	 *     French: "Cinq"
	 *     German: "Fünf"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	FIVE(new DeckType[]{
		FRENCH,
		SPANISH,
		SPANISH_EXTENDED,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Five"),
		new Tuple2(new Locale("ES"), "Cinco"),
		new Tuple2(Locale.FRENCH, "Cinq"),
		new Tuple2(Locale.GERMAN, "Fünf")
	}),
	/**
	 * French, Spanish, extended Spanish and German card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Six"
	 *     Spanish: "Seis"
	 *     French: "Six"
	 *     German: "Sechs"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	SIX(new DeckType[]{
		FRENCH,
		SPANISH,
		SPANISH_EXTENDED,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Six"),
		new Tuple2(new Locale("ES"), "Seis"),
		new Tuple2(Locale.FRENCH, "Six"),
		new Tuple2(Locale.GERMAN, "Sechs")
	}),
	/**
	 * French, Spanish, extended Spanish and German card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Seven"
	 *     Spanish: "Siete"
	 *     French: "Sept"
	 *     German: "Sieben"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	SEVEN(new DeckType[]{
		FRENCH,
		SPANISH,
		SPANISH_EXTENDED,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Seven"),
		new Tuple2(new Locale("ES"), "Siete"),
		new Tuple2(Locale.FRENCH, "Sept"),
		new Tuple2(Locale.GERMAN, "Sieben")
	}),
	/**
	 * French, extended Spanish and German card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Eight"
	 *     Spanish: "Ocho"
	 *     French: "Huit"
	 *     German: "Acht"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	EIGHT(new DeckType[]{
		FRENCH,
		SPANISH_EXTENDED,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Eight"),
		new Tuple2(new Locale("ES"), "Ocho"),
		new Tuple2(Locale.FRENCH, "Huit"),
		new Tuple2(Locale.GERMAN, "Acht")
	}),
	/**
	 * French, extended Spanish and German card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Nine"
	 *     Spanish: "Nueve"
	 *     French: "Neuf"
	 *     German: "Neun"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	NINE(new DeckType[]{
		FRENCH,
		SPANISH_EXTENDED,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Nine"),
		new Tuple2(new Locale("ES"), "Nueve"),
		new Tuple2(Locale.FRENCH, "Neuf"),
		new Tuple2(Locale.GERMAN, "Neun")
	}),
	/**
	 * French, extended Spanish and German card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Ten"
	 *     Spanish: "Diez"
	 *     French: "Dix"
	 *     German: "Zehn"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	TEN(new DeckType[]{
		FRENCH,
		SPANISH_EXTENDED,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Ten"),
		new Tuple2(new Locale("ES"), "Diez"),
		new Tuple2(Locale.FRENCH, "Dix"),
		new Tuple2(Locale.GERMAN, "Zehn")
	}),
	/**
	 * French and German card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Jack"
	 *     Spanish: "Jota"
	 *     French: "Jack"
	 *     German: "Bube"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	JACK(new DeckType[]{
		FRENCH,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Jack"),
		new Tuple2(new Locale("ES"), "Jota"),
		new Tuple2(Locale.FRENCH, "Jack"),
		new Tuple2(Locale.GERMAN, "Bube")
	}),
	/**
	 * Spanish and extended Spanish card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Page"
	 *     Spanish: "Sota"
	 *     French: "Jack"
	 *     German: "Bube"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 */
	PAGE(new DeckType[]{
		SPANISH,
		SPANISH_EXTENDED
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Page"),
		new Tuple2(new Locale("ES"), "Sota"),
		new Tuple2(Locale.FRENCH, "Jack"),
		new Tuple2(Locale.GERMAN, "Bube")
	}),
	/**
	 * French and German card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Queen"
	 *     Spanish: "Dama"
	 *     French: "Dame"
	 *     German: "Dame"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	QUEEN(new DeckType[]{
		FRENCH,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Queen"),
		new Tuple2(new Locale("ES"), "Dama"),
		new Tuple2(Locale.FRENCH, "Dame"),
		new Tuple2(Locale.GERMAN, "Dame")
	}),
	/**
	 * Spanish and extended Spanish card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Knight"
	 *     Spanish: "Caballo"
	 *     French: "Cheval"
	 *     German: "Pferd"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 */
	KNIGHT(new DeckType[]{
		SPANISH,
		SPANISH_EXTENDED
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Knight"),
		new Tuple2(new Locale("ES"), "Caballo"),
		new Tuple2(Locale.FRENCH, "Cheval"),
		new Tuple2(Locale.GERMAN, "Pferd")
	}),
	/**
	 * French, Spanish, extended Spanish and German card symbol.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "King"
	 *     Spanish: "Rey"
	 *     French: "Roi"
	 *     German: "König"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	KING(new DeckType[]{
		FRENCH,
		SPANISH,
		SPANISH_EXTENDED,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "King"),
		new Tuple2(new Locale("ES"), "Rey"),
		new Tuple2(Locale.FRENCH, "Roi"),
		new Tuple2(Locale.GERMAN, "König")
	});
	/**
	 * List containing all the {@link java.util.Locale Locales} to whose
	 * languages the elements of this {@code Enum} can be translated.
	 */
	public static final List<Locale> supported_locale=Collections.unmodifiableList(Arrays.asList(new Locale[]{
		Locale.ENGLISH,
		new Locale("ES"),
		Locale.FRENCH,
		Locale.GERMAN
	}));
	/**
	 * List containing all the {@link es.iguanod.games.cards.DeckType
	 * DeckTypes} this {@code CardSymbol} constant belongs to.
	 */
	public final Set<DeckType> types;
	/**
	 * Map containing the translations of this {@code CardSymbol} constant in
	 * the languages of all the {@link #supported_locale supported locales}.
	 */
	public final Map<Locale, String> translations;
	/**
	 * Mapping of all the translations to their associated {@code CardSymbol}
	 * for reverse lookup in the fromString function.
	 */
	private static final Map<Locale, Map<String, CardSymbol>> mappings;

	static{
		mappings=new HashMap<>();
		for(Locale locale:supported_locale){
			mappings.put(locale, new HashMap<String, CardSymbol>());
		}
		for(CardSymbol symbol:values()){
			for(Map.Entry<Locale, String> translation:symbol.translations.entrySet()){
				mappings.get(translation.getKey()).put(translation.getValue().toUpperCase(), symbol);
			}
		}
	}

	/**
	 * Initializes the fields types and translations.
	 *
	 * @param types the DeckTypes the symbol belongs to.
	 * @param translations the translations for the symbol in the supported
	 * locales.
	 */
	private CardSymbol(DeckType[] types, final Tuple2<Locale, String>[] translations){
		this.types=Collections.unmodifiableSet(new HashSet<>(Arrays.asList(types)));
		this.translations=Collections.unmodifiableMap(new HashMap(){
			private static final long serialVersionUID=5492858756416492L;

			{
				for(Tuple2<Locale, String> trans:translations){
					put(trans.getFirst(), trans.getSecond());
				}
			}
		});
	}

	/**
	 * Returns the name of this {@code CardSymbol} constant. The first letter
	 * is uppercase, and all the others are lowercase.
	 *
	 * @return the name of this {@code CardSymbol}
	 */
	@Override
	public String toString(){
		return name().charAt(0) + name().substring(1).toLowerCase();
	}

	/**
	 * Returns the name of this {@code CardSymbol} constant in the language
	 * specified by the passed {@link java.util.Locale Locale}. The first
	 * letter is uppercase, and all the others are lowercase.
	 *
	 * @param locale the {@code Locale} specifying the language to translate
	 * this {@code CardSymbol}
	 *
	 * @return the string representation of this {@code CardSymbol} in the
	 * specified {@code Locale}
	 *
	 * @throws IllegalArgumentException if the specified {@code Locale} is not
	 * supported
	 * @throws NullPointerException if {@code locale} is {@code null}
	 *
	 * @see es.iguanod.games.cards.CardSymbol#supported_locale
	 */
	public String toString(Locale locale) throws IllegalArgumentException{
		if(locale == null){
			throw new NullPointerException("Locale can't be null");
		}
		if(!supported_locale.contains(locale)){
			throw new IllegalArgumentException("Unsupported Locale");
		}

		return translations.get(locale);
	}

	/**
	 * Returns the {@code CardSymbol} constant with the specified name (case
	 * insensitive). The {@code name} may be in any of the {@link
	 * #supported_locale supported locales}.
	 *
	 * @param name the name corresponding to the searched {@code CardSymbol}.
	 * @param locale the {@code Locale} specifying the language of
	 * {@code name}
	 *
	 * @return the {@code CardSymbol} corresponding with {@code name}.
	 *
	 * @throws IllegalArgumentException if this {@link java.lang.Enum Enum}
	 * does not have a constant with the specified name, or if {@code locale}
	 * is not supported
	 * @throws NullPointerException if {@code name} or {@code locale} is
	 * {@code null}
	 */
	public static Maybe<CardSymbol> fromString(String name, Locale locale){
		if(name == null || locale == null){
			throw new NullPointerException("Null values not accepted");
		}
		if(!supported_locale.contains(locale)){
			throw new IllegalArgumentException("Locale not supported");
		}
		CardSymbol ret=mappings.get(locale).get(name.toUpperCase());
		if(ret == null){
			return Maybe.ABSENT;
		}else{
			return Maybe.from(ret);
		}
	}
}
