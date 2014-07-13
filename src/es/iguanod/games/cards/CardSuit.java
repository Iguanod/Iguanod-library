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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * An {@link java.lang.Enum Enum} representing different suits in playing cards.
 *
 * <p>
 * The suits belong to one or more {@link es.iguanod.games.cards.DeckType
 * DeckTypes}, and have translations to all the languages specified by the
 * {@link java.util.Locale Locales} in {@link #supported_locale} (the default
 * language is English). </p>
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.1.1.a
 * @version 1.0.1.b
 */
public enum CardSuit{

	/**
	 * French deck suit.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Spades"
	 *     Spanish: "Picas"
	 *     French: "Piques"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 */
	SPADES(new DeckType[]{
		FRENCH
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Spades"),
		new Tuple2(new Locale("ES"), "Picas"),
		new Tuple2(Locale.FRENCH, "Piques")
	}),
	/**
	 * French and German deck suit.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Hearts"
	 *     Spanish: "Corazones"
	 *     French: "Coeurs"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	HEARTS(new DeckType[]{
		FRENCH,
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Hearts"),
		new Tuple2(new Locale("ES"), "Corazones"),
		new Tuple2(Locale.FRENCH, "Coeurs")
	}),
	/**
	 * French deck suit.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Diamonds"
	 *     Spanish: "Diamantes"
	 *     French: "Carreaux"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 */
	DIAMONDS(new DeckType[]{
		FRENCH
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Diamonds"),
		new Tuple2(new Locale("ES"), "Diamantes"),
		new Tuple2(Locale.FRENCH, "Carreaux")
	}),
	/**
	 * French deck suit.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Clubs"
	 *     Spanish: "Tréboles"
	 *     French: "Trèfles"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#FRENCH
	 */
	CLUBS(new DeckType[]{
		FRENCH
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Clubs"),
		new Tuple2(new Locale("ES"), "Tréboles"),
		new Tuple2(Locale.FRENCH, "Trèfles")
	}),
	/**
	 * Spanish and extended Spanish deck suit.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Golds"
	 *     Spanish: "Oros"
	 *     French: "Or"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 */
	GOLDS(new DeckType[]{
		SPANISH,
		SPANISH_EXTENDED
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Golds"),
		new Tuple2(new Locale("ES"), "Oros"),
		new Tuple2(Locale.FRENCH, "Or")
	}),
	/**
	 * Spanish and extended Spanish deck suit.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Cups"
	 *     Spanish: "Copas"
	 *     French: "Coupes"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 */
	CUPS(new DeckType[]{
		SPANISH,
		SPANISH_EXTENDED
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Cups"),
		new Tuple2(new Locale("ES"), "Copas"),
		new Tuple2(Locale.FRENCH, "Coupes")
	}),
	/**
	 * Spanish and extended Spanish deck suit.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Swords"
	 *     Spanish: "Espadas"
	 *     French: "Piques"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 */
	SWORDS(new DeckType[]{
		SPANISH,
		SPANISH_EXTENDED
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Swords"),
		new Tuple2(new Locale("ES"), "Espadas"),
		new Tuple2(Locale.FRENCH, "Piques")
	}),
	/**
	 * Spanish and extended Spanish deck suit.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Batons"
	 *     Spanish: "Bastos"
	 *     French: "Bâtons"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#SPANISH
	 * @see es.iguanod.games.cards.DeckType#SPANISH_EXTENDED
	 */
	BATONS(new DeckType[]{
		SPANISH,
		SPANISH_EXTENDED
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Batons"),
		new Tuple2(new Locale("ES"), "Bastos"),
		new Tuple2(Locale.FRENCH, "Bâtons")
	}),
	/**
	 * German deck suit.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Bells"
	 *     Spanish: "Campanas"
	 *     French: "Cloche"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	BELLS(new DeckType[]{
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Bells"),
		new Tuple2(new Locale("ES"), "Campanas"),
		new Tuple2(Locale.FRENCH, "Cloche")
	}),
	/**
	 * German deck suit.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Leaves"
	 *     Spanish: "Hojas"
	 *     French: "Feuilles"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	LEAVES(new DeckType[]{
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Leaves"),
		new Tuple2(new Locale("ES"), "Hojas"),
		new Tuple2(Locale.FRENCH, "Feuilles")
	}),
	/**
	 * German deck suit.
	 * <p>
	 * Translations:<br/>
	 * <pre>
	 *     English: "Acorns"
	 *     Spanish: "Bellotas"
	 *     French: "Glands"
	 * </pre> </p>
	 *
	 * @see es.iguanod.games.cards.DeckType#GERMAN
	 */
	ACORNS(new DeckType[]{
		GERMAN
	}, new Tuple2[]{
		new Tuple2(Locale.ENGLISH, "Acorns"),
		new Tuple2(new Locale("ES"), "Bellotas"),
		new Tuple2(Locale.FRENCH, "Glands")
	});
	//
	//**********
	//
	/**
	 * List containing all the {@link java.util.Locale Locales} to whose
	 * languages the elements of this {@code Enum} can be translated.
	 */
	public static final List<Locale> supported_locale=Collections.unmodifiableList(Arrays.asList(new Locale[]{
		Locale.ENGLISH,
		new Locale("ES"),
		Locale.FRENCH
	}));
	/**
	 * List containing all the {@link es.iguanod.games.cards.DeckType
	 * DeckTypes} this {@code CardSuit} constant belongs to.
	 */
	public final Collection<DeckType> types;
	/**
	 * Map containing the translations of this {@code CardSuit} constant in
	 * the languages of all the {@link #supported_locale supported locales}.
	 */
	public final Map<Locale, String> translations;
	/**
	 * Mapping of all the translations to their associated {@code CardSuit}
	 * for reverse lookup in the fromString function.
	 */
	private static final Map<Locale, Map<String, CardSuit>> mappings;

