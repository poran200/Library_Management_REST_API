package com.library.api.model;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;


@Data@NoArgsConstructor@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String isbn;
    private String title;
    private String authorName;
    private String edition;
    private String bookSelfNo;
    private int rowNo;
    private int columnNo;
    private int noOfCopies;
    private String status;

}
