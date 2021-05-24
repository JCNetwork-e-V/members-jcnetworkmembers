package com.jcnetwork.members.service;

import com.jcnetwork.members.model.data.InternalMessage;
import com.jcnetwork.members.model.dto.NewInternalMessageDto;
import com.jcnetwork.members.repository.InternalMessageRepository;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InternalMessageService {

    @Autowired
    private InternalMessageRepository internalMessageRepository;

    public void createMessage(InternalMessage message) { internalMessageRepository.save(message); }

    public void deleteMessage(InternalMessage message) { internalMessageRepository.delete(message); }

    public void deleteMessageById(String id){
        Optional<InternalMessage> message = internalMessageRepository.findById(id);
        if(message.isPresent()) internalMessageRepository.delete(message.get());
        else throw new MongoException("Internal Message not found");
    }

    public Optional<InternalMessage> getById(String id) { return internalMessageRepository.findById(id); }

    public Page<InternalMessage> getByRecipientAndFolder(String recipientId, String folder, Pageable pageable) {
        return internalMessageRepository.findAllByRecipientAndFolder(recipientId, folder, pageable);
    }

    public InternalMessage changeFolder(String id, String newFolder) {
        Optional<InternalMessage> optionalMessage = internalMessageRepository.findById(id);
        if(optionalMessage.isPresent()){
            InternalMessage message = optionalMessage.get();
            message.setFolder(newFolder);
            return internalMessageRepository.save(message);
        } else {
            throw new MongoException("Internal Message not found");
        }
    }

    public InternalMessage markAsRead(String id) {
        Optional<InternalMessage> optionalMessage = internalMessageRepository.findById(id);
        if(optionalMessage.isPresent()){
            InternalMessage message = optionalMessage.get();
            message.setRead(true);
            return internalMessageRepository.save(message);
        } else {
            throw new MongoException("Internal Message not found");
        }
    }

    public Long countAllRecipientsMessagesByFolder(String recipientId, String folder) {
        return internalMessageRepository.countByRecipientAndFolder(recipientId, folder);
    }

    public Long countUnreadRecipientsMessagesByFolder(String recipientId, String folder) {
        return internalMessageRepository.countByRecipientAndFolderAndRead(recipientId, folder, false);
    }
}
