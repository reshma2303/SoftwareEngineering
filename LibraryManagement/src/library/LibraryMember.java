package library;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryMember {
	String member_id;
	int n_copies_taken;
	String first_name;
	String last_name;
	boolean check_if_eligible_to_borrow;

	public LibraryMember(String member_id, String n_copies_taken, String first_name, String last_name) {

		this.member_id = member_id;
		this.n_copies_taken = Integer.parseInt(n_copies_taken);

		this.first_name = first_name;
		this.last_name = last_name;
		this.check_if_eligible_to_borrow = check_if_eligible_to_borrow(this.n_copies_taken);
	}

	public boolean isCheck_if_eligible_to_borrow() {
		return check_if_eligible_to_borrow;
	}

	public void setCheck_if_eligible_to_borrow(boolean check_if_eligible_to_borrow) {
		this.check_if_eligible_to_borrow = check_if_eligible_to_borrow;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public int getN_copies_taken() {
		return n_copies_taken;
	}

	public void setN_copies_taken(int n_copies_taken) {
		this.n_copies_taken = n_copies_taken;
	}

	public static boolean check_if_eligible_to_borrow(int n_copies_taken) {
		if (n_copies_taken >= 6) {
			return false;
		}
		return true;

	}

	public static List<Map<String, String>> fetch_all_members_details() throws FileNotFoundException {
		List<Map<String, String>> result = CsvConvertion.csvToListOfMap("members");
		return result;
	}

	public static Map<String, String> validate_membership(String member_id, List<Map<String, String>> members)
			throws FileNotFoundException {
		Map<String, String> result = new HashMap<>();
		for (Map<String, String> map : members) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().equals("member_id") && entry.getValue().equals(member_id)) {

					result = map;
					return result;
				}
			}
		}
		System.out.println("You are not a library member");
		return result;
	}

	public static void bookBorrow(List<Copy> copies, LibraryMember member, Book book,
			List<Map<String, String>> all_Copies, List<Map<String, String>> members, List<Map<String, String>> books)
			throws IOException {
		members=get_members_datbase_updated(members,member);
		 books=get_books_datbase_updated(books,book);
		 all_Copies=get_copies_datbase_updated(all_Copies,copies);
		

		System.out.println(member.getFirst_name() + " " + member.getLast_name() + " , you have borrowed "
				+ copies.size() + " copy/copies of " + book.getBook_id() + " succesfully");
		MemberOfStaff.librarian_update_catalog( all_Copies, members, books);

	}

	public static void bookReturn(List<Copy> copies, LibraryMember member, Book book,
			List<Map<String, String>> all_Copies, List<Map<String, String>> members, List<Map<String, String>> books)
			throws IOException {
		 members=get_members_datbase_updated(members,member);
		 books=get_books_datbase_updated(books,book);
		 all_Copies=get_copies_datbase_updated(all_Copies,copies);
		

	
		System.out.println(member.getFirst_name() + " " + member.getLast_name() + " , you have returned "
				+ copies.size() + " copy/copies of " + book.getBook_id() + " succesfully");
		MemberOfStaff.librarian_update_catalog( all_Copies, members, books);

	}

	public static List<Map<String, String>> get_members_datbase_updated(List<Map<String, String>> members,
			LibraryMember member) {

		for (Map<String, String> map : members) {
			if (map.get("member_id").equals(member.getMember_id())) {
				map.replace("n_items_taken", String.valueOf(member.getN_copies_taken()));
			}
		}
		return members;
	}

	public static List<Map<String, String>> get_books_datbase_updated(List<Map<String, String>> books, Book book) {

		for (Map<String, String> map : books) {
			if (map.get("book_id").equals(book.getBook_id())) {
				map.replace("n_copies_available", String.valueOf(book.getN_copies_available()));
			}
		}
		return books;
	}

	public static List<Map<String, String>> get_copies_datbase_updated(List<Map<String, String>> all_Copies,
			List<Copy> copies) {

		for (Map<String, String> map : all_Copies) {
			for (Copy copy : copies) {

				if (map.get("book_id").equals(copy.getBook_id())) {
					map.replace("is_reserved", copy.getIs_reserved());
					if(copy.getReserved_date()!=null) {
						map.replace("reserved_date", copy.getReserved_date().toString());	
					}else {
						map.replace("reserved_date","null");
					}
					
					map.replace("member_id", copy.getMember_id());

				}
			}

		}
		return all_Copies;
	}

}
