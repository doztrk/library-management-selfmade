package com.doztrk.libraryproject.payload.request.business;


import com.doztrk.libraryproject.entity.concretes.business.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthorRequest {

    @NotNull
    @Size(min = 4,max = 70)
    private String name;

    @NotNull
    private Boolean builtIn = false;


}
