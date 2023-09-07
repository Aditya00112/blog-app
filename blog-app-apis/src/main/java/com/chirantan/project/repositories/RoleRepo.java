package com.chirantan.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chirantan.project.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
