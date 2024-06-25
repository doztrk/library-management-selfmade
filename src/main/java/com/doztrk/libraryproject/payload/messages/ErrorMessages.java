package com.doztrk.libraryproject.payload.messages;

import org.springframework.stereotype.Component;

public class ErrorMessages {



    private ErrorMessages() {
    }

    public static final String CATEGORY_NOT_FOUND = "No Category with this ID" ;

    public static final String AUTHOR_NOT_FOUND = "No Author with this ID";

    public static final String PUBLISHER_NOT_FOUND = "No Publisher with this ID";

    public static final String BOOK_NOT_FOUND = "Book with ID %s not found";
    public static final String BOOK_ALREADY_EXISTS = "Book with name %s already exists";
    public static final String BOOK_ALREADY_EXISTS_BY_ISBN = "Book with Isbn %s already exists" ;
    public static final String BOOK_ALREADY_EXISTS_BY_SHELFCODE ="Book with this shelf code %s already exists" ;


}
