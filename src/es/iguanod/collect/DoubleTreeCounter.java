package es.iguanod.collect;

import es.iguanod.collect.DoubleHashCounter.DoubleHashCounterBuilder;
import es.iguanod.util.Caster;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class DoubleTreeCounter<K> extends TreeCounter<K, Double>{

	private static final long serialVersionUID=39846006499716463L;

	public static class DoubleTreeCounterBuilder<K> extends TreeCounterBuilder<K, Double>{

		private static final long serialVersionUID=2364064954622L;

		public DoubleTreeCounterBuilder(){
			super(new Caster<BigDecimal, Double>(){
				private static final long serialVersionUID=879861688360612623L;

				@Override
				public Double cast(BigDecimal t){
					return t.doubleValue();
				}
			});
			super.setLookupBuilder(new DoubleHashCounterBuilder<K>());
		}

		@Override
		public DoubleTreeCounterBuilder<K> reverse(boolean reverse){
			super.reverse(reverse);
			return this;
		}

		@Override
		public DoubleTreeCounterBuilder<K> setComparator(Comparator<? super Double> comparator){
			super.setComparator(comparator);
			return this;
		}

		/**
		 * The <i>? extends Double</i> is pointless as Integer is final, but
		 * is needed to be considered an actual override
		 *
		 * @param map
		 *
		 * @return
		 */
		@Override
		public DoubleTreeCounterBuilder<K> setInitialMappings(Map<? extends K, ? extends Double> map){
			super.setInitialMappings(map);
			return this;
		}

		@Override
		public DoubleTreeCounter<K> build(){
			return new DoubleTreeCounter<>(this);
		}
	}
	public static final Caster<Number, Double> NUM_CASTER=new Caster<Number, Double>(){
		private static final long serialVersionUID=164912068267529453L;

		@Override
		public Double cast(Number t){
			return t.doubleValue();
		}
	};

	protected DoubleTreeCounter(DoubleTreeCounterBuilder<K> builder){
		super(builder);
	}

	public DoubleTreeCounter(){
		this(new DoubleTreeCounterBuilder<K>());
	}

	/**
	 * Adds {@code 1.0/keys.}{@link java.util.Collection#size() size()} to the
	 * current value associated with every key in the specified. If the key is
	 * not already present in this {@code Counter}, this method behaves as if
	 * it was present with an associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(Collection<? extends K> keys){
		sumAll(keys, 1.0 / keys.size());
	}

	/**
	 * Adds {@code 1.0/keys.length} to the current value associated with every
	 * key in the specified. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(K[] keys){
		sumAll(keys, 1.0 / keys.length);
	}

	/**
	 * Adds {@code value/keys.}{@link java.util.Collection#size() size()} to
	 * the current value associated with every key in the specified
	 * {@code Collection}. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(Collection<? extends K> keys, Double value){
		sumAll(keys, value / keys.size());
	}

	/**
	 * Adds {@code value/keys.length} to the current value associated with
	 * every key in the specified array. If the key is not already present in
	 * this {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 */
	@Override
	public void balancedSum(K[] keys, Double value){
		sumAll(keys, value / keys.length);
	}

	/**
	 * Adds {@code 1.0/this.}{@link #size() size()}) to the current value
	 * associated with every key in this {@code Counter}.
	 *
	 * @see #sum(Object,Number)
	 * @see #sumToAll(Number)
	 */
	@Override
	public void balancedSumToAll(){
		sumToAll(1.0 / this.size());
	}

	/**
	 * Adds {@code value/this.}{@link #size() size()}), to the current value
	 * associated with every key in this {@code Counter}.
	 *
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #sum(Object,Number)
	 * @see #sumToAll(Number)
	 */
	@Override
	public void balancedSumToAll(Double value){
		sumToAll(value / this.size());
	}

	/**
	 * Deducts {@code 1.0/keys.}{@link java.util.Collection#size() size()} to
	 * the current value associated with every key in the specified. If the
	 * key is not already present in this {@code Counter}, this method behaves
	 * as if it was present with an associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(Collection<? extends K> keys){
		deductAll(keys, 1.0 / keys.size());
	}

	/**
	 * Deducts {@code 1.0/keys.length} to the current value associated with
	 * every key in the specified. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(K[] keys){
		deductAll(keys, 1.0 / keys.length);
	}

	/**
	 * Deducts {@code value/keys.}{@link java.util.Collection#size() size()}
	 * to the current value associated with every key in the specified
	 * {@code Collection}. If the key is not already present in this
	 * {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(Collection<? extends K> keys, Double value){
		deductAll(keys, value / keys.size());
	}

	/**
	 * Deducts {@code value/keys.length} to the current value associated with
	 * every key in the specified array. If the key is not already present in
	 * this {@code Counter}, this method behaves as if it was present with an
	 * associated value of {@code 0} (zero).
	 *
	 * @param keys {@inheritDoc}
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 */
	@Override
	public void balancedDeduct(K[] keys, Double value){
		deductAll(keys, value / keys.length);
	}

	/**
	 * Deducts {@code 1/this.}{@link #size() size()}) to the current value
	 * associated with every key in this {@code Counter}.
	 *
	 * @see #deduct(Object,Number)
	 * @see #deductToAll(Number)
	 */
	@Override
	public void balancedDeductToAll(){
		deductToAll(1.0 / this.size());
	}

	/**
	 * Deducts {@code value/this.}{@link #size() size()}), to the current
	 * value associated with every key in this {@code Counter}.
	 *
	 * @param value {@inheritDoc}
	 *
	 * @throws NullPointerException {@inheritDoc}
	 *
	 * @see #deduct(Object,Number)
	 * @see #deductToAll(Number)
	 */
	@Override
	public void balancedDeductToAll(Double value){
		deductToAll(value / this.size());
	}
}