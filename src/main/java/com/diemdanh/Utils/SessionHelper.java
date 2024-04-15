package com.diemdanh.Utils;

import com.diemdanh.model.Users;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



public class SessionHelper {
    public static Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return ((SessionUser)authentication.getPrincipal()).getUser();
        }
        return null;
    }
}

