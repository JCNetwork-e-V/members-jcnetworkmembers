package com.jcnetwork.members.model.data.consultancy;

import com.jcnetwork.members.security.model.User;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationalEntity {

    private String name;
    @DBRef
    private User head;
    private List<OrganizationalEntity> children;
}
