package com.jcnetwork.members.model.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Address {

    private String country;
    private String city;
    private String zipCode;
    private String streetName;
    private String streetNumber;
}
