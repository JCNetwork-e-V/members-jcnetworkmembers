package com.jcnetwork.members.model.data.user.resume;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProjectExperience {

    private String role;
    private String customer;
    private Date startDate;
    private Date endDate;
    private String description;
}
