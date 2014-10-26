package es.iguanod.collect;

import es.iguanod.util.Caster;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Set;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since
 * @version
 */
public abstract class AbstractSortedCounter<K, V extends Number> extends AbstractCounter<K, V> implements SortedCounter<K, V>{

	private static final long serialVersionUID=534613464139168129L;

	public AbstractSortedCounter(Caster<BigDecimal, V> caster){
		super(caster, null);
	}

	public AbstractSortedCounter(Caster<BigDecimal, V> caster, Comparator<? super V> comparator){
		super(caster, comparator);
	}

	@Override
	public Set<K> maxKeySet(){
		return keySet(maxValue());
	}

	@Override
	public Set<K> minKeySet(){
		return keySet(minValue());
	}

	@Override
	public SortedCounter<K, V> tailCounter(V from_value){
		return tailCounter(from_value, true);
	}

	@Override
	public SortedCounter<K, V> headCounter(V to_value){
		return headCounter(to_value, false);
	}

	@Override
	public SortedCounter<K, V> subCounter(V from_value, V to_value){
		return subCounter(from_value, true, to_value, false);
	}

	@Override
	public String toString(){
		String str=entrySet().toString();
		return "{" + str.substring(1, str.length() - 1) + "}";
	}
}
