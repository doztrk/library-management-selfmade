package com.doztrk.libraryproject.controller.business;


import com.doztrk.libraryproject.payload.response.business.BookResponse;
import com.doztrk.libraryproject.service.business.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MENTOR','ANONYMOUS')")
    @GetMapping("/getBooksByPage") // /books?q=sefiller&cat=4&author=34&publisher=42&page=1&size=10&sort=name&type=asc
    public Page<BookResponse> getBooksByPage(
            @RequestParam(name = "q") String query,
            @RequestParam(name = "cat") Long categoryId,
            @RequestParam(name = "author") Long authorId,
            @RequestParam(name = "publisher") Long publisherId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "type", defaultValue = "asc") String type) {
        return bookService.getBooksByPage(query, categoryId, authorId, publisherId, page,
                size, sort, type);
    }


}
