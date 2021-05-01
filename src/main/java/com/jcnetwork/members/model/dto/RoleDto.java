package com.jcnetwork.members.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleDto {

    private String name;
    private String description;
    private String associatedOrganizationalEntity;
    private Boolean dashboardAccess;
    private Boolean messagesAccess;
    private Boolean membersListAccess;
    private Boolean organizationalStructureAccess;
    private Boolean roleManagementAccess;
    private Boolean accountSettingsAccess;
}
