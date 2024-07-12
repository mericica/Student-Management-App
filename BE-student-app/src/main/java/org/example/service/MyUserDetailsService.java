package org.example.service;

import jakarta.transaction.Transactional;
import org.example.entity.RoleEntity;
import org.example.entity.UserEntity;
import org.example.repository.UserDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDBRepository userDBRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userDBRepository.findByEmail(username).get();
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(RoleEntity::getName).toArray(String[]::new))
                .build();
    }
}
