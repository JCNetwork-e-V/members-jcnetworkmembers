package com.jcnetwork.members.mapper;

import com.jcnetwork.members.model.data.consultancy.OrganizationalEntity;
import com.jcnetwork.members.model.dto.EntityDetailsDto;
import com.jcnetwork.members.model.dto.OrganizationalEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrganizationalEntityMapper {

    @Autowired
    private EntityMapper entityMapper;

    public OrganizationalEntityDto toDto(OrganizationalEntity organizationalEntity){

        EntityDetailsDto entityHead = new EntityDetailsDto();
        if(organizationalEntity.getHead() != null){
            entityHead = entityMapper.userToDto(organizationalEntity.getHead());
        }

        return new OrganizationalEntityDto(
                organizationalEntity.getName(),
                entityHead,
                this.toDto(organizationalEntity.getChildren())
        );
    }

    public List<OrganizationalEntityDto> toDto(List<OrganizationalEntity> organizationalEntities){

        if(organizationalEntities != null){
            List<OrganizationalEntityDto> organizationalEntityDtos = new ArrayList<>();
            for (OrganizationalEntity organizationalEntity : organizationalEntities){
                organizationalEntityDtos.add(this.toDto(organizationalEntity));
            }
            return organizationalEntityDtos;
        }
        return null;
    }
}
