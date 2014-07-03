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
 * @version 0.0.5.1.a
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
	 * Returns the name of this DeckType constant. The first letter is
	 * uppercase, and all the others are lowercase.
	 */
	@Override
	public String toString(){
		return name().charAt(0) + name().substring(1).toLowerCase();
	}
}
