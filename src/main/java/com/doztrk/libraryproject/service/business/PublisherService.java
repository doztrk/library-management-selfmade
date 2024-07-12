package com.doztrk.libraryproject.service.business;

import com.doztrk.libraryproject.entity.concretes.business.Publisher;
import com.doztrk.libraryproject.exception.BadRequestException;
import com.doztrk.libraryproject.exception.ConflictException;
import com.doztrk.libraryproject.exception.ResourceNotFoundException;
import com.doztrk.libraryproject.payload.mappers.PublisherMapper;
import com.doztrk.libraryproject.payload.messages.ErrorMessages;
import com.doztrk.libraryproject.payload.messages.SuccessMessages;
import com.doztrk.libraryproject.payload.request.business.PublisherRequest;
import com.doztrk.libraryproject.payload.response.business.PublisherResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.repository.business.PublisherRepository;
import com.doztrk.libraryproject.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PageableHelper pageableHelper;
    private final PublisherMapper publisherMapper;


    public Page<PublisherResponse> getAllPublishers(Integer page, Integer size, String name, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, name, type);
        Page<Publisher> publishers = publisherRepository.findAll(pageable);

        return publishers.map(publisherMapper::mapPublisherToPublisherResponse);
    }

    public ResponseMessage<PublisherResponse> getPublisherById(Long publisherId) {
        Publisher publisher =
                publisherRepository
                        .findById(publisherId)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PUBLISHER_NOT_FOUND, publisherId)));
        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_FOUND)
                .httpStatus(HttpStatus.FOUND)
                .object(publisherMapper.mapPublisherToPublisherResponse(publisher))
                .build();
    }

    public ResponseMessage<PublisherResponse> createPublisher(PublisherRequest publisherRequest) {

        if (!publisherRepository.existsByName(publisherRequest.getName().isEmpty())) {
            throw new ConflictException(String.format(ErrorMessages.PUBLISHER_ALREADY_EXISTS, publisherRequest.getName()));
        }
        Publisher publisher = publisherMapper.mapPublisherRequestToPublisher(publisherRequest);

        publisherRepository.save(publisher);

        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_CREATED)
                .httpStatus(HttpStatus.CREATED)
                .object(publisherMapper.mapPublisherToPublisherResponse(publisher))
                .build();
    }


    public ResponseMessage<PublisherResponse> updatePublisher(Long publisherId, PublisherRequest updatePublisherRequest) {

        Publisher publisher =
                publisherRepository
                        .findById(publisherId)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PUBLISHER_NOT_FOUND, publisherId)));


        Publisher publisherToBeUpdated = publisherMapper.mapPublisherUpdateRequestToPublisher(updatePublisherRequest, publisherId);

        publisherRepository.save(publisher);

        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_UPDATED)
                .httpStatus(HttpStatus.OK)
                .object(publisherMapper.mapPublisherToPublisherResponse(publisher))
                .build();
    }


    public ResponseMessage<PublisherResponse> deletePublisher(Long publisherId) {

        Publisher publisher =
                publisherRepository
                        .findById(publisherId)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PUBLISHER_NOT_FOUND, publisherId)));
        if (!publisher.getBookList().isEmpty()) {
            throw new BadRequestException(String.format(ErrorMessages.PUBLISHER_HAS_BOOKS_ASSIGNED, publisher.getId()));
        } else if (Boolean.TRUE.equals(publisher.getBuiltIn())) {
            throw new BadRequestException(ErrorMessages.NOT_AUTHORIZED);
        } else {
            publisherRepository.deleteById(publisherId);
        }

        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_DELETED)
                .httpStatus(HttpStatus.OK)
                .object(publisherMapper.mapPublisherToPublisherResponse(publisher))
                .build();
    }
}
