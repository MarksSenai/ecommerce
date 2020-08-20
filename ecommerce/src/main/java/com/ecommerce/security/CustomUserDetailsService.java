package com.ecommerce.security;

import java.util.Optional;

import com.ecommerce.domains.User;
import com.ecommerce.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    User user;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (userRepository.findByEmail(userName) != null) {
            user = userRepository.findByEmail(userName);
            return UserPrincipal.create(user);
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado com o email : " + userName);
        }
    }

    // This method is used by JWTAuthenticationFilter
    public UserDetails loadUserById(Long id) {
        if (userRepository.findById(id) != null) {
            Optional<User> user = userRepository.findById(id);
            return UserPrincipal.create(user.get());
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado com o id : " + id);
        }

    }
}
