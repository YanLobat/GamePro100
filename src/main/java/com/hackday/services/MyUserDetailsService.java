package com.hackday.services;

import com.hackday.entity.UserEntity;
import com.hackday.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    //get userEntity from the database, via Hibernate
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        UserEntity user = userRepository.findByLogin(username);
        List<GrantedAuthority> authorities =
                buildUserAuthority(user.getGroup().toString());

        return buildUserForAuthentication(user, authorities);
    }

    // Converts UserEntity userEntity to
    // org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(UserEntity userEntity,
                                            List<GrantedAuthority> authorities) {
        return new User(userEntity.getLogin(), userEntity.getPassword(),
                true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(String group) {

        Set<GrantedAuthority> setAuths = new HashSet<>();
        setAuths.add(new SimpleGrantedAuthority(group));
        return new ArrayList<>(setAuths);
    }
}