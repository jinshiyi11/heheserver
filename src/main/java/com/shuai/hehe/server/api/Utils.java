package com.shuai.hehe.server.api;

import com.shuai.hehe.server.entity.AuthenticatedUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 */
public class Utils {
    public static long getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((AuthenticatedUser)authentication.getPrincipal()).getUid();
    }
}
