package com.doztrk.libraryproject.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportResponse {

    private Long books;
    private Long authors;
    private Long publishers;
    private Long categories;
    private Long loans;
    private Long unreturnedBooks;
    private Long expiredBooks;
    private Long members;
}
