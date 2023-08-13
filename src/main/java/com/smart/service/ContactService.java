package com.smart.service;


import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.repository.ContactRepository;

@Service
public class ContactService {
	
	@Autowired
	ContactRepository contactRepository;
	
//	Method to return list of contacts of user by user_id
	public List<Contact> findAllByUserId(int id)
	{
		return contactRepository.findAllByUserId(id);
	}
	
//	Method to save a contact 
	public void save(Contact contact)
	{
		contactRepository.save(contact);
	}
	
//	Method to delete a contact
	public void delete(Contact contact)
	{
		contactRepository.delete(contact);
	}
	
//	Method to return a contact by its id
	public Contact findById(int id)
	{
		Optional<Contact> Contact = contactRepository.findById(id);
		return Contact.get();
	}
	
//	Method to update a contact
	public void update(Contact contact)
	{
		contactRepository.save(contact);
	}
	
	public List<Contact> findByNameContainingAndUser(String name, User user)
	{
		return contactRepository.findByNameContainingAndUser(name, user);
	}
	
}
