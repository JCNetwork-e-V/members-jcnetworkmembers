package com.jcnetwork.members.model.data.user;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserDetails {

    private String firstName;
    private String lastName;

    private String profilePictureBase64 = "data:image/png;base64,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";

    private String privateMail;
    private String phoneNumber;
    private Date birthDate;
}
