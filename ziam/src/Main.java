//This Source Code is Using the Abstract Factory Pattern, DI Container, Reflection Pattern..
//Coded by KookminUniversity ComputerScience Jonghoon,Park

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
 
public class Main {
     public static void main(String[] args){
 
		JSONParser parser = new JSONParser();
		ArrayList<String> strlist = new ArrayList<String>();
		
		try { 
				//Read the configuration file, input.json
				Object obj = parser.parse(new FileReader("input.json"));
				JSONObject jsonObject = (JSONObject) obj;
		  		//Get the algorism type
				String algorism = (String) jsonObject.get("algorism");
				//Get the string lists
				JSONArray msg = (JSONArray) jsonObject.get("strings");
				Iterator<String> iterator = msg.iterator();
				while (iterator.hasNext()) {
					strlist.add(iterator.next());
			}
			//Print Which algorism will you use to operation
			System.out.println(algorism);
			
			//Using Reflection Pattern
			AlgorismImpl algo = (AlgorismImpl) Class.forName(algorism + "Algorism").newInstance();
			algo.operation(strlist);		
	 
		} catch (Exception e) {
			e.printStackTrace();
		} 
 
   }
 
}