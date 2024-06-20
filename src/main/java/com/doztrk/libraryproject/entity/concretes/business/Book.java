package com.doztrk.libraryproject.entity.concretes.business;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;
@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = true)
    private Integer pageCount;

    @ManyToOne
    @JoinColumn(name = "authorId",nullable = false)
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisherId",nullable = false)
    private Publisher publisher;

    private Integer publishDate;

    @ManyToOne
    @JoinColumn(name = "categoryId",nullable = false)
    private Category category;

    @Lob
    @Column(nullable = true)
    private File image;

    @Column(nullable = false)
    private Boolean isLoanable = true;

    @Column(nullable = false)
    private String shelfCode;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private Boolean isFeatured;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private Boolean builtIn;









}
