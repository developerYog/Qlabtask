package com.yogesh.QlabTask.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    @Table(name = "users")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long userId;

        @Column(name = "full_name")
        private String name;

        @Column(name = "username", nullable = false, unique = true)
        private String username;

        @Column(name = "email", nullable = false, unique = true)
        private String email;

        @Column(name = "user_password", length = 500)
        private String password;

        @Column(name="about", length = 2000)
        private String about;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Article> articles;


}

