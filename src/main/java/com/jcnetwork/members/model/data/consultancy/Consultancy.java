package com.jcnetwork.members.model.data.consultancy;

import com.jcnetwork.members.model.data.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Document
public class Consultancy extends MongoDocument {

    private ConsultancyDetails consultancyDetails;
    private List<TimelineEntry> timeline;

    private OrganizationalEntity rootEntity;
    private List<Member> members = new ArrayList<>();
    private Set<Role> roles = new HashSet<>();

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
            entity.setChildren(new ArrayList<>());
            for(OrganizationalEntity child : entity.getChildren()){
                entity.getChildren().add(replaceOrganizationalEntity(child, replacement));
            }
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
            if(member.getEmail().toLowerCase().equals(email.toLowerCase())) return member;
        }
        return null;
    }
}
