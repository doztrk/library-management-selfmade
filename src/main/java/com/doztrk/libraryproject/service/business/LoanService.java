package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.repository.business.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

}
