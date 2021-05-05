package com.jcnetwork.members.model.data.consultancy;

import com.jcnetwork.members.model.data.MongoDocument;
import com.jcnetwork.members.model.data.user.UserDetails;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.*;

@Getter
@Setter
public class Member extends MongoDocument {

    private String email;
    @DBRef
    private UserDetails userDetails;
    private Set<String> roles = new HashSet<>();

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

    public void addRole(String roleName) {
        this.roles.add(roleName);
    }

    public void removeRole(String roleName) {
        if(this.roles.contains(roleName)){
            this.roles.remove(roleName);
        }
    }
}