	static{
		mappings=new HashMap<>();
		for(Locale locale:supported_locale){
			mappings.put(locale, new HashMap<String, CardSuit>());
		}
		for(CardSuit suit:values()){
			for(Map.Entry<Locale, String> translation:suit.translations.entrySet()){
				mappings.get(translation.getKey()).put(translation.getValue().toUpperCase(), suit);
			}
		}
	}

	/**
	 * Initializes the fields types and translations.
	 *
	 * @param types the DeckTypes the suit belongs to.
	 * @param translations the translations for the suit in the supported
	 * locales.
	 */
	private CardSuit(DeckType[] types, final Tuple2<Locale, String>[] translations){
		this.types=Collections.unmodifiableList(Arrays.asList(types));
		this.translations=Collections.unmodifiableMap(new HashMap(){
			private static final long serialVersionUID=165900193011800L;

			{
				for(Tuple2<Locale, String> trans:translations){
					put(trans.getFirst(), trans.getSecond());
				}
			}
		});
	}

	/**
	 * Returns the name of this {@code CardSuit} constant. The first letter is
	 * uppercase, and all the others are lowercase.
	 *
	 * @return the string representation of this {@code CardSuit}
	 */
	@Override
	public String toString(){
		return name().charAt(0) + name().substring(1).toLowerCase();
	}

	/**
	 * Returns the name of this {@code CardSuit} constant in the language
	 * specified by the passed {@link java.util.Locale Locale}. The first
	 * letter is uppercase, and all the others are lowercase.
	 *
	 * @param locale the {@code Locale} specifying the language to translate
	 * this {@code CardSuit}
	 *
	 * @return the string representation of this {@code CardSuit} in the
	 * specified {@code Locale}
	 *
	 * @throws IllegalArgumentException if the specified {@code Locale} is not
	 * supported
	 * @throws NullPointerException if {@code locale} is {@code null}
	 *
	 * @see es.iguanod.games.cards.CardSymbol#supported_locale
	 */
	public String toString(Locale locale) throws IllegalArgumentException{
		if(locale==null){
			throw new NullPointerException("Locale can't be null");
		}
		if(!supported_locale.contains(locale)){
			throw new IllegalArgumentException("Unsupported Locale");
		}

		return translations.get(locale);
	}

	/**
	 * Returns the {@code CardSuit} constant with the specified name (case
	 * insensitive). The {@code name} may be in any of the {@link
	 * #supported_locale supported locales}.
	 *
	 * @param name the name corresponding to the searched {@code CardSuit}.
	 * @param locale the {@code Locale} specifying the language of
	 * {@code name}
	 *
	 * @return the {@code CardSuit} corresponding with {@code name}.
	 *
	 * @throws IllegalArgumentException if this {@link java.lang.Enum Enum}
	 * does not have a constant with the specified name, or if {@code locale}
	 * is not supported
	 * @throws NullPointerException if {@code name} or {@code locale} is
	 * {@code null}
	 */
	public static Maybe<CardSuit> fromString(String name, Locale locale) throws IllegalArgumentException{
		if(name==null || locale==null){
			throw new NullPointerException("Null values not accepted");
		}
		if(!supported_locale.contains(locale)){
			throw new IllegalArgumentException("Locale not supported");
		}
		CardSuit ret=mappings.get(locale).get(name.toUpperCase());
		if(ret == null){
			return Maybe.ABSENT;
		}else{
			return Maybe.from(ret);
		}
	}
}
