package com.jcnetwork.members.model.data.consultancy;

import com.jcnetwork.members.security.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class OrganizationalEntity {

    private String name;
    @DBRef
    private User head;
    private List<OrganizationalEntity> children;
}
