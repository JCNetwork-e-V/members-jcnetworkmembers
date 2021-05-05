package com.jcnetwork.members.controller.rest;

import com.jcnetwork.members.mapper.InternalMessageMapper;
import com.jcnetwork.members.exception.ItemNotFoundException;
import com.jcnetwork.members.model.InternalMessage;
import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.dto.FolderMessagesCountDto;
import com.jcnetwork.members.model.dto.InternalMessageDto;
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

    @Autowired
    private InternalMessageMapper mapper;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMessage(@PathVariable("id") String id) {

        //TODO make sure principal matches with message

        InternalMessage message = messageService.getById(id)
                .orElseThrow(() -> new ItemNotFoundException("Message not found"));

        return ResponseEntity.ok(mapper.toDto(message));
    }

    @GetMapping(path = "/{consultancy}/{folder}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getConsultancyMessages(
            @PathVariable("consultancy") String consultancyName,
            @PathVariable("folder") String folder,
            Pageable pageable) {

        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow(() -> new ItemNotFoundException("Consultancy not found"));

        Page<InternalMessage> messages = messageService.getByRecipientAndFolder(consultancy, folder, pageable);

        PageImpl<InternalMessageDto> response = new PageImpl<>(
                mapper.toDto(messages.getContent()),
                pageable,
                messages.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable("id") String id){
        messageService.deleteMessageById(id);
        return ResponseEntity.ok("Message" + id + "deleted");
    }

    @PutMapping(path = "/move/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InternalMessageDto> moveMessage(
            @PathVariable("id") String id,
            @RequestBody String newFolder) {
        return ResponseEntity.ok(mapper.toDto(messageService.changeFolder(id, newFolder)));
    }

    @PutMapping(path = "mark-as-read/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InternalMessageDto> markAsRead(@PathVariable("id") String id) {
        return ResponseEntity.ok(mapper.toDto(messageService.markAsRead(id)));
    }

    @GetMapping(path="/count/{consultancy}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FolderMessagesCountDto>> folderMessagesCount(@PathVariable("consultancy") String consultancyName){

        Consultancy consultancy = consultancyService.getByName(consultancyName)
                .orElseThrow(() -> new ItemNotFoundException("Consultancy not found"));

        List<FolderMessagesCountDto> folderMessageCount = new ArrayList<>();

        String[] folders = {"Inbox", "Gesendet", "Entwürfe", "Papierkorb"};

        for(String folder : folders){
            Long count = messageService.countAllRecipientsMessagesByFolder(consultancy, folder);
            Long unreadCount = messageService.countUnreadRecipientsMessagesByFolder(consultancy, folder);
            folderMessageCount.add(new FolderMessagesCountDto(folder, count, unreadCount));
        }
        return ResponseEntity.ok(folderMessageCount);
    }
}
