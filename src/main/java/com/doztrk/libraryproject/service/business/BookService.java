package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.payload.mappers.BookMapper;
import com.doztrk.libraryproject.payload.response.business.BookResponse;
import com.doztrk.libraryproject.repository.business.BookRepository;
import com.doztrk.libraryproject.service.helper.MethodHelper;
import com.doztrk.libraryproject.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final MethodHelper methodHelper;
    private final PageableHelper pageableHelper;









    public Page<BookResponse> getBooksByPage(String query, Long categoryId, Long authorId, Long publisherId, Integer page, Integer size, String sort, String type) {

        if (query.isEmpty() && categoryId == null && authorId == null && publisherId == null) {
            throw new IllegalArgumentException("At least one parameter is needed");
        }
        if (categoryId != null){
            methodHelper.isCategoryExistsById(categoryId);
        }
        if (authorId != null){
            methodHelper.isAuthorExistsById(authorId);
        }
        if (publisherId != null){
            methodHelper.isPublisherExistsById(publisherId);
        }
       Pageable pageable =  pageableHelper.getPageableWithProperties(page,size,sort,type);

        r




    }

}
