package com.jcnetwork.members.model.data;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.jcnetwork.members.security.model.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Member extends MongoDocument{

    @DBRef
    private User user;
    private List<Role> roles = new ArrayList<>();

    private Resume resume;
    // a container for all unexpected fields
    private Map<String, Object> schemalessData;

    @JsonAnySetter
    public void add(String key, Object value) {
        if (null == schemalessData) {
            schemalessData = new HashMap<>();
        }
        schemalessData.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> get() {
        return schemalessData;
    }
}
