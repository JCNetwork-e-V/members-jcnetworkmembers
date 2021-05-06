package com.jcnetwork.members.model.data.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSettings {

    private Boolean darkMode;

    public UserSettings() {
        this.darkMode = false;
    }
}
