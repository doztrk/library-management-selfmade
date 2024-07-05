package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.entity.concretes.business.Author;
import com.doztrk.libraryproject.exception.BadRequestException;
import com.doztrk.libraryproject.exception.ConflictException;
import com.doztrk.libraryproject.exception.ResourceNotFoundException;
import com.doztrk.libraryproject.payload.mappers.AuthorMapper;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import com.doztrk.libraryproject.payload.messages.SuccessMessages;
import com.doztrk.libraryproject.payload.request.business.AuthorRequest;
import com.doztrk.libraryproject.payload.response.business.AuthorResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.repository.business.AuthorRepository;
import com.doztrk.libraryproject.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final PageableHelper pageableHelper;
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;


    public Page<AuthorResponse> getAllAuthorsByPage(Integer page, Integer size, String name, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, name, type);
        Page<Author> authors = authorRepository.findAll(pageable);
        return authors.map(authorMapper::mapAuthorToAuthorResponse);
    }


    public ResponseMessage<AuthorResponse> getAuthorById(Long authorId) {
        Author author = authorRepository
                .findById(authorId).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.AUTHOR_NOT_FOUND, authorId)));

        AuthorResponse authorResponse = authorMapper.mapAuthorToAuthorResponse(author);

        return ResponseMessage.<AuthorResponse>builder()
                .message(SuccessMessages.AUTHOR_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(authorResponse)
                .build();
    }


    public ResponseMessage<AuthorResponse> createAuthor(AuthorRequest authorRequest) {

        Author author = authorMapper.mapAuthorRequestToAuthor(authorRequest);

        if (!authorRepository.existsByName(authorRequest.getName().isEmpty())) {
            throw new BadRequestException(String.format(ErrorMessages.AUTHOR_ALREADY_EXISTS, authorRequest.getName()));
        }

        authorRepository.save(author);

        AuthorResponse authorResponse = authorMapper.mapAuthorToAuthorResponse(author);

        return ResponseMessage.<AuthorResponse>builder()
                .message(SuccessMessages.AUTHOR_FOUND)
                .httpStatus(HttpStatus.CREATED)
                .object(authorResponse)
                .build();
    }

    public ResponseMessage<AuthorResponse> deleteAuthor(Long authorId) {

        Author author =
                authorRepository.findById(authorId)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.AUTHOR_NOT_FOUND, authorId)));

        if (!author.getBookList().isEmpty()) {
            throw new BadRequestException(String.format(ErrorMessages.AUTHOR_HAS_BOOKS_ASSIGNED, author.getId()));
        }
        authorRepository.delete(author);
        AuthorResponse authorResponse = authorMapper.mapAuthorToAuthorResponse(author);
        return ResponseMessage.<AuthorResponse>builder()
                .message(SuccessMessages.AUTHOR_DELETED)
                .httpStatus(HttpStatus.OK)
                .object(authorResponse)
                .build();
    }
}
