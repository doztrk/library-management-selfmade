package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.entity.concretes.business.Book;
import com.doztrk.libraryproject.exception.ConflictException;
import com.doztrk.libraryproject.exception.ResourceNotFoundException;
import com.doztrk.libraryproject.payload.mappers.BookMapper;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import com.doztrk.libraryproject.payload.messages.SuccessMessages;
import com.doztrk.libraryproject.payload.request.business.BookRequest;
import com.doztrk.libraryproject.payload.response.business.BookResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.repository.business.BookRepository;
import com.doztrk.libraryproject.service.helper.MethodHelper;
import com.doztrk.libraryproject.service.helper.PageableHelper;
import com.doztrk.libraryproject.service.validator.PropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final MethodHelper methodHelper;
    private final PageableHelper pageableHelper;
    private final PropertyValidator propertyValidator;


    public Page<BookResponse> getBooksByPage(HttpServletRequest httpServletRequest,
                                             String query, Long categoryId, Long authorId, Long publisherId,
                                             Integer page, Integer size, String sort, String type) {

        if (query.isEmpty() && categoryId == null && authorId == null && publisherId == null) {
            throw new IllegalArgumentException("At least one parameter is needed");
        }
        propertyValidator.validateParameters(categoryId, authorId, publisherId);

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        boolean isAdmin = httpServletRequest.isUserInRole("ADMIN");

        Page<Book> booksByPage = isAdmin
                ? bookRepository.findAllBooksByPageWithAdmin(pageable, query, categoryId, authorId, publisherId)
                : bookRepository.findAllBooksActive(pageable, query, categoryId, authorId, publisherId);

        return booksByPage.map(bookMapper::mapBookToBookResponse);
    }

    public ResponseMessage<BookResponse> getBookById(Long id) {
        Book bookFound = bookRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.BOOK_NOT_FOUND, id)));
        return ResponseMessage.<BookResponse>builder()
                .message(SuccessMessages.BOOK_FOUND)
                .object(bookMapper.mapBookToBookResponse(bookFound))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<BookResponse> saveBook(BookRequest bookRequest) {
        isBookExistsByBookName(bookRequest.getName());

        Book savedBook = bookRepository.save(bookMapper.mapBookRequestToBook(bookRequest));

        return ResponseMessage.<BookResponse>builder()
                .message(SuccessMessages.BOOK_SAVED)
                .object(bookMapper.mapBookToBookResponse(savedBook))
                .httpStatus(HttpStatus.CREATED)
                .build();
    }


    private boolean isBookExistsByBookName(String bookName){
        boolean isBookExists =  bookRepository.existsBookByNameEqualsIgnoreCase(bookName);

        if (isBookExists){
            throw new ConflictException(String.format(ErrorMessages.BOOK_ALREADY_EXISTS,bookName));
        }else return false;
    }
}
