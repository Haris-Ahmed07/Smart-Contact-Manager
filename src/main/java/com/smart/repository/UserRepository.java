package com.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

//	Method to return the user by its id
	public User findById(int id);
	
//	Method to return the user by its UserNAME(EMAIL)
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByUserName(@Param("email") String email);

}
