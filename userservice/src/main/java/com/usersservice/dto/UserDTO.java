package com.usersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long userid;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 10, max = 50)
    private String address;

    @NotEmpty
    @Length(min = 3, max = 15)
    private String phoneno;

}
