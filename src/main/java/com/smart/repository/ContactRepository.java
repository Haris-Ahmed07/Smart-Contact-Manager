package com.smart.repository;



import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.smart.entities.Contact;
import com.smart.entities.User;

public interface ContactRepository extends CrudRepository<Contact, Integer> {

	
//	Method to return the list of contacts of the user by user_id
	public List<Contact> findAllByUserId(int userId);
	
//	Method to return the list of contacts from a User For searching
	public List<Contact> findByNameContainingAndUser(String name, User user);
}
