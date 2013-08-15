import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class WorstAlgorism implements AlgorismImpl{
	
	public void operation(ArrayList<String> strlist){
	
		Comparator<String> comparator = new Comparator<String>(){
			public int compare(String str1, String str2)
			{
				if(str1.length() < str2.length())
					return 1;
				else
					return 0;
			}
		};
		Collections.sort(strlist, comparator);
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
		System.out.println("The Worst Sequence of Operation is This..");
		for(String str : strlist)
		{
			System.out.println(str);
		}
		Cost(strlist);
	}

}
