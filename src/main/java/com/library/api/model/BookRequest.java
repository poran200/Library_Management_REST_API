package com.library.api.model;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@AllArgsConstructor@NoArgsConstructor
public class BookRequest extends DateAudit {
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bookTitle;
    private String edition;
    private String authorName;
    private String status;
    @ManyToOne
    private User user;
}
