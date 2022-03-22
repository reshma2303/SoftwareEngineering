package library;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Copy {
	String copy_id;
	String book_id;
	String is_reserved;
	LocalDate reserved_date;
	String member_id;
	public Copy(String copy_id, String book_id, String is_reserved, LocalDate reserved_date, String member_id) {
		this.copy_id = copy_id;
		this.book_id = book_id;
		this.is_reserved = is_reserved;
		this.reserved_date = reserved_date;
		this.member_id = member_id;
	}
	
	
	public static List<Map<String,String>> fetch_all_copies_details() throws FileNotFoundException{
		List<Map<String,String>> result=CsvConvertion.csvToListOfMap("book_copies");
		return result;
	}
	
	public String getCopy_id() {
		return copy_id;
	}


	public void setCopy_id(String copy_id) {
		this.copy_id = copy_id;
	}


	public String getBook_id() {
		return book_id;
	}


	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}


	public String getIs_reserved() {
		return is_reserved;
	}


	public void setIs_reserved(String is_reserved) {
		this.is_reserved = is_reserved;
	}


	public LocalDate getReserved_date() {
		return reserved_date;
	}


	public void setReserved_date(LocalDate reserved_date) {
		this.reserved_date = reserved_date;
	}


	public String getMember_id() {
		return member_id;
	}


	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}


	public static List<Map<String,String>>  get_copies_details_of_book(String book_id,List<Map<String,String>> all_copies) throws FileNotFoundException {
		List<Map<String,String>> result= new ArrayList<>();
		
		for (Map<String, String> mp : all_copies) {

			for (Map.Entry<String,String> entry : mp.entrySet()) {
				if(entry.getKey().equals("book_id") && entry.getValue().equals(book_id)) {
				
					result.add(mp);
				}
			}
		}
		
		return result;
		
	}
	
	public static List<Map<String,String>>  get_copies_details_of_member(String member_id,List<Map<String,String>> all_copies) throws FileNotFoundException {
		List<Map<String,String>> result= new ArrayList<>();
		
		for (Map<String, String> mp : all_copies) {

			for (Map.Entry<String,String> entry : mp.entrySet()) {
				if(entry.getKey().equals("member_id") && entry.getValue().equals(member_id)) {
				
					result.add(mp);
				}
			}
		}
		
		return result;
		
	}

}
