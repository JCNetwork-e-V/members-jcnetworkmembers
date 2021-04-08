package com.jcnetwork.members.repository;

import com.jcnetwork.members.security.model.AccountRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRoleRepository extends MongoRepository<AccountRole, String> {

    AccountRole findByRoleName(String roleName);
}
