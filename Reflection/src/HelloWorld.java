public class HelloWorld{
	
	public HelloWorld(){
		System.out.println("��1�� ������");
	}
	
	
	public HelloWorld(String strCon){
		System.out.println("��2�� ������"+strCon);
	}

	public String process(String strInput)
	{
		System.out.println("ȣ�⼺����1:"+ strInput);	
		return ("�����߾��1");
	}
	public String process(String strInput1,String strInput2)
	{
		System.out.println("ȣ�⼺����2:"+ strInput1+strInput2);	
		return ("�����߾��2");
	}
}