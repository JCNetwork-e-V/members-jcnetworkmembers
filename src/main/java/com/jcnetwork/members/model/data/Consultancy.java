package com.jcnetwork.members.model.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document
public class Consultancy extends MongoDocument{

    private ConsultancyDetails consultancyDetails;

    private OrganizationalEntity rootEntity;
    private List<Member> members = new ArrayList<>();

    private Boolean enabled;

    public Consultancy() {
        this.enabled = false;
    }
}
