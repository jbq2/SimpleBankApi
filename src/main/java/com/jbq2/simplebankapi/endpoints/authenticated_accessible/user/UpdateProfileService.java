package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user;

import com.jbq2.simplebankapi.user_packages.user.User;
import com.jbq2.simplebankapi.user_packages.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UpdateProfileService {
    private UserService userService;

    public String updateProfile(UpdateProfileForm updateProfileForm) {
        if(!updateProfileForm.getPassword().equals(updateProfileForm.getMatching())){
            throw new RuntimeException("Non-matching passwords");
        }
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        Matcher matcher = pattern.matcher(updateProfileForm.getPassword());
        if(!matcher.find()){
            throw new RuntimeException("Invalid password");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setEmail(updateProfileForm.getEmail());
        user.setPassword(encoder.encode(updateProfileForm.getPassword()));
        if(userService.updateUserByEmail(user)) {
            return user.getEmail();
        }
        else {
            throw new RuntimeException("DB_ERR");
        }
    }
}
