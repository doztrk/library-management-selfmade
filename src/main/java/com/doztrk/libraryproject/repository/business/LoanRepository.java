package com.doztrk.libraryproject.repository.business;

import com.doztrk.libraryproject.entity.concretes.business.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,Long> {

    Page<Loan> findByUserId(Long userId, Pageable pageable);

    Page<Loan> findByBookId(Long id, Pageable pageable);

    List<Loan> findByUserId(Long userId);




}
