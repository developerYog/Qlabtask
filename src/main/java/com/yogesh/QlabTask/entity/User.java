package com.yogesh.QlabTask.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Table(name = "users")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long userId;

        @Column(name = "full_name")
        private String name;

        @Column(name = "email", nullable = false, unique = true)
        private String email;

        @Column(name = "user_password", length = 500)
        private String password;

        @Column(name = "mobile_number", unique = true, nullable = false)
        private Long mobileNumber;

        @Column(name="about", length = 2000)
        private String about;

        @Enumerated(EnumType.STRING)
        private Role role;


}

