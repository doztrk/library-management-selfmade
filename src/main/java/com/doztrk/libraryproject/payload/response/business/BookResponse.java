package com.doztrk.libraryproject.payload.response.business;

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
