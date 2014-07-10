package es.iguanod.functional;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 *
 * @param <P>
 * 
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since
 * @version
 */
public class Lambda<P,R>{

	protected R res=null;
	//*********
	private Class cls=null;
	private Constructor constructor=null;
	private Field this$0=null;
	private int num_params;
	//*********
	private static Boolean usable_dynamic=null;
	private static Boolean usable_static=null;

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
			}catch(MalformedLambdaException | IllegalArgumentException | ReflectiveOperationException ex){
				usable_dynamic=false;
			}finally{
				return usable_dynamic;
			}
		}
	}

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
			}catch(ReflectiveOperationException | MalformedLambdaException | IllegalArgumentException ex){
				usable_static=false;
			}finally{
				return usable_static;
			}
		}
	}

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

		try{
			if(this$0 != null){
				switch(num_params){
					case 1:
						return ((Lambda<P,R>)constructor.newInstance(this$0.get(this))).res;
					case 2:
						return ((Lambda<P,R>)constructor.newInstance(this$0.get(this), params[0])).res;
					case 3:
						return ((Lambda<P,R>)constructor.newInstance(this$0.get(this), params[0], params[1])).res;
					case 4:
						return ((Lambda<P,R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2])).res;
					case 5:
						return ((Lambda<P,R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3])).res;
					case 6:
						return ((Lambda<P,R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3], params[4])).res;
					case 7:
						return ((Lambda<P,R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3], params[4], params[5])).res;
					case 8:
						return ((Lambda<P,R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3], params[4], params[5], params[6])).res;
					case 9:
						return ((Lambda<P,R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7])).res;
					case 10:
						return ((Lambda<P,R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8])).res;
					case 11:
						return ((Lambda<P,R>)constructor.newInstance(this$0.get(this), params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9])).res;
					default:
						throw new MalformedLambdaException("Lambda created with too many parameters");
				}
			}else{
				switch(num_params){
					case 0:
						return ((Lambda<P,R>)constructor.newInstance()).res;
					case 1:
						return ((Lambda<P,R>)constructor.newInstance(params[0])).res;
					case 2:
						return ((Lambda<P,R>)constructor.newInstance(params[0], params[1])).res;
					case 3:
						return ((Lambda<P,R>)constructor.newInstance(params[0], params[1], params[2])).res;
					case 4:
						return ((Lambda<P,R>)constructor.newInstance(params[0], params[1], params[2], params[3])).res;
					case 5:
						return ((Lambda<P,R>)constructor.newInstance(params[0], params[1], params[2], params[3], params[4])).res;
					case 6:
						return ((Lambda<P,R>)constructor.newInstance(params[0], params[1], params[2], params[3], params[4], params[5])).res;
					case 7:
						return ((Lambda<P,R>)constructor.newInstance(params[0], params[1], params[2], params[3], params[4], params[5], params[6])).res;
					case 8:
						return ((Lambda<P,R>)constructor.newInstance(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7])).res;
					case 9:
						return ((Lambda<P,R>)constructor.newInstance(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8])).res;
					case 10:
						return ((Lambda<P,R>)constructor.newInstance(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9])).res;
					default:
						throw new MalformedLambdaException("Lambda created with too many parameters");
				}
			}
		}catch(ArrayIndexOutOfBoundsException ex){
			throw new IllegalArgumentException("Wrong number of arguments");
		}
	}
}
