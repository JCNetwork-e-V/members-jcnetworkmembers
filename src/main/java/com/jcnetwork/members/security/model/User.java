package com.jcnetwork.members.security.model;

import com.jcnetwork.members.model.data.TimelineEntry;
import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.data.MongoDocument;
import com.jcnetwork.members.model.data.user.UserDetails;
import com.jcnetwork.members.model.data.user.UserSettings;
import com.jcnetwork.members.model.data.user.resume.Resume;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Document(collection = "user")
public class User extends MongoDocument {

    private Account account;
    @DBRef
    private Set<AccountRole> roles = new HashSet<>();
    private UserSettings userSettings;
    private UserDetails userDetails;
    private List<TimelineEntry> timeline = new ArrayList<>();
    private Resume resume;
    private Set<String> azureAccounts = new HashSet<>();
}
