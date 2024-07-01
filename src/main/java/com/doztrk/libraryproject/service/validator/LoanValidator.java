package com.doztrk.libraryproject.service.validator;


import com.doztrk.libraryproject.entity.concretes.business.Loan;
import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.exception.BadRequestException;
import com.doztrk.libraryproject.exception.ConflictException;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class LoanValidator {


    //Validates if the user owns the loan.
    public void validateLoanOwner(Loan loan, User user) {
        if (!loan.getUser().getId().equals(user.getId())) {
            throw new BadRequestException(ErrorMessages.NOT_AUTHORIZED);
        }
    }


    public boolean canBorrow(User user, List<Loan> loanList) {
        for (Loan loan : loanList) {
            if (!user.getId().equals(loan.getUser().getId())) {
                throw new ConflictException(String.format(ErrorMessages.USER_AND_LOAN_NO_MATCH, user.getId()));
            }
            if (loan.getExpireDate().isAfter(loan.getReturnDate())) {
                throw new BadRequestException(ErrorMessages.PAST_DUE_LOAN);
            }
        }
        return true;
    }
}