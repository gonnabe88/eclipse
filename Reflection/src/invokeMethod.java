import java.lang.reflect.*;

public class invokeMethod{

	public static void main(String args[])
	{
		try
		{
			System.out.println("invoke Method dynamically");
			if (args.length<3)
			{
					System.out.println("param must be 3");
					return ;			
			}
			String strClassName = args[0];
			String strMethodName = args[1];
			String strParameter = args[2];
			
			//기본 생성자 호출			
			Class ctrClass = Class.forName(strClassName);
			Constructor constructor = ctrClass.getConstructor(new Class[]{});
			Object object = constructor.newInstance(new Object[]{});
			Method method = ctrClass.getMethod(strMethodName, new Class[] { String.class });
			Object resultObj = method.invoke(object, new Object[] { strParameter });
			
			System.out.println("받은값:"+ resultObj.toString());
			
			System.out.println("---------------------------");
			//다른 생성자를 호출해봄
			Class ctrClass1 = Class.forName(strClassName);
			Constructor constructor1 = ctrClass1.getConstructor(new Class[]{String.class});
			Object object1 = constructor1.newInstance(new Object[]{"생성자가 다른거야?"});
			Method method1 = ctrClass1.getMethod(strMethodName, new Class[] { String.class,String.class });
			Object resultObj1 = method1.invoke(object1, new Object[] { strParameter,strParameter });
			
			System.out.println("받은값:"+ resultObj1.toString());
			
		}catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
		
		
	}
}