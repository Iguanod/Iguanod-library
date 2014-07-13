package es.iguanod.games.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.1.1.a
 * @version
 */
public class Deck extends ArrayList<Card>{

	private static final long serialVersionUID=1514154583449501651L;
	//************

	public Deck(Collection<? extends Card> cards){
		super(cards);
	}

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

	public Deck sort(final List<CardSymbol> symbols, final List<CardSuit> suits){
		Collections.sort(this, new Comparator<Card>(){
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
}
