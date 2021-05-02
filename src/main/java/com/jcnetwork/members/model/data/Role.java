package com.jcnetwork.members.model.data;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class Role {

    private String name;
    private String description;
    private String associatedOrganizationalEntity;
    private Set<String> privileges;
}
