package com.roshan.taskmgmt.controllers;

import com.roshan.taskmgmt.entities.LoginResponse;
import com.roshan.taskmgmt.entities.LoginUser;
import com.roshan.taskmgmt.entities.RegisterUser;
import com.roshan.taskmgmt.entities.Users;
import com.roshan.taskmgmt.services.AuthenticationService;
import com.roshan.taskmgmt.services.JWTService;
import org.apache.coyote.Response;
import org.apache.juli.logging.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JWTService jwtService;
    private final AuthenticationService authenticationService;

    public AuthController(JWTService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    public ResponseEntity<Users> registerHandler(@RequestBody RegisterUser registerUser) {
        Users registeredUser = authenticationService.register(registerUser);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUser loginUser) throws  Exception{
        Users authenticatedUser = authenticationService.authenticate(loginUser);
        String jwtToken = jwtService.generateToken(authenticatedUser.getUsername());
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.extractExpiration(jwtToken).getTime());

        return ResponseEntity.ok(loginResponse);
    }
}
