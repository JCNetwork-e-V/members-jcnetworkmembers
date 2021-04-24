package com.jcnetwork.members.model.data;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Role {

    private String name;
    private String description;
    private OrganizationalEntity associatedOrganizationalEntity;
    private Boolean dashboardAccess;
    private Boolean messagesAccess;
    private Boolean membersListAccess;
    private Boolean organizationalStructureAccess;
    private Boolean roleManagementAccess;
    private Boolean accountSettingsAccess;
}
