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
    private EntityDetailsDto head;
    private List<OrganizationalEntityDto> children;
}
