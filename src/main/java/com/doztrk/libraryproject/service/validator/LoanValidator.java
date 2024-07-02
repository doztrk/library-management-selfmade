package com.doztrk.libraryproject.service.validator;


import com.doztrk.libraryproject.entity.concretes.business.Loan;
import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.exception.BadRequestException;
import com.doztrk.libraryproject.exception.ConflictException;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import com.doztrk.libraryproject.repository.business.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class LoanValidator {
    private final LoanRepository loanRepository;


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

    public Map<String, Integer> scoreCalculator(User user) {
        Map<String, Integer> borrowingLimits = new HashMap<>();

        int score = user.getScore();
        if (score >= 2) {
            borrowingLimits.put("books", 5);
            borrowingLimits.put("days", 20);
        } else if (score == 1) {
            borrowingLimits.put("books", 4);
            borrowingLimits.put("days", 15);
        } else if (score == 0) {
            borrowingLimits.put("books", 3);
            borrowingLimits.put("days", 10);
        } else if (score == -1) {
            borrowingLimits.put("books", 2);
            borrowingLimits.put("days", 6);
        } else if (score == -2) {
            borrowingLimits.put("books", 1);
            borrowingLimits.put("days", 3);
        }

        return borrowingLimits;
    }
}