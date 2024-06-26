package com.doztrk.libraryproject.service.helper;


import com.doztrk.libraryproject.entity.concretes.business.Author;
import com.doztrk.libraryproject.entity.concretes.business.Category;
import com.doztrk.libraryproject.entity.concretes.business.Publisher;
import com.doztrk.libraryproject.entity.concretes.user.Role;
import com.doztrk.libraryproject.entity.concretes.user.User;
import com.doztrk.libraryproject.entity.enums.RoleType;
import com.doztrk.libraryproject.exception.ResourceNotFoundException;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import com.doztrk.libraryproject.repository.business.AuthorRepository;
import com.doztrk.libraryproject.repository.business.BookRepository;
import com.doztrk.libraryproject.repository.business.CategoryRepository;
import com.doztrk.libraryproject.repository.business.PublisherRepository;
import com.doztrk.libraryproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class MethodHelper {


    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final UserRepository userRepository;





    public Category isCategoryExistsById(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND));
    }


    public Author isAuthorExistsById(Long authorId) {
        return authorRepository.findById(authorId).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.AUTHOR_NOT_FOUND));
    }

    public Publisher isPublisherExistsById(Long publisherId){
        return publisherRepository.findById(publisherId).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.PUBLISHER_NOT_FOUND));
    }

    public User isUserExistByUsername(String username){
        User user =  userRepository.findByUsername(username);
        if (user.getId() == null){
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_USER_MESSAGE);
        }
        return user;
    }


    public  boolean checkRole(Set<Role> roles, RoleType roleType) {
        for (Role role : roles) {
            if (role.getRoleType().equals(roleType)) {
                return true;
            }
        }
        return false;
    }

}
