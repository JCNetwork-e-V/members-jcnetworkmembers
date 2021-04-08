package com.jcnetwork.members.security.model;

import com.jcnetwork.members.model.data.MongoDocument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Document
public class VerificationToken extends MongoDocument {

    private String token;
    @Setter(AccessLevel.NONE)
    private LocalDate expiryDate;
    private String relatedObjectId;

    public VerificationToken(String relatedObjectId) {
        this.relatedObjectId = relatedObjectId;
        this.token = UUID.randomUUID().toString();
        this.expiryDate = LocalDate.now().plus(1, ChronoUnit.DAYS);
    }

}
