package com.jcnetwork.members.model.data.user.resume;

import com.jcnetwork.members.model.data.user.Address;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Resume {

    private String firstName;
    private String lastName;
    private String profilePictureBase64;
    private Address address;
    @DateTimeFormat(pattern="dd.MM.yyyy")
    private Date birthDate;
    private String birthPlace;
    private String email;
    private String phoneNumber;
    private String nationality;

    private List<UniversityEducation> universityEducation;
    private List<Apprenticeship> apprenticeships;
    private SchoolEducation schoolEducation;

    private List<WorkExperience> workExperiences;
    private List<ProjectExperience> projectExperiences;

    private List<ExtraUniversityActivity> extraUniversityActivities;

    private Boolean shareCjcProgress;

    private List<Skill> skills;
    private List<Skill> languages;

    private String individualText;

    private Boolean shareData;

    public Resume(){
        this.address = new Address();
        this.universityEducation = new ArrayList<>();
        this.apprenticeships = new ArrayList<>();
        this.workExperiences = new ArrayList<>();
        this.projectExperiences = new ArrayList<>();
        this.extraUniversityActivities = new ArrayList<>();
    }
}
