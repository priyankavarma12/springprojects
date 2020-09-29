package com.epam.atithi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "invitation")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invitationid;

    private Long visitorid;

    private String purpose;

    private String poc;

    private String visitorresponse;

    private String visitorlocation;

    private String epamlocation;

    private String visitdatetime;

    private String estimatedduration;

    private Long createdbyadmin;

    private Date createdate;

    private Long updatedbyadmin;

    private Date updatedate;

    private String intime;

    private String outtime;

}
