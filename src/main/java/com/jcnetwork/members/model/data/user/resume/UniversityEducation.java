package com.jcnetwork.members.model.data.user.resume;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UniversityEducation {

    private String uiversity;
    private String fieldOfStudy;
    private int semesters;
    private String degree;
    private Date since;
    private Date until;
    private double grade;
    private String focus;
    private Boolean completed;
}
