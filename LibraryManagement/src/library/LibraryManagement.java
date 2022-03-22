package library;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LibraryManagement {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter MemberId: ");
		String member_id = sc.nextLine();

		List<Map<String, String>> members = LibraryMember.fetch_all_members_details();

		Map<String, String> member_details = LibraryMember.validate_membership(member_id, members);
		boolean is_eligible_to_borrow = false;

		if (!member_details.isEmpty()) {
			if (member_details.get("is_staff_member").equals("N")) {
				LibraryMember libraryMember = new LibraryMember(member_details.get("member_id"),
						member_details.get("n_items_taken"), member_details.get("first_name"),
						member_details.get("last_name"));
				is_eligible_to_borrow = libraryMember.isCheck_if_eligible_to_borrow();
				if (!is_eligible_to_borrow) {
					System.out.println(libraryMember.getFirst_name() + " " + libraryMember.getLast_name()
							+ " , you have already taken " + libraryMember.getN_copies_taken() + " and the limit is 6");

				} else {
					System.out.println("Borrow or Return (b/r)?");
					String event = sc.nextLine();

					if (event.equals("b")) {

						System.out.println("Enter the bookId you want to borrow: ");
						String book_id = sc.nextLine();
						List<Map<String, String>> books = Book.fetch_all_books_details();
						Map<String, String> book_details = Book.get_book_details(book_id, books);
						if (!book_details.isEmpty()) {

							if (book_details.get("is_journal").equals("Y")) {
								System.out.println(libraryMember.getFirst_name() + " " + libraryMember.getLast_name()
										+ " , you are not eligible to borrow journals");
							} else {
								Book book = new Book(book_details.get("book_id"),
										book_details.get("n_copies_available"), book_details.get("is_journal"));
								System.out.println(
										"Enter number of copies of " + book.getBook_id() + " you want to borrow: ");
								String n_copies = sc.nextLine();
								if (Integer.parseInt(n_copies) > 6) {
									System.out.println(libraryMember.getFirst_name() + " "
											+ libraryMember.getLast_name() + " , you are not eligible to borrow "
											+ n_copies + " of " + book.getBook_id() + ".Limit is 6");

								} else if (book.getN_copies_available() < Integer.parseInt(n_copies)) {
									System.out
											.println(libraryMember.getFirst_name() + " " + libraryMember.getLast_name()
													+ " ,there are only " + book.getN_copies_available()
													+ " copies available of " + book.getBook_id());

								} else {

									List<Map<String, String>> all_copies = Copy.fetch_all_copies_details();
									List<Map<String, String>> member_Copies = Copy.get_copies_details_of_book(book.getBook_id(),
											all_copies);
									member_Copies = member_Copies.subList(0, Integer.valueOf(n_copies) - 1);
									libraryMember.setN_copies_taken(
											libraryMember.getN_copies_taken() + Integer.valueOf(n_copies));
									book.setN_copies_available(
											book.getN_copies_available() - Integer.valueOf(n_copies));
									List<Copy> final_copies = new ArrayList<>();
									for (int i = 0; i < member_Copies.size(); i++) {
										Copy copy = new Copy(member_Copies.get(i).get("copy_id"), book.getBook_id(),
												"Y", java.time.LocalDate.now(), libraryMember.getMember_id());
										final_copies.add(copy);

									}

									LibraryMember.bookBorrow(final_copies, libraryMember, book, all_copies, members,
											books);

								}
							}

						}
					}else {
						System.out.println("Enter the bookId you want to return: ");
						String book_id = sc.nextLine();
						List<Map<String, String>> books = Book.fetch_all_books_details();
						Map<String, String> book_details = Book.get_book_details(book_id, books);
						Book book = new Book(book_details.get("book_id"),
								book_details.get("n_copies_available"), book_details.get("is_journal"));
						System.out.println(
								"Enter number of copies of " + book.getBook_id() + " you want to return: ");
						String n_copies = sc.nextLine();
						List<Map<String, String>> all_copies = Copy.fetch_all_copies_details();
						List<Map<String, String>> member_Copies = Copy.get_copies_details_of_member(libraryMember.getMember_id(),
								all_copies);
						libraryMember.setN_copies_taken(
								libraryMember.getN_copies_taken() - Integer.valueOf(n_copies));
						book.setN_copies_available(
								book.getN_copies_available() + Integer.valueOf(n_copies));
						List<Copy> final_copies = new ArrayList<>();
						for (int i = 0; i < member_Copies.size(); i++) {
							Copy copy = new Copy(member_Copies.get(i).get("copy_id"), book.getBook_id(),
									"N",null, "null");
							final_copies.add(copy);

						}
						LibraryMember.bookReturn(final_copies, libraryMember, book, all_copies, members,
								books);

						
						
					}
				}

			}
		}

	}

}
