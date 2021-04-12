package com.jcnetwork.members.model.dto;

import com.jcnetwork.members.security.model.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDto {

    private Account account;
    private String selectedConsultancy;
}
