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



    @Query("SELECT b FROM Book b WHERE " +
            "(b.name LIKE %:query% OR b.author.name LIKE %:query% OR b.isbn LIKE %:query% OR b.publisher.name LIKE %:query%) " +
            "AND b.category.id = :categoryId " +
            "AND b.author.id = :authorId " +
            "AND b.publisher.id = :publisherId")
    Page<Book> findAllBooksByPageWithAdmin(Pageable pageable, String query, Long categoryId, Long authorId, Long publisherId);

    @Query("SELECT b FROM Book b " +
            "WHERE b.active = true " +
            "AND (b.name LIKE %:query% OR b.author.name LIKE %:query% OR b.isbn LIKE %:query% OR b.publisher.name LIKE %:query%) " +
            "AND b.category.id = :categoryId " +
            "AND b.author.id = :authorId " +
            "AND b.publisher.id = :publisherId")
    Page<Book> findAllBooksActive(Pageable pageable, String query, Long categoryId, Long authorId, Long publisherId);

    boolean existsBookByNameEqualsIgnoreCase(String bookName);

    boolean existsByIsbn(String isbn);

    boolean existsByShelfCode(String shelfCode);




}
