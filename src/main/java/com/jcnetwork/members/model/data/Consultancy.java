package com.jcnetwork.members.model.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Document
public class Consultancy extends MongoDocument{

    private ConsultancyDetails consultancyDetails;

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
        for(Role role : this.getRoles()){
            if(role.getName().equals(roleName)){
                return role;
            }
        }
        throw new Exception("Role not found");
    }
}
