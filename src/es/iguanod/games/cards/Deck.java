package es.iguanod.games.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.1.1.a
 * @version
 */
public class Deck implements Serializable{

	private static final long serialVersionUID=1514154583449501651L;
	//************
	private ArrayList<Card> cards=new ArrayList<>();

	public Deck(Collection<? extends Card> cards){
		if(cards == null){
			throw new NullPointerException("The collection can't be null");
		}
		this.cards.addAll(cards);
	}

	public Deck(Deck deck){
		this(deck.cards);
	}

	public Deck(DeckType type){

		if(type == null){
			throw new NullPointerException("The type can't be null");
		}

		for(CardSuit suit:CardSuit.values()){
			if(suit.types.contains(type)){
				for(CardSymbol symbol:CardSymbol.values()){
					if(symbol.types.contains(type)){
						cards.add(new Card(type, symbol, suit));
					}
				}
			}
		}
	}

	public Deck sort(){
		Collections.sort(cards);
		return this;
	}

	public Deck sort(final List<CardSymbol> symbols, final List<CardSuit> suits){
		Collections.sort(cards, new Comparator<Card>(){
			@Override
			public int compare(Card o1, Card o2){
				int cmp=suits.indexOf(o1.suit) - suits.indexOf(o2.suit);
				if(cmp != 0){
					return cmp;
				}else{
					return symbols.indexOf(o1.symbol) - symbols.indexOf(o2.symbol);
				}
			}
		});
		return this;
	}

	public Deck shuffle(){
		Collections.shuffle(cards);
		return this;
	}

	public Collection<Card> getCards(){
		return new ArrayList<>(cards);
	}
}
