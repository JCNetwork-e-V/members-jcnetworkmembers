package com.jcnetwork.members.model.data.consultancy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomDataField {

    private String name;
    private String type;
    private Boolean required;
}
