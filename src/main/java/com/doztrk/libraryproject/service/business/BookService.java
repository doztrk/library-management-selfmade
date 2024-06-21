package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.repository.business.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public Page<BookResponse> getBooksByPage(
            HttpServletRequest httpServletRequest,
            String query, Long categoryId, Long authorId,
            Long publisherId, Integer page, Integer size, String sort, String type) {


    }
}
