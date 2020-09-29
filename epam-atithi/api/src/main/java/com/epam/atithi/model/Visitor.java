package com.epam.atithi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

/**
 * User Model
 *
 * @author Spallya Omar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "visitors")
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitorsid;

    private Long adminid;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String primarycountrycode;

    private String primaryphnumber;

    private String secondarycountrycode;

    private String secondaryphnumber;

    private String verificationemailsent;

    private String nationalid;

    private String companyname;

    private String activated;

}
