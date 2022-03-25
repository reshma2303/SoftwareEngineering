package library;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	// Fetch all copies from database
	public static List<Map<String, String>> fetch_all_copies_details() throws FileNotFoundException {
		return CsvConvertion.csvToListOfMap("copies");
	}

	public String getCopy_id() {
		return copy_id;
	}

	public String getIs_reserved() {
		return is_reserved;
	}

	public LocalDate getReserved_date() {
		return reserved_date;
	}

	public String getMember_id() {
		return member_id;
	}

	// Fetching copies based on book id
	public static List<Map<String, String>> get_copies_details_of_book(String book_id,
			List<Map<String, String>> all_copies) throws FileNotFoundException {
		return all_copies.stream().filter(
				m -> m.get("book_id").toLowerCase().contains(book_id.toLowerCase()) && m.get("is_reserved").equals("N"))
				.collect(Collectors.toList());
	}

	// Fetching copies based on member id who borrowed
	public static List<Map<String, String>> get_copies_details_of_member(String member_id,
			List<Map<String, String>> all_copies) throws FileNotFoundException {
		return all_copies.stream().filter(m -> m.get("member_id").toLowerCase().contains(member_id.toLowerCase()))
				.collect(Collectors.toList());
	}
}
