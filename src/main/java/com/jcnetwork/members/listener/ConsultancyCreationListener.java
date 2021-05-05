package com.jcnetwork.members.listener;

import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.data.consultancy.ConsultancyDetails;
import com.jcnetwork.members.model.data.consultancy.OrganizationalEntity;
import com.jcnetwork.members.model.data.user.UserDetails;
import com.jcnetwork.members.model.dto.ConsultancyCreationDto;
import com.jcnetwork.members.model.event.OnConsultancyCreationEvent;
import com.jcnetwork.members.security.model.Account;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.UserService;
import com.jcnetwork.members.service.ConsultancyService;
import com.jcnetwork.members.service.MailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ConsultancyCreationListener implements ApplicationListener<OnConsultancyCreationEvent> {

    @Autowired
    private ConsultancyService consultancyService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Override
    public void onApplicationEvent(OnConsultancyCreationEvent event) { this.registerNewConsultancy(event); }

    public void registerNewConsultancy(OnConsultancyCreationEvent event) {

        ConsultancyCreationDto consultancyCreationDto = event.getConsultancyCreationDto();

        // Create root organizational element
        OrganizationalEntity root = new OrganizationalEntity();
        root.setName("Vorstand");
        root.setChildren(new ArrayList<>());

        // Create consultancy details object
        ConsultancyDetails consultancyDetails = new ConsultancyDetails();
        consultancyDetails.setName(consultancyCreationDto.getName());
        consultancyDetails.setCity(consultancyCreationDto.getCity());
        consultancyDetails.setDomain(consultancyCreationDto.getDomain());

        // Create consultancy object
        Consultancy consultancy = new Consultancy();
        consultancy.setRootEntity(root);
        consultancy.setConsultancyDetails(consultancyDetails);

        consultancy = consultancyService.save(consultancy);

        // Create consultancy admin user
        String username = consultancy.getConsultancyDetails().getName() + "_Admin";
        String passwordPlain = RandomStringUtils.randomAlphabetic(10);

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordPlain);

        User technicalUser = userService.createNewUser(account, consultancy, "CONSULTANCY_ADMIN");
        technicalUser.getAccount().setIsAccountEnabled(true);

        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(consultancy.getConsultancyDetails().getName());
        userDetails.setLastName("Admin");

        technicalUser.setUserDetails(userDetails);
        userService.saveUser(technicalUser);

        // Send registration mail
        mailService.sendConsultancyCreationMail(
                consultancyCreationDto.getMail(),
                consultancy.getConsultancyDetails().getName(),
                username,
                passwordPlain);
    }
}
