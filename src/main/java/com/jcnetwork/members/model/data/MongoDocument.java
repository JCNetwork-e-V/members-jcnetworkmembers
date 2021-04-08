package com.jcnetwork.members.model.data;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public abstract class MongoDocument {

    @Id
    private String id;
}
