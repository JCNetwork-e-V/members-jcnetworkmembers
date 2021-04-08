package com.jcnetwork.members.model.rest;

import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.data.Department;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsultancyRESTModel {

    private String id;
    private String name;
    private String city;
    @Setter(AccessLevel.NONE)
    private int numberOfMembers;
    private List<Department> departments;

    public ConsultancyRESTModel(Consultancy consultancy) {
        this.id = consultancy.getId();
        this.name = consultancy.getName();
        this.city = consultancy.getCity();
        this.numberOfMembers = consultancy.getMembers().size();
        this.departments = consultancy.getDepartments();
    }
}
