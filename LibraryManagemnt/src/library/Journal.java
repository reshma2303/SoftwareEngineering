package library;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Journal {
	String journal_id;
	int n_issues_available;

	public Journal(String journal_id, int n_issues_available) {

		this.journal_id = journal_id;
		this.n_issues_available = n_issues_available;
	}

	public String getJournal_id() {
		return journal_id;
	}

	public int getN_issues_available() {
		return n_issues_available;
	}

	// Fetching all journals from database
	public static List<Map<String, String>> fetch_all_journals_details() throws FileNotFoundException {
		return Book.fetch_all_books_details();
	}

	// Fetching journal details based on journal id
	public static Map<String, String> get_journal_details(String journal_id, List<Map<String, String>> journals)
			throws FileNotFoundException {
		return Book.get_book_details(journal_id, journals);
	}

}
