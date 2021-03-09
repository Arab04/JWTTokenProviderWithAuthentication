package com.app.polling.reprository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.polling.model.Role;
import com.app.polling.model.RoleName;
import com.app.polling.model.User;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>{

	Optional<Role> findByName(RoleName roleName);
}
