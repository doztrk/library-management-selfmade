package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.entity.concretes.business.Book;
import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.entity.enums.RoleType;
import com.doztrk.libraryproject.payload.mappers.BookMapper;
import com.doztrk.libraryproject.payload.mappers.ReportMapper;
import com.doztrk.libraryproject.payload.mappers.UserMapper;
import com.doztrk.libraryproject.payload.messages.SuccessMessages;
import com.doztrk.libraryproject.payload.response.business.BookResponse;
import com.doztrk.libraryproject.payload.response.business.ReportResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.payload.response.user.UserResponse;
import com.doztrk.libraryproject.repository.business.*;
import com.doztrk.libraryproject.repository.user.UserRepository;
import com.doztrk.libraryproject.repository.user.UserRoleRepository;
import com.doztrk.libraryproject.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final LoanRepository loanRepository;
    private final UserRoleRepository userRoleRepository;
    private final ReportMapper reportMapper;
    private final PageableHelper pageableHelper;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ResponseMessage<ReportResponse> getReport() {

        Long books = bookRepository.count();
        Long authors = authorRepository.count();
        Long publishers = publisherRepository.count();
        Long categories = categoryRepository.count();
        Long loans = loanRepository.count();
        Long unreturnedBooks = loanRepository.countByReturnDateIsNull();
        Long expiredBooks = loanRepository.countByExpireDateBeforeAndReturnDateIsNull(LocalDateTime.now());
        Long members = userRoleRepository.countByRoleType(RoleType.MEMBER);

        ReportResponse reportResponse = reportMapper.mapReportToReportResponse(books, authors, publishers, categories, loans, unreturnedBooks, expiredBooks, members);

        return ResponseMessage.<ReportResponse>builder()
                .message(SuccessMessages.REPORT_RETRIEVED)
                .httpStatus(HttpStatus.OK)
                .object(reportResponse)
                .build();
    }


    public Page<BookResponse> getMostPopularBooks(Integer amount, Integer page, Integer size) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size);

        Page<Object[]> loanPage = loanRepository.findMostPopularBooks(pageable);

        //We have gotten loans, now have to get the book Id of the loans.
        // Then need to bring the book Ids,book names and isbns.
        //Then need to map it to BookResponse and return it.

        List<Long> bookIds = loanPage.stream()
                .map(loan -> (Long) loan[0])
                .toList(); //Book Id List

        List<Book> books = bookRepository.findByIdIn(bookIds); //Brings the Books from DB

        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::mapBookToBookResponseWithIdIsbnName)
                .toList(); // maps to relative Response
        return new PageImpl<>(bookResponses, pageable, loanPage.getTotalElements());
    }


    public Page<BookResponse> getUnreturnedBooksByPage(Integer page, Integer size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        Page<Object[]> loanPage = loanRepository.findUnreturnedBooks(pageable);

        List<Long> bookIds = loanPage.stream().map(loan -> (Long) loan[0]).toList();

        List<Book> books = bookRepository.findByIdIn(bookIds); //Bring the books from DB

        List<BookResponse> bookResponses = books.stream().map(bookMapper::mapBookToBookResponseWithIdIsbnName).toList();
        return new PageImpl<>(bookResponses, pageable, loanPage.getTotalElements());
    }

    public Page<BookResponse> getExpiredBookByPage(Integer page, Integer size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        Page<Object[]> loanPage = loanRepository.findUnreturnedAndExpiredBooks(pageable);

        List<Long> bookIds = loanPage.stream().map(loan -> (Long) loan[0]).toList();

        List<Book> books = bookRepository.findByIdIn(bookIds); //Bring the books from DB

        List<BookResponse> bookResponses = books.stream().map(bookMapper::mapBookToBookResponseWithIdIsbnName).toList();

        return new PageImpl<>(bookResponses, pageable, loanPage.getTotalElements());
    }

    public Page<UserResponse> getMostBorrowers(Integer page, Integer size) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size);

        Page<Object[]> loanPage = userRepository.findMostBorrowers(pageable);

        List<Long> bookIds = loanPage.stream().map(loan -> (Long) loan[0]).toList();

        List<User> books = userRepository.findByIdIn(bookIds); //Bring the users from DB

        List<UserResponse> userResponses = books.stream().map(userMapper::mapUserToUserResponse).toList();

        return new PageImpl<>(userResponses, pageable, loanPage.getTotalElements());
    }
}