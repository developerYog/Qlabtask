package com.yogesh.QlabTask.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

    public class UserDto {

        private String name;
        private String username;
        private String email;
        private String password;
        private String about;
    }

