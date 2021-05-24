package com.jcnetwork.members.service;

import com.jcnetwork.members.mapper.EntityMapper;
import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.dto.EntityDetailsDto;
import com.jcnetwork.members.repository.ConsultancyRepository;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultancyService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private ConsultancyRepository consultancyRepository;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private MailService mailService;

    public Consultancy save(Consultancy consultancy) { return consultancyRepository.save(consultancy); }

    public void delete(Consultancy consultancy) {consultancyRepository.delete(consultancy);}

    public Optional<Consultancy> getById(String id) {return consultancyRepository.findById(id);}

    public Optional<Consultancy> getByName(String name) {
        return consultancyRepository.findByConsultancyDetailsNameIgnoreCase(name);
    }

    public Optional<Consultancy> getByDomain(String domain) {
        return consultancyRepository.findByConsultancyDetailsDomain(domain);
    }

    public List<Consultancy> getAll() {return consultancyRepository.findAll();}

    public String getIdByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("consultancyDetails.name").is(name));
        query.fields().include("_id");

        Optional<Consultancy> consultancy = mongoTemplate.query(Consultancy.class).matching(query).one();
        if(consultancy.isPresent()) return consultancy.get().getId();
        else return null;
    }

    public List<EntityDetailsDto> getAllAsEntity() {

        Query query = new Query();
        query.fields().include("_id").include("consultancyDetails.name");

        return entityMapper.consultancyToDto(mongoTemplate.query(Consultancy.class).matching(query).all());
    }
}
