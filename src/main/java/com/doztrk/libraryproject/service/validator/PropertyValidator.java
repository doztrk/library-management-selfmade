package com.doztrk.libraryproject.service.validator;

import com.doztrk.libraryproject.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PropertyValidator {

    private final MethodHelper methodHelper;

    public void validateParameters(Long categoryId,Long authorId,Long publisherId){
        if (categoryId != null){
            methodHelper.isCategoryExistsById(categoryId);
        }
        if (authorId != null){
            methodHelper.isAuthorExistsById(authorId);
        }
        if (publisherId != null){
            methodHelper.isPublisherExistsById(publisherId);
        }
    }
}
