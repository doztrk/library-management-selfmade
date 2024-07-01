package com.doztrk.libraryproject.repository.business;
import com.doztrk.libraryproject.entity.concretes.business.Book;
import com.doztrk.libraryproject.payload.response.business.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book,Long> {




    // TODO :Solve  IllegalArgumentException
    @Query(value = "SELECT b FROM Book b " +
            "WHERE (b.active = true) " +
            "AND (:query IS NULL OR (b.name LIKE %:query% OR b.author.name LIKE %:query% OR b.isbn LIKE %:query% OR b.publisher.name LIKE %:query%)) " +
            "AND (:categoryId IS NULL OR b.category.id = :categoryId) " +
            "AND (:authorId IS NULL OR b.author.id = :authorId) " +
            "AND (:publisherId IS NULL OR b.publisher.id = :publisherId)")
    Page<Book> findAllBooks(Pageable pageable, String query, Long categoryId, Long authorId, Long publisherId);


    // TODO :Solve  IllegalArgumentException
    @Query(value = "SELECT b FROM Book b " +
            "WHERE (:query IS NULL OR (b.name LIKE %:query% OR b.author.name LIKE %:query% OR b.isbn LIKE %:query% OR b.publisher.name LIKE %:query%)) " +
            "AND (:categoryId IS NULL OR b.category.id = :categoryId) " +
            "AND (:authorId IS NULL OR b.author.id = :authorId) " +
            "AND (:publisherId IS NULL OR b.publisher.id = :publisherId)")
    Page<Book> findAllBooksAdmin(Pageable pageable, String query, Long categoryId, Long authorId, Long publisherId);


    boolean existsBookByNameEqualsIgnoreCase(String bookName);

    boolean existsByIsbn(String isbn);

    boolean existsByShelfCode(String shelfCode);




}
