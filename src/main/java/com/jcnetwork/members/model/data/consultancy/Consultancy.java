package com.jcnetwork.members.model.data.consultancy;

import com.jcnetwork.members.model.data.*;
import com.jcnetwork.members.security.model.User;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Setter
@Document
public class Consultancy extends MongoDocument {

    private ConsultancyDetails consultancyDetails;
    private List<TimelineEntry> timeline;

    private OrganizationalEntity rootEntity;
    private List<Member> members = new ArrayList<>();
    private Set<Role> roles = new HashSet<>();
    private Map<String, CustomDataField> customDataFields = new HashMap<>();

    private Boolean enabled;

    public Consultancy() {
        this.enabled = false;
    }

    public List<OrganizationalEntity> getOrganizationalEntities(){

        List<OrganizationalEntity> entities = new ArrayList<>();
        entities.add(this.rootEntity);
        entities.addAll(getChildren(this.rootEntity));
        return entities;
    }

    public OrganizationalEntity getOrganizationalEntity(String entityName) throws Exception {
        for(OrganizationalEntity entity : this.getOrganizationalEntities()){
            if(entity.getName().equals(entityName)) return entity;
        }
        throw new Exception("Organizational entity not found");
    }

    public void updateOrganizationalEntity(OrganizationalEntity organizationalEntity) {
        replaceOrganizationalEntity(this.rootEntity, organizationalEntity);
    }

    private OrganizationalEntity replaceOrganizationalEntity(OrganizationalEntity entity, OrganizationalEntity replacement) {
        if(entity.getName().equals(replacement.getName())) {
            return entity;
        } else {
            List<OrganizationalEntity> updatedChildren = new ArrayList<>();
            if(entity.getChildren() != null) {
                for(OrganizationalEntity child : entity.getChildren()){
                    updatedChildren.add(replaceOrganizationalEntity(child, replacement));
                }
            }
            entity.setChildren(updatedChildren);
        }
        return entity;
    }

    private List<OrganizationalEntity> getChildren(OrganizationalEntity entity){

        List<OrganizationalEntity> children = new ArrayList<>();
        if(entity.getChildren() != null){
            for(OrganizationalEntity childEntity : entity.getChildren()){
                children.addAll(getChildren(childEntity));
                children.add(childEntity);
            }
        }
        return children;
    }

    public Role getRole(String roleName) throws Exception {
        for(Role role : this.roles){
            if(role.getName().equals(roleName)) return role;
        }
        throw new Exception("Role not found");
    }

    public Member getMemberByEmail(String email) {
        for(Member member : this.members){
            if(member.getEmail().equalsIgnoreCase(email)) return member;
        }
        return null;
    }

    public Member getMemberById(String id) {
        for(Member member : this.members){
            if(member.getUser().getId().equals(id)) return member;
        }
        return null;
    }

    public Member getMemberByUser(User user) throws Exception {

        Set<String> userMails = new HashSet<>();
        if (user.getAccount() != null) userMails.add(user.getAccount().getUsername());
        if (user.getAzureAccounts() != null) userMails.addAll(user.getAzureAccounts());

        for(String userMail : userMails) {
            if(userMail.endsWith(consultancyDetails.getDomain())) {
                return getMemberByEmail(userMail);
            }
        }
        throw new Exception("User not found");
    }

    public Map<String, CustomDataField> addCustomDataField(CustomDataField newField) {
        this.customDataFields.put(newField.getName(), newField);
        return this.customDataFields;
    }

    public void deleteCustomDataField(String fieldName) {
        this.customDataFields.remove(fieldName);
    }

    public void setDataFieldEnforcement() {
        for (Member member : this.members) {
            member.setHasNewDataField(true);
        }
    }
}
