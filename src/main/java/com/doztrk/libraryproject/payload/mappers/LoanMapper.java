package com.doztrk.libraryproject.payload.mappers;


import com.doztrk.libraryproject.entity.concretes.business.Book;
import com.doztrk.libraryproject.entity.concretes.business.Loan;
import com.doztrk.libraryproject.payload.response.business.BookResponse;
import com.doztrk.libraryproject.payload.response.business.LoanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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


}
