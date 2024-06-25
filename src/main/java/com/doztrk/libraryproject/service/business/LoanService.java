package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.entity.concretes.business.Loan;
import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.entity.enums.RoleType;
import com.doztrk.libraryproject.payload.mappers.LoanMapper;
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



    public ResponseMessage<List<LoanResponse>> getAllLoansForUser(HttpServletRequest httpServletRequest, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        String username = (String) httpServletRequest.getAttribute("username");
        User user = methodHelper.isUserExistByUsername(username);
        Page<Loan> loanPage = loanRepository.findByUserId(user.getId(), pageable);

        List<LoanResponse> loanResponseList = loanPage.stream()
                .map(loan -> {
                    LoanResponse loanResponse = loanMapper.mapLoanToLoanResponse(loan);
                    // TODO: Further abstraction
                    if (user.getRoles().equals(RoleType.EMPLOYEE) || user.getRoles().equals(RoleType.ADMIN)) {
                        loanResponse.setNotes(loan.getNotes());
                    } else {
                        loanResponse.setNotes(null);
                    }
                    return loanResponse;
                })
                .collect(Collectors.toList());

        return ResponseMessage.<List<LoanResponse>>builder()
                .message(SuccessMessages.LOANS_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(loanResponseList)
                .build();
    }
}
