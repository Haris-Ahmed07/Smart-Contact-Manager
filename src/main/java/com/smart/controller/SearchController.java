package com.smart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import com.smart.service.ContactService;
import com.smart.service.UserService;
import com.smart.entities.Contact;
import com.smart.entities.User;

@RestController
public class SearchController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ContactService contactService;
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal)
	{
//		Getting the current User to search contacts only of this user
		User user = userService.getUserByUserName(principal.getName());
		
//		Saving contacts in a list
		List<Contact> contacts = contactService.findByNameContainingAndUser(query, user);	
		
//		 Returning the contacts list
		return ResponseEntity.ok(contacts);
	}	
		
}
