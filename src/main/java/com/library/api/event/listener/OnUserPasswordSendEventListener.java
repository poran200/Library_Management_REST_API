package com.library.api.event.listener;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */


import com.library.api.event.OnUserPasswordSendEvent;
import com.library.api.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class OnUserPasswordSendEventListener implements ApplicationListener<OnUserPasswordSendEvent> {
    private final EmailService emailService;
    @Override
    @Async
    public void onApplicationEvent(OnUserPasswordSendEvent event) {
        sendNewUserPassword(event);
    }

    private void sendNewUserPassword(OnUserPasswordSendEvent event) {
         emailService.sendPasswordEmail(event.getFristName(),event.getPassword(),event.getEmail());
    }
}
