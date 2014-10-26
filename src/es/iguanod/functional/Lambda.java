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
package es.iguanod.functional;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * <p>
 * This class allows to simulate the behaviour of a lambda function.
 * </p>
 * The way of constructnig a {@code Lambda} is to create an anonymous class that
 * extends it. Then, with double curly braces initialization, the definition of
 * the lambda is created. The result of the operation performed by the lambda
 * (if any), has to be stored in a protected attribute of {@code Lambda} called
 * {@code res}. For example, to create a {@code Lambda} that always returns
 * {@code 1}:
 * <pre>
 *
 *     Lambda lambda=new Lambda(){{ res=1; }};
 * </pre> Then, to retrieve the result of the {@code Lambda}:
 * <pre>
 *
 *     int result=(int)lambda.eval();
 * </pre>
 * <br/>
 * In case the evaltion function of the {@code Lambda} needs parameters, they
 * have to be declared before the {@code Lambda}. When evaluating it, the
 * arguments have to be passed in the same order as they are used. For example,
 * to create a {@code Lambda} that squares an {@code Integer} and then adds it
 * to another, and evaluate it for the values {@code (2,3)}:
 * <pre>
 *
 *     final Integer x=0,y=0;
 *     Lambda<Integer,Integer> lambda=new Lambda(){{ res=x*x+y; }};
 *     int result=lambda.eval(2,3);    // result=7
 * </pre> Note we have declared {@code x} and {@code y} as {@code final}. This
 * is necessary to be able to use them inside an anonymous class. Also, we have
 * assigned them a value of zero when declaring them. The reason is that the
 * code executed by the {@code Lambda} has to be executable without errors at
 * the moment the {@code Lambda} is created, not only when evaluated. That is
 * why the {@code Integers} had to be assigned a value, although that value is
 * never going to be used. If we had done the following, a
 * {@link NullPointerException} would have arised on runtime when the
 * {@code Lambda} was created:
 * <pre>
 *
 *     final Integer x=null,y=null;
 *     Lambda<Integer,Integer> lambda=new Lambda(){{ res=x*x+y; }};   // NullPointerException
 *     int result=lambda.eval(2,3);
 * </pre> Of course, if a primitive type is used, there is no risk of a
 * {@code NullPointerException} happening, but a value is still needed for the
 * code to compile:
 * <pre>
 *
 *     final int x;
 *     Lambda<Integer,Integer> lambda=new Lambda(){{ res=x; }};   // Variable x might not have been initialized
 *     int result=lambda.eval(2);
 * </pre>
 * <br/>
 * It is also important to note that if the {@code Lambda} is parameterized, all
 * the parameters have to be of the same type (the parameterized type). That
 * doesn't mean that the {@code Lambda} cannot be parameterized if parameters of
 * different types want to be used, but one of two techniques has to be used.
 * First of all, the input parameters type can be declared as simply
 * {@code Object} (if parameterization still wants to be used for the return
 * type, otherwise the {@code Lambda} could simply be left unparameterized):
 * <pre>
 *
 *     final Object str="";
 *     final Object x=0;
 *     Lambda<Object,String> lambda=new Lambda(){{
 *         res=str+" "+x.toString();
 *     }};
 *     String result=lambda.eval("Hi!", 3);    // result="Hi! 3"
 * </pre> It is important that if the {@code Lambda} is declared as
 * {@code Lambda<Object,String>}, the parameters are actually declared as
 * {@code Object}. If instead the are declared as their real types, the order in
 * which the evaluation function would accept the parameters is unpredictable
 * and can lead to errors:
 * <pre>
 *
 *     final String str="";
 *     final int x=0;
 *     Lambda<Object,String> lambda=new Lambda(){{
 *         res=str+" "+x.toString();
 *     }};
 *     String result=lambda.eval("Hi!", 3);    // Possible exception, expected lambda.eval(3, "Hi!")
 * </pre> The other possible technique (the preferred one, as it allows for
 * parameterization of input types when they are different instead of just using
 * {@code Object}), consists on wrapping the parameters in some kind of
 * container (like a {@link es.iguanod.util.tuples.Tuple2 Tuple2}). This
 * technique is also needed if more than {@code 10} parameters are needed, as
 * this is the maximum the evaluation function accepts. An example of parameter
 * wrapping:
 * <pre>
 *
 *     final Tuple2<String,Integer> tuple=new Tuple2<>("Hi!",3);
 *     Lambda<Tuple2<String,Integer>,String> lambda=new Lambda(){{
 *         res=tuple.getFirst()+" "+tuple.getSecond().toString();
 *     }};
 *     String result=lambda.eval(tuple);
 * </pre> This time we have used the same tuple for both declaring the lambda
 * and as the argument for the evaluation. Although normally this would not be
 * the case (as the {@code Lambda} is probably created to be passed around to
 * methods), it is perfectly doable.<br/>
 * Regarding paremeterization, in all the previous examples the declaration of
 * the variable {@code lambda} is parameterized, but the definition of the
 * {@code Lambda} itself isn't. The reason is that the parameterization is
 * usefull when declaring the variable so the arguments of {@link #eval} are
 * checked, but even if the definition is parameterized there is no way for the
 * compiler to check that the variables used inside the body of the
 * {@code Lambda} are of the correct type.
 * <br/>
 * <br/>
 * <b>Warnings</b>
 * <br/> {@code Lambdas} should be used cautiously. First of all, the
 * implementation of this class relies on undocumented compiler behaviour.
 * Although a great deal of this behaviour is well known even if not officially
 * documented, and so this class should work properly, two methods are offered
 * to check at runtime wether your compiler supports {@code Lambdas} or not:
 * {@link #usableDeclaredStatic} and {@link #usableDeclaredDynamic}. Although
 * both methods should return the same value, two different methods are provided
 * since the code generated by the compiler depends on wether the {@code Lambda}
 * is declared in a static scope (ie, class static initializer block or static
 * method) or in a dynamic one (instance member or method).
 * <br/>
 * <br/>
 * Second, {@code Lambdas} are slower than normal code, so they should only be
 * used when actually needed. The performance degradation varies from a 10 or
 * 20% if the body of the {@code Lambda} calls other functions, to a whole order
 * of magnitude if it consists of only basic mathematical operations.
 * <br/>
 * <br/>
 * Another important warning is that {@code Lambdas} should not have side
 * effects. Since the code of the lambda is actually executed internally when it
 * is created, the side effect will happen one extra time:
 * <pre>
 *
 *     final int x=0;
 *     Lambda<Integer,Integer> lambda=new Lambda(){{
 *         System.out.println("Hi");
 *         res=x;
 *     }};    // "Hi" printed a first time
 *     int result=lambda.eval(,10);    // "Hi" printed a second time
 * </pre> However, side effects can happen safely if they affect parameters. For
 * example, the following is totally safe:
 * <pre>
 *
 *     final ArrayList<Integer> array=new ArrayList();
 *     Lambda<ArrayList<Integer>,Integer> lambda=new Lambda(){{
 *         array.add(0);
 *         res=array.size();
 *     }};
 *     int result=lambda.eval(new ArrayList<Integer>());    // result=1
 * </pre> But the following isn't, since we are reusing the declaration
 * parameter for the evaluation call parameter (which is safe in general, like
 * in the {@code Tuple2} example, but not when the parameter is mutable):
 * <pre>
 *
 *     final ArrayList<Integer> array=new ArrayList<>();
 *     Lambda<ArrayList<Integer>,Integer> lambda=new Lambda(){{
 *         array.add(0);
 *         res=array.size();
 *     }};
 *     int result=lambda.eval(array);    // Wrong: result=2
 * </pre> As a last note, there is no need for the {@code Lambda} to actually
 * return anything (ie, no need to assign {@code res} any value). In that case,
 * if parameterization is used, any type can be chosen for the return type. The
 * last example (the correct version) could be rewritten like this:
 * <pre>
 *
 *     final ArrayList<Integer> array=new ArrayList<>();
 *     Lambda<ArrayList<Integer>,Integer> lambda=new Lambda(){{
 *         array.add(0);
 *     }};
 *     ArrayList<Integer> param_array=new ArrayList<>();
 *     lambda.eval(param_array);
 *     int size=param_array.size();    // size=1
 * </pre>
 *
 * @param <P> the class of the elements used as input parameters for the
 * evaluation of the {@code Lambda}
 * @param <R> the class of the return value of the evaluation of the
 * {@code Lambda}
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1.b
 * @version 1.0.1.b
 */
public class Lambda<P, R>{

	/**
	 * Is assigned the result of the evaluation of the {@code Lambda}.
	 */
	protected R res=null;
	//*********
	/**
	 * Lookup for the Class of the created Lambda. If not null, all the other
	 * lookups are assigned.
	 */
	private Class cls=null;
	/**
	 * Lookup for the Constructor of the created Lambda.
	 */
	private Constructor constructor=null;
	/**
	 * Reference to the created Lambda.
	 */
	private Field this$0=null;
	/**
	 * Number of parameters the eval function takes.
	 */
	private int num_params;
	//*********
	/**
	 * Tells wether Lambdas are supported from dynamic context.
	 */
	private static Boolean usable_dynamic=null;
	/**
	 * Tells wether Lambdas are supported from static context.
	 */
	private static Boolean usable_static=null;

	/**
	 * Returns wether this {@code Lambda}, created in a non-static scope, is
	 * usable. The result depends on the compiler, and every {@code Lambda}
	 * created in a non-static scope and compiled by the same compiler will
	 * return the same value.
	 *
	 * @return true if the {@code Lambda} is usable, false otherwise
	 */
	public boolean usableDeclaredDynamic(){
		if(usable_dynamic != null){
			return usable_dynamic;
		}else{
			try{
				final Integer x=0, y=0;
				Lambda test=new Lambda(){
					{
						res=x - y;
					}
				};
				usable_dynamic=test.eval(3, 2).equals(1);
				return usable_dynamic;
			}catch(MalformedLambdaException | IllegalArgumentException | ReflectiveOperationException ex){
				usable_dynamic=false;
				return usable_dynamic;
			}
		}
	}

	/**
	 * Returns wether a {@code Lambda} created in a static scope, is usable.
	 * The result depends on the compiler, and every {@code Lambda} created in
	 * a static scope and compiled by the same compiler will return the same
	 * value.
	 *
	 * @return true if the {@code Lambda} is usable, false otherwise
	 */
	public static boolean usableDeclaredStatic(){
		if(usable_static != null){
			return usable_static;
		}else{
			try{
				final Integer x=0, y=0;
				Lambda test=new Lambda(){
					{
						res=x - y;
					}
				};
				usable_static=test.eval(3, 2).equals(1);
				return usable_static;
			}catch(ReflectiveOperationException | MalformedLambdaException | IllegalArgumentException ex){
				usable_static=false;
				return usable_static;
			}
		}
	}

	/**
	 * Evaluate the result of this {@code Lambda} when the parameters
	 * {@code params} are applied to it. A maximum of 10 parameters can be
	 * passed.
	 *
	 * @param params the parameters to evaluate the {@code Lambda}
	 *
	 * @return the result of the evaluation
	 *
	 * @throws ReflectiveOperationException if an internal error regarding
	 * reflection occurs (this is most likely a result of {@code Lambdas} not
	 * being usable)
	 * @throws MalformedLambdaException if the {@code Lambda} takes more than
	 * 10 parameters
	 * @throws IllegalArgumentException if the number of parameters passed to
	 * this method isn't the same number of parameters the {@code Lambda}
	 * takes
	 */
	public R eval(P... params) throws ReflectiveOperationException, MalformedLambdaException, IllegalArgumentException{

		if(cls == null){
			try{
				cls=this.getClass();
				constructor=cls.getDeclaredConstructors()[0];
				constructor.setAccessible(true);
				Class[] types=constructor.getParameterTypes();
				num_params=types.length;
				if(num_params != 0 && types[0] == cls.getEnclosingClass()){
					this$0=cls.getDeclaredField("this$0");
					this$0.setAccessible(true);
				}
			}catch(NoSuchFieldException ex){
				cls=null;
				throw ex;
			}
		}

		MalformedLambdaException mlex=null;
		try{
			if(this$0 != null){
				try{
					switch(num_params){
						case 1:
							return ((Lambda<P, R>)constructor.newInstance(this$0.get(this))).res;
						case 2:
							return ((Lambda<P, R>)constructor.newInstance(this$0.get(this), params[0])).res;
						case 3:
							return ((Lambda<P, R>)constructor.newInstance(this$0.get(this), params[0], params[1])).res;
						case 4:
							return ((Lambda<P, R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2])).res;
						case 5:
							return ((Lambda<P, R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3])).res;
						case 6:
							return ((Lambda<P, R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3], params[4])).res;
						case 7:
							return ((Lambda<P, R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3], params[4], params[5])).res;
						case 8:
							return ((Lambda<P, R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3], params[4], params[5], params[6])).res;
						case 9:
							return ((Lambda<P, R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7])).res;
						case 10:
							return ((Lambda<P, R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8])).res;
						case 11:
							return ((Lambda<P, R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9])).res;
						default:
							mlex=new MalformedLambdaException("Lambda created with too many parameters");
					}
				}finally{
					if(mlex != null){
						throw mlex;
					}
					usable_dynamic=true;
				}
			}else{
				try{
					switch(num_params){
						case 0:
							return ((Lambda<P, R>)constructor.newInstance()).res;
						case 1:
							return ((Lambda<P, R>)constructor.newInstance(params[0])).res;
						case 2:
							return ((Lambda<P, R>)constructor.newInstance(params[0], params[1])).res;
						case 3:
							return ((Lambda<P, R>)constructor.newInstance(params[0], params[1], params[2])).res;
						case 4:
							return ((Lambda<P, R>)constructor.newInstance(params[0], params[1], params[2], params[3])).res;
						case 5:
							return ((Lambda<P, R>)constructor.newInstance(params[0], params[1], params[2], params[3], params[4])).res;
						case 6:
							return ((Lambda<P, R>)constructor.newInstance(params[0], params[1], params[2], params[3], params[4], params[5])).res;
						case 7:
							return ((Lambda<P, R>)constructor.newInstance(params[0], params[1], params[2], params[3], params[4], params[5], params[6])).res;
						case 8:
							return ((Lambda<P, R>)constructor.newInstance(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7])).res;
						case 9:
							return ((Lambda<P, R>)constructor.newInstance(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8])).res;
						case 10:
							return ((Lambda<P, R>)constructor.newInstance(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9])).res;
						default:
							mlex=new MalformedLambdaException("Lambda created with too many parameters");
					}
				}finally{
					if(mlex != null){
						throw mlex;
					}
					usable_dynamic=true;
				}
			}
		}catch(ArrayIndexOutOfBoundsException ex){
			throw new IllegalArgumentException("Wrong number of arguments");
		}

		return null; //Unreachable but compiler complains
	}
}
