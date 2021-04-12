package com.jcnetwork.members.model.dto.rest;

import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.data.Department;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsultancyRESTDto {

    private String id;
    private String name;
    private String city;
    @Setter(AccessLevel.NONE)
    private int numberOfMembers;
    private List<Department> departments;

    public ConsultancyRESTDto(Consultancy consultancy) {
        this.id = consultancy.getId();
        this.name = consultancy.getConsultancyDetails().getName();
        this.city = consultancy.getConsultancyDetails().getCity();
        this.numberOfMembers = consultancy.getMembers().size();
        this.departments = consultancy.getDepartments();
    }
}
