package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user;

import com.jbq2.simplebankapi.user_packages.pojo.User;
import com.jbq2.simplebankapi.user_packages.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateProfileService {
    private UserService userService;

    public UpdateProfileResponse updateProfile(UpdateProfileForm updateProfileForm) {
        if(!updateProfileForm.getEmail().equals(updateProfileForm.getMatching())){
            throw new RuntimeException("non-matching passwords");
        }


        userService.updateUser()
    }
}
