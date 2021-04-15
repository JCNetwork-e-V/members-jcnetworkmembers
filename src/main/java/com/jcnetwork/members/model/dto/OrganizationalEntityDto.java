package com.jcnetwork.members.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrganizationalEntityDto {

    private String name;
    private List<OrganizationalEntityDto> children;
}
