package com.doztrk.libraryproject.controller.business;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MENTOR','ANONYMOUS')")
    @GetMapping("/getBooksByPage")
    public Page<BookResponse> getBooksByPage(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "cat", required = false) Integer categoryId,
            @RequestParam(value = "author", required = false) Integer authorId,
            @RequestParam(value = "publisher", required = false) Integer publisherId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type)
     {
         return bookService.getBooksByPage(query,categoryId,authorId,publisherId,page,
                 size,sort,type);
    }


}
