package com.jcnetwork.members.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsDto {

    private String firstName;
    private String lastName;

    private String profilePictureBase64;
}
