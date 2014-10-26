package es.iguanod.collect;

import es.iguanod.collect.IntHashCounter.IntHashCounterBuilder;
import es.iguanod.util.Caster;
import es.iguanod.util.tuples.Tuple2;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class IntTreeCounter<K> extends TreeCounter<K, Integer>{

	private static final long serialVersionUID=74987659825035409L;

	public static class IntTreeCounterBuilder<K> extends TreeCounterBuilder<K, Integer>{

		private static final long serialVersionUID=440930751035796130L;

		public IntTreeCounterBuilder(){
			super(new Caster<BigDecimal, Integer>(){
				private static final long serialVersionUID=364591019071281915L;

				@Override
				public Integer cast(BigDecimal t){
					return t.intValue();
				}
			});
			super.setLookupBuilder(new IntHashCounterBuilder<K>());
		}

		@Override
		public IntTreeCounterBuilder<K> reverse(boolean reverse){
			super.reverse(reverse);
			return this;
		}

		@Override
		public IntTreeCounterBuilder<K> setComparator(Comparator<? super Integer> comparator){
			super.setComparator(comparator);
			return this;
		}

		/**
		 * The ? extends Integer is pointless as Integer is final, but is
		 * needed to be considered an actual override
		 *
		 * @param map
		 *
		 * @return
		 */
		@Override
		public IntTreeCounterBuilder<K> setInitialMappings(Map<? extends K, ? extends Integer> map){
			super.setInitialMappings(map);
			return this;
		}

		@Override
		public IntTreeCounter<K> build(){
			return new IntTreeCounter<>(this);
		}
	}
	public static final Caster<Number, Integer> NUM_CASTER=new Caster<Number, Integer>(){
		private static final long serialVersionUID=164912068267529453L;

		@Override
		public Integer cast(Number t){
			return t.intValue();
		}
	};

	protected IntTreeCounter(IntTreeCounterBuilder<K> builder){
		super(builder);
	}

	public IntTreeCounter(){
		this(new IntTreeCounterBuilder<K>());
	}

	/**
	 * Adds {@code 1/keys.}{@link java.util.Collection#size() size()} to the
	 * current value associated with every key in the specified. If the key is
	 * not already present in this {@code Counter}, this method behaves as if
	 * it was present with an associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the collection is one, the amount summed to the
	 * elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(Collection<? extends K> keys){
		sumAll(keys, 1 / keys.size());
	}

	/**
	 * Adds {@code 1/keys.length} to the current value associated with every
	 * key in the specified. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the array is one, the amount summed to the elements
	 * will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(K[] keys){
		sumAll(keys, 1 / keys.length);
	}

	/**
	 * Adds {@code value/keys.}{@link java.util.Collection#size() size()} to
	 * the current value associated with every key in the specified
	 * {@code Collection}. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the collection is greater or equal than
	 * {@code value}, the amount summed to the elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(Collection<? extends K> keys, Integer value){
		sumAll(keys, value / keys.size());
	}

	/**
	 * Adds {@code value/keys.length} to the current value associated with
	 * every key in the specified array. If the key is not already present in
	 * this {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the array greater or equal than {@code value}, the
	 * amount summed to the elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(K[] keys, Integer value){
		sumAll(keys, value / keys.length);
	}

	/**
	 * Adds {@code 1/this.}{@link #size() size()}) to the current value
	 * associated with every key in this {@code Counter}.
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of this {@code Counter} is one, the amount summed to
	 * the elements will be zero.</p>
	 *
	 * @see #sum(Object,Number)
	 * @see #sumToAll(Number)
	 */
	@Override
	public void balancedSumToAll(){
		if(this.size() == 1){
			sumToAll(1);
		}
	}

	/**
	 * Adds {@code value/this.}{@link #size() size()}), to the current value
	 * associated with every key in this {@code Counter}.
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of this {@code Counter} is greater or equal than
	 * {@code value}, the amount summed to the elements will be zero.</p>
	 *
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 * @see #sumToAll(Number)
	 */
	@Override
	public void balancedSumToAll(Integer value){
		int size=this.size();
		if(value >= size){
			sumToAll(value / size);
		}
	}

	/**
	 * Deducts {@code 1/keys.}{@link java.util.Collection#size() size()} to
	 * the current value associated with every key in the specified. If the
	 * key is not already present in this {@code Counter}, this method behaves
	 * as if it was present with an associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the collection is one, the amount deducted to the
	 * elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(Collection<? extends K> keys){
		deductAll(keys, 1 / keys.size());
	}

	/**
	 * Deducts {@code 1/keys.length} to the current value associated with
	 * every key in the specified. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the array is one, the amount deducted to the
	 * elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(K[] keys){
		deductAll(keys, 1 / keys.length);
	}

	/**
	 * Deducts {@code value/keys.}{@link java.util.Collection#size() size()}
	 * to the current value associated with every key in the specified
	 * {@code Collection}. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the collection is greater or equal than
	 * {@code value}, the amount deducted to the elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(Collection<? extends K> keys, Integer value){
		deductAll(keys, value / keys.size());
	}

	/**
	 * Deducts {@code value/keys.length} to the current value associated with
	 * every key in the specified array. If the key is not already present in
	 * this {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of the array greater or equal than {@code value}, the
	 * amount deducted to the elements will be zero.</p>
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(K[] keys, Integer value){
		deductAll(keys, value / keys.length);
	}

	/**
	 * Deducts {@code 1/this.}{@link #size() size()}) to the current value
	 * associated with every key in this {@code Counter}.
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of this {@code Counter} is one, the amount deducted to
	 * the elements will be zero.</p>
	 *
	 * @see #deduct(Object,Number)
	 * @see #deductToAll(Number)
	 */
	@Override
	public void balancedDeductToAll(){
		if(this.size() == 1){
			deductToAll(1);
		}
	}

	/**
	 * Deducts {@code value/this.}{@link #size() size()}), to the current
	 * value associated with every key in this {@code Counter}.
	 *
	 * <p><b>Note</b>: since this {@code Counter} works with {@code Integers},
	 * unless the size of this {@code Counter} is greater or equal than
	 * {@code value}, the amount deducted to the elements will be zero.</p>
	 *
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 * @see #deductToAll(Number)
	 */
	@Override
	public void balancedDeductToAll(Integer value){
		int size=this.size();
		if(value >= size){
			deductToAll(value / size);
		}
	}
}