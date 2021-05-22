package com.jcnetwork.members.repository;

import com.jcnetwork.members.model.data.InternalMessage;
import com.jcnetwork.members.model.data.MongoDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalMessageRepository extends MongoRepository<InternalMessage, String> {

    Page<InternalMessage> findAllByRecipientAndFolder(
            @Param("recipient") MongoDocument recipient,
            String folder,
            Pageable pageable);

    Long countByRecipientAndFolder(MongoDocument recipient, String folder);

    Long countByRecipientAndFolderAndRead(MongoDocument recipient, String folder, Boolean read);
}
