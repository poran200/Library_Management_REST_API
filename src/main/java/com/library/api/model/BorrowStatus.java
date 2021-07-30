package com.library.api.model;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

public enum BorrowStatus {
    RETURNED("return"),
    BORROWED("borrow");
    private  String status;

    BorrowStatus(String status) {
        this.status = status;
    }
}
