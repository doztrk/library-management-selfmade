package com.doztrk.libraryproject.repository.business;

import com.doztrk.libraryproject.entity.concretes.business.Book;
import com.doztrk.libraryproject.entity.concretes.business.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    Page<Loan> findByUserId(Long userId, Pageable pageable);

    Page<Loan> findByBookId(Long id, Pageable pageable);

    List<Loan> findByUserId(Long userId);


    Long countByReturnDateIsNull();

    Long countByExpireDateBeforeAndReturnDateIsNull(LocalDateTime date);

    @Query("SELECT l.book.id, COUNT(l) as loanCount FROM Loan l GROUP BY l.book.id ORDER BY loanCount DESC")
    Page<Object[]> findMostPopularBooks(Pageable pageable);

    //We could deduct Id, make  it "SELECT l.book, ...." to retrieve book objects but wanted to do it in this way.
    @Query("SELECT l.book FROM Loan l WHERE l.returnDate IS NULL ORDER BY l.expireDate DESC")
    Page<Object[]> findUnreturnedBooks(Pageable pageable);

    @Query("SELECT l.book FROM Loan l WHERE l.returnDate IS NULL AND l.expireDate < CURRENT_TIMESTAMP ORDER BY l.expireDate DESC")
    Page<Object[]> findUnreturnedAndExpiredBooks(Pageable pageable);

}
