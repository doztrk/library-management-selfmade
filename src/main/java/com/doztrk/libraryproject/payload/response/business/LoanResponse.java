package com.doztrk.libraryproject.payload.response.business;

import com.doztrk.libraryproject.entity.concretes.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanResponse {

    private Long userId;
    private Long bookId;
    private LocalDateTime loanDate;
    private LocalDateTime expireDate;
    private LocalDateTime returnDate;
    private String notes;
    private BookResponse book;
    private User user;





}
