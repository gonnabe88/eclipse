package test;
import java.lang.reflect.*;  

import ioccontainer.IOContainer;

public class IOCContainerTest{

	public static void main( String[] args ){
		   /*
		    * We instantiate our IOC Container, and load our application-context
		    * Then we use IOCContainer.getBean() to inject our Band and BandMember bean 
		    * configuration attributes.
		    */
		   IOContainer IOCContainer = new IOContainer( "IOC-application-context.xml" );
		   

		   System.out.println(IOCContainer.getBean( "sort" ).toString());
		   //Sort sort = new QSort();
		   Class<?> c = null;
		try {
			//c = Class.forName("objects.QSort");
			c = Class.forName(IOCContainer.getBean( "sort" ).toString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("No Such class!");
		}
		
		
		   Object o = null;
		try {
			o = c.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		   Method m = null;
		try {
			m = o.getClass().getDeclaredMethod("sort");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		   try {
			m.invoke(o);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
