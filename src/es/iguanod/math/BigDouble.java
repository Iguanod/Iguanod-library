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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

/**
 * WARNING: always use functions that accept long instead of double if the
 * argument is an integral number
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.8.1.a
 * @version
 */
public class BigDouble extends Number implements Comparable<BigDouble>{

	/**
	 * The representation isn't actually IEEE-754, although behaves as if it
	 * were. Three booleans are used to indicate if the number is possitive,
	 * if it's NaN or if it's Infinity. The exponent is 16 bits and has no
	 * offset. Two longs are used for the mantisa, and they never have zero
	 * bits at the left (except in the case of the number being zero), but the
	 * first one (ommited in IEEE format) is explicitly present.
	 */
	//
	//************
	//
	private static final long serialVersionUID=1343316263024578181L;
	//
	//************
	//
	/**
	 * Wether the number is possitive. True in case of zero, although it
	 * shouldn't matter.
	 */
	private boolean pos;
	/**
	 * Wether the number is not-a-number. Cannot be both NaN and Infinity.
	 */
	private boolean nan;
	/**
	 * Wether the number is infinity. Cannot be both NaN and Infinity.
	 */
	private boolean inf;
	/**
	 * Exponent of the number.
	 */
	private short exp;
	/**
	 * Most significant bits of the mantisa.
	 */
	private long mant1;
	/**
	 * Least significant bits of the mantisa.
	 */
	private long mant2;
	//
	//************
	//
	/**
	 * The value <i>pi</i>.
	 */
	public static final BigDouble PI;
	/**
	 * The value <i>e</i>.
	 */
	public static final BigDouble E;
	/**
	 * BigDouble representation of possitive infinity.
	 */
	public static final BigDouble POS_INF;
	/**
	 * BigDouble representation of negative infinity.
	 */
	public static final BigDouble NEG_INF;
	/**
	 * BigDouble representation of possitive NaN (not-a-number).
	 */
	public static final BigDouble POS_NAN;
	/**
	 * BigDouble representation of negative NaN (not-a-number).
	 */
	public static final BigDouble NEG_NAN;
	/**
	 * The value 0 (zero).
	 */
	public static final BigDouble ZERO;
	/**
	 * The value 1 (one).
	 */
	public static final BigDouble ONE;
	/**
	 * The value 2 (two).
	 */
	public static final BigDouble TWO;
	/**
	 * The value 5 (five).
	 */
	public static final BigDouble FIVE;
	/**
	 * The value 10 (ten).
	 */
	public static final BigDouble TEN;
	/**
	 * Maximum value for a {@code BigDouble}.
	 */
	public static final BigDouble MAX_VALUE;
	/**
	 * Minimum value for a {@code BigDouble}.
	 */
	public static final BigDouble MIN_VALUE;
	public static final BigDouble POS_SMALLEST_VALUE;
	public static final BigDouble NEG_SMALLEST_VALUE;

	/**
	 * Maximum value for the exponent of a BigDouble.
	 */
	public static final short MAX_EXPONENT=Short.MAX_VALUE;
	/**
	 * Minimum value for the exponent of a BigDouble.
	 */
	public static final short MIN_EXPONENT=Short.MIN_VALUE;
	//
	//************
	//
	/**
	 * Pi/2.
	 */
	private static final BigDouble PI_2;
	/**
	 * 3Pi/2.
	 */
	private static final BigDouble PI3_2;
	/**
	 * -Pi.
	 */
	private static final BigDouble PI_neg;
	/**
	 * -Pi/2.
	 */
	private static final BigDouble PI_2_neg;
	/**
	 * -3Pi/2.
	 */
	private static final BigDouble PI3_2_neg;
	/**
	 * 1/2^n for n from 1 to 252.
	 */
	private static final BigDecimal[] pows_of_2_bd=new BigDecimal[252];
	/**
	 * BigDecimal representing the value 2.
	 */
	private static final BigDecimal bd2=new BigDecimal(2);
	/**
	 * BigDecimal representing the value 1/2.
	 */
	private static final BigDecimal bd0_5=BigDecimal.ONE.divide(bd2);
	/**
	 * Constant for the division algorithm.
	 */
	private static final BigDouble div_const_1;
	/**
	 * Constant for the division algorithm.
	 */
	private static final BigDouble div_const_2;

	static{
		BigDecimal bd=BigDecimal.ONE;
		for(int i=0; i < 252; i++){
			bd=bd.divide(bd2);
			pows_of_2_bd[i]=bd;
		}

		div_const_1=new BigDouble("2.82352941176470588235294117647058823529");
		div_const_2=new BigDouble("1.882352941176470588235294117647058823529");

		PI=new BigDouble("3.14159265358979323846264338327950288419");
		E=new BigDouble("2.71828182845904523536028747135266249775");
		PI_2=PI.shiftRight(1);
		PI3_2=PI_2.mul(3);
		PI_neg=PI.negate();
		PI_2_neg=PI_2.negate();
		PI3_2_neg=PI3_2.negate();

		POS_INF=new BigDouble("inf");
		NEG_INF=new BigDouble("-inf");
		POS_NAN=new BigDouble("nan");
		NEG_NAN=new BigDouble("-nan");

		ZERO=new BigDouble(0);
		ONE=new BigDouble(1);
		TWO=new BigDouble(2);
		FIVE=new BigDouble(5);
		TEN=new BigDouble(10);
		MAX_VALUE=new BigDouble(true, false, false, MAX_EXPONENT, 0x7FFFFFFFFFFFFFFFL, 0x7FFFFFFFFFFFFFFFL);
		MIN_VALUE=new BigDouble(false, false, false, MAX_EXPONENT, 0x7FFFFFFFFFFFFFFFL, 0x7FFFFFFFFFFFFFFFL);
		POS_SMALLEST_VALUE=new BigDouble(true, false, false, MIN_EXPONENT, 0x4000000000000000L, 0x4000000000000000L);
		NEG_SMALLEST_VALUE=new BigDouble(false, false, false, MIN_EXPONENT, 0x4000000000000000L, 0x4000000000000000L);
	}

