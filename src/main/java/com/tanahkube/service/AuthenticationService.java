package com.tanahkube.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.tanahkube.entity.Biodata;
import com.tanahkube.entity.Users;
import com.tanahkube.model.WebResponse;
import com.tanahkube.model.users.CreateUserRequest;
import com.tanahkube.model.users.JwtResponse;
import com.tanahkube.model.users.LoginRequest;
import com.tanahkube.model.users.UserResponse;
import com.tanahkube.repository.BiodataRepository;
import com.tanahkube.repository.UsersRepository;
import com.tanahkube.security.service.JwtService;


@Service
public class AuthenticationService {
    private UsersRepository userRepository;
    private BiodataRepository biodataRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    

    public AuthenticationService(UsersRepository userRepository,
        BiodataRepository biodataRepository, 
        AuthenticationManager authenticationManager,
        JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.biodataRepository = biodataRepository;
    }
    // Response-------------------------------------------------------------------
    private JwtResponse toJwtResponse(String email, String token){
        return JwtResponse.builder()
        .email(email)
        .token(token)
        .build();
    }

    private UserResponse toUserResponse(Users users){
        return UserResponse.builder()
        .email(users.getEmail())
        .build();
    }

    private WebResponse toWebResponse(String status, Object message, Object data, Object paging){
        return WebResponse.builder()
        .status(status)
        .message(message)
        .data(data)
        .paging(paging)
        .build();
    }


    // Create--------------------------------------------------------------------
    public WebResponse register(CreateUserRequest request, Errors errors){
        try {
            // validation
            if (errors.hasErrors()) {
                List<Object> error = new ArrayList<>();
                for (ObjectError err : errors.getAllErrors()) {
                    error.add(err.getDefaultMessage());
                }
                return toWebResponse("failed", error, new HashMap<>(), new HashMap<>());
            }
            // end validation
            Biodata biodata = new Biodata();
            biodata.setFullname(request.getBiodata().getFullname());
            biodata.setMobilePhone(request.getBiodata().getMobilePhone());
            biodata.setImagePath(request.getBiodata().getImagePath());

            biodata.setCreatedOn(new Date());
            biodata.setIsDelete(false);

            Biodata biodataSaved = this.biodataRepository.save(biodata);

            Users users = new Users();
            users.setUsername(request.getUsers().getUsername());
            users.setEmail(request.getUsers().getEmail());
            users.setPassword(bCryptPasswordEncoder.encode(request.getUsers().getPassword()));
            users.setRoleId(request.getUsers().getRoleId());
            users.setBiodataId(biodataSaved.getId());
            users.setCreatedOn(new Date());
            users.setIsDelete(false);

            Users userSaved = this.userRepository.save(users);

            UserResponse userResponse = toUserResponse(userSaved);
            return toWebResponse("success", "success create data", userResponse, new HashMap<>());
        } catch (Exception e) {
            return toWebResponse("failed", "failed create data", new HashMap<>(), new HashMap<>());
        }
    }

    // Read----------------------------------------------------------------------
    
    // Update--------------------------------------------------------------------

    // Login----------------------------------------------------------------------
    public WebResponse login(LoginRequest request, Errors errors){
        try {
            // validation
            if (errors.hasErrors()) {
                List<Object> error = new ArrayList<>();
                for (ObjectError err : errors.getAllErrors()) {
                    error.add(err.getDefaultMessage());
                }
                return toWebResponse("failed", error, new HashMap<>(), new HashMap<>());
            }
            // End Validation
            
            // login logic
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(request.getUsername());
                JwtResponse jwtResponse = toJwtResponse(request.getUsername(), token);
                return toWebResponse("success", "login success", jwtResponse, new HashMap<>());
            } else {
                return toWebResponse("failed", "login failed", new HashMap<>(), new HashMap<>());
            }
		} catch (BadCredentialsException e ) {
            return toWebResponse("failed", e.getMessage() + " | invalid username or password", new HashMap<>(), new HashMap<>());
        }
    }

}
