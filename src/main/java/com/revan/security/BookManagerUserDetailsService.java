package com.revan.security;

import com.revan.dao.Student;
import com.revan.model.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Optional;
import static org.springframework.security.core.userdetails.User.withUsername;

@Component
public class BookManagerUserDetailsService implements UserDetailsService {

    @Autowired
    private Student studentRepo;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Students user = studentRepo.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Student with name %s does not exist", username)));
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public Optional<UserDetails> loadUserByJwtToken(String jwtToken) {
        if (jwtTokenProvider.isValidToken(jwtToken)) {
            return Optional.of(
                    withUsername(jwtTokenProvider.getUsername(jwtToken))
                            .authorities(jwtTokenProvider.getRoles(jwtToken))
                            .password("") //token does not have password but field may not be empty
                            .accountExpired(false)
                            .accountLocked(false)
                            .credentialsExpired(false)
                            .disabled(false)
                            .build());
        }
        return Optional.empty();
    }

    public Optional<UserDetails> loadUserByJwtTokenAndDatabase(String jwtToken) {
        if (jwtTokenProvider.isValidToken(jwtToken)) {
            return Optional.of(loadUserByUsername(jwtTokenProvider.getUsername(jwtToken)));
        } else {
            return Optional.empty();
        }
    }
}
