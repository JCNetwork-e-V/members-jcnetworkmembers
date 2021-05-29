package com.jcnetwork.members.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto {

    private String userId;
    private String email;
    private UserDetailsDto userDetails;
    private Set<String> roles;
    private Set<String> organizationalEntities;
    private Map<String, Object> customFields;
}
