package com.jcnetwork.members.security.model;

import com.jcnetwork.members.model.data.MongoDocument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Document
public class VerificationToken extends MongoDocument {

    private String token;
    @Setter(AccessLevel.NONE)
    private LocalDate expiryDate;
    private User user;

    public VerificationToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expiryDate = LocalDate.now().plus(1, ChronoUnit.DAYS);
    }

}