	/**
	 * WARNING: BigDouble follows IEEE 754, so the smallest possitive non-zero
	 * accepted value is Double.MIN_NORMAL, not Double.MIN_VALUE
	 *
	 * @param n
	 */
	public BigDouble(double n){

		fromDouble(n);
	}

	private void fromDouble(double n){

		if(Double.isNaN(n)){
			pos=true;
			nan=true;
			inf=false;
			exp=0;
			mant1=mant2=0;
		}else{
			pos=(n >= 0);
			if(!pos)
				n=-n;
			if(Double.isInfinite(n)){
				nan=false;
				inf=true;
				exp=0;
				mant1=mant2=0;
			}else{
				nan=false;
				inf=false;
				mant2=0;
				long bits=Double.doubleToRawLongBits(n);
				exp=(short)(((bits >> 52) & 0x7FFL) - 1022);
				if(exp == -959){
					exp=1;
					mant1=0;
				}else{
					mant1=bits & 0x000FFFFFFFFFFFFFL;
					mant1|=0x0010000000000000L;
					mant1<<=10;
				}
			}
		}
	}

	public BigDouble(long n){

		pos=(n >= 0);
		if(!pos)
			n=-n;
		nan=false;
		inf=false;
		mant2=0;
		mant1=n;
		exp=63;
		shiftLeftMant();
	}

	public BigDouble(String str){
		switch(str.toLowerCase()){
			case "nan":
			case "+nan":
				pos=true;
				exp=0;
				nan=true;
				inf=false;
				mant1=mant2=0;
				return;
			case "-nan":
				pos=false;
				exp=0;
				nan=true;
				inf=false;
				mant1=mant2=0;
				return;
			case "inf":
			case "+inf":
			case "infinity":
			case "+infinty":
				pos=true;
				exp=0;
				nan=false;
				inf=true;
				mant1=mant2=0;
				return;
			case "-inf":
			case "-infinity":
				pos=false;
				exp=0;
				nan=false;
				inf=true;
				mant1=mant2=0;
				return;
		}

		nan=inf=false;

		fromBigDecimal(new BigDecimal(str));
	}

	public BigDouble(BigInteger n){

		fromBigDecimal(new BigDecimal(n));
	}

	public BigDouble(BigDecimal n){

		fromBigDecimal(n);
	}

	public BigDouble(Number n){

		this(n.doubleValue());
	}

	private void fromBigDecimal(BigDecimal bd){

		if(bd.compareTo(BigDecimal.ZERO) == 0){
			exp=1;
			mant1=mant2=0;
			return;
		}

		exp=0;
		pos=bd.signum() > 0;
		if(!pos)
			bd=bd.negate();

		BigInteger bi=bd.toBigInteger();
		BigInteger two=new BigInteger("2");
		int count=0;
		int desp_count=0;
		boolean init=false;
		while(bi.compareTo(BigInteger.ZERO) > 0 && count < 126){
			if(bi.mod(two).compareTo(BigInteger.ONE) == 0){
				mant1|=0x8000000000000000L;
				init=true;
			}
			if(init){
				shiftRightMant(1);
				count++;
			}else{
				exp++;
			}
			desp_count++;
			bi=bi.divide(two);
		}

		if(count == 126)
			return;

		bd=bd.subtract(new BigDecimal(bd.toBigInteger()));

		int pow_index=0;
		desp_count++;
		BigDecimal last_pow=pows_of_2_bd[251];
		while(bd.compareTo(BigDecimal.ZERO) > 0 && desp_count < 126){
			if(pow_index < 252?(bd.compareTo(pows_of_2_bd[pow_index]) >= 0):(last_pow=last_pow.divide(bd2)) != null && bd.compareTo(last_pow) >= 0){
				if(desp_count < 64){
					mant1|=0x8000000000000000L >>> desp_count;
					init=true;
				}else{
					mant2|=0x8000000000000000L >>> (desp_count - 63);
					init=true;
				}
				bd=bd.subtract(pow_index < 252?pows_of_2_bd[pow_index]:last_pow);
			}
			if(init){
				desp_count++;
			}else{
				exp--;
			}
			count++;
			pow_index++;
		}
	}

	public BigDouble(BigDouble n){
		fromBigDouble(n);
	}

	private void fromBigDouble(BigDouble n){
		this.pos=n.pos;
		this.nan=n.nan;
		this.inf=n.inf;
		this.exp=n.exp;
		this.mant1=n.mant1;
		this.mant2=n.mant2;
	}

	/**
	 * Given the implementation of Random, this method cannot return all
	 * possible BigDouble values
	 *
	 * @param rand
	 */
	public BigDouble(Random rand){

		this(rand.nextBoolean(), false, false, (short)rand.nextInt(), rand.nextLong() & 0x7FFFFFFFFFFFFFFFL, rand.nextLong() & 0x7FFFFFFFFFFFFFFFL);
	}

	protected BigDouble(boolean pos, boolean nan, boolean inf, short exp, long mant1, long mant2){

		this.pos=pos;
		this.nan=nan;
		this.inf=inf;
		this.exp=exp;
		this.mant1=mant1;
		this.mant2=mant2;
		shiftLeftMant();
	}

	private void shiftRightMant(int n){

		exp+=n;

		if(n == 0){
			//Nothing to do
		}else if(n >= 126){
			mant1=mant2=0;
		}else if(n > 63){
			mant2=mant1 >>> (n - 63);
			mant1=0;
		}else{
			mant2>>=n;
			long mask=0xFFFFFFFFFFFFFFFFL >>> (64 - n);
			mant2|=((mant1 & mask) << (63 - n));
			mant1>>>=n;
		}
	}

