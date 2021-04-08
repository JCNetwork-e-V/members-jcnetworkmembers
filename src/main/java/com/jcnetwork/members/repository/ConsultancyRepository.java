package com.jcnetwork.members.repository;

import com.jcnetwork.members.model.data.Consultancy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultancyRepository  extends MongoRepository<Consultancy, String> {

    Optional<Consultancy> findByName(String name);

    Optional<Consultancy> findByDomain(String domain);

    @Query(value = "{}", fields = "{ name : 1, _id : 0 }")
    public List findAllNames();
}
