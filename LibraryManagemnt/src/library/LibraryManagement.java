package library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LibraryManagement {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);

		// Member logging in
		System.out.println("Enter MemberId: ");
		String member_id = sc.nextLine();

		List<Map<String, String>> members = LibraryMember.fetch_all_members_details();
		Map<String, String> member_details = LibraryMember.get_member_details(member_id, members);
		boolean is_staff = false;
		String book_journals = "";
		String copies_issues = "";
		int max_limit = 0;
		boolean check_eligible_to_borrow = false;

		// Checking whether the member is staff or not
		if (member_details.get("is_staff_member").equals("N")) {
			is_staff = false;
			max_limit = 6;
		} else {
			is_staff = true;
			max_limit = 12;
		}

		LibraryMember libraryMember = null;
		if (!member_details.isEmpty()) {
			// Library Member
			if (!is_staff) {
				libraryMember = new LibraryMember(member_details.get("member_id"), member_details.get("n_items_taken"),
						member_details.get("first_name"), member_details.get("last_name"));
				book_journals = "book(s)";
				copies_issues = "copies";
				// Staff Member
			} else {
				libraryMember = new MemberOfStaff(member_details.get("member_id"), member_details.get("n_items_taken"),
						member_details.get("first_name"), member_details.get("last_name"));
				book_journals = "book(s)/journal(s)";
				copies_issues = "copie(s)/issue(s)";

			}
			// Checking the eligibility of the member to borrow (Max Limit)
			if (libraryMember.getN_copies_taken() < max_limit) {
				check_eligible_to_borrow = true;
			}
			System.out.println(libraryMember.getFirst_name() + " " + libraryMember.getLast_name() + " ,"
					+ "Do you want to borrow or Return (b/r)?");
			String event = sc.nextLine();
			List<Map<String, String>> books = Book.fetch_all_books_details();
			List<Map<String, String>> all_copies = Copy.fetch_all_copies_details();

			// Borrowing a book or journal
			if (event.equals("b") && check_eligible_to_borrow) {
				System.out.println("Enter the " + book_journals + " name  you want to borrow: ");
				String book_name = sc.nextLine();
				List<Map<String, String>> filtered_books = books.stream()
						.filter(m -> m.get("book_name").toLowerCase().contains(book_name.toLowerCase()))
						.collect(Collectors.toList());

				System.out.println("\n");
				for (Map<String, String> book : filtered_books) {
					System.out.println(book.get("book_name") + " - " + book.get("book_id"));
				}
				System.out
						.println("Enter the " + book_journals + " Id  from the above list of " + book_journals + " : ");
				String book_id = sc.nextLine();
				Map<String, String> book_details = Book.get_book_details(book_id, books);

				if (!book_details.isEmpty()) {
					// Library Member not eligible to borrow journals
					if (book_details.get("is_journal").equals("Y") && !is_staff) {
						System.out.println(libraryMember.getFirst_name() + " " + libraryMember.getLast_name()
								+ " , you are not eligible to borrow journals");
					} else {
						Book book = new Book(book_details.get("book_id"), book_details.get("n_copies_available"));
						System.out.println("Enter number of " + copies_issues + " of " + book.getBook_id()
								+ " you want to borrow: ");
						String n_copies = sc.nextLine();

						// Check on Maximum limit
						if (libraryMember.getN_copies_taken() + Integer.parseInt(n_copies) > max_limit) {
							System.out.println(libraryMember.getFirst_name() + " " + libraryMember.getLast_name()
									+ " , you are not eligible to borrow " + n_copies + " copies of "
									+ book.getBook_id() + ".Limit is " + max_limit);
							// If number of copies needed is less than the available copies
						} else if (book.getN_copies_available() < Integer.parseInt(n_copies)) {
							System.out.println(libraryMember.getFirst_name() + " " + libraryMember.getLast_name()
									+ " ,there are only " + book.getN_copies_available() + " " + copies_issues
									+ "  available of " + book.getBook_id());
						} else {
							List<Map<String, String>> member_Copies = Copy.get_copies_details_of_book(book.getBook_id(),
									all_copies);
							member_Copies = member_Copies.subList(0, Integer.valueOf(n_copies));
							libraryMember
									.setN_copies_taken(libraryMember.getN_copies_taken() + Integer.valueOf(n_copies));
							book.setN_copies_available(book.getN_copies_available() - Integer.valueOf(n_copies));
							List<Copy> final_copies = new ArrayList<>();
							for (int i = 0; i < member_Copies.size(); i++) {
								Copy copy = new Copy(member_Copies.get(i).get("copy_id"), book.getBook_id(), "Y",
										java.time.LocalDate.now(), libraryMember.getMember_id());
								final_copies.add(copy);
							}
							if (!is_staff) {//Borrowing a book
								LibraryMember.bookBorrow(final_copies, libraryMember, book, all_copies, members, books);
							} else {//Borrowing a journal
								MemberOfStaff.journalBorrow(final_copies, libraryMember, book, all_copies, members,
										books);
							}
						}
					}
				}
			} else if (event.equals("r")) {
				List<Map<String, String>> member_Copies = Copy
						.get_copies_details_of_member(libraryMember.getMember_id(), all_copies);

				System.out.println(libraryMember.getFirst_name() + " " + libraryMember.getLast_name()
						+ " , below are the " + book_journals + " and its " + copies_issues + " you borrowed : ");

				for (Map<String, String> copy : member_Copies) {
					System.out.println(copy.get("copy_id") + "-" + copy.get("book_id"));
				}

				System.out.println("Enter the " + book_journals + " you want to return from above: ");
				String book_id = sc.nextLine();

				Map<String, String> book_details = Book.get_book_details(book_id, books);
				member_Copies = member_Copies.stream()
						.filter(m -> m.get("book_id").toLowerCase().contains(book_id.toLowerCase()))
						.collect(Collectors.toList());

				Book book = new Book(book_details.get("book_id"), book_details.get("n_copies_available"));
				System.out.println(
						"Enter number of " + copies_issues + " of " + book.getBook_id() + " you want to return: ");
				String n_copies = sc.nextLine();
				member_Copies = member_Copies.subList(0, Integer.valueOf(n_copies));
				libraryMember.setN_copies_taken(libraryMember.getN_copies_taken() - Integer.valueOf(n_copies));
				book.setN_copies_available(book.getN_copies_available() + Integer.valueOf(n_copies));
				List<Copy> final_copies = new ArrayList<>();
				for (int i = 0; i < member_Copies.size(); i++) {
					Copy copy = new Copy(member_Copies.get(i).get("copy_id"), book.getBook_id(), "N", null, "null");
					final_copies.add(copy);
				}
				if (!is_staff) {//Returning a book
					LibraryMember.bookReturn(final_copies, libraryMember, book, all_copies, members, books);
				} else {//Returning a journal
					MemberOfStaff.journalReturn(final_copies, libraryMember, book, all_copies, members, books);
				}
			} else {
				System.out.println(libraryMember.getFirst_name() + " " + libraryMember.getLast_name()
						+ " , you have already taken " + libraryMember.getN_copies_taken() + " and the limit is "
						+ max_limit);
			}
		} else {
			System.out.println("You are not a library member");
		}
	}
}
