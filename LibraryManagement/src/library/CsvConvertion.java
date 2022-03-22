package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CsvConvertion {
	
	public static List<Map<String,String>> csvToListOfMap(String filename) throws FileNotFoundException {
		List<Map<String,String>> result=new ArrayList<>();
		
		
		String file="database/"+filename+".txt";
		File myObj = new File(file);
	    Scanner db = new Scanner(myObj);
	    int i=0;
	    String[] headers=new String[5];
	    while (db.hasNextLine()) {
	    	LinkedHashMap<String, String> map
            = new LinkedHashMap<String, String>();
	    	
	    	String[] splited = db.nextLine().split(",");
	    	if(i==0) {
	    		headers=splited;
	    		
	    	}else {
	    		for(int j=0;j<splited.length;j++) {
	    			map.put(headers[j], splited[j]);
	    	
	    		}
	    		result.add(map);
	    	}
	    	i++;
	        

	      }
	   
	   // System.out.print(result.get(0));
		return result;
		
	}

}
