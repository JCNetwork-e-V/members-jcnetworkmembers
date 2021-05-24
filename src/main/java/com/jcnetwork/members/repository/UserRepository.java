package com.jcnetwork.members.repository;

import com.jcnetwork.members.security.model.User;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByAccountUsername(String username);

    Optional<User> findByAzureAccountsContaining(String username);

    @Aggregation(pipeline = {"{$match: {'_id' : ?0}}", "{$project: {count: {$size : [$timeline]}}}"})
    AggregationResults totalTimelineEntriesByUserId(String id);
}
