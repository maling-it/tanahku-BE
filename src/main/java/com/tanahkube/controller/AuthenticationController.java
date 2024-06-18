package com.tanahkube.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanahkube.model.WebResponse;
import com.tanahkube.model.users.CreateUserRequest;
import com.tanahkube.model.users.LoginRequest;
import com.tanahkube.service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/user/")
public class AuthenticationController {
    
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    // Create---------
    @PostMapping("admin/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public WebResponse createData(@Valid @RequestBody CreateUserRequest request, Errors errors){
        return this.authenticationService.register(request, errors);
    }

    // Login---------
    @PostMapping("login")
    public WebResponse login(@Valid @RequestBody LoginRequest request, Errors errors){
        return this.authenticationService.login(request, errors);
    }
}
