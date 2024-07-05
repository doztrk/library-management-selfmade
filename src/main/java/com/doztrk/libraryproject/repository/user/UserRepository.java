package com.doztrk.libraryproject.repository.user;

import com.doztrk.libraryproject.entity.concretes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // User findByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.loanList l WHERE l.id = :loanId")
    Optional<User> findByLoanId(@Param("loanId") Long loanId);

   Optional<User>  findByEmail(String email);
}
