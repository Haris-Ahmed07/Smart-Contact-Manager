package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.entities.User;
import com.smart.service.UserService;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserService userService;

//	Override method to return the UserDetailsServiceImpl with the user details
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//		Fetching User From DataBase
		User user = userService.getUserByUserName(username);

		if(user == null)
		{
			throw new UsernameNotFoundException("Could not found user !!");
		}

//		UserDetailsImpl object with the user details in it 
		UserDetailsImpl userDetailImpl = new UserDetailsImpl(user);

		return userDetailImpl;
	}

}
