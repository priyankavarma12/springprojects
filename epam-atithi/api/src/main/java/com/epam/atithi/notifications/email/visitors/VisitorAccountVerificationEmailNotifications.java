package com.epam.atithi.notifications.email.visitors;

import com.epam.atithi.model.Visitor;
import com.epam.atithi.notifications.email.EmailNotifications;
import com.epam.atithi.notifications.email.EmailNotificationsDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("visitorAccountVerificationEmailNotifications")
public class VisitorAccountVerificationEmailNotifications extends EmailNotifications {

    public boolean sendAccountVerificationEmail(Visitor visitor) {
        List<String> body = new ArrayList<>();
        body.add("Greetings of the day!!!");
        body.add("Your account has been created on EPAM Atithi Portal.");
        body.add("As the next steps please login to: visit.epam.com by using below credentials to verify your account");
        body.add("Email: " + visitor.getEmail());
        body.add("Password: " + visitor.getPassword());
        body.add("Have a great day ahead!!!");
        EmailNotificationsDTO emailNotificationsDTO = EmailNotificationsDTO.builder()
                .from("testemail.forepam@gmail.com")
                .salutation("Hello ")
                .subject("Verify your account - EPAM Atithi")
                .regards("EPAM Atithi")
                .body(body)
                .to(visitor.getEmail())
                .name(createFullName(visitor.getFirstname(), visitor.getLastname()))
                .note("This is an auto-generated email. Please do not reply. \nFor more information contact us at: support_atithi@epam.com")
                .build();
        return send(emailNotificationsDTO);
    }

    private String createFullName(String firstname, String lastname) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(firstname)) {
            builder.append(firstname);
        }
        if (StringUtils.isNotBlank(lastname)) {
            builder.append(" ");
            builder.append(lastname);
        }
        return WordUtils.capitalizeFully(builder.toString());
    }

}
