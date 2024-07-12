package com.doztrk.libraryproject.payload.mappers;

import com.doztrk.libraryproject.payload.response.business.ReportResponse;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {


    public ReportResponse mapReportToReportResponse(
            Long books,
            Long authors,
            Long publishers,
            Long categories,
            Long loans,
            Long unreturnedBooks,
            Long expiredBooks,
            Long members) {
        return ReportResponse.builder()
                .books(books)
                .authors(authors)
                .publishers(publishers)
                .categories(categories)
                .loans(loans)
                .unreturnedBooks(unreturnedBooks)
                .expiredBooks(expiredBooks)
                .members(members)
                .build();
    }
}
