package com.doztrk.libraryproject.entity.concretes.business;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean builtIn;

    @Column(nullable = false)
    private Integer sequence;

    @OneToMany(mappedBy = "category")
    private List<Book> bookList;



}
