package com.doztrk.libraryproject.payload.mappers;

import com.doztrk.libraryproject.entity.concretes.business.Category;
import com.doztrk.libraryproject.payload.request.business.CategoryRequest;
import com.doztrk.libraryproject.payload.response.business.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {



    public CategoryResponse mapCategoryToCategoryResponse(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category mapCategoryRequestToCategory(CategoryRequest categoryRequest){
        return Category.builder()
                .name(categoryRequest.getName())
                .builtIn(categoryRequest.getBuiltIn())
                .sequence(categoryRequest.getSequence())
                .build();
    }

    public Category mapCategoryUpdateRequestToCategory(CategoryRequest categoryUpdateRequest, Category category) {
        return Category.builder()
                .id(category.getId())
                .sequence(categoryUpdateRequest.getSequence())
                .builtIn(categoryUpdateRequest.getBuiltIn())
                .name(categoryUpdateRequest.getName())
                .build();
    }
}
