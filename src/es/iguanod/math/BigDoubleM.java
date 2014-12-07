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
import java.util.Random;

/**
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class BigDoubleM extends BigDouble{

	private static final long serialVersionUID=5024369511508431962L;

	public BigDoubleM(double n){
		super(n);
	}

	public BigDoubleM(long n){
		super(n);
	}

	public BigDoubleM(String str){
		super(str);
	}

	public BigDoubleM(BigInteger n){
		super(n);
	}

	public BigDoubleM(BigDecimal n){
		super(n);
	}

	public BigDoubleM(BigDouble n){
		super(n);
	}

	public BigDoubleM(Number n){
		super(n);
	}

	/**
	 * Given the implementation of Random, this method cannot return all
	 * possible BigLong values
	 *
	 * @param rand
	 */
	public BigDoubleM(Random rand){

		this(rand.nextBoolean(), false, false, (short)rand.nextInt(), rand.nextLong() & 0x7FFFFFFFFFFFFFFFL, rand.nextLong() & 0x7FFFFFFFFFFFFFFFL);
	}

	protected BigDoubleM(boolean pos, boolean nan, boolean inf, short exp, long mant1, long mant2){
		super(pos, nan, inf, exp, mant1, mant2);
	}

	public static BigDoubleM maxValue(){
		return new BigDoubleM(MAX_VALUE);
	}

	public static BigDoubleM minValue(){
		return new BigDoubleM(MIN_VALUE);
	}

	public static BigDoubleM infinity(){
		return new BigDoubleM(true, false, true, (short)0, 0, 0);
	}

	public static BigDoubleM NaN(){
		return new BigDoubleM(true, true, false, (short)0, 0, 0);
	}

	public static BigDoubleM Pi(){
		return new BigDoubleM(PI);
	}

	public static BigDoubleM E(){
		return new BigDoubleM(E);
	}

	@Override
	public BigDoubleM addM(double n){
		return addM(new BigDouble(n));
	}

	@Override
	public BigDoubleM addM(long n){
		return addM(new BigDouble(n));
	}

	@Override
	public BigDoubleM subM(double n){
		return subM(new BigDouble(n));
	}

	@Override
	public BigDoubleM subM(long n){
		return subM(new BigDouble(n));
	}

	@Override
	public BigDoubleM mulM(double n){
		return mulM(new BigDouble(n));
	}

	@Override
	public BigDoubleM mulM(long n){
		return mulM(new BigDouble(n));
	}

	@Override
	public BigDoubleM divM(double n){
		return divM(new BigDouble(n));
	}

	@Override
	public BigDoubleM divM(long n){
		return divM(new BigDouble(n));
	}

	@Override
	public BigDoubleM modM(double n){
		return modM(new BigDouble(n));
	}

	//public BigDoubleM modM(long n) -> Has a real implementation
	//
	@Override
	public BigDoubleM toPossitiveM(){
		return super.toPossitiveM();
	}

	@Override
	public BigDoubleM toNegativeM(){
		return super.toNegativeM();
	}

	@Override
	public BigDoubleM negateM(){
		return super.negateM();
	}

	@Override
	public BigDoubleM inverseM(){
		return super.inverseM();
	}

	@Override
	public BigDoubleM addM(BigDouble n){
		return super.addM(n);
	}

	@Override
	public BigDoubleM subM(BigDouble n){
		return super.subM(n);
	}

	@Override
	public BigDoubleM mulM(BigDouble n){
		return super.mulM(n);
	}

	@Override
	public BigDoubleM divM(BigDouble n){
		return super.divM(n);
	}

	@Override
	public BigDoubleM modM(BigDouble n){
		return super.modM(n);
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
	@Override
	public BigDoubleM modM(long n){
		return super.modM(n);
	}

	@Override
	public BigDoubleM sqrtM(){
		return super.sqrtM();
	}

	@Override
	public BigDoubleM shiftRightM(int n){
		return super.shiftRightM(n);
	}

	@Override
	public BigDoubleM shiftLeftM(int n){
		return super.shiftLeftM(n);
	}

	@Override
	public BigDoubleM cosM(){
		return super.cosM();
	}

	@Override
	public BigDoubleM sinM(){
		return super.sinM();
	}

	@Override
	public BigDoubleM tanM(){
		return super.tanM();
	}

	@Override
	public BigDoubleM acosM(){
		return super.acosM();
	}

	@Override
	public BigDoubleM asinM(){
		return super.asinM();
	}

	@Override
	public BigDoubleM atanM(){
		return super.atanM();
	}

	@Override
	public BigDoubleM toIntegralValueM(){
		return super.toIntegralValueM();
	}
}
