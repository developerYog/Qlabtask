package com.yogesh.QlabTask.service;

import com.yogesh.QlabTask.payloads.UserDto;


public interface UserService {
    public UserDto createUser(UserDto dto);
    public UserDto getUserProfile(String username);


}
