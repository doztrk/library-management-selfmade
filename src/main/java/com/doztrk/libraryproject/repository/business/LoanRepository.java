package com.doztrk.libraryproject.repository.business;

import com.doztrk.libraryproject.entity.concretes.business.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan,Long> {

    Page<Loan> findByUserIdEquals(Long userId, Pageable pageable);
}
