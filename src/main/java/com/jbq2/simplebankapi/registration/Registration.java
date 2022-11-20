package com.jbq2.simplebankapi.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class Registration {
    /* registration form as of 11-18-2022 will contain the 3 fields below */
    private String email;
    private String password;
    private String matching;
}
