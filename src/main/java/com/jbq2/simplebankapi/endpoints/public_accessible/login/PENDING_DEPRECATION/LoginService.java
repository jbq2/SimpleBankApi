package com.jbq2.simplebankapi.endpoints.public_accessible.login.PENDING_DEPRECATION;

import com.jbq2.simplebankapi.endpoints.public_accessible.exceptions.EmailNotFoundException;
import com.jbq2.simplebankapi.endpoints.public_accessible.exceptions.IncorrectPasswordException;
import com.jbq2.simplebankapi.endpoints.public_accessible.exceptions.InvalidEmailException;
import com.jbq2.simplebankapi.endpoints.public_accessible.exceptions.InvalidPasswordException;
import com.jbq2.simplebankapi.endpoints.public_accessible.login.Login;
import com.jbq2.simplebankapi.user_packages.service.UserService;
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

    public UserDetails validateLogin(Login login){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        /* validating entered email */
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(login.getEmail());
        if(!matcher.find()){
            throw new InvalidEmailException("Email is incorrectly formatted.");
        }

        /* validating entered password */
        pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        matcher = pattern.matcher(login.getPassword());
        if (!matcher.find()){
            throw new InvalidPasswordException("Password does not meet requirements (At least 8 characters, no spaces, and contains at least 1 letter, 1 digit, and 1 special character).");
        }

        /* fetching user using loadUserByUsername */
        UserDetails userDetails;
        try{
            userDetails = userService.loadUserByUsername(login.getEmail());
        }
        catch(UsernameNotFoundException e){
            throw new EmailNotFoundException("Email does not exist.");
        }

        /* checking if passwords match */
        /* must user encoder to properly match */
        if(!encoder.matches(login.getPassword(), userDetails.getPassword())){
            throw new IncorrectPasswordException("Incorrect Password.");
        }

        return userDetails;
    }
}
