package com.doztrk.libraryproject.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
// @JsonInclude(JsonInclude.Include.NON_NULL)  Can make it so it only brings the non-null fields in response.
public class BookResponse {

    private Long id;
    private String name;
    private String isbn;
    private Integer pageCount;
    private Long authorId;
    private Long publisherId;
    private Integer publishDate;
    private Long categoryId;
    private File image;
    private Boolean isLoanable;
    private String shelfCode;
    private Boolean isActive;
    private Boolean isFeatured;
    private LocalDateTime createDate;
    private Boolean builtIn;
}
