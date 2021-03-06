package com.jcnetwork.members.mapper;

import com.jcnetwork.members.model.data.consultancy.Role;
import com.jcnetwork.members.model.dto.RoleDto;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDto toDto(Role role) {
        return new RoleDto(
                role.getName(),
                role.getDescription(),
                role.getAssociatedOrganizationalEntity(),
                role.getPrivileges()
        );
    }
}