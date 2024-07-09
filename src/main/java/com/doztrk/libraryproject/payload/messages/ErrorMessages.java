package com.doztrk.libraryproject.payload.messages;

public class ErrorMessages {


    public static final String EMAIL_NOT_FOUND = "Email with this %s field not found" ;

    private ErrorMessages() {
    }

    public static final String CATEGORY_NOT_FOUND = "No Category with this ID %s";

    public static final String AUTHOR_NOT_FOUND = "No Author with this ID %s";
    public static final String AUTHOR_ALREADY_EXISTS = "Author with this name %s already exists";
    public static final String AUTHOR_HAS_BOOKS_ASSIGNED = "Author with this name %s has books assigned to it";


    public static final String PUBLISHER_NOT_FOUND = "No Publisher with this ID %s";
    public static final String PUBLISHER_ALREADY_EXISTS = "Publisher with the name %s already exists";


    public static final String BOOK_NOT_FOUND = "Book with ID %s not found";
    public static final String BOOK_ALREADY_EXISTS = "Book with name %s already exists";
    public static final String BOOK_ALREADY_EXISTS_BY_ISBN = "Book with Isbn %s already exists";
    public static final String BOOK_ALREADY_EXISTS_BY_SHELFCODE = "Book with this shelf code %s already exists";
    public static final String BOOK_IS_NOT_ACTIVE = "Book with id %s is not active and cannot be borrowed";


    public static final String BOOK_HAS_LOAN = "Book you are trying to delete has loan";
    public static final String LOAN_NOT_FOUND = "Loan with id %s is not found";

    public static final String NOT_AUTHORIZED = "Not authorized for this action, builtIn value is set to true";

    public static final String BOOK_BUILTIN = "Book with id %s has built-in true";

    public static final String USER_NOT_FOUND_WITH_EMAIL = "User not found with email %s";
    public static final String USER_NOT_FOUND_WITH_ID = "User not found with ID %s";



    public static final String NOT_FOUND_USER_MESSAGE = "User not found with id %s ";

    public static final String NOT_FOUND_USER_WITH_ROLE_MESSAGE = "The role information of the user with id %s is not role: %s";
    public static final String USER_AND_LOAN_NO_MATCH = "Loan not found with user ID %s";
    public static final String PAST_DUE_LOAN = "User with id %s has past due book return date";
    public static final String EXCEEDED_LOAN_LIMIT = "User has reached the maximum number of borrowed books";


    public static final String PUBLISHER_HAS_BOOKS_ASSIGNED = "Pushier with id %s has books assigned to it.";

    public static final String CATEGORY_EXISTS ="Category you are trying to create with name %s already exists" ;
    public static final String CATEGORY_HAS_BOOKS_ASSIGNED = "Category you are trying to delete with ID %s  has books assigned to it";

    public static final String ALREADY_REGISTER_MESSAGE_PHONE = "A User with this phone %s already exists";
    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "A User with this E-mail %s already exists";


}
