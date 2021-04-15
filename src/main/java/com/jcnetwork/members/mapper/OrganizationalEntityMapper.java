package com.jcnetwork.members.mapper;

import com.jcnetwork.members.model.data.OrganizationalEntity;
import com.jcnetwork.members.model.dto.OrganizationalEntityDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OrganizationalEntityMapper {

    public OrganizationalEntityDto toDto(OrganizationalEntity organizationalEntity){

        return new OrganizationalEntityDto(
                UUID.randomUUID().toString(),
                organizationalEntity.getName(),
                this.toDto(organizationalEntity.getChildren())
        );
    }

    public List<OrganizationalEntityDto> toDto(List<OrganizationalEntity> organizationalEntities){

        List<OrganizationalEntityDto> organizationalEntityDtos = new ArrayList<>();
        for (OrganizationalEntity organizationalEntity : organizationalEntities){
            organizationalEntityDtos.add(this.toDto(organizationalEntity));
        }
        return organizationalEntityDtos;
    }
}
