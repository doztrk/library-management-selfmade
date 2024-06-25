package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.entity.concretes.business.Book;
import com.doztrk.libraryproject.payload.mappers.BookMapper;
import com.doztrk.libraryproject.payload.response.business.BookResponse;
import com.doztrk.libraryproject.repository.business.BookRepository;
import com.doztrk.libraryproject.service.helper.MethodHelper;
import com.doztrk.libraryproject.service.helper.PageableHelper;
import com.doztrk.libraryproject.service.validator.PropertyValidator;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public Page<BookResponse> getBooksByPage(HttpServletRequest httpServletRequest, String query, Long categoryId, Long authorId, Long publisherId, Integer page, Integer size, String sort, String type) {

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

        return booksByPage.map(bookMapper::mapBookToBookResponse);
    }

}
