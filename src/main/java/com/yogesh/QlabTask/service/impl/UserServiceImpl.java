package com.yogesh.QlabTask.service.impl;

import com.yogesh.QlabTask.entity.Role;
import com.yogesh.QlabTask.entity.User;
import com.yogesh.QlabTask.payloads.UserDto;
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
    public UserDto createUser(UserDto userDto) {
        // create user object
        User user = User.builder()
                .name(userDto.getName())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .about(userDto.getAbout())
                .build();

        Role roles = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(roles));

        User newuser= userRepository.save(user);


        return mapToDto(newuser);
    }

    @Override
    public UserDto getUserProfile(String username) {
        User user = userRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
       return mapToDto(user);
        // return modelMapper.map(user, UserDto.class);
    }


    public UserDto mapToDto(User user){
        UserDto newUserDto = new UserDto();
        newUserDto.setName(user.getName());
        newUserDto.setEmail(user.getEmail());
        newUserDto.setUsername(user.getUsername());
        newUserDto.setPassword("Hidden");
        newUserDto.setAbout(user.getAbout());
        return newUserDto;
    }

}
