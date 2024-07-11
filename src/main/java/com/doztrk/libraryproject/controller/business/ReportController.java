package com.doztrk.libraryproject.controller.business;

import com.doztrk.libraryproject.payload.response.business.BookResponse;
import com.doztrk.libraryproject.payload.response.business.ReportResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.payload.response.user.UserResponse;
import com.doztrk.libraryproject.service.business.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage<ReportResponse> getReport() {
        return reportService.getReport();
    }

    @GetMapping("/most-popular-books")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public Page<BookResponse> getMostPopularBooksByPage(
            @RequestParam(value = "amount", defaultValue = "0") Integer amount,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        return reportService.getMostPopularBooks(amount, page, size);
    }

    @GetMapping("/unreturned-books")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public Page<BookResponse> getUnreturnedBooksByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "expireDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {
        return reportService.getUnreturnedBooksByPage(page,size,sort,type);
    }
    @GetMapping("/expired-books")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public Page<BookResponse> getExpiredBooksByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "expireDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {
        return reportService.getExpiredBookByPage(page,size,sort,type);
    }

    @GetMapping("/most-borrowers")
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public Page<UserResponse> getMostBorrowers(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size){
    return reportService.getMostBorrowers(page,size);
    }


}
