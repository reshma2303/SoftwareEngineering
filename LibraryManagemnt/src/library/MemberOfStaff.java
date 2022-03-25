package library;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MemberOfStaff extends LibraryMember {
	public MemberOfStaff(String member_id, String n_copies_taken, String first_name, String last_name) {
		super(member_id, n_copies_taken, first_name, last_name);
	}

	// Borrowing a journal
	public static void journalBorrow(List<Copy> copies, LibraryMember member, Book book,
			List<Map<String, String>> all_Copies, List<Map<String, String>> members, List<Map<String, String>> books)
			throws IOException {
		LibraryMember.bookBorrow(copies, member, book, all_Copies, members, books);
	}

	// Returning a journal
	public static void journalReturn(List<Copy> copies, LibraryMember member, Book book,
			List<Map<String, String>> all_Copies, List<Map<String, String>> members, List<Map<String, String>> books)
			throws IOException {
		LibraryMember.bookReturn(copies, member, book, all_Copies, members, books);
	}

	// Librarian updates database(catalog)
	public static void librarian_update_catalog(List<Map<String, String>> all_Copies, List<Map<String, String>> members,
			List<Map<String, String>> books) throws IOException {
		update_table("members", members);
		update_table("books_and_journals", books);
		update_table("copies", all_Copies);
		System.out.println("Librarian updated catalog successfully");
	}

	// Write data into CSV file (database)
	public static void update_table(String filename, List<Map<String, String>> members) throws IOException {
		String fname = "database/" + filename + ".txt";
		FileWriter writer = new FileWriter(fname, false);
		Set<String> headers = members.get(0).keySet();
		String result = String.join(",", headers);
		writer.append(result);
		writer.append("\n");
		for (Map<String, String> map : members) {
			int i = 0;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				writer.append(entry.getValue());
				if (i != map.size() - 1) {
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
