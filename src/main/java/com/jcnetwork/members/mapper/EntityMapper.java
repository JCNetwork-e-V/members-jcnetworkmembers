package com.jcnetwork.members.mapper;

import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.dto.EntityDetailsDto;
import com.jcnetwork.members.security.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntityMapper {

    public EntityDetailsDto userToDto(User user) {
        return new EntityDetailsDto(
                user.getId(),
                user.getUserDetails().getFirstName() + " " + user.getUserDetails().getLastName()
        );
    }

    public List<EntityDetailsDto> userToDto(List<User> users){

        if(users != null){
            List<EntityDetailsDto> entityDetailsDtos = new ArrayList<>();
            for (User user : users){
                entityDetailsDtos.add(this.userToDto(user));
            }
            return entityDetailsDtos;
        }
        return null;
    }

    public EntityDetailsDto consultancyToDto (Consultancy consultancy) {
        return new EntityDetailsDto(
                consultancy.getId(),
                consultancy.getConsultancyDetails().getName()
        );
    }

    public List<EntityDetailsDto> consultancyToDto (List<Consultancy> consultancies){

        if(consultancies != null){
            List<EntityDetailsDto> entityDetailsDtos = new ArrayList<>();
            for (Consultancy consultancy : consultancies){
                entityDetailsDtos.add(this.consultancyToDto(consultancy));
            }
            return entityDetailsDtos;
        }
        return null;
    }
}
