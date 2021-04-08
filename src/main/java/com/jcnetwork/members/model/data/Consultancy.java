package com.jcnetwork.members.model.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document
public class Consultancy extends MongoDocument{

    @NonNull
    private String name;
    @NonNull
    private String city;
    private String domain;
    private String profilePictureBase64;

    private List<Department> departments = new ArrayList<>();
    private List<Member> members = new ArrayList<>();

    private Boolean enabled;

    public Consultancy(String name, String city, String domain) {
        this.name = name;
        this.city = city;
        this.domain = domain;
        this.enabled = false;
    }
}
