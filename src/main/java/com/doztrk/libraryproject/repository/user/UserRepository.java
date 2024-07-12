package com.doztrk.libraryproject.repository.user;

import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.entity.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // User findByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.loanList l WHERE l.id = :loanId")
    Optional<User> findByLoanId(@Param("loanId") Long loanId);

    Optional<User> findByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    List<User> findByIdIn(List<Long> userId);

    @Query("SELECT u, COUNT(l) as loanCount FROM User u JOIN u.loanList l GROUP BY u.id ORDER BY loanCount DESC")
    Page<Object[]> findMostBorrowers(Pageable pageable);




}
