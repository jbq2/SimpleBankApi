package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user.update_profile_page;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jbq2.simplebankapi.jwt.JwtConstants;
import com.jbq2.simplebankapi.user_packages.user.User;
import com.jbq2.simplebankapi.user_packages.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UpdateProfileService {
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtConstants jwtConstants;

    public String getPageContent(String jwt) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtConstants.key)).build().verify(jwt);
            return decodedJWT.getSubject();
        }
        catch(Exception e) {
            throw new RuntimeException("invalid jwt");
        }
    }

    public String updateProfile(UpdateProfileForm updateProfileForm) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        Matcher matcher = pattern.matcher(updateProfileForm.getPassword());
        if(!matcher.find()){
            throw new RuntimeException("Invalid password.");
        }
        if(!updateProfileForm.getPassword().equals(updateProfileForm.getMatching())){
            throw new RuntimeException("Non-matching passwords.");
        }

        UserDetails userDetails = userService.loadUserByUsername(updateProfileForm.getOldEmail());
        if(!bCryptPasswordEncoder.matches(updateProfileForm.getOldPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Incorrect password.");
        }

        User user = new User();
        user.setEmail(updateProfileForm.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(updateProfileForm.getPassword()));
        try {
            userService.updateExistingUser(user, updateProfileForm.getOldEmail());
            return updateProfileForm.getEmail();
        }
        catch(RuntimeException e) {
            throw new RuntimeException("Email already exists.");
        }
    }
}
