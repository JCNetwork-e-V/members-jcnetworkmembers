package com.jcnetwork.members.mapper;

import com.jcnetwork.members.model.data.user.UserDetails;
import com.jcnetwork.members.model.dto.UserDetailsDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsMapper {

    public UserDetailsDto toDto(UserDetails userDetails) {
        return new UserDetailsDto(
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getProfilePictureBase64()
        );
    }

    public List<UserDetailsDto> toDto(List<UserDetails> userDetailsList) {
        List<UserDetailsDto> userDetailsDtos = new ArrayList<>();
        for(UserDetails userDetails : userDetailsList){
            userDetailsDtos.add(toDto(userDetails));
        }
        return userDetailsDtos;
    }
}
