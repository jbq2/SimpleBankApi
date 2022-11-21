package com.jbq2.simplebankapi.login;

import com.jbq2.simplebankapi.userpackage.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class LoginService {

    private UserService userService;

    public LoginStatus validateLogin(Login login){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        /* validating entered email */
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(login.getEmail());
        if(!matcher.find()){
            return LoginStatus.FAIL_BAD_EMAIL;
        }

        /* validating entered password */
        pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        matcher = pattern.matcher(login.getPassword());
        if (!matcher.find()){
            return LoginStatus.FAIL_BAD_PASSWORD;
        }

        /* fetching user using loadUserByUsername */
        UserDetails userDetails;
        try{
            userDetails = userService.loadUserByUsername(login.getEmail());
        }
        catch(UsernameNotFoundException e){
            return LoginStatus.FAIL_EMAIL_NOT_EXIST;
        }

        /* checking if passwords match */
        /* must user encoder to properly match */
        if(!encoder.matches(login.getPassword(), userDetails.getPassword())){
            return LoginStatus.FAIL_INCORRECT_PASSWORD;
        }

        return LoginStatus.SUCCESS;
    }
}
