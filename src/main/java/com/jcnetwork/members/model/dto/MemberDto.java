package com.jcnetwork.members.model.dto;

import com.jcnetwork.members.model.data.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto {

    private String firstName;
    private String lastName;
    private Set<Role> roles;
    private Map<String, Object> schemalessData;
}
