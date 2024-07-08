package com.doztrk.libraryproject.payload.mappers;

import com.doztrk.libraryproject.entity.concretes.business.Publisher;
import com.doztrk.libraryproject.payload.request.business.PublisherRequest;
import com.doztrk.libraryproject.payload.response.business.PublisherResponse;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {


    public PublisherResponse mapPublisherToPublisherResponse(Publisher publisher) {

        return PublisherResponse.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .build();
    }

    public Publisher mapPublisherRequestToPublisher(PublisherRequest publisherRequest) {

        return Publisher.builder()
                .name(publisherRequest.getName())
                .build();
    }

    public Publisher mapUpdatePublisherRequestToPublisher(PublisherRequest updatePublisherRequest) {
        return Publisher.builder()
                .name(updatePublisherRequest.getName())
                .builtIn(updatePublisherRequest.getBuiltIn())
                .build();
    }

    public Publisher mapPublisherUpdateRequestToPublisher(PublisherRequest updatePublisherRequest, Long publisherId) {
        return Publisher.builder()
                .id(publisherId)
                .name(updatePublisherRequest.getName())
                .builtIn(updatePublisherRequest.getBuiltIn())
                .build();
    }
}
