package com.doztrk.libraryproject.payload.request.business;

import com.doztrk.libraryproject.entity.concretes.business.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryRequest {


    @NotNull
    @Size(min = 2,max = 80)
    private String name;

    @NotNull
    private Boolean builtIn = false;


    //TODO: default should be one more than the largest number in sequence fields
    private int sequence;




}
