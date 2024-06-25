package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.entity.concretes.business.Book;
import com.doztrk.libraryproject.exception.BadRequestException;
import com.doztrk.libraryproject.exception.ConflictException;
import com.doztrk.libraryproject.exception.ResourceNotFoundException;
import com.doztrk.libraryproject.payload.mappers.BookMapper;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import com.doztrk.libraryproject.payload.messages.SuccessMessages;
import com.doztrk.libraryproject.payload.request.business.BookRequest;
import com.doztrk.libraryproject.payload.response.business.BookResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.repository.business.BookRepository;
import com.doztrk.libraryproject.service.helper.PageableHelper;
import com.doztrk.libraryproject.service.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final PageableHelper pageableHelper;
    private final BookValidator bookValidator;


    public Page<BookResponse> getBooksByPage(HttpServletRequest httpServletRequest,
                                             String query, Long categoryId, Long authorId, Long publisherId,
                                             Integer page, Integer size, String sort, String type) {

        if (query.isEmpty() && categoryId == null && authorId == null && publisherId == null) {
            throw new IllegalArgumentException("At least one parameter is needed");
        }
        bookValidator.validateParameters(categoryId, authorId, publisherId);

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
        bookValidator.validateParameters(bookRequest.getCategoryId(),
                bookRequest.getAuthorId(),
                bookRequest.getPublisherId());

        bookValidator.validate(bookRequest);
        Book savedBook = bookMapper.mapBookRequestToBook(bookRequest);
        savedBook.setCreateDate(LocalDateTime.now());
        bookRepository.save(savedBook);

        return ResponseMessage.<BookResponse>builder()
                .message(SuccessMessages.BOOK_SAVED)
                .object(bookMapper.mapBookToBookResponse(savedBook))
                .httpStatus(HttpStatus.CREATED)
                .build();
    }


    private void validateBookRequest(BookRequest bookRequest) {
        isBookExistsByIsbn(bookRequest.getIsbn());
        isBookExistsByShelfCode(bookRequest.getShelfCode());
        isBookExistsByBookName(bookRequest.getName());
    }

    private void isBookExistsByIsbn(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            throw new ConflictException(String.format(ErrorMessages.BOOK_ALREADY_EXISTS_BY_ISBN, isbn));
        }
    }

    private void isBookExistsByShelfCode(String shelfCode) {
        if (bookRepository.existsByShelfCode(shelfCode)) {
            throw new ConflictException(String.format(ErrorMessages.BOOK_ALREADY_EXISTS_BY_SHELFCODE, shelfCode));
        }
    }

    private void isBookExistsByBookName(String bookName) {
        if (bookRepository.existsBookByNameEqualsIgnoreCase(bookName)) {
            throw new ConflictException(String.format(ErrorMessages.BOOK_ALREADY_EXISTS, bookName));
        }
    }


    public ResponseMessage<BookResponse> deleteBookById(Long id) {
        Book bookToBeDeleted = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BOOK_NOT_FOUND));

        if (bookToBeDeleted.getIsLoanable().equals(false)) {
            throw new BadRequestException(ErrorMessages.BOOK_HAS_LOAN);
        } else {
            bookRepository.delete(bookToBeDeleted);
        }
        return ResponseMessage.<BookResponse>builder()
                .message(SuccessMessages.BOOK_DELETED)
                .httpStatus(HttpStatus.OK)
                .object(bookMapper.mapBookToBookResponse(bookToBeDeleted))
                .build();
    }
}
