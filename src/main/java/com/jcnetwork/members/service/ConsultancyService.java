package com.jcnetwork.members.service;

import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.repository.ConsultancyRepository;
import com.jcnetwork.members.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultancyService {

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
}
