package com.jcnetwork.members.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConsultancyDto {

    private String id;
    private String name;
    private String city;
    private String domain;
    private int numberOfMembers;
}
