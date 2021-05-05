package com.jcnetwork.members.model.data.user.resume;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Apprenticeship {

    private String company;
    private String label;
    private Date startDate;
    private Date endDate;
    private double grade;
    private String focus;
    private String department;
}
