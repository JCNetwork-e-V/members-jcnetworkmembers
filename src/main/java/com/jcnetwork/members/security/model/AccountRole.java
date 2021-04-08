package com.jcnetwork.members.security.model;

import com.jcnetwork.members.model.data.MongoDocument;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@RequiredArgsConstructor
@Document
public class AccountRole extends MongoDocument {

    @NonNull
    private String roleName;
}
