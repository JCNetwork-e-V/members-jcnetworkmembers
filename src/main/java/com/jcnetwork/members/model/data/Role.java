package com.jcnetwork.members.model.data;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class Role {

    @NonNull
    private String name;
    private Date expirationDate;
}
