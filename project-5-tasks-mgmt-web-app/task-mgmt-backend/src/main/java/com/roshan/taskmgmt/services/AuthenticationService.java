package com.roshan.taskmgmt.services;

import com.roshan.taskmgmt.entities.LoginUser;
import com.roshan.taskmgmt.entities.RegisterUser;
import com.roshan.taskmgmt.entities.Users;
import com.roshan.taskmgmt.repositories.UsersRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Users register(RegisterUser input) {
        Users u = new Users(input.getName(), input.getEmail(), passwordEncoder.encode(input.getPassword()));

        return usersRepository.save(u);
    }

    public Users authenticate(LoginUser input) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
        ));

        return usersRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
