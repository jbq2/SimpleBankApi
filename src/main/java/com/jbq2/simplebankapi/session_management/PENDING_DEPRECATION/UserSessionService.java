package com.jbq2.simplebankapi.session_management.PENDING_DEPRECATION;

import com.jbq2.simplebankapi.session_management.PENDING_DEPRECATION.exceptions.UserSessionNotFoundException;
import com.jbq2.simplebankapi.user_packages.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

@Service
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class UserSessionService {
    private final UserService userService;
    private final HttpServletRequest request;


    /* createUserSession will only be called by LoginService */
    public String createUserSession(UserDetails userDetails) {

        /* TODO: create NEW SESSION for each user !! */
        request.getSession().invalidate(); /* invalidate session whenever you login */
        HttpSession httpSession = request.getSession(); /* create new session (if not exists) */
        httpSession.setAttribute("AUTHORITIES", userDetails.getAuthorities());  /* set the new session to this */

        return httpSession.getId();
    }

    public Collection<? extends GrantedAuthority> getUserSessionData() throws UserSessionNotFoundException {
        HttpSession httpSession = request.getSession(); /* get the session (automatically creates if there isn't one) */
        /* NOTE: if there is no existing session, the newly created session will not have the AUTHORITIES attribute */
        Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) httpSession.getAttribute("AUTHORITIES");
        if(authorities == null){ /* authorities will be empty if there is no AUTHORITIES attribute */
            /* if authorities is null, then there did not exist a session for the user */
            /* invalidate the newly created session and throw an error */
            request.getSession().invalidate();
            throw new UserSessionNotFoundException("There does not exist a session for this user.  Fix by creating a session of the user (by advising the user to login or register and login with a new account)");
        }
        /* it is assumed that AUTHORITIES attribute always holds data for a Collection of GrantedAuthority objects */
        return (Collection<? extends GrantedAuthority>) httpSession.getAttribute("AUTHORITIES");
    }

    public void deleteSession(){
        /* invalidates current session (if there is one) */
        request.getSession().invalidate();
    }
}
