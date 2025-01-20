package org.example.back.security.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.back.security.dao.UserDetailsImplDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Ezzaim Mohammed
 **/
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsImplDao userDetailsImplDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        return userDetailsImplDao.findByEmail(userEmail).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );
    }
}
