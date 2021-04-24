package com.jcnetwork.members.model.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Document
public class Consultancy extends MongoDocument{

    private ConsultancyDetails consultancyDetails;

    private OrganizationalEntity rootEntity;
    private List<Member> members = new ArrayList<>();
    private Set<Role> roles = new HashSet<>();

    private Boolean enabled;

    public Consultancy() {
        this.enabled = false;
    }
}
