# Run project using
mvn clean install
Run individual test under test folder but LibraryTest is starting point.


Decisions:
1. Replace list to map -- improve performance in borrowBook
   Currently to remove element from List - it iterate over complete list so O(N) which can be reduced to O(1)

2. Before borrow any book copy, first we should be able to add more copies for the same book so addBook needs to change first before borrowBook.
   So addBook implemented before borrowBook in BookService
