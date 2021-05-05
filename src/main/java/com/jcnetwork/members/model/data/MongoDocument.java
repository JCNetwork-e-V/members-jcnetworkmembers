package com.jcnetwork.members.model.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public abstract class MongoDocument {

    @Id
    private String id;
}
