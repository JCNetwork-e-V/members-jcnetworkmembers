package com.jcnetwork.members.security.service;

import com.jcnetwork.members.mapper.EntityMapper;
import com.jcnetwork.members.model.data.TimelineEntry;
import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.dto.EntityDetailsDto;
import com.jcnetwork.members.repository.AccountRoleRepository;
import com.jcnetwork.members.repository.UserRepository;
import com.jcnetwork.members.repository.VerificationTokenRepository;
import com.jcnetwork.members.security.AccountDetails;
import com.jcnetwork.members.security.model.Account;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.model.VerificationToken;
import com.mongodb.client.model.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRoleRepository roleRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findUserByUsername(String username) {
         Optional<User> user = userRepository.findByAccountUsername(username);
         if (user.isEmpty()) {
             user = userRepository.findByAzureAccountsContaining(username);
         }
         return user;
    }

    public String getUserIdByUsername(String username) throws Exception {

        Query query = new Query();
        query.addCriteria(Criteria.where("account.username").is(username));
        query.fields().include("_id");

        Optional<User> user = mongoTemplate.query(User.class).matching(query).one();
        if(user.isEmpty()) throw new Exception("User not found");
        return user.get().getId();
    }

    public List<EntityDetailsDto> getAllAsEntity() {

        Query query = new Query();
        query.fields().include("_id").include("userDetails.firstName").include("userDetails.lastName");

        return entityMapper.userToDto(mongoTemplate.query(User.class).matching(query).all());
    }

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    public User createNewUser(Account account, String roleName) {

        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        account.setIsAccountEnabled(true); //TODO revert to false
        account.setIsAccountNonLocked(true);

        User user = new User();
        user.setAccount(account);

        user.getRoles().add(roleRepository.findByRoleName(roleName));

        return userRepository.save(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(user, token);
        tokenRepository.save(myToken);
    }

    public Optional<VerificationToken> getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public Page<TimelineEntry> getTimelineByUserId(String id, Pageable pageable) {

        int skip = (int) pageable.getOffset();
        int limit = pageable.getPageSize();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(new Criteria("_id").is(id)),
                Aggregation.unwind("timeline"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "timeline.date")),
                Aggregation.group().push("timeline").as("sorted"),
                Aggregation.project().and("sorted").slice(limit, skip).as("sliced"),
                Aggregation.unwind("sliced"),
                Aggregation.project(
                        Fields.from(
                                Fields.field("date", "sliced.date"),
                                Fields.field("title", "sliced.title"),
                                Fields.field("description", "sliced.description"),
                                Fields.field("icon", "sliced.icon"),
                                Fields.field("iconColor", "sliced.iconColor"),
                                Fields.field("entryColor", "sliced.entryColor")
                        )
                )
        );

        List<TimelineEntry> timeline = mongoTemplate.aggregate(aggregation, "user", TimelineEntry.class).getMappedResults();

        Map<String, Object> response = (LinkedHashMap) userRepository.totalTimelineEntriesByUserId(id).getMappedResults().get(0);
        int timelineEntries = (int) response.get("count");

        PageImpl<TimelineEntry> result = new PageImpl<>(timeline, pageable, timelineEntries);
        return result;
    }

    @Override
    public AccountDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByAccountUsername(username);
        if(user.isPresent()) {
            return new AccountDetails(user.get());
        } else {
            throw new UsernameNotFoundException("user " + username + " not found");
        }
    }
}