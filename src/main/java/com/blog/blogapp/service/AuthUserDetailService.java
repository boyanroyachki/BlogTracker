package com.blog.blogapp.service;

import com.blog.blogapp.model.AuthUser;
import com.blog.blogapp.repository.AuthUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthUserDetailService implements UserDetailsService {

    @Autowired
    private final AuthUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> authUser = userRepository.findByUsername(username.toLowerCase());
        if (!authUser.isPresent()) {
            throw new UsernameNotFoundException(username);
        } else {
            return User.builder()
                    .username(authUser.get().getUsername())
                    .password(authUser.get().getPassword())
                    .disabled(!authUser.get().isActive())
                    .build();
        }
    }

    public String getCurrentUsername() {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = null;

            if (authentication != null) {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    username = ((UserDetails) authentication.getPrincipal()).getUsername();
                } else {
                    username = authentication.getPrincipal().toString();
                }
            }

            return username;
    }
}
