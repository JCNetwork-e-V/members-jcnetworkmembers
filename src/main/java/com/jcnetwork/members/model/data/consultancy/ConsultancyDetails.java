package com.jcnetwork.members.model.data.consultancy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConsultancyDetails {

    private String name;
    private String city;
    private String domain;
    private String iconBase64;

    private String primaryColor;
    private String secondaryColor;
}
