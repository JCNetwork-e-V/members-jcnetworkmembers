package com.jcnetwork.members.service;

import com.jcnetwork.members.dto.ConsultancyCreationDto;
import com.jcnetwork.members.model.data.Consultancy;
import com.jcnetwork.members.model.data.UserDetails;
import com.jcnetwork.members.repository.ConsultancyRepository;
import com.jcnetwork.members.security.model.Account;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.MembersUserDetailsService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultancyService {

    @Autowired
    private ConsultancyRepository consultancyRepository;

    @Autowired
    private MembersUserDetailsService userDetailsService;

    @Autowired
    private MailService mailService;

    public Consultancy save(Consultancy consultancy) { return consultancyRepository.save(consultancy); }

    public Consultancy registerNewConsultancy(ConsultancyCreationDto consultancyCreationDto) {

        Consultancy consultancy = new Consultancy(
                consultancyCreationDto.getName(),
                consultancyCreationDto.getCity(),
                consultancyCreationDto.getDomain()
        );
        consultancy = save(consultancy);
        String username = consultancy.getName() + "_Admin";
        String passwordPlain = RandomStringUtils.randomAlphabetic(10);

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordPlain);

        User technicalUser = userDetailsService.createNewUser(account, consultancy, "CONSULTANCY_ADMIN");

        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(consultancy.getName());
        userDetails.setLastName("Admin");

        technicalUser.setUserDetails(userDetails);
        userDetailsService.saveUser(technicalUser);

        mailService.sendConsultancyCreationMail(
                consultancyCreationDto.getMail(),
                consultancy.getName(),
                username,
                passwordPlain);

        return consultancy;
    }

    public void delete(Consultancy consultancy) {consultancyRepository.delete(consultancy);}

    public Optional<Consultancy> getById(String id) {return consultancyRepository.findById(id);}

    public Optional<Consultancy> getByName(String name) {return consultancyRepository.findByName(name);}

    public Optional<Consultancy> getByDomain(String domain) {return consultancyRepository.findByDomain(domain);}

    public List<Consultancy> getAll() {return consultancyRepository.findAll();}

    public List<String> getAllConsultancyNames() {

        List<String> consultancyNames = new ArrayList();
        List<Consultancy> consultancies = consultancyRepository.findAllNames();
        for(Consultancy consultancy : consultancies){
            consultancyNames.add(consultancy.getName());
        }

        return consultancyNames;
    }
}
