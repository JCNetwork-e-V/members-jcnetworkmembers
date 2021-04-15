package com.jcnetwork.members.security.model;

import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.data.MongoDocument;
import com.jcnetwork.members.model.data.UserDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document(collection = "user")
public class User extends MongoDocument {

    private Account account;
    @DBRef
    private Set<AccountRole> roles = new HashSet<>();
    @DBRef
    private UserDetails userDetails;
    @DBRef
    private Set<Consultancy> consultancies = new HashSet<>();
    private Set<String> azureAccounts = new HashSet<>();
}
