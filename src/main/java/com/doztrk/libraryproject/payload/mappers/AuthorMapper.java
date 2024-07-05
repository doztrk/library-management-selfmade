package com.doztrk.libraryproject.payload.mappers;

import com.doztrk.libraryproject.entity.concretes.business.Author;
import com.doztrk.libraryproject.payload.request.business.AuthorRequest;
import com.doztrk.libraryproject.payload.response.business.AuthorResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {


    public AuthorResponse mapAuthorToAuthorResponse(Author author){
        return AuthorResponse.builder()
                .name(author.getName())
                .build();
    }

    public Author mapAuthorRequestToAuthor(AuthorRequest authorRequest){
        return Author.builder()
                .name(authorRequest.getName())
                .build();
    }
}
