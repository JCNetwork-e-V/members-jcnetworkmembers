package com.jcnetwork.members.model.data.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private String city;
    private String zipCode;
    private String streetName;
    private String streetNumber;
}
