package com.doztrk.libraryproject.payload.messages;

import org.springframework.stereotype.Component;

import java.util.Locale;

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
    public static final String BOOK_HAS_LOAN ="Book you are trying to delete has loan" ;


    public static final String NOT_FOUND_USER_MESSAGE = "User not found with id %s ";

    public static final String NOT_FOUND_USER_WITH_ROLE_MESSAGE ="The role information of the user with id %s is not role: %s" ;




}
