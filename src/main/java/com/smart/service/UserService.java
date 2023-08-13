package com.smart.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.smart.entities.User;
import com.smart.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
//	Method to save a user
	public void save(User user)
	{
		userRepository.save(user);
	}
	
//	Method to delete a contact by its id
	public boolean delete(int id)
	{	
//		Checking if the user exists 
		if(userRepository.existsById(id))
		{
//			Getting user by its id 
			User user = userRepository.findById(id);
			
//			Deleting the user
			userRepository.delete(user);
			
//			return true if  the user is deleted
			return true;
		}
		
//		return false if the user is not found
		return false;
	}
	
	
//	Method to get user by id
	public User get(int id)
	{	
		return userRepository.findById(id);
	}
	
//	Method to get user by UserName(email)
	public User getUserByUserName(String username)
	{
		return userRepository.getUserByUserName(username);
	}
	
//	Method to get user by id
	public User getUserById(int id)
	{
		User user = userRepository.findById(id);
		return user;
	}
	
	public boolean userExist(String userName)
	{
		User user = getUserByUserName(userName);
		
		try {
			if(userRepository.existsById(user.getId())){
				
				return true;
			}
			else {
				
				return false;
			}
		} catch (Exception e) {
			System.out.println("User Not Found");
			return false;
		}
		
	}
	
}
