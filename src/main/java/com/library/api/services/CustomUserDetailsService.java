package com.library.api.services;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.model.UserPrincipal;
import com.library.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s)
                .map(UserPrincipal::new)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found :" +s));
    }
}
