package com.epam.atithi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationDTO {

    private Long invitationId;

    private Long visitorId;

    private String purpose;

    private String poc;

    private String visitorLocation;

    private String epamLocation;

    private String visitDateTime;

    private String estimatedDuration;

    private String inTime;

    private String outTime;

    private boolean visitCompleted;

    private String status;

    private String visitorResponse;


}
