package com.jcnetwork.members.model.dto.rest;

import com.jcnetwork.members.model.InternalMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalMessageRESTDto {

    private String id;
    private String senderName;
    private String senderShortName;
    private String senderImage;
    private String subject;
    private String body;
    private String folder;
    private String timespanSinceSent;

    public InternalMessageRESTDto(
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
        this.timespanSinceSent = message.getTimespanSinceSent();
        this.folder = message.getFolder();
    }
}