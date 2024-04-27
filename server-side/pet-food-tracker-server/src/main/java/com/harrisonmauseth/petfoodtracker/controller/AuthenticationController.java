package com.harrisonmauseth.petfoodtracker.controller;

import com.harrisonmauseth.petfoodtracker.dao.UserDao;
import com.harrisonmauseth.petfoodtracker.dto.LoginDto;
import com.harrisonmauseth.petfoodtracker.dto.LoginResponseDto;
import com.harrisonmauseth.petfoodtracker.dto.RegisterUserDto;
import com.harrisonmauseth.petfoodtracker.exception.DaoException;
import com.harrisonmauseth.petfoodtracker.model.UserEntity;
import com.harrisonmauseth.petfoodtracker.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * AuthenticationController is a class used for handling requests to authenticate Users.
 * <p>
 * It depends on an instance of a UserDao for retrieving and storing user data and is
 * provided through dependency injection.
 */
@RestController
@CrossOrigin
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserDao userDao,
                                    PasswordEncoder passwordEncoder,
                                    JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.createToken(authentication, loginDto.isRemembered());

            UserEntity user = userDao.getUserByUsername(loginDto.getUsername());
            return new LoginResponseDto(jwt, user);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO error - " + e.getMessage());
        }

    }

    @PostMapping("/register")
    @PreAuthorize("permitAll")
    public ResponseEntity<String> register(@RequestBody RegisterUserDto registerUserDto) {
        if (userDao.existsByUsername(registerUserDto.getUsername())) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setRoles("USER");
        user.setActivated(true);

        try {
            userDao.createUser(user);
        } catch (DaoException e) {
            return new ResponseEntity<>("Unable to connect to database", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }


}
