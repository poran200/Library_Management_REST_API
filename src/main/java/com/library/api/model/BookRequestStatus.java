package com.library.api.model;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

public enum BookRequestStatus {
    PENDING("pending"),
    ACCEPT("accept"),
    REJECT("reject");
    String status;
    BookRequestStatus(String status) {
        this.status = status;
    }
}
