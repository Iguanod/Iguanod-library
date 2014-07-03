package es.iguanod.games.cards;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.1.1.a
 * @version
 */
public class Card implements Comparable<Card>, Serializable{

	private static final long serialVersionUID=2411911287533715178L;
	//************
	@SuppressWarnings("serial") // Because is static and so transient
	private static final Map<Locale, String> translations=new HashMap<Locale, String>(){
		{
			put(Locale.ENGLISH, " of ");
			put(new Locale("ES"), " de ");
			put(Locale.FRENCH, " du ");
		}
	};
	//************
	public final CardSuit suit;
	public final CardSymbol symbol;

	public Card(DeckType type, CardSymbol symbol, CardSuit suit) throws NullPointerException, IllegalArgumentException{
		if(type == null || symbol == null || suit == null)
			throw new NullPointerException("The constructor doesn't accept null values");
		if(!symbol.types.contains(type) || !suit.types.contains(type))
			throw new IllegalArgumentException("The card symbol \"" + symbol + "\" or the card suit \"" + suit + "\" don't have the type \"" + type + "\" as one of their types.");
		this.symbol=symbol;
		this.suit=suit;
	}

	public Card(Card card){
		this.suit=card.suit;
		this.symbol=card.symbol;
	}

	@Override
	public int compareTo(Card card){

		int cmp;
		if((cmp=this.suit.compareTo(card.suit)) == 0){
			return this.symbol.compareTo(card.symbol);
		}else{
			return cmp;
		}
	}

	@Override
	public String toString(){
		return symbol + " of " + suit;
	}

	public String toString(Locale locale){
		return symbol.toString(locale) + (translations.containsKey(locale)?translations.get(locale):" of ") + suit.toString(locale);
	}

	@Override
	public boolean equals(Object obj){

		if(obj == null)
			return false;

		if(obj == this)
			return true;

		if(!(obj instanceof Card))
			return false;

		Card card=(Card)obj;
		return this.suit == card.suit
		&& this.symbol == card.symbol;
	}

	@Override
	public int hashCode(){
		int hash=5;
		hash=97 * hash + (this.suit != null?this.suit.hashCode():0);
		hash=97 * hash + (this.symbol != null?this.symbol.hashCode():0);
		return hash;
	}
}
