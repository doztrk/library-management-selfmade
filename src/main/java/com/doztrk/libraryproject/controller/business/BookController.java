package com.doztrk.libraryproject.controller.business;


import com.doztrk.libraryproject.payload.request.business.BookRequest;
import com.doztrk.libraryproject.payload.response.business.BookResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.service.business.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MENTOR','ANONYMOUS')")
    @GetMapping
    //  http://localhost:8080/books?q=sefiller&cat=4&author=34&publisher=42&page=1&size=10&sort=name&type=asc
    public Page<BookResponse> getBooksByPage(
            HttpServletRequest httpServletRequest,
            @RequestParam(name = "q") String query,
            @RequestParam(name = "cat") Long categoryId,
            @RequestParam(name = "author") Long authorId,
            @RequestParam(name = "publisher") Long publisherId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "20") Integer size,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "type", defaultValue = "asc") String type) {
        return bookService.getBooksByPage(httpServletRequest, query, categoryId, authorId, publisherId, page,
                size, sort, type);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MENTOR','ANONYMOUS')")
    @GetMapping("/{id}") // http://localhost:8080/books/5
    public ResponseMessage<BookResponse> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping // http://localhost:8080/books
    public ResponseMessage<BookResponse> saveBook(@RequestBody @Valid BookRequest bookRequest) { // Not needed to check http since it has PreAuthorize
        return bookService.saveBook(bookRequest);
    }

    @PreAuthorize(("hasAnyAuthority('ADMIN')"))
    @DeleteMapping("/{id}") // http://localhost:8080/books
    public ResponseMessage<BookResponse> deleteBookById(@PathVariable Long id) {
        return bookService.deleteBookById(id);

    }


}
