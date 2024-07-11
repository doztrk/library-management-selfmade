    package com.doztrk.libraryproject.entity.concretes.business;

    import com.doztrk.libraryproject.entity.concretes.user.User;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import javax.persistence.*;
    import java.time.LocalDateTime;

    @Entity

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class Loan {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "userId",nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name = "bookId",nullable = false)
        private Book book;

        @Column(nullable = false)
        private LocalDateTime loanDate;

        @Column(nullable = false)
        private LocalDateTime expireDate;

        private LocalDateTime returnDate;

        @Column(nullable = false,length = 300)
        private String notes;







    }
