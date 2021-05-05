package com.jcnetwork.members.model.data.consultancy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class OrganizationalEntity {

    private String name;
    private List<OrganizationalEntity> children;
    private Set<Member> members;
}