	private void shiftLeftMant(){

		// TODO: why is it slower with numberOfLeadingZeros?
		if(mant1 == 0){
			if(mant2 == 0){
				return;
			}
			exp-=63;
			mant1=mant2;
			mant2=0;
			shiftLeftMant();
			return;
		}

		int count=0;
		long mask=0x4000000000000000L;
		while((mant1 & mask) == 0 && count < 63){
			count++;
			mask>>=1;
		}
		if(count == 0)
			return;
		mask=0x8000000000000000L >> count;
		mant1<<=count;
		mant1|=((mask & mant2) >>> (63 - count));
		mant2<<=count;
		mant2&=0x7FFFFFFFFFFFFFFFL;
		exp-=count;
	}

	public boolean isInfinity(){
		return inf;
	}

	public boolean isNaN(){
		return nan;
	}

	public boolean isPossitive(){
		return pos || (mant1 == 0 && mant2 == 0);
	}

	public boolean isNegative(){
		return !pos && (mant1 != 0 || mant2 != 0);
	}

	public int signum(){

		if(!inf && !nan && mant1 == 0 && mant2 == 0){
			return 0;
		}else if(pos){
			return 1;
		}else{
			return -1;
		}
	}

	@Override
	public int compareTo(BigDouble n){

		if(nan){
			if(n.nan)
				return pos == n.pos?0:pos?1:-1;
			else
				return pos?1:-1;
		}

		if(n.nan)
			return n.pos?1:-1;

		if(inf){
			if(n.inf)
				return pos == n.pos?0:pos?1:-1;
			else
				return pos?1:-1;
		}

		if(n.inf)
			return pos?1:-1;

		if(mant1 != mant2 || mant1 != n.mant1 || mant1 != n.mant2 || mant1 != 0){
			int sig1=signum();
			int sig2=n.signum();
			if(sig1 != sig2)
				return sig1 - sig2;
		}else{
			return 0;
		}

		BigDouble a, b;
		if(exp >= n.exp){
			a=this;
			b=new BigDouble(n);
			b.shiftRightMant(exp - n.exp);
		}else{
			a=new BigDouble(this);
			a.shiftRightMant(n.exp - exp);
			b=n;
		}

		int cmp=((Long)a.mant1).compareTo(b.mant1);
		if(cmp != 0){
			return cmp * (pos?1:-1);
		}else{
			return ((Long)a.mant2).compareTo(b.mant2) * (pos?1:-1);
		}
	}

	public BigDoubleM toPossitive(){
		return new BigDoubleM(this).toPossitiveM();
	}

	public BigDoubleM toNegative(){
		return new BigDoubleM(this).toNegativeM();
	}

	public BigDoubleM negate(){
		return new BigDoubleM(this).negateM();
	}

	//public BigDoubleM inverse() -> Has a real implementation
	//
	public BigDoubleM add(BigDouble n){
		return new BigDoubleM(this).addM(n);
	}

	public BigDoubleM add(double n){
		return new BigDoubleM(n).addM(this);
	}

	public BigDoubleM add(long n){
		return new BigDoubleM(n).addM(this);
	}

	protected BigDoubleM addM(double n){
		return addM(new BigDouble(n));
	}

	protected BigDoubleM addM(long n){
		return addM(new BigDouble(n));
	}

	public BigDoubleM sub(BigDouble n){
		return new BigDoubleM(this).subM(n);
	}

	public BigDoubleM sub(double n){
		return new BigDoubleM(n).subM(this).negateM();
	}

	public BigDoubleM sub(long n){
		return new BigDoubleM(n).subM(this).negateM();
	}

	protected BigDoubleM subM(double n){
		return subM(new BigDouble(n));
	}

	protected BigDoubleM subM(long n){
		return subM(new BigDouble(n));
	}

	public BigDoubleM mul(BigDouble n){
		return new BigDoubleM(this).mulM(n);
	}

	public BigDoubleM mul(double n){
		return new BigDoubleM(n).mulM(this);
	}

	public BigDoubleM mul(long n){
		return new BigDoubleM(n).mulM(this);
	}

	protected BigDoubleM mulM(double n){
		return mulM(new BigDouble(n));
	}

	protected BigDoubleM mulM(long n){
		return mulM(new BigDouble(n));
	}

	public BigDoubleM div(BigDouble n){
		return new BigDoubleM(this).divM(n);
	}

	public BigDoubleM div(double n){
		return new BigDoubleM(this).divM(new BigDouble(n));
	}

	public BigDoubleM div(long n){
		return new BigDoubleM(this).divM(new BigDouble(n));
	}

	protected BigDoubleM divM(double n){
		return divM(new BigDouble(n));
	}

	protected BigDoubleM divM(long n){
		return divM(new BigDouble(n));
	}

	public BigDoubleM mod(BigDouble n){
		return new BigDoubleM(this).modM(n);
	}

	public BigDoubleM mod(double n){
		return new BigDoubleM(this).modM(new BigDouble(n));
	}

	public BigDoubleM mod(long n){
		return new BigDoubleM(this).modM(new BigDouble(n));
	}

	protected BigDoubleM modM(double n){
		return modM(new BigDouble(n));
	}

	//protected BigDoubleM modM(long n) -> Has a real implementation
	//
	public BigDoubleM sqrt(){
		return new BigDoubleM(this).sqrtM();
	}

	public BigDoubleM shiftRight(int n){
		return new BigDoubleM(this).shiftRightM(n);
	}

	public BigDoubleM shiftLeft(int n){
		return new BigDoubleM(this).shiftLeftM(n);
	}

	public BigDoubleM cos(){
		return new BigDoubleM(this).cosM();
	}

	public BigDoubleM sin(){
		return new BigDoubleM(this).sinM();
	}

	public BigDoubleM tan(){
		return new BigDoubleM(this).tanM();
	}

