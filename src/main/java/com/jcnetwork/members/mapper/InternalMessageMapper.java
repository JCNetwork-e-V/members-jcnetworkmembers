package com.jcnetwork.members.mapper;

import com.jcnetwork.members.model.data.InternalMessage;
import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.data.MongoDocument;
import com.jcnetwork.members.model.dto.InternalMessageDto;
import com.jcnetwork.members.security.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InternalMessageMapper {

    public InternalMessageDto toDto(InternalMessage message) {

        String profileIcon;
        String senderName;
        String senderShortName;

        MongoDocument sender = message.getSender();
        if(sender instanceof Consultancy) {
            profileIcon = ((Consultancy) sender).getConsultancyDetails().getIconBase64();
            senderName = ((Consultancy) sender).getConsultancyDetails().getName();
            senderShortName = ((Consultancy) sender).getConsultancyDetails().getName().substring(0,3).toUpperCase();
        } else if(sender instanceof User) {
            profileIcon = ((User) sender).getUserDetails().getProfilePictureBase64();
            senderName = ((User) sender).getUserDetails().getFirstName()
                    + " " + ((User) sender).getUserDetails().getLastName();
            senderShortName = ((User) sender).getUserDetails().getFirstName().substring(0,1).toUpperCase()
                    + " " + ((User) sender).getUserDetails().getLastName().substring(0,1).toUpperCase();
        } else {
            profileIcon = null;
            senderName = null;
            senderShortName = null;
        }

        return new InternalMessageDto(message, senderName, senderShortName, profileIcon);
    }

    public List<InternalMessageDto> toDto(List<InternalMessage> messages){

        List<InternalMessageDto> messageDtos = new ArrayList<>();
        for(InternalMessage message : messages){
            messageDtos.add(toDto(message));
        }
        return messageDtos;
    }
}
