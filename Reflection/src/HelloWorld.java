public class HelloWorld{
	
	public HelloWorld(){
		System.out.println("제1의 생성자");
	}
	
	
	public HelloWorld(String strCon){
		System.out.println("제2의 생성자"+strCon);
	}

	public String process(String strInput)
	{
		System.out.println("호출성공됨1:"+ strInput);	
		return ("성공했어요1");
	}
	public String process(String strInput1,String strInput2)
	{
		System.out.println("호출성공됨2:"+ strInput1+strInput2);	
		return ("성공했어요2");
	}
}