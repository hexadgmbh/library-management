# Library Management
Library management is responsible to add books into library, Add new user into library, borrow and return the book by user.

# Build the Library Management
  Run `mvn clean install` from current directory

# Run project using
   - mvn clean install
   - Run individual test under test folder,  LibraryTest is starting point.


Decisions:
1. Only single instance of Library supported.
2. Replace list to map -- improve performance in borrowBook
   Currently to remove element from List - it iterate over complete list so O(N) which can be reduced to O(1)
3. Before borrow any book copy, first we should be able to add more copies for the same book so addBook needs to change first before borrowBook.
   So addBook implemented before borrowBook in BookService
