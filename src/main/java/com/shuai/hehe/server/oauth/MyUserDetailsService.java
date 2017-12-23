package com.shuai.hehe.server.oauth;

import com.shuai.hehe.server.entity.AuthenticatedUser;
import com.shuai.hehe.server.entity.User;
import com.shuai.hehe.server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Component("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper mUserMapper;

    private List<SimpleGrantedAuthority> mAuthorities = new ArrayList<>();

    public MyUserDetailsService() {
        mAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = mUserMapper.getByPhone(phone);
        return new AuthenticatedUser(user.getId(), user.getPhone(), user.getPassword(), mAuthorities);
    }
}
