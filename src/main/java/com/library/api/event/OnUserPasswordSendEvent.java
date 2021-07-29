package com.library.api.event;/*
 * @created 7/23/2021
 *
 * @Author Poran chowdury
 */

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnUserPasswordSendEvent extends ApplicationEvent {
    private String fristName;
    private String email;
    private String password;
    public OnUserPasswordSendEvent(String fristName, String email, String password) {
        super(email);
        this.fristName = fristName;
        this.email = email;
        this.password = password;
    }
}
