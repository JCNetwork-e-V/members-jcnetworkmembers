package com.jcnetwork.members.model.dto;

import com.jcnetwork.members.model.data.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto {

    private String firstName;
    private String lastName;
    private List<Role> roles = new ArrayList<>();
    private Map<String, Object> schemalessData;
}
