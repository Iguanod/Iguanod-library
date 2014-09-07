package es.iguanod.collect;

import es.iguanod.util.Maybe;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @param <T>
 *
 * @since
 * @version
 */
public class LinkedFixedCapacityQueue<T> extends AbstractCollection<T> implements FixedCapacityQueue<T>, Serializable{

	private static final long serialVersionUID=1249212336486513005L;
	//**********
	private int capacity;
	private LinkedList<T> queue=new LinkedList<>();

	public LinkedFixedCapacityQueue(int capacity){
		if(capacity<=0){
			throw new IllegalArgumentException("The capacity has to be possitive");
		}
		this.capacity=capacity;
	}

	@Override
	public Maybe<T> push(T elem){
		queue.addLast(elem);
		if(queue.size()>capacity){
			return Maybe.from(queue.removeFirst());
		}else{
			return Maybe.ABSENT;
		}
	}

	@Override
	public Maybe<T> pop(){
		if(queue.isEmpty()){
			return Maybe.ABSENT;
		}else{
			return Maybe.from(queue.removeFirst());
		}
	}

	@Override
	public Maybe<T> peek(){
		if(queue.isEmpty()){
			return Maybe.ABSENT;
		}else{
			return Maybe.from(queue.peekFirst());
		}
	}

	@Override
	public int capacity(){
		return capacity;
	}

	@Override
	public int size(){
		return queue.size();
	}
	
	@Override
	public boolean isFull(){
		return queue.size()==capacity;
	}

	@Override
	public Iterator<T> iterator(){
		return new Iterator<T>() {
			
			private Iterator<T> iter=queue.iterator();

			@Override
			public boolean hasNext(){
				return iter.hasNext();
			}

			@Override
			public T next(){
				return iter.next();
			}

			@Override
			public void remove(){
				iter.remove();
			}
		};
	}
	
	@Override
	public boolean add(T elem){
		queue.addLast(elem);
		if(queue.size()>capacity){
			queue.removeFirst();
		}
		return true;
	}

	/**
	 * 
	 * @param col
	 * @return 
	 */
	@Override
	public boolean addAll(Collection<? extends T> col){
		return super.addAll(col);
	}
	
	/**
	 * Left old
	 * @return 
	 */
	@Override
	public String toString(){
		return super.toString();
	}
}
