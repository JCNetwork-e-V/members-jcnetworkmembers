package com.jcnetwork.members.model.data.consultancy;

import com.jcnetwork.members.security.model.User;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.*;

@Getter
@Setter
public class Member {

    private String email;
    @DBRef
    private User user;
    private Set<String> roles = new HashSet<>();
    private Set<String> organizationalEntities = new HashSet<>();
    private Boolean hasNewDataField;

    // a container for all custom fields
    private Map<String, Object> schemalessData;

    public void addCustomFields(Map<String, Object> dataFields) {
        if (schemalessData == null) schemalessData = new HashMap<>();
        schemalessData.putAll(dataFields);
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

    public void addOrganizationalEntity(String entityName) {
        this.organizationalEntities.add(entityName);
    }

    public void removeOrganizationalEntity(String entityName) {
        if(this.organizationalEntities.contains(entityName)){
            this.organizationalEntities.remove(entityName);
        }
    }
}
