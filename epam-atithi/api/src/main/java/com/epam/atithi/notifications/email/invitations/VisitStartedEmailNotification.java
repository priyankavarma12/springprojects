package com.epam.atithi.notifications.email.invitations;

import com.epam.atithi.model.Invitation;
import com.epam.atithi.model.Visitor;
import com.epam.atithi.notifications.email.EmailNotifications;
import com.epam.atithi.notifications.email.EmailNotificationsDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("visitStartedEmailNotification")
public class VisitStartedEmailNotification extends EmailNotifications {

    public boolean sendVisitStartedEmail(Invitation invitation, Visitor visitor) {
        List<String> body = new ArrayList<>();
        body.add("Greetings of the day!!!");
        body.add("Your visit has been marked started.");
        body.add("Visit Details:");
        body.add("Purpose of the visit: " + invitation.getPurpose());
        body.add("Visit Date and Time: " + invitation.getVisitdatetime());
        body.add("EPAM Office Location: " + invitation.getEpamlocation());
        body.add("Point of Contact: " +invitation.getPoc());
        body.add("Have a happy visit!!!");
        EmailNotificationsDTO emailNotificationsDTO = EmailNotificationsDTO.builder()
                .from("testemail.forepam@gmail.com")
                .salutation("Hello ")
                .subject("Visit started - EPAM Atithi")
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