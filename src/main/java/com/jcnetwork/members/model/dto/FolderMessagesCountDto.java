package com.jcnetwork.members.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FolderMessagesCountDto {
    
    private String folderName;
    private Long messagesCount;
    private Long unreadMessagesCount;
}
