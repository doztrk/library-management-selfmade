package com.doztrk.libraryproject.controller.business;

import com.doztrk.libraryproject.payload.request.business.PublisherRequest;
import com.doztrk.libraryproject.payload.response.business.PublisherResponse;
import com.doztrk.libraryproject.payload.response.business.ResponseMessage;
import com.doztrk.libraryproject.repository.business.PublisherRepository;
import com.doztrk.libraryproject.service.business.PublisherService;
import com.doztrk.libraryproject.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherRepository publisherRepository;
    private final MethodHelper methodHelper;
    private final PublisherService publisherService;

    @GetMapping ///publishers?page=1&size=10&sort=name&type=asc
    public Page<PublisherResponse> getAllPublishers(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "20") Integer size,
            @RequestParam(name = "sort", defaultValue = "name") String name,
            @RequestParam(name = "type", defaultValue = "asc") String type) {
        return publisherService.getAllPublishers(page, size, name, type);
    }

    @GetMapping("/{publisherId}") ///publishers/4
    public ResponseMessage<PublisherResponse> getPublisherById(@PathVariable Long publisherId) {
        return publisherService.getPublisherById(publisherId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<PublisherResponse> createPublisher(@RequestBody @Valid PublisherRequest publisherRequest) {
        return publisherService.createPublisher(publisherRequest);
    }

    @PutMapping("/{publisherId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<PublisherResponse> updatePublisher(@RequestBody @Valid PublisherRequest publisherRequest,
                                                              @PathVariable Long publisherId) {
        return publisherService.updatePublisher(publisherId, publisherRequest);
    }

    @DeleteMapping("/{publisherId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<PublisherResponse> deletePublisher(@PathVariable Long publisherId) {
        return publisherService.deletePublisher(publisherId);
    }


}
