package com.ecommerce.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ecommerce.security.UserPrincipal;

public class UserSecurityService {

    public static UserPrincipal authenticated() {
        try {
            return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e ) {
            return null;
        }
    }
 }