	public BigDoubleM acos(){
		return new BigDoubleM(this).acosM();
	}

	public BigDoubleM asin(){
		return new BigDoubleM(this).asinM();
	}

	public BigDoubleM atan(){
		return new BigDoubleM(this).atanM();
	}

	public BigDoubleM toIntegralValue(){
		return new BigDoubleM(this).toIntegralValueM();
	}

	protected BigDoubleM toPossitiveM(){
		pos=true;
		return (BigDoubleM)this;
	}

	protected BigDoubleM toNegativeM(){
		pos=false;
		return (BigDoubleM)this;
	}

	protected BigDoubleM negateM(){
		pos=!pos;
		return (BigDoubleM)this;
	}

	public BigDoubleM inverse(){
		return new BigDoubleM(1).divM(this);
	}

	protected BigDoubleM inverseM(){

		BigDouble n=new BigDoubleM(1).divM(this);
		this.pos=n.pos;
		this.nan=n.nan;
		this.inf=n.inf;
		this.exp=n.exp;
		this.mant1=n.mant1;
		this.mant2=n.mant2;

		return (BigDoubleM)this;
	}

	protected BigDoubleM addM(BigDouble n){

		if(nan){
			//Nothing to do
		}else if(n.nan){
			inf=false;
			nan=true;
		}else if(inf){
			if(n.inf && pos != n.pos){
				inf=false;
				nan=true;
			}else{
				inf=true;
			}
		}else if(n.inf){
			inf=true;
			pos=n.pos;
		}else if(mant1 == 0 && mant2 == 0){
			pos=n.pos;
			exp=n.exp;
			mant1=n.mant1;
			mant2=n.mant2;
		}else if(n.mant1 == 0 && n.mant2 == 0){
			//Nothing to do
		}else if(pos && n.pos){
			short e;
			BigDouble n1;
			if(exp == n.exp){
				e=exp;
				n1=this;
			}else{
				if(exp > n.exp){
					n1=this;
					n=new BigDouble(n);
				}else{
					n1=n;
					n=this;
				}
				e=n1.exp;
				n.shiftRightMant(n1.exp - n.exp);
			}
			long m2=n1.mant2 + n.mant2;
			long m1=n1.mant1 + n.mant1;
			if(m2 < 0){
				m2&=0x7FFFFFFFFFFFFFFFL;
				m1++;
			}
			if(m1 < 0){
				if((m1 & 0x1) == 1){
					m2|=0x8000000000000000L;
				}
				m2>>>=1;
				m1>>>=1;
				e++;
			}
			exp=e;
			mant1=m1;
			mant2=m2;
		}else if(pos && !n.pos){
			n.pos=!n.pos;
			this.subM(n);
			n.pos=!n.pos;
		}else if(!pos && n.pos){
			this.negateM();
			n.pos=!n.pos;
			this.addM(n).negateM();
			n.pos=!n.pos;
		}else{
			this.negateM();
			n.pos=!n.pos;
			this.addM(n).negateM();
			n.pos=!n.pos;
		}

		return (BigDoubleM)this;
	}

	protected BigDoubleM subM(BigDouble n){

		if(nan){
			//Nothing to do
		}else if(n.nan){
			inf=false;
			nan=true;
		}else if(inf){
			if(n.inf && pos == n.pos){
				inf=false;
				nan=true;
			}
			//else Nothing to do
		}else if(n.inf){
			inf=true;
			pos=!n.pos;
		}else if(n.mant1 == 0 && n.mant2 == 0){
			//Nothing to do
		}else if(mant1 == 0 && mant2 == 0){
			pos=!n.pos;
			exp=n.exp;
			mant1=n.mant1;
			mant2=n.mant2;
		}else if(pos && n.pos){
			short e;
			BigDouble n1;
			if(exp == n.exp){
				e=exp;
				n1=this;
			}else{
				if(exp > n.exp){
					e=exp;
					n1=this;
					n=new BigDouble(n);
					n.shiftRightMant(exp - n.exp);
				}else{
					e=n.exp;
					n1=this;
					n1.shiftRightMant(n.exp - exp);
				}
			}
			long m2=n1.mant2 - n.mant2;
			long m1=n1.mant1 - n.mant1;
			if(m2 < 0){
				m2&=0x7FFFFFFFFFFFFFFFL;
				m1--;
			}
			if(m1 < 0){
				m1=~m1;
				m2=~m2;
				m1&=0x7FFFFFFFFFFFFFFFL;
				m2&=0x7FFFFFFFFFFFFFFFL;
				m2++;
				if(m2 < 0){
					m2&=0x7FFFFFFFFFFFFFFFL;
					m1++;
				}
				if(m1 < 0){
					if((m1 & 0x1) == 1){
						m2|=0x80000000000000L;
					}
					m2>>>=1;
					m1>>>=1;
					e++;
				}
				pos=false;
			}
			exp=e;
			mant1=m1;
			mant2=m2;
			this.shiftLeftMant();
		}else if(pos && !n.pos){
			n.pos=!n.pos;
			this.addM(n);
			n.pos=!n.pos;
		}else if(!pos && n.pos){
			this.negateM();
			this.addM(n).negateM();
		}else{
			this.negateM();
			n.pos=!n.pos;
			this.subM(n).negateM();
			n.pos=!n.pos;
		}

		return (BigDoubleM)this;
	}

