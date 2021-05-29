package com.jcnetwork.members.mapper;

import com.jcnetwork.members.model.data.consultancy.OrganizationalEntity;
import com.jcnetwork.members.model.dto.EntityDetailsDto;
import com.jcnetwork.members.model.dto.OrganizationalEntityDto;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrganizationalEntityMapper {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private UserService userService;

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

    public OrganizationalEntity toOrganizationalEntity(OrganizationalEntityDto organizationalEntityDto){

        User head = null;
        if(organizationalEntityDto.getHead().getId() != null){
            Optional<User> optionalUser =  userService.findUserById(organizationalEntityDto.getHead().getId());
            if(optionalUser.isPresent()) head = optionalUser.get();
        }

        return new OrganizationalEntity(
                organizationalEntityDto.getName(),
                head,
                this.toOrganizationalEntity(organizationalEntityDto.getChildren())
        );
    }

    public List<OrganizationalEntity> toOrganizationalEntity(List<OrganizationalEntityDto> organizationalEntityDtos){

        if(organizationalEntityDtos != null){
            List<OrganizationalEntity> organizationalEntities = new ArrayList<>();
            for (OrganizationalEntityDto organizationalEntityDto : organizationalEntityDtos){
                organizationalEntities.add(this.toOrganizationalEntity(organizationalEntityDto));
            }
            return organizationalEntities;
        }
        return null;
    }
}
