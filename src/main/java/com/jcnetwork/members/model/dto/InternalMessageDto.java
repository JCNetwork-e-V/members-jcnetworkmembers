package com.jcnetwork.members.model.dto;

import com.jcnetwork.members.model.InternalMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InternalMessageDto {

    private String id;
    private String senderName;
    private String senderShortName;
    private String senderImage;
    private String subject;
    private String body;
    private String folder;
    private Date creationDate;
    private String timespanSinceSent;
    private Boolean read;

    public InternalMessageDto(
            InternalMessage message,
            String senderName,
            String senderShortName,
            String senderImage) {
        this.id = message.getId();
        this.senderName = senderName;
        this.senderShortName = senderShortName;
        this.senderImage = senderImage;
        this.subject = message.getSubject();
        this.body = message.getBody();
        this.creationDate = message.getCreationDate();
        this.timespanSinceSent = message.getTimespanSinceSent();
        this.folder = message.getFolder();
        this.read = message.getRead();
    }
}