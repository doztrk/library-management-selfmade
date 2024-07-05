package com.doztrk.libraryproject.payload.request.business;

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
public class PublisherRequest {

    @NotNull
    @Size(min = 2,max = 50)
    private String name;

    @NotNull
    private Boolean builtIn = false;
}
