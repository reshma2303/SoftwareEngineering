package library;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MemberOfStaff extends LibraryMember{

	public MemberOfStaff(String member_id, String n_copies_taken, String first_name, String last_name) {
		super(member_id, n_copies_taken, first_name, last_name);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public static void librarian_update_catalog(
			List<Map<String,String>> all_Copies,
			List<Map<String,String>> members, List<Map<String,String>> books) throws IOException {

	       
		update_table("members",members);
		update_table("books",books);
		update_table("book_copies",all_Copies);
		
		System.out.println("Librarian updated catalog successfully");
	}
	
	
	public static void update_table(String filename,List<Map<String,String>> members) throws IOException {
		String fname="database/"+filename+".txt";
		FileWriter writer = new FileWriter(fname,false);
	      
		
	
        Set<String> headers=members.get(0).keySet();
        
        String result = String.join(",", headers);
        writer.append(result);
        writer.append("\n");
        for (Map<String, String> map : members) {          
        	int i=0;
        	for (Map.Entry<String,String> entry : map.entrySet()) {
        	
        		writer.append(entry.getValue());
        		if(i!=map.size()-1) {
        			writer.append(",");  
        		}
        		i++;
        	}
        	 writer.append("\n");
        }
        writer.flush();
        writer.close();
	}

	
}
