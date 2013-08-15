import java.util.ArrayList;
import java.util.Iterator;

public class CommonAlgorism implements AlgorismImpl{
	
	public void operation(ArrayList<String> strlist){	
		print_seq(strlist);		
	}
	
	private void Cost(ArrayList<String> strlist)
	{
		int cost = 0;
		int accumulated_cost = 0;
		Iterator<String> iter = strlist.iterator();
		
		while(iter.hasNext())
		{
			cost = cost + iter.next().toString().length();
			if(cost != 0 && cost != strlist.get(0).length())
				accumulated_cost = accumulated_cost + cost;
		}
		
		System.out.println("Total Cost of This Sequence is : " + accumulated_cost);
	}
	
	private void print_seq(ArrayList<String> strlist)
	{
		System.out.println();
		System.out.println("The Common Sequence of Operation is This..");
		for(String str : strlist)
		{
			System.out.println(str);
		}
		Cost(strlist);
	}

}
