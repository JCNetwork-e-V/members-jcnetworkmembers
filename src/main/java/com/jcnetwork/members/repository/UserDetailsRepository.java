package com.jcnetwork.members.repository;

import com.jcnetwork.members.model.data.user.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends MongoRepository<UserDetails, String> {

}
