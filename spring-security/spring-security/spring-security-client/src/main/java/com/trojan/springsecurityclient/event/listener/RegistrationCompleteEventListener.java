package com.trojan.springsecurityclient.event.listener;

import com.trojan.springsecurityclient.entity.User;
import com.trojan.springsecurityclient.event.RegistrationCompleteEvent;
import com.trojan.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        //Create the verification token for the user with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(user, token);
        //Send mail to the user
        String url = event.getApplicationUrl()
                + "/verifyRegistration?token="
                + token;

        log.info("Click the link to verify you account: {}", url);

    }
}
