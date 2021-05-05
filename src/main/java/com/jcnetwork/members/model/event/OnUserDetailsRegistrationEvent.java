package com.jcnetwork.members.model.event;

import com.jcnetwork.members.model.data.user.UserDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnUserDetailsRegistrationEvent extends ApplicationEvent {

    private Object principal;
    public UserDetails userDetails;

    public OnUserDetailsRegistrationEvent(Object principal, UserDetails userDetails) {
        super(userDetails);
        this.principal = principal;
        this.userDetails = userDetails;
    }
}
