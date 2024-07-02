package com.doztrk.libraryproject.payload.request.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LoanRequest {


    @NotNull
    private Long userId;

    @NotNull
    private Long bookId;


    @NotNull
    @Size(max = 300)
    private String notes;


}