	protected BigDoubleM mulM(BigDouble n){

		pos=(pos == n.pos);

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
			long op14=mant2 & 0x00000000FFFFFFFFL;
			long op13=(mant2 & 0xFFFFFFFF00000000L) >>> 32;
			long op12=mant1 & 0x00000000FFFFFFFFL;
			long op11=(mant1 & 0xFFFFFFFF00000000L) >>> 32;
			long op24=n.mant2 & 0x00000000FFFFFFFFL;
			long op23=(n.mant2 & 0xFFFFFFFF00000000L) >>> 32;
			long op22=n.mant1 & 0x00000000FFFFFFFFL;
			long op21=(n.mant1 & 0xFFFFFFFF00000000L) >>> 32;

			Restructure r=new Restructure();
			r.exp=(short)(exp + n.exp);
			long res;

			//************
			r.r4=op14 * op24;
			r.restructure();

			//************
			res=op14 * op23;
			r.r4+=(res & 0x7FFFFFFFL) << 32;
			r.restructure();
			r.r3+=(res & 0x7FFFFFFF80000000L) >>> 31;

			res=op13 * op24;
			r.r4+=(res & 0x7FFFFFFFL) << 32;
			r.restructure();
			r.r3+=(res & 0x7FFFFFFF80000000L) >>> 31;

			//************
			res=op14 * op22;
			if(res < 0){
				r.r2++;
				res&=0x7FFFFFFFFFFFFFFFL;
			}
			r.r3+=res;
			r.restructure();

			res=op13 * op23;
			res<<=1;
			r.r3+=res;
			r.restructure();

			res=op12 * op24;
			if(res < 0){
				r.r2++;
				res&=0x7FFFFFFFFFFFFFFFL;
			}
			r.r3+=res;
			r.restructure();

			//************
			res=op14 * op21;
			r.r3+=(res & 0x7FFFFFFFL) << 32;
			r.restructure();
			r.r2+=(res & 0x7FFFFFFF80000000L) >>> 31;

			res=op13 * op22;
			r.r3+=(res & 0x7FFFFFFFL) << 32;
			r.restructure();
			r.r2+=(res & 0x7FFFFFFF80000000L) >>> 31;

			res=op12 * op23;
			r.r3+=(res & 0x7FFFFFFFL) << 32;
			r.restructure();
			r.r2+=(res & 0x7FFFFFFF80000000L) >>> 31;

			res=op11 * op24;
			r.r3+=(res & 0x7FFFFFFFL) << 32;
			r.restructure();
			r.r2+=(res & 0x7FFFFFFF80000000L) >>> 31;

			//************
			res=op13 * op21;
			res<<=1;
			r.r2+=res;
			r.restructure();

			res=op12 * op22;
			if(res < 0){
				r.r1++;
				res&=0x7FFFFFFFFFFFFFFFL;
			}
			r.r2+=res;
			r.restructure();

			res=op11 * op23;
			res<<=1;
			r.r2+=res;
			r.restructure();

			//************
			res=op12 * op21;
			r.r2+=(res & 0x7FFFFFFFL) << 32;
			r.restructure();
			r.r1+=(res & 0x7FFFFFFF80000000L) >>> 31;

			res=op11 * op22;
			r.r2+=(res & 0x7FFFFFFFL) << 32;
			r.restructure();
			r.r1+=(res & 0x7FFFFFFF80000000L) >>> 31;

			//************
			res=op11 * op21;
			res<<=1;
			boolean carry=false;
			if(res < 0){
				carry=true;
				res&=0x7FFFFFFFFFFFFFFFL;
			}
			r.r1+=res;
			if(r.r1 < 0){
				r.restructure();
				if(carry){
					r.r1+=0x4000000000000000L;
					r.restructure();
				}
			}else if(carry){
				r.r1+=0x8000000000000000L;
				r.restructure();
			}

			//************
			r.shiftLeft();
			mant1=r.r1;
			mant2=r.r2;
			exp=r.exp;
		}

