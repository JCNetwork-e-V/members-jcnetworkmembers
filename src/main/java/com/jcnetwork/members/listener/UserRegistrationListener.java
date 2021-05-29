package com.jcnetwork.members.listener;

import com.jcnetwork.members.model.event.OnUserRegistrationCompleteEvent;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.UserService;
import com.jcnetwork.members.service.MailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener implements ApplicationListener<OnUserRegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Override
    public void onApplicationEvent(OnUserRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnUserRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = RandomStringUtils.randomAlphabetic(20);
        userService.createVerificationToken(user, token);

        //mailService.sendUserVerificationMail(user.getAccount().getUsername(), token); TODO enable again
    }
}