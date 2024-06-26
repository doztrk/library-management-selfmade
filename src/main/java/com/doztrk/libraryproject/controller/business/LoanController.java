package com.doztrk.libraryproject.controller.business;


import com.doztrk.libraryproject.payload.response.business.LoanResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.service.business.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;



    @PreAuthorize("hasAnyAuthority('MEMBER')")
    @GetMapping // http://localhost:8080/loans?page=1&size=10&sort=loanDate&type=desc
    public ResponseMessage<Page<LoanResponse>> getAllLoansForAuthenticatedUser( //TODO:PAGE çevir
                                                                                HttpServletRequest httpServletRequest,
                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "20") int size,
                                                                                @RequestParam(defaultValue = "loanDate") String sort,
                                                                                @RequestParam(defaultValue = "desc") String type){
        return loanService.getAllLoansForAuthenticatedUser(httpServletRequest,page,size,sort,type);
    }

    @PreAuthorize("hasAnyAuthority('MEMBER')")
    @GetMapping("/{id}")
    public ResponseMessage<LoanResponse> getLoanForAuthenticatedUser(@PathVariable Long id, HttpServletRequest httpServletRequest){
        return loanService.getLoanForAuthenticatedUser(id,httpServletRequest);

    }



}
