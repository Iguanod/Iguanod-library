package es.iguanod.collect;

import es.iguanod.util.Maybe;
import java.util.Collection;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @param <T>
 * @since 
 * @version 
 */
public interface FixedSizeQueue<T> extends Collection<T>{

	public Maybe<T> push(T elem);
	
	public Maybe<T> pop();
	
	public Maybe<T> peek();
	
	public int capacity();
}
