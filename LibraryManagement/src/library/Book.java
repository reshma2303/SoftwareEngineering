package library;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Book {

	String book_id;
	int n_copies_available;
	String is_journal;
	


	public Book(String book_id, String n_copies_available, String is_journal) {
		this.book_id = book_id;
		this.n_copies_available =Integer.parseInt(n_copies_available);
		this.is_journal = is_journal;
	}
	
	public String getBook_id() {
		return book_id;
	}

	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	public int getN_copies_available() {
		return n_copies_available;
	}

	public void setN_copies_available(int n_copies_available) {
		this.n_copies_available = n_copies_available;
	}

	public String getIs_journal() {
		return is_journal;
	}

	public void setIs_journal(String is_journal) {
		this.is_journal = is_journal;
	}
	
	public static List<Map<String,String>> fetch_all_books_details() throws FileNotFoundException{
		List<Map<String,String>> result=CsvConvertion.csvToListOfMap("books");
		return result;
	}

	public static Map<String,String> get_book_details(String book_id,List<Map<String,String>> books) throws FileNotFoundException {
		Map<String,String> result=new HashMap<>();
		for (Map<String, String> map : books) {
			for (Map.Entry<String,String> entry : map.entrySet()) {
				if(entry.getKey().equals("book_id") && entry.getValue().equals(book_id)) {
				
					result=map;
					return result;
				}
			}
		}
		System.out.println("There is no such book with BookId: "+book_id+" in library");
		return result;
		
	}


}
