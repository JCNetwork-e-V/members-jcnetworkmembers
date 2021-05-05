package com.jcnetwork.members.model.data.user.resume;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class WorkExperience {

    private String company;
    private String label;
    private String city;
    private Date startDate;
    private Date endDate;
    private List<String> activities;
}
