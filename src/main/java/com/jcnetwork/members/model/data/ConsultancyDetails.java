package com.jcnetwork.members.model.data;

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

    private String primaryColour;
    private String secondaryColour;
}
