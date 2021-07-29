package com.library.api.repository;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.model.BorrowBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowBookRepository  extends JpaRepository<BorrowBook,Long> {
    void deleteAllByBook_Id(long id);
}
