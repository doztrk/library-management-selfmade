package com.doztrk.libraryproject.payload.mappers;


import com.doztrk.libraryproject.entity.concretes.business.Book;
import com.doztrk.libraryproject.entity.concretes.business.Loan;
import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.payload.request.business.LoanRequest;
import com.doztrk.libraryproject.payload.response.business.BookResponse;
import com.doztrk.libraryproject.payload.response.business.LoanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LoanMapper {

    private final BookMapper bookMapper;

    public LoanResponse mapLoanToLoanResponseWithBook(Loan loan){
        return LoanResponse.builder()
                .userId(loan.getUser().getId())
                .bookId(loan.getBook().getId())
                .loanDate(loan.getLoanDate())
                .expireDate(loan.getExpireDate())
                .returnDate(loan.getReturnDate())
                .book(bookMapper.mapBookToBookResponse(loan.getBook())) // returns book object with it.
                .build();
    }

    public LoanResponse mapLoanToLoanResponse(Loan loan){
        return LoanResponse.builder()
                .userId(loan.getUser().getId())
                .bookId(loan.getBook().getId())
                .loanDate(loan.getLoanDate())
                .expireDate(loan.getExpireDate())
                .returnDate(loan.getReturnDate())
                .build();
    }

    public LoanResponse mapLoanToLoanResponseWithUser(Loan loan){
        return LoanResponse.builder()
                .userId(loan.getUser().getId())
                .bookId(loan.getBook().getId())
                .loanDate(loan.getLoanDate())
                .expireDate(loan.getExpireDate())
                .returnDate(loan.getReturnDate())
                .user(loan.getUser())
                .build();
    }

    public Loan mapLoanRequestToLoan(LoanRequest loanRequest, Book book, User user, LocalDateTime loanDate,LocalDateTime expireDate ){
        return Loan.builder()
                .book(book)
                .user(user)
                .loanDate(loanDate)
                .expireDate(expireDate)
                .returnDate(null)
                .notes(loanRequest.getNotes())
                .build();
    }


}
