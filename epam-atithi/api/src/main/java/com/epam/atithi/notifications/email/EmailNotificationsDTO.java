package com.epam.atithi.notifications.email;

import com.epam.atithi.notifications.NotificationsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationsDTO extends NotificationsDTO {

    private String from;

    private String name;

    private String salutation;

    private String to;

    private String cc;

    private String subject;

    private String note;

    private String regards;

    private List<String> body;

}
