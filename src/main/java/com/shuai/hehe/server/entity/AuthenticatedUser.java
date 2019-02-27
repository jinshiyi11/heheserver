//package com.shuai.hehe.server.entity;
//
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
///**
// *
// */
//public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {
//    private long mUid;
//
//    public AuthenticatedUser(long uid, String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, authorities);
//        mUid=uid;
//    }
//
//    public long getUid() {
//        return mUid;
//    }
//
//    public void setUid(long uid) {
//        this.mUid = uid;
//    }
//}
