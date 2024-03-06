package com.yogesh.QlabTask.service;

import com.yogesh.QlabTask.payloads.SignUpDto;


public interface UserService {
    public SignUpDto createUser(SignUpDto dto);
    public SignUpDto getUserProfile(String username);


}
