package com.yogesh.QlabTask.repository;

import com.yogesh.QlabTask.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.nio.CharBuffer;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
   Optional<Role> findByName(String roleAdmin);
}
