package com.epam.atithi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitorDTO {

    private Long visitorId;

    private Long adminId;

    private String firstName;

    private String lastName;

    private String email;

    private String primaryCountryCode;

    private String primaryPhNumber;

    private String secondaryCountryCode;

    private String secondaryPhNumber;

    private boolean verificationEmailSent;

    private String nationalId;

    private String companyName;

    private boolean activated;

    private List<InvitationDTO> invitations;

}
