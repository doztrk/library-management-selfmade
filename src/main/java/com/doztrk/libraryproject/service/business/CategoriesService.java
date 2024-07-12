package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.entity.concretes.business.Book;
import com.doztrk.libraryproject.entity.concretes.business.Category;
import com.doztrk.libraryproject.exception.BadRequestException;
import com.doztrk.libraryproject.exception.ConflictException;
import com.doztrk.libraryproject.exception.ResourceNotFoundException;
import com.doztrk.libraryproject.payload.mappers.CategoryMapper;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import com.doztrk.libraryproject.payload.messages.SuccessMessages;
import com.doztrk.libraryproject.payload.request.business.CategoryRequest;
import com.doztrk.libraryproject.payload.response.business.CategoryResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.repository.business.BookRepository;
import com.doztrk.libraryproject.repository.business.CategoryRepository;
import com.doztrk.libraryproject.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoryRepository categoryRepository;
    private final PageableHelper pageableHelper;
    private final CategoryMapper categoryMapper;
    private final BookRepository bookRepository;


    public Page<CategoryResponse> getAllCategories(Integer page, Integer size, String name, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, name, type);

        Page<Category> categories = categoryRepository.findAll(pageable);

        return categories.map(categoryMapper::mapCategoryToCategoryResponse);
    }

    public ResponseMessage<CategoryResponse> getCategoryById(Long categoryId) {

        Category category =
                categoryRepository
                        .findById(categoryId).
                        orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND, categoryId)));

        CategoryResponse categoryResponse = categoryMapper.mapCategoryToCategoryResponse(category);

        return ResponseMessage.<CategoryResponse>builder()
                .message(SuccessMessages.CATEGORY_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(categoryResponse)
                .build();
    }

    public ResponseMessage<CategoryResponse> createCategory(CategoryRequest categoryRequest) {


        if (categoryRepository.existsByName(categoryRequest.getName()).isPresent()) {
            throw new ConflictException(String.format(ErrorMessages.CATEGORY_EXISTS, categoryRequest.getName()));
        }

        Category category = categoryMapper.mapCategoryRequestToCategory(categoryRequest);

        return ResponseMessage.<CategoryResponse>builder()
                .message(SuccessMessages.CATEGORY_CREATED)
                .httpStatus(HttpStatus.CREATED)
                .object(categoryMapper.mapCategoryToCategoryResponse(category))
                .build();
    }

    public ResponseMessage<CategoryResponse> updateCategory(CategoryRequest categoryUpdateRequest, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND, categoryId)));


        Category categoryToBeUpdated =
                categoryMapper
                        .mapCategoryUpdateRequestToCategory(categoryUpdateRequest, category);


        return ResponseMessage.<CategoryResponse>builder()
                .message(SuccessMessages.CATEGORY_UPDATED)
                .httpStatus(HttpStatus.OK)
                .object(categoryMapper.mapCategoryToCategoryResponse(category))
                .build();
    }

    public ResponseMessage<CategoryResponse> deleteCategory(Long categoryId) {
        Category category =
                categoryRepository
                        .findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND, categoryId)));

        boolean isRelatedToAnyBook = bookRepository.existsByCategoryId(category.getId());
        if (isRelatedToAnyBook) {
            throw new BadRequestException(String.format(ErrorMessages.CATEGORY_HAS_BOOKS_ASSIGNED, categoryId));
        }
        categoryRepository.delete(category);

        return ResponseMessage.<CategoryResponse>builder()
                .message(SuccessMessages.CATEGORY_DELETED)
                .httpStatus(HttpStatus.OK)
                .object(categoryMapper.mapCategoryToCategoryResponse(category))
                .build();

    }
}
