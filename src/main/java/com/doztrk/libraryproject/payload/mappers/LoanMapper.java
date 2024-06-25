package com.doztrk.libraryproject.payload.mappers;


import com.doztrk.libraryproject.entity.concretes.business.Loan;
import com.doztrk.libraryproject.payload.response.business.LoanResponse;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {


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
