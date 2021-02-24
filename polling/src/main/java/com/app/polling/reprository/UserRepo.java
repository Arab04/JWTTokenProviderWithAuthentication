package com.app.polling.reprository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.polling.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
    Optional<User> findByEmail(String email);
    
    Optional<User> findByUsername(String username);
	
	Optional<User> findByUsernameOrEmail(String username,String email);
	
	List<User> findByIdIn(List<User> UserIdIn);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);

}