		return (BigDoubleM)this;
	}

	private static class Restructure{

		public long r1=0, r2=0, r3=0, r4=0;
		public short exp;

		public void restructure(){

			if(r4 < 0){
				r4&=0x7FFFFFFFFFFFFFFFL;
				r3++;
			}
			if(r3 < 0){
				r3&=0x7FFFFFFFFFFFFFFFL;
				r2++;
			}
			if(r2 < 0){
				r2&=0x7FFFFFFFFFFFFFFFL;
				r1++;
			}
			if(r1 < 0){
				exp++;
				if((r3 & 0x1) == 1){
					r4&=0x8000000000000000L;
				}
				r4>>>=1;
				if((r2 & 0x1) == 1){
					r3&=0x8000000000000000L;
				}
				r3>>>=1;
				if((r1 & 0x1) == 1){
					r2&=0x8000000000000000L;
				}
				r2>>>=1;
				r1>>>=1;
			}
		}

		public void shiftLeft(){

			if(r1 == 0){
				if(r2 == 0){
					if(r3 == 0){
						if(r4 == 0){
							return;
						}else{
							r1=r4;
						}
					}else{
						r1=r3;
						r2=r4;
						r3=0;
					}
				}else{
					r1=r2;
					r2=r3;
					r3=r4;
				}
			}

			int count=0;
			long mask=0x4000000000000000L;
			while((r1 & mask) == 0 && count < 63){
				count++;
				mask>>=1;
			}
			if(count == 0)
				return;
			mask=0x8000000000000000L >> count;
			r1<<=count;
			r1|=((mask & r2) >>> (63 - count));
			r2<<=count;
			r2&=0x7FFFFFFFFFFFFFFFL;
			r2|=((mask & r3) >>> (63 - count));
			exp-=count;
		}
	}

	protected BigDoubleM divM(BigDouble n){

		if(nan){
			//Nothing to do
		}else if(n.nan){
			nan=true;
			inf=false;
		}else if(inf){
			if(n.inf){
				nan=true;
				inf=false;
			}
			//else Nothing to do
		}else if(n.inf){
			mant1=mant2=0;
		}else if(mant1 == 0 && mant2 == 0){
			if(n.mant1 == 0 && n.mant2 == 0){
				nan=true;
				return (BigDoubleM)this; //To avoid sign change if n is negative zero
			}
			//else Nothing to do
		}else if(n.mant1 == 0 && n.mant2 == 0){
			inf=true;
			return (BigDoubleM)this; //To avoid sign change if n is negative zero
		}else if(mant1 == n.mant1 && mant2 == n.mant2){
			exp-=n.exp - 1;
			mant1=0x4000000000000000L;
			mant2=0;
		}else{
			this.shiftRightM(n.exp);
			BigDouble den=new BigDoubleM(n);
			if(!den.pos){
				den.negateM();
			}
			den.exp=0;
			BigDoubleM x=div_const_1.sub(div_const_2.mul(den));
			for(int i=0; i < 5; i++){
				x.addM(x.mul(new BigDoubleM(1).subM(den.mul(x))));
			}
			this.mulM(x);
		}

		pos=(pos == n.pos);

		return (BigDoubleM)this;
	}

	protected BigDoubleM modM(BigDouble n){

		if(inf || n.inf){
			inf=false;
			nan=true;
		}else if(nan || n.nan || (mant1 == 0 && mant2 == 0)){
			//Nothing to do;
		}else if(n.mant1 == 0 && n.mant2 == 0){
			nan=true;
		}else{
			this.subM(this.div(n).toIntegralValueM().mulM(n));
		}

		return (BigDoubleM)this;
	}

	/**
	 * NOTE: preferred over modM(BigDouble) when
	 * Long.MIN_VALUE&le;this&le;Long.MAX_VALUE. In case of doubt it is
	 * recommended to use this method since if used when
	 * this&gt;Long.MAX_VALUE or this&lt;Long.MIN_VALUE it is 0.5 times slower
	 * than modM(BigDouble) but if Long.MIN_VALUE&le;this&le;Long.MAX_VALUE
	 * this method is 40 times faster than modM(BigDouble).
	 *
	 * @param n
	 *
	 * @return
	 */
	protected BigDoubleM modM(long n){

		if(inf){
			inf=false;
			nan=true;
		}else if(nan || (mant1 == 0 && mant2 == 0)){
			//Nothing to do;
		}else if(n == 0){
			nan=true;
		}else{
			if(!pos){
				negateM();
			}
			try{
				long x=this.longValue();
				this.subM((x / n) * n);
			}catch(IllegalStateException ise){
				this.modM(new BigDouble(n));
			}
			if(n < 0){
				this.addM(n);
			}
		}

		return (BigDoubleM)this;
	}

	protected BigDoubleM sqrtM(){

		if(!pos){
			nan=true;
			inf=false;
			pos=true;
		}else if(nan || inf || (mant1 == 0 && mant2 == 0)){
			//Nothing to do
		}else{
			BigDoubleM this_cpy=new BigDoubleM(this);
			this.exp=(short)(exp % 2 == 0?exp / 2:exp / 2 + 1);
			{
				BigDoubleM last=new BigDoubleM(this);
				BigDoubleM lastlast;
				int count=0;
				do{
					count++;
					lastlast=last;
					last=new BigDoubleM(this);
					BigDouble res=this_cpy.div(this).addM(last);
					mant1=res.mant1;
					mant2=res.mant2;
					exp=(short)(res.exp - 1);
				}while(!this.equals(lastlast) && count < 11);
			}
		}

		return (BigDoubleM)this;
	}

	protected BigDoubleM shiftRightM(int n){

		exp-=n;
		return (BigDoubleM)this;
	}

	protected BigDoubleM shiftLeftM(int n){

		exp+=n;
		return (BigDoubleM)this;
	}

	protected BigDoubleM cosM(){

		if(inf || nan){
			inf=false;
			nan=true;
		}else if(mant1 == 0 && mant2 == 0){
			fromDouble(1);
		}else if(this.compareTo(PI) == 0 || this.compareTo(PI_neg) == 0){
			fromDouble(-1);
		}else if(this.compareTo(PI_2) == 0 || this.compareTo(PI3_2) == 0 || this.compareTo(PI_2_neg) == 0 || this.compareTo(PI3_2_neg) == 0){
			mant1=mant2=0;
			pos=false;
		}else{
			try{
				fromDouble(Math.cos(doubleValue()));
			}catch(IllegalStateException ise){
				fromDouble(Math.cos(this.mod(BigDouble.TWO.mul(BigDouble.PI)).doubleValue()));
			}
		}
		return (BigDoubleM)this;
	}

	protected BigDoubleM sinM(){

		if(inf || nan){
			inf=false;
			nan=true;
		}else if((mant1 == 0 && mant2 == 0) || this.compareTo(PI) == 0 || this.compareTo(PI_neg) == 0){
			mant1=mant2=0;
			pos=false;
		}else if(this.compareTo(PI_2) == 0 || this.compareTo(PI3_2_neg) == 0){
			fromDouble(1);
		}else if(this.compareTo(PI3_2) == 0 || this.compareTo(PI_2_neg) == 0){
			fromDouble(-1);
		}else{
			try{
				fromDouble(Math.sin(doubleValue()));
			}catch(IllegalStateException ise){
				fromDouble(Math.sin(this.mod(BigDouble.TWO.mul(BigDouble.PI)).doubleValue()));
			}
		}
		return (BigDoubleM)this;
	}

	protected BigDoubleM tanM(){

		if(inf || nan){
			inf=false;
			nan=true;
		}else if((mant1 == 0 && mant2 == 0) || this.compareTo(PI) == 0 || this.compareTo(PI_neg) == 0){
			mant1=mant2=0;
			pos=false;
		}else if(this.compareTo(PI_2) == 0 || this.compareTo(PI3_2) == 0 || this.compareTo(PI_2_neg) == 0 || this.compareTo(PI3_2_neg) == 0){
			nan=true;
		}else{
			try{
				fromDouble(Math.tan(doubleValue()));
			}catch(IllegalStateException ise){
				fromDouble(Math.tan(this.mod(BigDouble.TWO.mul(BigDouble.PI)).doubleValue()));
			}
		}
		return (BigDoubleM)this;
	}

	protected BigDoubleM acosM(){

		if(inf || nan || this.compareTo(BigDouble.ONE) > 0 || this.compareTo(BigDouble.ONE.negate()) < 1){
			this.inf=false;
			this.nan=true;
		}else if(this.compareTo(BigDouble.ONE) == 0){
			mant1=mant2=0;
		}else if(this.compareTo(BigDouble.ONE.negate()) == 0){
			fromBigDouble(PI);
		}else if(this.compareTo(BigDouble.ZERO) == 0){
			fromBigDouble(PI_2);
		}else{
			fromDouble(Math.acos(doubleValue()));
		}

		return (BigDoubleM)this;
	}

	protected BigDoubleM asinM(){

		if(inf || nan || this.compareTo(BigDouble.ONE) > 0 || this.compareTo(BigDouble.ONE.negate()) < 1){
			this.inf=false;
			this.nan=true;
		}else if(this.compareTo(BigDouble.ONE) == 0){
			fromBigDouble(PI_2);
		}else if(this.compareTo(BigDouble.ONE.negate()) == 0){
			fromBigDouble(PI3_2);
		}else if(this.compareTo(BigDouble.ZERO) == 0){
			//Nothing to do
		}else{
			fromDouble(Math.asin(doubleValue()));
		}

		return (BigDoubleM)this;
	}

	protected BigDoubleM atanM(){

		if(nan){
			//Nothing to do
		}else if(inf){
			if(pos){
				fromBigDouble(PI_2);
			}else{
				fromBigDouble(PI3_2);
			}
		}else if(this.compareTo(BigDouble.ONE) == 0){
			fromBigDouble(PI_2.shiftRight(1));
		}else if(this.compareTo(BigDouble.ONE.negate()) == 0){
			fromBigDouble(PI_2.shiftRight(1).negate());
		}else{
			fromDouble(Math.atan(doubleValue()));
		}

		return (BigDoubleM)this;
	}

	/**
	 * NOTE: according to IEEE 754, NaN.equals(x) and x.equals(NaN) is false
	 * for every x; although NaN.compareTo(NaN)==0 and
	 * NaN1.hashCode()==NaN2.hashCode()
	 *
	 * @param obj
	 *
	 * @return
	 */
	@Override
	public boolean equals(Object obj){

		if(obj == null)
			return false;

		if(!(obj instanceof BigDouble))
			return false;

		if(nan || ((BigDouble)obj).nan)
			return false;

		if(obj == this)
			return true;

		if(inf || ((BigDouble)obj).inf){
			return inf == ((BigDouble)obj).inf && pos == ((BigDouble)obj).pos;
		}else{
			return (mant1 == 0 && mant2 == 0 && ((BigDouble)obj).mant1 == 0 && ((BigDouble)obj).mant2 == 0)
			|| (pos == ((BigDouble)obj).pos
			&& inf == ((BigDouble)obj).inf
			&& exp == ((BigDouble)obj).exp
			&& mant1 == ((BigDouble)obj).mant1
			&& mant2 == ((BigDouble)obj).mant2);
		}
	}

	@Override
	public int hashCode(){
		int hash=7;
		hash=59 * hash + (this.pos?1:0);
		hash=59 * hash + (this.nan?1:0);
		hash=59 * hash + (this.inf?1:0);
		hash=59 * hash + this.exp;
		hash=59 * hash + (int)(this.mant1 ^ (this.mant1 >>> 32));
		hash=59 * hash + (int)(this.mant2 ^ (this.mant2 >>> 32));
		return hash;
	}

	protected BigDoubleM toIntegralValueM(){

		if(inf || nan || exp >= 126){
			//Nothing to do
		}else if(exp < 1){
			mant1=mant2=0;
		}else if(exp >= 63){
			mant2&=(0xFFFFFFFFFFFFFFFFL) << (126 - exp);
		}else{
			mant2=0;
			mant1&=(0xFFFFFFFFFFFFFFFFL) << (63 - exp);
		}

		return (BigDoubleM)this;
	}

	public BigLongM bigLongValue() throws IllegalStateException{

		if(inf){
			return pos?BigLongM.infinity():BigLongM.infinity().negateM();
		}else if(nan){
			return pos?BigLongM.NaN():BigLongM.NaN().negateM();
		}else if(pos && exp > 126){
			throw new IllegalStateException("Number too big for a BigLong");
		}else if(!pos){
			if(exp >= 127){
				if(exp == 127 && mant1 == 0x4000000000000000L && mant2 == 0){
					return BigLongM.minValue();
				}else{
					throw new IllegalStateException("Number too small for a BigLong");
				}
			}
		}

		if(exp < 0){
			return new BigLongM(BigLong.ZERO);
		}

		BigLongM ret;
		if(exp > 63){
			ret=new BigLongM(mant1).shiftLeftM(exp - 63).addM(mant2 >>> (126 - exp));
		}else{
			ret=new BigLongM(mant1 >>> (63 - exp));
		}

		if(pos){
			return ret;
		}else{
			return ret.negateM();
		}
	}

	@Override
	public double doubleValue() throws IllegalStateException{

		if(exp > 1024){
			if(pos){
				throw new IllegalStateException("Number too big for a double");
			}else{
				throw new IllegalStateException("Number too small for a double");
			}
		}else if(exp < -1022){
			return 0;
		}

		if(inf){
			if(pos){
				return Double.longBitsToDouble(0x7FF0000000000000L);
			}else{
				return Double.longBitsToDouble(0xFFF0000000000000L);
			}
		}else if(nan){
			return Double.longBitsToDouble(0x7FF8000000000000L);
		}else if(mant1 == 0 && mant2 == 0){
			return 0;
		}else{
			return Double.longBitsToDouble((pos?0:0x8000000000000000L) | (((long)exp + 1022) << 52) | ((mant1 & 0x3FFFFFFFFFFFFFFFL) >> 10));
		}
	}

	@Override
	public float floatValue() throws IllegalStateException{

		if(exp > 128){
			if(pos){
				throw new IllegalStateException("Number too big for a float");
			}else{
				throw new IllegalStateException("Number too small for a float");
			}
		}else if(exp < -127){
			return 0;
		}

		if(inf){
			if(pos){
				return Float.intBitsToFloat(0x7F800000);
			}else{
				return Float.intBitsToFloat(0xFF800000);
			}
		}else if(nan){
			return Float.intBitsToFloat(0x7FC00000);
		}else if(mant1 == 0 && mant2 == 0){
			return 0;
		}else{
			return Float.intBitsToFloat((pos?0:0x80000000) | ((exp + 126) << 23) | (int)((mant1 & 0x3FFFFFFFFFFFFFFFL) >> 39));
		}
	}

	@Override
	public long longValue() throws IllegalStateException{

		if(inf){
			throw new IllegalStateException("Long doesn't support Infinity");
		}else if(nan){
			throw new IllegalStateException("Long doesn't support NaN");
		}else if(pos && exp > 63){
			throw new IllegalStateException("Number too big for a long");
		}else if(!pos){
			if(exp >= 64){
				if(exp == 64 && mant1 == 0x4000000000000000L && mant2 == 0){
					return Long.MIN_VALUE;
				}else{
					throw new IllegalStateException("Number too small for a long");
				}
			}
		}

		if(exp < 0){
			return 0;
		}

		if(pos){
			return (mant1 >>> (63 - exp));
		}else{
			return (~(mant1 >>> (63 - exp))) + 1;
		}
	}

	@Override
	public int intValue() throws IllegalStateException{

		if(inf){
			throw new IllegalStateException("Int doesn't support Infinity");
		}else if(nan){
			throw new IllegalStateException("Int doesn't support NaN");
		}else if(pos && exp > 31){
			throw new IllegalStateException("Number too big for an int");
		}else if(!pos && (exp > 32 || (exp == 32 && (mant1 > 0x4000000000000000L || (mant1 == 0x4000000000000000L && mant2 > 0))))){
			throw new IllegalStateException("Number too small for an int");
		}

		if(exp < 0){
			return 0;
		}

		if(pos){
			return (int)(mant1 >>> (63 - exp));
		}else{
			return (~(int)(mant1 >>> (63 - exp))) + 1;
		}
	}

	@Override
	public short shortValue() throws IllegalStateException{

		if(inf){
			throw new IllegalStateException("Short doesn't support Infinity");
		}else if(nan){
			throw new IllegalStateException("Short doesn't support NaN");
		}else if(pos && exp > 15){
			throw new IllegalStateException("Number too big for a short");
		}else if(!pos && (exp > 16 || (exp == 16 && (mant1 > 0x4000000000000000L || (mant1 == 0x4000000000000000L && mant2 > 0))))){
			throw new IllegalStateException("Number too small for a short");
		}

		if(exp < 0){
			return 0;
		}

		if(pos){
			return (short)(mant1 >>> (63 - exp));
		}else{
			return (short)((~(short)(mant1 >>> (63 - exp))) + 1);
		}
	}

	@Override
	public byte byteValue() throws IllegalStateException{

		if(inf){
			throw new IllegalStateException("Byte doesn't support Infinity");
		}else if(nan){
			throw new IllegalStateException("Byte doesn't support NaN");
		}else if(pos && exp > 7){
			throw new IllegalStateException("Number too big for a byte");
		}else if(!pos && (exp > 8 || (exp == 8 && (mant1 > 0x4000000000000000L || (mant1 == 0x4000000000000000L && mant2 > 0))))){
			throw new IllegalStateException("Number too small for a byte");
		}

		if(exp < 0){
			return 0;
		}

		if(pos){
			return (byte)(mant1 >>> (63 - exp));
		}else{
			return (byte)((~(byte)(mant1 >>> (63 - exp))) + 1);
		}
	}

	/**
	 * @return
	 */
	public BigDecimal bigDecimalValue() throws IllegalStateException{

		if(nan){
			throw new IllegalStateException("BigDecimal doesn't support NaN");
		}else if(inf){
			throw new IllegalStateException("BigDecimal doesn't support Infinity");
		}

		if(mant1 == 0 && mant2 == 0){
			return BigDecimal.ZERO;
		}

		long m1=mant1;
		long m2=mant2;
		BigDecimal ret=BigDecimal.ZERO;

		if(mant2 != 0){
			for(int i=126; i > 63; i--){
				if((m2 & 0x1) == 1){
					if(exp - i > 0){
						ret=ret.add(bd2.pow(exp - i));
					}else{
						ret=ret.add(bd0_5.pow(i - exp));
					}
				}
				m2>>=1;
			}
		}

		for(int i=63; i > 0; i--){
			if((m1 & 0x1) == 1){
				if(exp - i > 0){
					ret=ret.add(bd2.pow(exp - i));
				}else{
					ret=ret.add(bd0_5.pow(i - exp));
				}
			}
			m1>>=1;
		}

		if(!pos)
			ret=ret.multiply(new BigDecimal(-1));

		return ret;
	}

	@Override
	public String toString(){

		if(nan){
			return pos?"NaN":"-NaN";
		}else if(inf){
			return pos?"Inf":"-Inf";
		}else{
			return bigDecimalValue().toString();
		}
	}

	/**
	 * NOTE: 0 indicates no rounding
	 *
	 * @param digits
	 *
	 * @return
	 */
	public String toString(int digits){

		if(nan){
			return pos?"NaN":"-NaN";
		}else if(inf){
			return pos?"Inf":"-Inf";
		}else{
			return bigDecimalValue().round(new MathContext(digits, RoundingMode.HALF_EVEN)).toString();
		}
	}
}
