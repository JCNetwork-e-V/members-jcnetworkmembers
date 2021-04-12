package com.jcnetwork.members.controller.rest;

import com.jcnetwork.members.exception.ItemNotFoundException;
import com.jcnetwork.members.model.InternalMessage;
import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.data.MongoDocument;
import com.jcnetwork.members.model.dto.rest.InternalMessageRESTDto;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.service.InternalMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/messages")
public class InternalMessagesRESTController {

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private InternalMessageService messageService;

    @GetMapping(path = "/{consultancy}/{folder}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getConsultancyMessages(
            @PathVariable("consultancy") String consultancyName,
            @PathVariable("folder") String folder,
            Pageable pageable) {

        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow(() -> new ItemNotFoundException("Invalid Item ID"));

        Page<InternalMessage> messages = messageService.getByRecipient(consultancy, folder, pageable);
        List<InternalMessageRESTDto> messagesREST = new ArrayList<>();

        for(InternalMessage message : messages){
            messagesREST.add(convertInternalMessageToDto(message));
        }

        PageImpl<InternalMessageRESTDto> response = new PageImpl<InternalMessageRESTDto>(
                messagesREST, pageable, messages.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable("id") String id){
        messageService.deleteMessageById(id);
        return ResponseEntity.ok("Message" + id + "deleted");
    }

    @PutMapping(path = "/move/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InternalMessageRESTDto> moveMessage(
            @PathVariable("id") String id,
            @RequestBody String newFolder) {
        return ResponseEntity.ok(convertInternalMessageToDto(messageService.changeFolder(id, newFolder)));
    }

    private InternalMessageRESTDto convertInternalMessageToDto(InternalMessage message) {

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

        return new InternalMessageRESTDto(message, senderName, senderShortName, profileIcon);
    }
}
