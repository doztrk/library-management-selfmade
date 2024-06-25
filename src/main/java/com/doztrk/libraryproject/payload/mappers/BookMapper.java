package com.doztrk.libraryproject.payload.mappers;


import com.doztrk.libraryproject.entity.concretes.business.Book;
import com.doztrk.libraryproject.payload.request.business.BookRequest;
import com.doztrk.libraryproject.payload.response.business.BookResponse;
import org.springframework.stereotype.Component;


@Component
public class BookMapper {

    public BookResponse mapBookToBookResponse(Book book){
        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .isbn(book.getIsbn())
                .pageCount(book.getPageCount())
                .publishDate(book.getPublishDate())
                .categoryId(book.getCategory().getId())
                .publisherId(book.getPublisher().getId())
                .authorId(book.getAuthor().getId())
                .image(book.getImage())
                .isLoanable(book.getIsLoanable())
                .shelfCode(book.getShelfCode())
                .isActive(book.getIsActive())
                .isFeatured(book.getIsFeatured())
                .createDate(book.getCreateDate())
                .builtIn(book.getBuiltIn())
                .build();
    }

    public Book mapBookRequestToBook(BookRequest bookRequest){
        return Book.builder()
                .name(bookRequest.getName())
                .isbn(bookRequest.getIsbn())
                .pageCount(bookRequest.getPageCount())
                .image(bookRequest.getImage())
                .isLoanable(bookRequest.getIsLoanable())
                .shelfCode(bookRequest.getShelfCode())
                .isActive(bookRequest.getIsActive())
                .isFeatured(bookRequest.getIsFeatured())
                .builtIn(bookRequest.getBuiltIn())
                .build();
    }
}
