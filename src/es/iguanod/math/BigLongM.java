package es.iguanod.math;

import es.iguanod.util.tuples.Tuple2;
import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.8.1.a
 * @version
 */
public class BigLongM extends BigLong{

	private static final long serialVersionUID=2569147417992977247L;

	public BigLongM(long n){
		super(n);
	}

	public BigLongM(String str){
		super(str);
	}

	public BigLongM(BigInteger n){
		super(n);
	}

	public BigLongM(BigLong n){
		super(n);
	}

	public BigLongM(Number n){
		super(n);
	}

	/**
	 * As with Random.nextLong(), since class Random uses a seed with only 48
	 * bits, this method will not return all possible BigLong values.
	 *
	 * @param rand
	 */
	public BigLongM(Random rand){

		this(false, false, rand.nextLong(), rand.nextLong() & 0x7FFFFFFFFFFFFFFFL);
	}

	protected BigLongM(boolean nan, boolean inf, long mant1, long mant2){
		super(nan, inf, mant1, mant2);
	}

	public static BigLongM maxValue(){
		return new BigLongM(MAX_VALUE);
	}

	public static BigLongM minValue(){
		return new BigLongM(MIN_VALUE);
	}

	public static BigLongM infinity(){
		return new BigLongM(POS_INF);
	}

	public static BigLongM NaN(){
		return new BigLongM(POS_NAN);
	}

	@Override
	public BigLongM addM(long n){
		return addM(new BigLong(n));
	}

	@Override
	public BigLongM subM(long n){
		return subM(new BigLong(n));
	}

	@Override
	public BigLongM mulM(long n){
		return mulM(new BigLong(n));
	}

	@Override
	public BigLongM divM(long n){
		return divM(new BigLong(n));
	}

	@Override
	public Tuple2<BigLongM, BigLongM> divRemM(long n){
		return divRemM(new BigLong(n));
	}

	@Override
	public BigLongM toPossitiveM(){
		return super.toPossitiveM();
	}

	@Override
	public BigLongM negateM(){
		return super.negateM();
	}

	@Override
	public BigLongM addM(BigLong n){
		return super.addM(n);
	}

	@Override
	public BigLongM subM(BigLong n){
		return super.subM(n);
	}

	@Override
	public BigLongM mulM(BigLong n){
		return super.mulM(n);
	}

	@Override
	public BigLongM divM(BigLong n){
		return super.divM(n);
	}

	@Override
	public Tuple2<BigLongM, BigLongM> divRemM(BigLong n){
		return super.divRemM(n);
	}

	@Override
	public BigLongM modM(BigLong n){
		return super.modM(n);
	}

	/**
	 * NOTE: preferred over modM(BigLong) when Long.MIN_VALUE &le; this &le;
	 * Long.MAX_VALUE. In case of doubt it is recommended to use this method
	 * since if used when this &gt; Long.MAX_VALUE or this &lt; Long.MIN_VALUE
	 * it is 0.5 times slower than modM(BigLong) but if Long.MIN_VALUE &le;
	 * this &lt; Long.MAX_VALUE this method is 40 times faster than
	 * modM(BigLong).
	 *
	 *
	 * --------------------------TESTEAR LA RAPIDEZ-----------------------
	 *
	 *
	 *
	 * @param n
	 *
	 * @return
	 */
	@Override
	public BigLongM modM(long n){
		return super.modM(n);
	}

	@Override
	public BigLongM shiftRightM(int n){
		return super.shiftRightM(n);
	}

	@Override
	public BigLongM shiftRightExtM(int n){
		return super.shiftRightExtM(n);
	}

	@Override
	public BigLongM shiftLeftM(int n){
		return super.shiftLeftM(n);
	}
}
