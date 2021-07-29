package com.library.api.repository;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.model.BookRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRequestRepository extends JpaRepository<BookRequest,Long> {
    void  deleteAllByUser_Id(Long user_id);
    Optional<BookRequest> findByUser_Username(String user_username);
}
