package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.entity.concretes.business.Book;
import com.doztrk.libraryproject.entity.concretes.business.Loan;
import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.entity.enums.RoleType;
import com.doztrk.libraryproject.exception.BadRequestException;
import com.doztrk.libraryproject.exception.ResourceNotFoundException;
import com.doztrk.libraryproject.payload.mappers.LoanMapper;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import com.doztrk.libraryproject.payload.messages.SuccessMessages;
import com.doztrk.libraryproject.payload.request.business.LoanRequest;
import com.doztrk.libraryproject.payload.response.business.LoanResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.repository.business.BookRepository;
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
    private final BookRepository bookRepository;

    public ResponseMessage<Page<LoanResponse>> getAllLoansForAuthenticatedUser(HttpServletRequest httpServletRequest, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        String username = (String) httpServletRequest.getAttribute("username");
        User user = methodHelper.isUserExistByUsername(username);

        if (!Boolean.TRUE.equals(methodHelper.checkRole(user.getRoles(), RoleType.MEMBER))) { //checks if the MEMBER is authenticated in USER.
            throw new BadRequestException(ErrorMessages.NOT_AUTHORIZED);
        }
        Page<Loan> loanPage = loanRepository.findByUserId(user.getId(), pageable);
        //Converts loanPage to loanResponse as Page
        Page<LoanResponse> loanResponsePage = loanPage.map(loan -> {
            LoanResponse loanResponse = loanMapper.mapLoanToLoanResponseWithBook(loan);
            setLoanResponseNotes(loanResponse, loan, user); // sets the book if the user has 'ADMIN' or 'EMPLOYEE' roles.
            return loanResponse;
        });
        return ResponseMessage.<Page<LoanResponse>>builder()
                .message(SuccessMessages.LOANS_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(loanResponsePage)
                .build();
    }

    private void setLoanResponseNotes(LoanResponse loanResponse, Loan loan, User user) { // Set notes if the role is ADMIN or EMPLOYEE
        if (methodHelper.checkRole(user.getRoles(), RoleType.ADMIN) || methodHelper.checkRole(user.getRoles(), RoleType.EMPLOYEE)) {
            loanResponse.setNotes(loan.getNotes());
        } else {
            loanResponse.setNotes(null);
        }
    }

    public ResponseMessage<LoanResponse> getLoanForAuthenticatedUser(Long id, HttpServletRequest httpServletRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        User authenticatedUser = methodHelper.isUserExistByUsername(username);
        if (Boolean.TRUE.equals(methodHelper.checkRole(authenticatedUser.getRoles(), RoleType.MEMBER))) {
            throw new BadRequestException(String.format(ErrorMessages.NOT_AUTHORIZED));
        }
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.LOAN_NOT_FOUND, id)));

        loanValidator.validateLoanOwner(loan, authenticatedUser); // validates if the user owns the loan, throws exception if not.
        LoanResponse loanResponse = loanMapper.mapLoanToLoanResponseWithBook(loan);
        setLoanResponseNotes(loanResponse, loan, authenticatedUser); //sets the book if the user has 'ADMIN' or 'EMPLOYEE' roles.

        return ResponseMessage.<LoanResponse>builder()
                .message(SuccessMessages.LOANS_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(loanResponse)
                .build();
    }

    public ResponseMessage<Page<LoanResponse>> getAllLoansByUserId(Long userId, int page, int size, String sort, String type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, userId)));

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        Page<Loan> loanPage = loanRepository.findByUserId(userId, pageable);

        Page<LoanResponse> loanResponsePage = loanPage.map(loan -> {
            LoanResponse loanResponse = loanMapper.mapLoanToLoanResponseWithBook(loan);
            setLoanResponseNotes(loanResponse, loan, user);
            return loanResponse;
        });

        return ResponseMessage.<Page<LoanResponse>>builder()
                .message(SuccessMessages.LOANS_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(loanResponsePage)
                .build();
    }


    public ResponseMessage<Page<LoanResponse>> getLoansByBookId(Long bookId, int page, int size, String sort, String type) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.BOOK_NOT_FOUND, bookId)));
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        Page<Loan> loanPage = loanRepository.findByBookId(book.getId(), pageable);
        Page<LoanResponse> loanResponsePage = loanPage.map(loanMapper::mapLoanToLoanResponseWithBook);

        return ResponseMessage.<Page<LoanResponse>>builder()
                .message(SuccessMessages.LOANS_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(loanResponsePage)
                .build();
    }

    public ResponseMessage<Page<LoanResponse>> getLoanDetailsByLoanId(Long loanId, int page, int size, String sort, String type) {
        Loan loan = loanRepository
                .findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.LOAN_NOT_FOUND, loanId)));
        User user = userRepository
                .findByLoanId(
                        loan.getId()).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.LOAN_NOT_FOUND, loanId)));

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        Page<Loan> loanPage = loanRepository.findByUserId(user.getId(), pageable);

        Page<LoanResponse> loanResponsePage = loanPage.map(loanMapper::mapLoanToLoanResponseWithUser);

        return ResponseMessage.<Page<LoanResponse>>builder()
                .message(SuccessMessages.LOANS_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(loanResponsePage)
                .build();
    }

    public ResponseMessage<LoanResponse> createLoan(LoanRequest loanRequest) {
        User user = userRepository
                .findById(loanRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, loanRequest.getUserId())));

        List<Loan> loanList  = loanRepository.findByUserId(user.getId());


        loanValidator.canBorrow(user,loanList);//Check if eligible for loan, if not throw exception

        Book book = bookRepository
                .findById(loanRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.BOOK_NOT_FOUND, loanRequest.getBookId())));
        if (!book.getIsLoanable()) {
            throw new BadRequestException(ErrorMessages.BOOK_IS_NOT_ACTIVE);
        }
        loanRepository.save()
    }

    private boolean hasNoBadRecord(User user){
        l


    }

}

