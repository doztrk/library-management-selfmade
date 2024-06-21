package com.doztrk.libraryproject.payload.request.business;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookRequest {

    @NotNull
    @Size(min = 2, max = 80, message = "Book name should be between 2-80 characters")
    private String name;

    @NotNull
    @Size(min = 17,max = 17,message = "ISBN should be consisted of 17 chars")
    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d", message = "ISBN format must be 999-99-99999-99-9")
    private String isbn;

    @Min(value = 1000,message = "Publish date must be a valid year (e.g., 1950)")
    @Min(value = 9999,message = "Publish date must be a valid year (e.g., 1950)")
    private Integer pageCount;

    @NotNull
    private Long authorId;

    @NotNull
    private Long publisherId;

    private Integer publishDate;

    @NotNull
    private Long categoryId;

    private File image;

    @NotNull
    private Boolean isLoanable = true;

    @NotNull
    @Size(min = 6,max = 6,message = "Shelf code must be 6 chars")
    @Pattern(regexp = "^[A-Z]{2}-\\d{3}$", message = "Shelf code must follow the format AA-999")
    private String shelfCode;

    @NotNull
    private Boolean isActive = true;

    @NotNull
    private Boolean isFeatured;

    @NotNull
    private Boolean builtIn;


}
