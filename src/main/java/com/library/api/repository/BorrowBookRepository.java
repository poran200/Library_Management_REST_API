package com.library.api.repository;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.model.BorrowBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowBookRepository  extends JpaRepository<BorrowBook,Long> {
    void deleteAllByBook_Id(long id);
    Optional<BorrowBook> findByBook_IdAndStudent_Username(Long book_id, String student_username);
}
