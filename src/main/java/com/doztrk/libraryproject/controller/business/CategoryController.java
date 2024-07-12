package com.doztrk.libraryproject.controller.business;

import com.doztrk.libraryproject.payload.request.business.CategoryRequest;
import com.doztrk.libraryproject.payload.response.business.CategoryResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.service.business.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoriesService categoriesService;


    @GetMapping //categories?page=1&size=10&sort=name&type=asc
    public Page<CategoryResponse> getAllCategories(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "20") Integer size,
            @RequestParam(name = "sort", defaultValue = "name") String name,
            @RequestParam(name = "type", defaultValue = "asc") String type) {
        return categoriesService.getAllCategories(page, size, name, type);
    }

    @GetMapping("/{categoryId}") /// categories/4
    public ResponseMessage<CategoryResponse> getCategoryById(@PathVariable Long categoryId){
        return categoriesService.getCategoryById(categoryId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        return categoriesService.createCategory(categoryRequest);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<CategoryResponse> updateCategory(@RequestBody @Valid CategoryRequest categoryRequest, @PathVariable Long categoryId){

        return categoriesService.updateCategory(categoryRequest,categoryId);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<CategoryResponse> deleteCategory(@PathVariable Long categoryId){
        return categoriesService.deleteCategory(categoryId);
    }

}
