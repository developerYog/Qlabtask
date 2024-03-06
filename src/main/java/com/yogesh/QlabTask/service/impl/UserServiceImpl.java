package com.yogesh.QlabTask.service.impl;

import com.yogesh.QlabTask.entity.Role;
import com.yogesh.QlabTask.entity.User;
import com.yogesh.QlabTask.payloads.SignUpDto;
import com.yogesh.QlabTask.repository.RoleRepository;
import com.yogesh.QlabTask.repository.UserRepository;
import com.yogesh.QlabTask.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SignUpDto createUser(SignUpDto signUpDto) {
        // create user object
        User user = User.builder()
                .name(signUpDto.getName())
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .about(signUpDto.getAbout())
                .build();

        Role roles = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(roles));

        User newuser= userRepository.save(user);


        return mapToDto(newuser);
    }

    @Override
    public SignUpDto getUserProfile(String username) {
        User user = userRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return modelMapper.map(user,SignUpDto.class);
    }


    public SignUpDto mapToDto(User user){
        SignUpDto newSignUpDto = new SignUpDto();
        newSignUpDto.setName(user.getName());
        newSignUpDto.setEmail(user.getEmail());
        newSignUpDto.setUsername(user.getUsername());
        newSignUpDto.setAbout(user.getAbout());
        return newSignUpDto;
    }

}
