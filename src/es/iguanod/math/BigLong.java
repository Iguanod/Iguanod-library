/*
 * -------------------- DO NOT REMOVE OR MODIFY THIS HEADER --------------------
 * 
 * Copyright (C) 2014 The Iguanod Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * A copy of the License should have been provided along with this file, usually
 * under the name "LICENSE.txt". If that is not the case you may obtain a
 * copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.iguanod.math;

import es.iguanod.util.tuples.Tuple2;
import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class BigLong extends Number implements Comparable<BigLong>{

	private static final long serialVersionUID=5106436255847615324L;
	//**********
	private boolean nan;
	private boolean inf;
	private long mant1;
	private long mant2;
	//**********
	public static final BigLong POS_INF;
	public static final BigLong NEG_INF;
	public static final BigLong POS_NAN;
	public static final BigLong NEG_NAN;
	public static final BigLong ZERO;
	public static final BigLong ONE;
	public static final BigLong TWO;
	public static final BigLong FIVE;
	public static final BigLong TEN;
	public static final BigLong MAX_VALUE;
	public static final BigLong MIN_VALUE;
	//**********
	private static final BigInteger bi2=new BigInteger("2");
	private static final BigInteger bi_biglong_max=bi2.pow(126).subtract(BigInteger.ONE);
	private static final BigInteger bi_biglong_min=bi2.pow(126).negate();
	private static final BigInteger bi_mant2_max=bi2.pow(63);

	static{
		POS_INF=new BigLong("inf");
		NEG_INF=new BigLong("-inf");
		POS_NAN=new BigLong("nan");
		NEG_NAN=new BigLong("-nan");

		ZERO=new BigLong(0);
		ONE=new BigLong(1);
		TWO=new BigLong(2);
		FIVE=new BigLong(5);
		TEN=new BigLong(10);
		MAX_VALUE=new BigLong(false, false, 0x7FFFFFFFFFFFFFFFL, 0x7FFFFFFFFFFFFFFFL);
		MIN_VALUE=new BigLong(false, false, 0x8000000000000000L, 0x0L);
	}

	public BigLong(long n){

		fromLong(n);
	}

	private void fromLong(long n){

		inf=nan=false;
		mant2=n;
		if(mant2 < 0){
			mant2&=0x7FFFFFFFFFFFFFFFL;
			mant1=0xFFFFFFFFFFFFFFFFL;
		}else{
			mant1=0;
		}
	}

	public BigLong(String str) throws IllegalStateException{
		switch(str.toLowerCase()){
			case "nan":
				nan=true;
				inf=false;
				mant1=1;
				mant2=0;
				return;
			case "-nan":
				nan=true;
				inf=false;
				mant1=-1;
				mant2=0;
				return;
			case "inf":
			case "infinity":
			case "infinite":
				nan=false;
				inf=true;
				mant1=1;
				mant2=0;
				return;
			case "-inf":
			case "-infinity":
			case "-infinite":
				nan=false;
				inf=true;
				mant1=-1;
				mant2=0;
				return;
		}

		nan=inf=false;

		fromBigInteger(new BigInteger(str));
	}

	public BigLong(BigInteger n) throws IllegalStateException{

		fromBigInteger(n);
	}

	private void fromBigInteger(BigInteger bi) throws IllegalStateException{

		if(bi.compareTo(bi_biglong_max) > 0){
			throw new IllegalStateException("Number too big for a BigLong");
		}

		if(bi.compareTo(bi_biglong_min) < 0){
			throw new IllegalStateException("Number too small for a BigLong");
		}

		if(bi.signum() == 0){
			nan=inf=false;
			mant1=mant2=0;
		}else{
			boolean neg=(bi.signum() < 0);
			mant2=bi.longValue() & 0x7FFFFFFFFFFFFFFFL;
			bi=bi.divide(bi_mant2_max);
			mant1=bi.longValue();
			if(neg && mant1 == 0){
				mant1=0xFFFFFFFFFFFFFFFFL;
			}
		}
	}

	public BigLong(BigLong n){

		this.nan=n.nan;
		this.inf=n.inf;
		this.mant1=n.mant1;
		this.mant2=n.mant2;
	}

	public BigLong(Number n){

		this(n.longValue());
	}

	/**
	 * As with Random.nextLong(), since class Random uses a seed with only 48
	 * bits, this method will not return all possible BigLong values.
	 *
	 * @param rand
	 */
	public BigLong(Random rand){

		this(false, false, rand.nextLong(), rand.nextLong() & 0x7FFFFFFFFFFFFFFFL);
	}

	protected BigLong(boolean nan, boolean inf, long mant1, long mant2){

		this.nan=nan;
		this.inf=inf;
		this.mant1=mant1;
		this.mant2=mant2;
	}

	public boolean isPossitive(){
		return mant1 >= 0;
	}

	public int signum(){

		if(mant1 > 0){
			return 1;
		}else if(mant1 < 0){
			return -1;
		}else if(mant2 != 0){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public int compareTo(BigLong n){

		int sig1=signum();
		int sig2=n.signum();
		if(sig1 != sig2)
			return sig1 - sig2;

		if(nan){
			if(n.nan){
				return 0;
			}else{
				return isPossitive() ? 1 : -1;
			}
		}

		if(n.nan){
			return n.isPossitive() ? 1 : -1;
		}

		if(inf){
			if(n.inf){
				return 0;
			}else{
				return isPossitive() ? 1 : -1;
			}
		}

		if(n.inf){
			return isPossitive() ? 1 : -1;
		}

		int cmp=((Long)this.mant1).compareTo(n.mant1);
		if(cmp != 0){
			return cmp;
		}else{
			return ((Long)this.mant2).compareTo(n.mant2);
		}
	}

	/**
	 * Compares two BigLongs when both are possitive, non inf and non nan
	 *
	 * @param n
	 *
	 * @return
	 */
	private int fastCompareTo(BigLong n){

		int cmp=((Long)this.mant1).compareTo(n.mant1);
		if(cmp != 0){
			return cmp;
		}else{
			return ((Long)this.mant2).compareTo(n.mant2);
		}
	}

	public int numberOfLeadingZeros(){

		if(mant1 == 0){
			return 63 + Long.numberOfLeadingZeros(mant2);
		}else{
			return Long.numberOfLeadingZeros(mant1);
		}
	}

	public int numberOfTrailingZeros(){

		if(mant2 == 0){
			return 63 + Long.numberOfTrailingZeros(mant1);
		}else{
			return Long.numberOfTrailingZeros(mant2);
		}
	}

	public BigLongM toPossitive(){
		return new BigLongM(this).toPossitiveM();
	}

	public BigLongM negate(){
		return new BigLongM(this).negateM();
	}

	public BigLongM add(BigLong n){
		return new BigLongM(this).addM(n);
	}

	public BigLongM add(long n){
		return new BigLongM(n).addM(this);
	}

	protected BigLongM addM(long n){
		return addM(new BigLong(n));
	}

	public BigLongM sub(BigLong n){
		return new BigLongM(this).subM(n);
	}

	public BigLongM sub(long n){
		return new BigLongM(n).subM(this).negateM();
	}

	protected BigLongM subM(long n){
		return subM(new BigLong(n));
	}

	public BigLongM mul(BigLong n){
		return new BigLongM(this).mulM(n);
	}

	public BigLongM mul(long n){
		return new BigLongM(n).mulM(this);
	}

	protected BigLongM mulM(long n){
		return mulM(new BigLong(n));
	}

	public BigLongM div(BigLong n){
		return new BigLongM(this).divM(n);
	}

	public BigLongM div(long n){
		return new BigLongM(this).divM(new BigLong(n));
	}

	protected BigLongM divM(long n){
		return divM(new BigLong(n));
	}

	public Tuple2<BigLongM, BigLongM> divRem(BigLong n){
		return new BigLongM(this).divRemM(n);
	}

	public Tuple2<BigLongM, BigLongM> divRem(long n){
		return new BigLongM(this).divRemM(new BigLong(n));
	}

	protected Tuple2<BigLongM, BigLongM> divRemM(long n){
		return divRemM(new BigLong(n));
	}

	//public BigLongM mod(BigLong n) -> Has a real implementation
	//
	public BigLongM mod(long n){
		return new BigLongM(this).modM(new BigLong(n));
	}

	//protected BigLongM modM(long n) -> Has a real implementation
	//
	public BigLongM sqrt(){
		return new BigLongM(this).sqrtM();
	}

	public BigLongM shiftRight(int n){
		return new BigLongM(this).shiftRightM(n);
	}

	public BigLongM shiftRightExt(int n){
		return new BigLongM(this).shiftRightExtM(n);
	}

	public BigLongM shiftLeft(int n){
		return new BigLongM(this).shiftLeftM(n);
	}

	protected BigLongM toPossitiveM(){

		if(mant1 < 0){
			negateM();
		}
		return (BigLongM)this;
	}

	protected BigLongM negateM(){

		mant1=~mant1;
		mant2=(~mant2) & 0x7FFFFFFFFFFFFFFFL;
		mant2++;
		if(mant2 < 0){
			mant2&=0x7FFFFFFFFFFFFFFFL;
			mant1++;
		}
		return (BigLongM)this;
	}

	private BigLong pvtNegateM(){

		mant1=~mant1;
		mant2=(~mant2) & 0x7FFFFFFFFFFFFFFFL;
		mant2++;
		if(mant2 < 0){
			mant2&=0x7FFFFFFFFFFFFFFFL;
			mant1++;
		}
		return this;
	}

	protected BigLongM addM(BigLong n){

		if(nan){
			//Nothing to do
		}else if(n.nan){
			inf=false;
			nan=true;
		}else if(inf){
			if(n.inf && isPossitive() != n.isPossitive()){
				inf=false;
				nan=true;
			}else{
				inf=true;
			}
		}else if(n.inf){
			inf=true;
			mant1=n.mant1;
		}else{
			mant2+=n.mant2;
			mant1+=n.mant1;
			if(mant2 < 0){
				mant2&=0X7FFFFFFFFFFFFFFFL;
				mant1++;
			}
			}

		return (BigLongM)this;
	}

	protected BigLongM subM(BigLong n){

		if(nan){
			//Nothing to do
		}else if(n.nan){
			inf=false;
			nan=true;
		}else if(inf){
			if(n.inf && isPossitive() == n.isPossitive()){
				inf=false;
				nan=true;
			}
			//else Nothing to do
		}else if(n.inf){
			inf=true;
			mant1=~mant1;
		}else{
			mant2-=n.mant2;
			mant1-=n.mant1;
			if(mant2 < 0){
				mant2&=0X7FFFFFFFFFFFFFFFL;
				mant1--;
			}
		}

		return (BigLongM)this;
	}

	/**
	 * When both BigLongs are non inf non nan
	 *
	 * @param n
	 *
	 * @return
	 */
	private BigLong fastSubM(BigLong n){

		mant2-=n.mant2;
		mant1-=n.mant1;
		if(mant2 < 0){
			mant2&=0X7FFFFFFFFFFFFFFFL;
			mant1--;
		}

		return this;
	}

	protected BigLongM mulM(BigLong n){

		if(nan){
			//Nothing to do
		}else if(n.nan){
			nan=true;
			inf=false;
		}else if(inf){
			if(n.mant1 == 0 && n.mant2 == 0){
				nan=true;
				inf=false;
			}
			//else Nothing to do
		}else if(n.inf){
			if(mant1 == 0 && mant2 == 0){
				nan=true;
			}else{
				inf=true;
			}
		}else if(mant1 == 0 && mant2 == 0){
			//Nothing to do
		}else if(n.mant1 == 0 && n.mant2 == 0){
			mant1=0;
			mant2=0;
		}else{

			boolean negate=false;
			if(!isPossitive() && !n.isPossitive()){
				negateM();
				negate=true;
			}

			long op14=mant2 & 0x00000000FFFFFFFFL;
			long op13=(mant2 & 0xFFFFFFFF00000000L) >> 32;
			long op12=mant1 & 0x00000000FFFFFFFFL;
			long op11=(mant1 & 0xFFFFFFFF00000000L) >> 32;
			long op24=n.mant2 & 0x00000000FFFFFFFFL;
			long op23=(n.mant2 & 0xFFFFFFFF00000000L) >> 32;
			long op22=n.mant1 & 0x00000000FFFFFFFFL;
			long op21=(n.mant1 & 0xFFFFFFFF00000000L) >> 32;

			mant1=mant2=0;
			long res;

			//************
			mant2=op14 * op24;
			if(mant2 < 0){
				mant2&=0x7FFFFFFFFFFFFFFFL;
				mant1++;
			}

			//************
			res=op14 * op23;
			mant2+=(res & 0x7FFFFFFFL) << 32;
			if(mant2 < 0){
				mant2&=0x7FFFFFFFFFFFFFFFL;
				mant1++;
			}
			mant1+=(res & 0x7FFFFFFF80000000L) >>> 31;

			res=op13 * op24;
			mant2+=(res & 0x7FFFFFFFL) << 32;
			if(mant2 < 0){
				mant2&=0x7FFFFFFFFFFFFFFFL;
				mant1++;
			}
			mant1+=(res & 0x7FFFFFFF80000000L) >>> 31;

			//************
			mant1+=op14 * op22;

			mant1+=op12 * op24;

			mant1+=(op13 * op23) << 1;

			//************
			mant1+=((op14 * op21) & 0xFFFFFFFFL) << 32;

			mant1+=((op13 * op22) & 0xFFFFFFFFL) << 32;

			mant1+=((op12 * op23) & 0xFFFFFFFFL) << 32;

			mant1+=((op11 * op24) & 0xFFFFFFFFL) << 32;

			//************
			if(negate){
				negateM();
			}
		}

		return (BigLongM)this;
	}

	protected BigLongM divM(BigLong n){

		divRemM(n);

		return (BigLongM)this;
	}

	protected Tuple2<BigLongM, BigLongM> divRemM(BigLong n){

		if(nan){
			//Nothing to do
			return new Tuple2<>((BigLongM)this, BigLongM.NaN());
		}else if(n.nan){
			nan=true;
			inf=false;
			return new Tuple2<>((BigLongM)this, BigLongM.NaN());
		}else if(inf){
			if(n.inf){
				nan=true;
				inf=false;
			}
			//else Nothing to do
			return new Tuple2<>((BigLongM)this, BigLongM.NaN());
		}else if(n.inf){
			mant1=mant2=0;
			return new Tuple2<>((BigLongM)this, new BigLongM(ZERO));
		}else if(mant1 == 0 && mant2 == 0){
			if(n.mant1 == 0 && n.mant2 == 0){
				nan=true;
				return new Tuple2<>((BigLongM)this, BigLongM.NaN());
			}
			//else Nothing to do
			return new Tuple2<>((BigLongM)this, new BigLongM(ZERO));
		}else if(n.mant1 == 0 && n.mant2 == 0){
			inf=true;
			return new Tuple2<>((BigLongM)this, BigLongM.NaN());
		}else if(mant1 == n.mant1 && mant2 == n.mant2){
			mant1=0x0;
			mant2=0x1;
			return new Tuple2<>((BigLongM)this, new BigLongM(ZERO));
		}else{
			boolean result_negate;
			boolean n_negate;
			if(mant1 < 0){
				negateM();
				if(n.mant1 < 0){
					n.pvtNegateM();
					n_negate=true;
					result_negate=false;
				}else{
					n_negate=false;
					result_negate=true;
				}
			}else{
				if(n.mant1 < 0){
					n.pvtNegateM();
					n_negate=true;
					result_negate=true;
				}else{
					n_negate=false;
					result_negate=false;
				}
			}

			long m1=0;
			long m2=0;

			int cpy_lead=n.numberOfLeadingZeros();
			int lead=this.numberOfLeadingZeros();
			int res=cpy_lead - lead;
			BigLongM cpy=n.shiftLeft(res);
			cpy_lead=lead;

			while(this.fastCompareTo(n) >= 0){
				if(this.fastCompareTo(cpy) < 0){
					cpy.shiftRightExtM(1);
					cpy_lead++;
					res--;
				}
				this.fastSubM(cpy);
				if(res >= 63){
					m1|=(0x1L) << (res - 63);
				}else{
					m2|=(0x1L) << res;
				}
				lead=this.numberOfLeadingZeros();
				res-=lead - cpy_lead;
				cpy.shiftRightExtM(lead - cpy_lead);
				cpy_lead=lead;
			}

			BigLongM remainder=new BigLongM(this);

			mant1=m1;
			mant2=m2;

			if(n_negate){
				n.pvtNegateM();
			}
			if(result_negate){
				negateM();
			}
			if(result_negate != n_negate){
				remainder.negateM();
			}

			return new Tuple2<>((BigLongM)this, remainder);
		}
	}

	public BigLongM mod(BigLong n){

		return new BigLongM(this).divRemM(n).getSecond();
	}

	protected BigLongM modM(BigLong n){

		BigLong rem=this.divRemM(n).getSecond();

		nan=rem.nan;
		inf=rem.inf;
		mant1=rem.mant1;
		mant2=rem.mant2;

		return (BigLongM)this;
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
	protected BigLongM modM(long n){

		if(inf){
			inf=false;
			nan=true;
		}else if(nan || (mant1 == 0 && mant2 == 0)){
			//Nothing to do;
		}else if(n == 0){
			nan=true;
		}else{
			try{
				long x=this.longValue();
				this.subM((x / n) * n);
			}catch(IllegalStateException ise){
				this.modM(new BigLong(n));
			}
		}

		return (BigLongM)this;
	}

	protected BigLongM sqrtM(){

		if(mant1 < 0){
			nan=true;
			inf=false;
			mant1=1;
			mant2=0;
		}else if(nan || inf || (mant1 == 0 && mant2 == 0)){
			//Nothing to do
		}else{
			BigLongM this_cpy=new BigLongM(this);
			this.shiftRightM((127 - this.numberOfLeadingZeros()) / 2);
			BigLongM last=new BigLongM(this);
			BigLongM lastlast;
			int count=0;
			do{
				count++;
				lastlast=last;
				last=new BigLongM(this);
				BigLong res=this_cpy.div(this).addM(last).shiftRightM(1);
				mant1=res.mant1;
				mant2=res.mant2;
			}while(!this.equals(lastlast) && count < 7);
			return this.mul(this).compareTo(this_cpy) <= 0 ? (BigLongM)this : last;
		}

		return (BigLongM)this;
	}

	protected BigLongM shiftRightM(int n){

		if(n >= 127){
			mant1=mant2=0;
		}else if(n >= 63){
			mant2=(mant1 & 0x7FFFFFFFFFFFFFFFL);
			mant1>>>=63;
			shiftRightM(n - 63);
		}else{
			mant2>>>=n;
			mant2|=(mant1 & (0xFFFFFFFFFFFFFFFFL >>> (64 - n))) << (63 - n);
			mant1>>>=n;
		}
		return (BigLongM)this;
	}

	private BigLong pvtShiftRightM(int n){
		if(n >= 127){
			mant1=mant2=0;
		}else if(n >= 63){
			mant2=(mant1 & 0x7FFFFFFFFFFFFFFFL);
			mant1>>>=63;
			shiftRightM(n - 63);
		}else{
			mant2>>>=n;
			mant2|=(mant1 & (0xFFFFFFFFFFFFFFFFL >>> (64 - n))) << (63 - n);
			mant1>>>=n;
		}
		return this;
	}

	protected BigLongM shiftRightExtM(int n){

		if(n >= 127){
			if(isPossitive()){
				mant1=mant2=0;
			}else{
				mant1=0xFFFFFFFFFFFFFFFFL;
				mant2=0x7FFFFFFFFFFFFFFFL;
			}
		}else if(n >= 63){
			mant2=(mant1 & 0x7FFFFFFFFFFFFFFFL);
			mant1>>=63;
			shiftRightExtM(n - 63);
		}else{
			mant2>>>=n;
			mant2|=(mant1 & (0xFFFFFFFFFFFFFFFFL >>> (64 - n))) << (63 - n);
			mant1>>=n;
		}
		return (BigLongM)this;
	}

	protected BigLongM shiftLeftM(int n){

		if(n >= 127){
			mant1=mant2=0;
		}else if(n >= 63){
			mant1<<=63;
			mant1|=(mant2);
			mant2=0;
			shiftLeftM(n - 63);
		}else{
			mant1<<=n;
			mant1|=(mant2 >>> (63 - n));
			mant2=(mant2 << n) & 0x7FFFFFFFFFFFFFFFL;
		}
		return (BigLongM)this;
	}

	private BigLong pvtShiftLeftM(int n){

		if(n >= 127){
			mant1=mant2=0;
		}else if(n >= 63){
			mant1<<=63;
			mant1|=(mant2);
			mant2=0;
			shiftLeftM(n - 63);
		}else{
			mant1<<=n;
			mant1|=(mant2 >>> (63 - n));
			mant2=(mant2 << n) & 0x7FFFFFFFFFFFFFFFL;
		}
		return this;
	}

	/**
	 * NOTE: according to IEEE 754 (although it refers to floating point
	 * numbers and not integrals, but standard integral representation doesn't
	 * support Nan), NaN.equals(x) and x.equals(NaN) is false for every x;
	 * although NaN.compareTo(NaN)==0 and NaN1.hashCode()==NaN2.hashCode()
	 *
	 * @param obj
	 *
	 * @return
	 */
	@Override
	public boolean equals(Object obj){

		if(obj == null){
			return false;
		}

		if(!(obj instanceof BigLong)){
			return false;
		}

		if(nan || ((BigLong)obj).nan){
			return false;
		}

		if(obj == this){
			return true;
		}

		return this.compareTo((BigLong)obj) == 0;
	}

	@Override
	public int hashCode(){
		int hash=5;
		hash=97 * hash + (this.nan ? 1 : 0);
		hash=97 * hash + (this.inf ? 1 : 0);
		hash=97 * hash + (int)(this.mant1 ^ (this.mant1 >>> 32));
		hash=97 * hash + (int)(this.mant2 ^ (this.mant2 >>> 32));
		return hash;
	}

	/**
	 * Just as casting Long.MIN_VALUE to double doesn't return the same
	 * result, due to loss of precision, since BigDouble uses a bit for
	 * signedness and 126 bit mantisa, and BigLong uses a 127 bit
	 * two's-complement representation, BigLong.MIN_VALUE.bigDoubleValue()
	 * returns the same result as BigLong.MIN_VALUE.sub(1).bigDoubleValue().
	 *
	 * @return
	 */
	public BigDoubleM bigDoubleValue(){

		if(inf){
			return mant1 >= 0 ? BigDoubleM.infinity() : BigDoubleM.infinity().negateM();
		}else if(nan){
			return mant1 >= 0 ? BigDoubleM.NaN() : BigDoubleM.NaN().negateM();
		}else if(mant1 == 0 && mant2 == 0){
			return new BigDoubleM(BigDouble.ZERO);
		}else if(mant1 == 0x8000000000000000L && mant2 == 0x0L){
			return new BigDoubleM(false, false, false, (short)126, 0x7FFFFFFFFFFFFFFFL, 0x7FFFFFFFFFFFFFFFL);
		}else{
			boolean pos=(mant1 >= 0);
			if(!pos){
				pvtNegateM();
			}
			int lead=numberOfLeadingZeros();
			pvtShiftLeftM(lead - 1);
			BigDoubleM ret=new BigDoubleM(pos, false, false, (short)(127 - lead), mant1, mant2);
			pvtShiftRightM(lead - 1);
			if(!pos){
				pvtNegateM();
			}
			return ret;
		}
	}

	@Override
	public double doubleValue(){

		if(inf){
			if(mant1 >= 0){
				return Double.longBitsToDouble(0x7FF0000000000000L);
			}else{
				return Double.longBitsToDouble(0xFFF0000000000000L);
			}
		}else if(nan){
			return Double.longBitsToDouble(0x7FF8000000000000L);
		}else if(mant1 == 0 && mant2 == 0){
			return 0;
		}else if(mant1 == 0x8000000000000000L && mant2 == 0x0L){
			return Double.longBitsToDouble(0xC7CFFFFFFFFFFFFFL);
		}else{
			boolean pos=(mant1 >= 0);
			if(!pos){
				pvtNegateM();
			}
			int lead=numberOfLeadingZeros();
			pvtShiftLeftM(lead - 1);
			double ret=Double.longBitsToDouble((pos ? 0 : 0x8000000000000000L) | (((long)1149 - lead) << 52) | ((mant1 & 0x3FFFFFFFFFFFFFFFL) >> 10));
			pvtShiftRightM(lead - 1);
			if(!pos){
				pvtNegateM();
			}
			return ret;
		}
	}

	@Override
	public float floatValue(){

		if(inf){
			if(mant1 >= 0){
				return Float.intBitsToFloat(0x7F800000);
			}else{
				return Float.intBitsToFloat(0xFF800000);
			}
		}else if(nan){
			return Float.intBitsToFloat(0x7FC00000);
		}else if(mant1 == 0 && mant2 == 0){
			return 0;
		}else if(mant1 == 0x8000000000000000L && mant2 == 0x0L){
			return Float.intBitsToFloat(0xFE7FFFFF);
		}else{
			boolean pos=(mant1 >= 0);
			if(!pos){
				pvtNegateM();
			}
			int lead=numberOfLeadingZeros();
			pvtShiftLeftM(lead - 1);
			float ret=Float.intBitsToFloat((pos ? 0 : 0x80000000) | ((253 - lead) << 23) | (int)((mant1 & 0x3FFFFFFFFFFFFFFFL) >> 39));
			pvtShiftRightM(lead - 1);
			if(!pos){
				pvtNegateM();
			}
			return ret;
		}
	}

	@Override
	public long longValue() throws IllegalStateException{

		if(inf){
			throw new IllegalStateException("Long doesn't support Infinity");
		}else if(nan){
			throw new IllegalStateException("Long doesn't support NaN");
		}else{
			boolean pos=(mant1 >= 0);
			if(pos && mant1 > 1){
				throw new IllegalStateException("Number too big for a long");
			}else if(!pos && mant1 < -1){
				throw new IllegalStateException("Number too small for a long");
			}else if(pos){
				return mant2;
			}else{
				return mant2 | 0x8000000000000000L;
			}
		}
	}

	@Override
	public int intValue() throws IllegalStateException{

		if(inf){
			throw new IllegalStateException("Int doesn't support Infinity");
		}else if(nan){
			throw new IllegalStateException("Int doesn't support NaN");
		}else{
			boolean pos=(mant1 >= 0);
			if(pos && (mant1 != 0 || mant2 > 0x7FFFFFFF)){
				throw new IllegalStateException("Number too big for an int");
			}else if(!pos && (mant1 != 0xFFFFFFFFFFFFFFFFL || mant2 < 0x7FFFFFFF80000000L)){
				throw new IllegalStateException("Number too small for an int");
			}else if(pos){
				return (int)mant2;
			}else{
				return (int)(mant2 & 0xFFFFFFFF);
			}
		}
	}

	@Override
	public short shortValue() throws IllegalStateException{

		if(inf){
			throw new IllegalStateException("Short doesn't support Infinity");
		}else if(nan){
			throw new IllegalStateException("Short doesn't support NaN");
		}else{
			boolean pos=(mant1 >= 0);
			if(pos && (mant1 != 0 || mant2 > 0x7FFF)){
				throw new IllegalStateException("Number too big for a short");
			}else if(!pos && (mant1 != 0xFFFFFFFFFFFFFFFFL || mant2 < 0x7FFFFFFFFFFF8000L)){
				throw new IllegalStateException("Number too small for a short");
			}else if(pos){
				return (short)mant2;
			}else{
				return (short)(mant2 & 0xFFFF);
			}
		}
	}

	@Override
	public byte byteValue() throws IllegalStateException{

		if(inf){
			throw new IllegalStateException("Byte doesn't support Infinity");
		}else if(nan){
			throw new IllegalStateException("Byte doesn't support NaN");
		}else{
			boolean pos=(mant1 >= 0);
			if(pos && (mant1 != 0 || mant2 > 0x7F)){
				throw new IllegalStateException("Number too big for a byte");
			}else if(!pos && (mant1 != 0xFFFFFFFFFFFFFFFFL || mant2 < 0x7FFFFFFFFFFFFF80L)){
				throw new IllegalStateException("Number too small for a byte");
			}else if(pos){
				return (byte)mant2;
			}else{
				return (byte)(mant2 & 0xFF);
			}
		}
	}

	public BigInteger bigIntegerValue() throws IllegalStateException{

		if(nan){
			throw new IllegalStateException("BigInteger doesn't support NaN");
		}else if(inf){
			throw new IllegalStateException("BigInteger doesn't support Infinity");
		}

		if(mant1 == 0x8000000000000000L && mant2 == 0){
			return bi_biglong_min;
		}

		boolean pos=(mant1 >= 0);
		if(!pos){
			pvtNegateM();
		}

		BigInteger ret;

		if(mant1 != 0){
			ret=BigInteger.valueOf(mant1).shiftLeft(63).add(BigInteger.valueOf(mant2));
		}else{
			ret=BigInteger.valueOf(mant2);
		}

		if(!pos){
			ret=ret.negate();
			pvtNegateM();
		}

		return ret;
	}

	@Override
	public String toString(){

		if(nan){
			return mant1 >= 0 ? "NaN" : "-NaN";
		}else if(inf){
			return mant1 >= 0 ? "Inf" : "-Inf";
		}else{
			return bigIntegerValue().toString();
		}
	}
}
