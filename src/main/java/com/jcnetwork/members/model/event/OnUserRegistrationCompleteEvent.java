package com.jcnetwork.members.model.event;

import com.jcnetwork.members.security.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnUserRegistrationCompleteEvent extends ApplicationEvent {

    private User user;

    public OnUserRegistrationCompleteEvent(User user) {
        super(user);
        this.user = user;
    }
}