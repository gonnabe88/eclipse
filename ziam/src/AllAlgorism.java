import java.util.ArrayList;


public class AllAlgorism implements AlgorismImpl{
		
	public void operation(ArrayList<String> strlist)
	{
		AlgorismImpl algo = new BestAlgorism();
		AlgorismImpl walgo = new WorstAlgorism();
		AlgorismImpl calgo = new CommonAlgorism();
		
		calgo.operation(strlist);
		walgo.operation(strlist);
		algo.operation(strlist);
	}
	
}
