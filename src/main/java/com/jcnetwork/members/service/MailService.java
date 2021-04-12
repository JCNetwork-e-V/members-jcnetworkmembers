package com.jcnetwork.members.service;

import com.jcnetwork.members.security.model.Account;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailService {

    @Value("${mail.from}")
    private String mailFrom;

    @Value("${mail.name}")
    private String mailName;

    @Value("${server.baseUrl}")
    private String baseUrl;

    @Autowired
    private SendGridAPI sendGridApi;

    private void sendMail(String subject, String mailTo, String contentText) {
        Email from = new Email(mailFrom, mailName);
        Email to = new Email(mailTo);
        Content content = new Content("text/plain", contentText);
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGridApi.api(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendUserVerificationMail(String mailTo, String token) {

        String subject = "Verifizierung deines JCNetwork Members Accounts";

        String mailContent =
                "Hallo,\n" +
                "herzlich willkommen bei JCNetwork Members. Dein Account wurde angelegt und muss nun nur noch von dir bestätigt werden. Bitte klicke dazu auf den unteren Link. \n \n" +
                baseUrl + "/registrationConfirmation?token=" + token + "\n \n" +
                "Viele Grüße \n" +
                "Dein JCNetwork Members Team";

        sendMail(subject, mailTo, mailContent);
    }

    public void sendConsultancyCreationMail(
            String mailTo,
            String consultancyName,
            String username,
            String password) {

        String subject = "Aktivierung eures JCNetwork Members Accounts";

        String mailContent =
                "Liebes " + consultancyName + "-Team, \n" +
                "herzlich willkommen bei JCNetwork Members. Euer Vereinsaccount wurde angelegt und ist ab sofort erreichbar. Um die Aktivierung abzuschließen müsst ihr euch nur noch mit eurem Admin-Nutzer anmelden und eure Einstellungen anpassen. \n \n" +
                "Nutzername: " + username + "\n" +
                "Password: " + password + "\n \n" +
                "Viele Grüße \n" +
                "Euer JCNetwork Members Team";

        sendMail(subject, mailTo, mailContent);

    }
}
