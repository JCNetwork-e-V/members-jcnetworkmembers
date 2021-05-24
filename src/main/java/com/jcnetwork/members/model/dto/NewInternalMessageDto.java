package com.jcnetwork.members.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewInternalMessageDto {

    private List<String> recipientIds;
    private String subject;
    private String body;
}
