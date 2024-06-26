package com.doztrk.libraryproject.service.validator;


import com.doztrk.libraryproject.entity.concretes.business.Loan;
import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.exception.BadRequestException;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoanValidator {


    //Validates if the user owns the loan.
    public void validateLoanOwner(Loan loan, User user){
        if (!loan.getUser().getId().equals(user.getId())) {
            throw new BadRequestException(ErrorMessages.NOT_AUTHORIZED);
        }
    }
}
