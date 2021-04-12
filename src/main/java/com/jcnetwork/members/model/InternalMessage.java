package com.jcnetwork.members.model;

import com.jcnetwork.members.model.data.MongoDocument;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

@Getter
@Setter
public class InternalMessage extends MongoDocument {

    @DBRef
    private MongoDocument recipient;
    @DBRef
    private MongoDocument sender;
    private String subject;
    private String body;
    private Date creationDate;
    private String folder;

    private Boolean read;

    public InternalMessage(MongoDocument recipient, MongoDocument sender, String subject, String body){
        this.recipient = recipient;
        this.sender = sender;
        this.subject = subject;
        this.body = body;
        this.creationDate = new Date();
        this.read = false;
        this.folder = "Inbox";
    }

    public String getTimespanSinceSent(){
        long difference = new Date().getTime() - this.creationDate.getTime();
        if(difference < 5000) return "vor wenigen Sekunden";
        if(difference < 60000) return "vor " + difference / 1000 % 60 + " Sekunden";
        if(difference < 120000) return "vor einer Minute";
        if(difference < 3600000) return "vor " + difference / (60 * 1000) % 60 + " Minuten";
        if(difference < 7200000) return "vor einer Stunde";
        if(difference < 86400000) return "vor " + difference / (60 * 60 * 1000) % 24 + " Stunden";
        if(difference < 172800000) return "vor einem Tag";
        if(difference < 2.629746 * 1000000000) return "vor " + difference / (24 * 60 * 60 * 1000) + " Tagen";
        if(difference < 5.259492 * 1000000000) return "vor einem Monat";
        if(difference < 3.1556952 * 1000000000 * 10) return "vor " +  (int) (difference/(2.629746 * 1000000000)) + " Monaten";
        if(difference < 6.3113904 * 1000000000 * 10) return "vor einem Jahr";
        else return "vor " + (int) (difference / (3.1556952 * 1000000000 * 10)) + " Jahren";
    }
}
