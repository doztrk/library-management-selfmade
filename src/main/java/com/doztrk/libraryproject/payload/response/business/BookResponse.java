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
    private String authorName;
    private String publisherName;
    private Integer publishDate;
    private String categoryName;
    private File image;
    private Boolean isLoanable;
    private String shelfCode;
    private Boolean isActive;
    private Boolean isFeatured;
    private LocalDateTime createDate;
    private Boolean builtIn;
}
