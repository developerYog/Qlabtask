package com.yogesh.QlabTask.controller;

import com.yogesh.QlabTask.payloads.JWTAuthResponse;
import com.yogesh.QlabTask.payloads.LoginDto;
import com.yogesh.QlabTask.payloads.SignUpDto;
import com.yogesh.QlabTask.service.UserService;
import com.yogesh.QlabTask.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));

    }

    //http://localhost:8080/api/v1/auth/registration
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

//        if(userRepository.existsByUsername(signUpDto.getUsername())){
//            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
//        }
//
//        if(userRepository.existsByEmail(signUpDto.getEmail())){
//            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
//        }
        try {
            userService.createUser(signUpDto);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User Creation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-profile")
    public ResponseEntity<SignUpDto> getProfile(HttpServletRequest request, Authentication authentication) {
        String token = jwtUtil.extractToken(request);

        if (token != null && jwtUtil.validateToken(token, (UserDetails) authentication.getPrincipal())) {
            String username = jwtUtil.extractUsername(token);
            SignUpDto  userDto = userService.getUserProfile(username);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            // Handle unauthorized access
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
