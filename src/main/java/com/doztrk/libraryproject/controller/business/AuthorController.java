package com.doztrk.libraryproject.controller.business;


import com.doztrk.libraryproject.entity.concretes.business.Author;
import com.doztrk.libraryproject.payload.request.business.AuthorRequest;
import com.doztrk.libraryproject.payload.response.business.AuthorResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.service.business.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping ///authors?page=1&size=10&sort=name&type=asc
    public Page<AuthorResponse> getAllAuthorsByPage(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "20") Integer size,
            @RequestParam(name = "sort", defaultValue = "name") String name,
            @RequestParam(name = "type", defaultValue = "asc") String type) {
        return authorService.getAllAuthorsByPage(page, size, name, type);
    }

    @GetMapping("/{authorId}")
    public ResponseMessage<AuthorResponse> getAuthorById(@PathVariable Long authorId) {
        return authorService.getAuthorById(authorId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<AuthorResponse> createAuthor(@RequestBody @Valid AuthorRequest authorRequest) {
        return authorService.createAuthor(authorRequest);
    }

    @DeleteMapping("/{authorId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<AuthorResponse> deleteAuthor(@PathVariable Long authorId) {
        return authorService.deleteAuthor(authorId);
    }


    @PutMapping("/{authorId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<AuthorResponse> updateAuthor(@RequestBody @Valid AuthorRequest updateAuthorRequest, @PathVariable Long authorId) {
        return authorService.updateAuthor(updateAuthorRequest,authorId);
    }
}
