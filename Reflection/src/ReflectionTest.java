import java.lang.reflect.*;  

class ReflectionModel   
{  
    private String name;  
    private int age;  
    public String email;  
    private String phone;  
    public String test;
      
    public ReflectionModel() {  
        //super();  
        // TODO Auto-generated constructor stub  
    }  
      
    public ReflectionModel(String name, int age, String email, String phone) {  
        this.name = name;  
        this.age = age;  
        this.email = email;  
        this.phone = phone;  
    }  
    
    public void kekeke(String name)
    {
    	this.name="kekeke";
    }
}
      
public class ReflectionTest   
{  
    /** 
     *  
     */  
    public ReflectionTest() {  
        //super();  
        // TODO Auto-generated constructor stub  
    }  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args)   
    {  
        // TODO Auto-generated method stub  
          
        ReflectionModel rModel = new ReflectionModel();    
        Class cls = rModel.getClass();  
          
        // Base  
        System.out.println("------------------ 클래스 --------------------");  
        System.out.println("Class Name: " + cls.getName());  
        System.out.println("Class Super Class: " + cls.getSuperclass());  
          
        // Modifiers   
        System.out.println("------------------ 접근자 --------------------");  
        int mods = cls.getModifiers();  
        System.out.println("Mod: " + mods);  
        System.out.println("Class is Public: " + Modifier.isPublic(mods));  
        System.out.println("Class is Protected: " + Modifier.isProtected(mods));  
        System.out.println("Class is Private: " + Modifier.isPrivate(mods));  
        System.out.println("Class is Final: " + Modifier.isFinal(mods));  
        System.out.println("Class is Static: " + Modifier.isStatic(mods));  
        System.out.println("Class is Abstract: " + Modifier.isAbstract(mods));   
        System.out.println("Class is Interface: " + Modifier.isInterface(mods));   
          
        // Fields    
        System.out.println("------------------ 필드 --------------------");  
        Field[] fields = cls.getDeclaredFields();  
        for(int i = 0; i < fields.length; i++)   
        {  
            Field field = fields[i];  
            Class type = field.getType();  
            String name = field.getName();  
            System.out.println(field.getModifiers()+":"+Modifier.toString(field.getModifiers()) + " " + type.getName() + " " + name + ";" );  
         }  
          
        // Constructors   
        System.out.println("------------------ 생성자 --------------------");  
        Constructor[] constructors = cls.getDeclaredConstructors();     
        for(int i = 0; i < constructors.length; i++)   
        {  
            Constructor cons = constructors[i];  
            String name = cons.getName();  
            System.out.print(Modifier.toString(cons.getModifiers()) + " " + name + "(");  
             
            Class[] paramTypes = cons.getParameterTypes();  
            for(int j = 0; j < paramTypes.length; j++) {  
                if(j > 0)  
                    System.out.print(", ");  
                System.out.print(paramTypes[j].getName());  
            }  
            System.out.println(");");  
        }  
          
        // Methods   
        System.out.println("------------------ 메쏘드 --------------------");  
        Method[] methods = cls.getDeclaredMethods();      
        for(int i = 0; i < methods.length; i++)   
        {  
            Method method = methods[i];  
            Class retType = method.getReturnType();  
            String name = method.getName();  
            System.out.print(Modifier.toString(method.getModifiers())+ " " + retType.getName() + " " + name + "(");  
            //System.out.print("  " + retType.getName() + "  " + name + "(");  
  
            // print parameter types  
            Class[] paramTypes = method.getParameterTypes();  
            for(int j = 0; j < paramTypes.length; j++) {  
                if (j > 0)  
                    System.out.print(", ");  
               System.out.print(paramTypes[j].getName());  
            }  
            System.out.println(");");  
        }  
        
        //getFields vs getDeclaredFields
        System.out.println("------------------ getFields vs getDeclaredFields --------------------");  
        Field[] fields1 = cls.getDeclaredFields();  
        Field[] fields2 = cls.getFields();
        for(int i = 0; i < fields1.length; i++)   
        {  
            Field field = fields1[i];  
            Class type = field.getType();  
            String name = field.getName();  
            System.out.println(Modifier.toString(field.getModifiers()) + "[getDeclaredFields]" + type.getName() + " " + name + ";" );  
         } 
         
         for(int j = 0; j < fields2.length; j++)   
        {  
            Field field2 = fields2[j];  
            Class type = field2.getType();  
            String name = field2.getName();  
            System.out.println(Modifier.toString(field2.getModifiers()) + " [getFields]" + type.getName() + " " + name + ";" );  
         } 
    }  
}  