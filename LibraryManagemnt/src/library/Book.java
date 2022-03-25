package library;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Book {
	String book_id;
	String book_name;
	int n_copies_available;

	public Book(String book_id, String n_copies_available) {
		this.book_id = book_id;
		this.n_copies_available = Integer.parseInt(n_copies_available);
	}

	public String getBook_id() {
		return book_id;
	}

	public int getN_copies_available() {
		return n_copies_available;
	}

	public void setN_copies_available(int n_copies_available) {
		this.n_copies_available = n_copies_available;
	}

	// Fetching all books from database
	public static List<Map<String, String>> fetch_all_books_details() throws FileNotFoundException {
		List<Map<String, String>> result = CsvConvertion.csvToListOfMap("books_and_journals");
		return result;
	}

	// Fetching book details based on book id
	public static Map<String, String> get_book_details(String book_id, List<Map<String, String>> books)
			throws FileNotFoundException {
		return books.stream().filter(m -> m.get("book_id").toLowerCase().contains(book_id.toLowerCase()))
				.collect(Collectors.toList()).get(0);

	}
}
