package com.doztrk.libraryproject.service.validator;

import com.doztrk.libraryproject.entity.concretes.business.Book;
import com.doztrk.libraryproject.entity.concretes.business.Loan;
import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.exception.BadRequestException;
import com.doztrk.libraryproject.exception.ConflictException;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import com.doztrk.libraryproject.payload.request.business.BookRequest;
import com.doztrk.libraryproject.repository.business.BookRepository;
import com.doztrk.libraryproject.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookValidator {


    private final BookRepository bookRepository;
    private final MethodHelper methodHelper;

    public void validate(BookRequest bookRequest) {
        validateIsbn(bookRequest.getIsbn());
        validateShelfCode(bookRequest.getShelfCode());
        validateBookName(bookRequest.getName());
    }

    private void validateIsbn(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            throw new ConflictException(String.format(ErrorMessages.BOOK_ALREADY_EXISTS_BY_ISBN, isbn));
        }
    }

    private void validateShelfCode(String shelfCode) {
        if (bookRepository.existsByShelfCode(shelfCode)) {
            throw new ConflictException(String.format(ErrorMessages.BOOK_ALREADY_EXISTS_BY_SHELFCODE, shelfCode));
        }
    }

    private void validateBookName(String bookName) {
        if (bookRepository.existsBookByNameEqualsIgnoreCase(bookName)) {
            throw new ConflictException(String.format(ErrorMessages.BOOK_ALREADY_EXISTS, bookName));
        }
    }

    public void validateParameters(Long categoryId, Long authorId, Long publisherId) {
        if (categoryId != null) {
            methodHelper.isCategoryExistsById(categoryId);
        }
        if (authorId != null) {
            methodHelper.isAuthorExistsById(authorId);
        }
        if (publisherId != null) {
            methodHelper.isPublisherExistsById(publisherId);
        }
    }
}
