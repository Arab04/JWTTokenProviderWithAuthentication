package com.app.polling.reprository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.polling.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>{

}
