package com.app.polling.reprository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.polling.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

}
