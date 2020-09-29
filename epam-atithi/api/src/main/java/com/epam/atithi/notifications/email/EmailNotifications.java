package com.epam.atithi.notifications.email;

import com.epam.atithi.notifications.Notifications;
import com.epam.atithi.notifications.NotificationsDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Slf4j
@Component("emailNotifications")
public class EmailNotifications extends Notifications {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean send(NotificationsDTO notificationsDTO) {
        boolean emailSentSuccessfully = false;
        if (notificationsDTO instanceof EmailNotificationsDTO) {
            EmailNotificationsDTO emailNotificationsDTO = (EmailNotificationsDTO) notificationsDTO;
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper messageHelper = getMimeMessageHelper(emailNotificationsDTO, message);
                String fromAddress = emailNotificationsDTO.getFrom();
                if (StringUtils.isNotBlank(fromAddress)) {
                    messageHelper.setFrom(fromAddress);
                    javaMailSender.send(message);
                    emailSentSuccessfully = true;
                } else {
                    log.error("From Address in the email properties can not be null or empty");
                    return emailSentSuccessfully;
                }
            } catch (Exception exception) {
                log.error(exception.getLocalizedMessage(), exception);
                return emailSentSuccessfully;
            }
        }
        return emailSentSuccessfully;
    }

    private MimeMessageHelper getMimeMessageHelper(EmailNotificationsDTO emailNotificationsDTO, MimeMessage message) throws MessagingException {
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setTo(emailNotificationsDTO.getTo());
        messageHelper.setText(createBodyText(emailNotificationsDTO));
        messageHelper.setSubject(emailNotificationsDTO.getSubject());
        return messageHelper;
    }

    private String createBodyText(EmailNotificationsDTO emailNotificationsDTO) {
        List<String> bodyParts = emailNotificationsDTO.getBody();
        String salutation = emailNotificationsDTO.getSalutation();
        String regards = emailNotificationsDTO.getRegards();
        String note = emailNotificationsDTO.getNote();
        StringBuilder emailBody = new StringBuilder();
        String name = emailNotificationsDTO.getName();
        if (StringUtils.isNotBlank(salutation)) {
            emailBody.append(salutation)
                    .append(" ")
                    .append(name)
                    .append(",")
                    .append("\n\n");
        }
        bodyParts.forEach(bodyPart -> emailBody.append(bodyPart).append("\n\n"));
        if (StringUtils.isNotBlank(regards)) {
            emailBody.append("Regards, \n");
            emailBody.append(regards);
        }
        if (StringUtils.isNotBlank(note)) {
            emailBody.append("\n\n")
                    .append("Note: ")
                    .append(note);
        }
        return emailBody.toString();
    }
}
