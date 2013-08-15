
public class BinaryCode {

	public static void main(String[] args) {
		String arg = "123210122";
		int int_message = Integer.parseInt(arg);
		String[] decode_msg;
		decode_msg = new String[2];		
		int [] arr_msg;
		
		arr_msg = new int[arg.length()];
		
		for(int i = 0; i<arr_msg.length ; i++)
		{	
			arr_msg[i] = (int) (int_message / Math.pow(10, arr_msg.length-i-1));
			int_message = (int) (int_message - (arr_msg[i] * Math.pow(10, arr_msg.length-i-1)));
			System.out.println(arr_msg[i]);
		}
		
		
		//System.out.println(int_message);
		

	}
/*	public BinaryCode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String[] decode(String message)
	{
		int int_message = Integer.parseInt(message);
		String[] decode_msg;
		decode_msg = new String[2];		
		
		System.out.println(decode_msg);
		
		return decode_msg;
	}
*/

}
