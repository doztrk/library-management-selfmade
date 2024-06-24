package com.doztrk.libraryproject.payload.messages;

import org.springframework.stereotype.Component;

public class ErrorMessages {



    private ErrorMessages() {
    }

    public static final String CATEGORY_NOT_FOUND = "No Category with this ID" ;

    public static final String AUTHOR_NOT_FOUND = "No Author with this ID";

    public static final String PUBLISHER_NOT_FOUND = "No Publisher with this ID";

}
