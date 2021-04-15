package com.jcnetwork.members.model.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Member extends MongoDocument{

    @DBRef
    private UserDetails userDetails;
    private List<Role> roles = new ArrayList<>();

    // a container for all unexpected fields
    private Map<String, Object> schemalessData;

    public void addCustomField(String key, Object value) {
        if (null == schemalessData) {
            schemalessData = new HashMap<>();
        }
        schemalessData.put(key, value);
    }

    public Map<String, Object> getCustomFields() {
        return schemalessData;
    }

    public Object getCustomField(String fieldName) { return schemalessData.get(fieldName); }
}
