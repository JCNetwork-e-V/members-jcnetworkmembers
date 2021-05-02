package com.jcnetwork.members.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class RoleDto {

    private String name;
    private String description;
    private String associatedOrganizationalEntity;
    private Set<String> privileges;
}
