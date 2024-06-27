package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.entity.concretes.business.Loan;
import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.entity.enums.RoleType;
import com.doztrk.libraryproject.exception.ResourceNotFoundException;
import com.doztrk.libraryproject.payload.mappers.LoanMapper;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import com.doztrk.libraryproject.payload.messages.SuccessMessages;
import com.doztrk.libraryproject.payload.response.business.LoanResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.repository.business.LoanRepository;
import com.doztrk.libraryproject.repository.user.UserRepository;
import com.doztrk.libraryproject.service.helper.MethodHelper;
import com.doztrk.libraryproject.service.helper.PageableHelper;
import com.doztrk.libraryproject.service.validator.LoanValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final MethodHelper methodHelper;
    private final PageableHelper pageableHelper;
    private final LoanMapper loanMapper;
    private final LoanValidator loanValidator;
    private final UserRepository userRepository;


    public ResponseMessage<Page<LoanResponse>> getAllLoansForAuthenticatedUser(HttpServletRequest httpServletRequest, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        String username = (String) httpServletRequest.getAttribute("username");
        User user = methodHelper.isUserExistByUsername(username);
        Page<Loan> loanPage = loanRepository.findByUserId(user.getId(), pageable);

        //Converts loanPage to loanResponse as Page
        Page<LoanResponse> loanResponsePage = loanPage.map(loan -> {
            LoanResponse loanResponse = loanMapper.mapLoanToLoanResponse(loan);
            setLoanResponseNotes(loanResponse, loan, user); // sets the book if the user has 'ADMIN' or 'EMPLOYEE' roles.
            return loanResponse;
        });

        return ResponseMessage.<Page<LoanResponse>>builder()
                .message(SuccessMessages.LOANS_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(loanResponsePage)
                .build();
    }

    private void setLoanResponseNotes(LoanResponse loanResponse, Loan loan, User user) {
        if (methodHelper.checkRole(user.getRoles(), RoleType.ADMIN) || methodHelper.checkRole(user.getRoles(), RoleType.EMPLOYEE)) {
            loanResponse.setNotes(loan.getNotes());
        } else {
            loanResponse.setNotes(null);
        }
    }

    public ResponseMessage<LoanResponse> getLoanForAuthenticatedUser(Long id, HttpServletRequest httpServletRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        User authenticatedUser = methodHelper.isUserExistByUsername(username);
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.LOAN_NOT_FOUND, id)));

        loanValidator.validateLoanOwner(loan, authenticatedUser); // validates if the user owns the loan, throws exception if not.
        LoanResponse loanResponse = loanMapper.mapLoanToLoanResponse(loan);
        setLoanResponseNotes(loanResponse, loan, authenticatedUser); //sets the book if the user has 'ADMIN' or 'EMPLOYEE' roles.

        return ResponseMessage.<LoanResponse>builder()
                .message(SuccessMessages.LOANS_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(loanResponse)
                .build();
    }

    public ResponseMessage<LoanResponse> getLoansForUser(Long userId, int page, int size, String sort, String type) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,userId)));
        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);
        Page<Loan> loanPage = loanRepository.findByUserId(userId,pageable);


        return null;//ResponseMessage.<LoanResponse>builder()
//                .message(SuccessMessages.LOANS_FOUND)
//                .httpStatus(HttpStatus.OK)
//                .object(loanPage.map(loan -> loanMapper.mapLoanToLoanResponse()))

    }
}
