package com.springboot.kafkaproducer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;
    private String address;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("name='").append(name).append('\'');
     //   sb.append(", id='").append(id).append('\'');
        sb.append("address='").append(address).append('\'');
      //  sb.append("age='").append(age).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
