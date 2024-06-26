package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.entity.concretes.business.Loan;
import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.entity.enums.RoleType;
import com.doztrk.libraryproject.exception.BadRequestException;
import com.doztrk.libraryproject.exception.ResourceNotFoundException;
import com.doztrk.libraryproject.payload.mappers.LoanMapper;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import com.doztrk.libraryproject.payload.messages.SuccessMessages;
import com.doztrk.libraryproject.payload.response.business.LoanResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.repository.business.LoanRepository;
import com.doztrk.libraryproject.service.helper.MethodHelper;
import com.doztrk.libraryproject.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final MethodHelper methodHelper;
    private final PageableHelper pageableHelper;
    private final LoanMapper loanMapper;


    public ResponseMessage<List<LoanResponse>> getAllLoansForAuthenticatedUser(HttpServletRequest httpServletRequest, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        String username = (String) httpServletRequest.getAttribute("username");
        User user = methodHelper.isUserExistByUsername(username);
        Page<Loan> loanPage = loanRepository.findByUserId(user.getId(), pageable);

        List<LoanResponse> loanResponseList = loanPage.stream()
                .map(loan -> {
                    LoanResponse loanResponse = loanMapper.mapLoanToLoanResponse(loan);
                    // TODO: Further abstraction
                    setLoanResponseNotes(loanResponse, loan, user);
                    return loanResponse;
                })
                .collect(Collectors.toList());

        return ResponseMessage.<List<LoanResponse>>builder()
                .message(SuccessMessages.LOANS_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(loanResponseList)
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
        Loan loan = loanRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.LOAN_NOT_FOUND,id)));


        //TODO : Further abstraction
        if (!loan.getUser().getId().equals(authenticatedUser.getId())) {
            throw new BadRequestException(ErrorMessages.NOT_AUTHORIZED);
        }

       LoanResponse loanResponse =   loanMapper.mapLoanToLoanResponse(loan);
        setLoanResponseNotes(loanResponse,loan,authenticatedUser);

        return ResponseMessage.<LoanResponse>builder()
                .message(SuccessMessages.LOANS_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(loanResponse)
                .build();
    }
}
