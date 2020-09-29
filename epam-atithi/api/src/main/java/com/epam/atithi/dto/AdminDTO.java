package com.epam.atithi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDTO {

    private Long adminId;

    private String email;

    private String firstName;

    private String lastName;

}
