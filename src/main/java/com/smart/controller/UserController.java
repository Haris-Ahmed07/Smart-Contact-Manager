package com.smart.controller;


import java.security.Principal;
import java.util.List;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.service.ContactService;
import com.smart.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	ContactService contactService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal)
	{
    	User user = userService.getUserByUserName(principal.getName());
    	model.addAttribute("user", user);

	}
	
    @GetMapping("/dashboard")
    public String dashBoard(Model model)
    {	
    	model.addAttribute("title", "DashBoard  -Smart_Contact_Manager");
    	
        return "user/user_dashboard";
    }
    
    @GetMapping("/user_add-contact")
    public String addContact(Model model)
    {
    	model.addAttribute("title", "Add Contact  -Smart_Contact_Manager");
    	
//    	Adding the contact object so it cansave the attributes in the webpage
    	model.addAttribute("contact", new Contact());
    	return "user/user_add_contact_form";
    }
    

    @PostMapping("/process-contact")
    public String processContact(@Valid @ModelAttribute Contact contact,
    		BindingResult result,
    		Principal principal,
    		Model model,
    		HttpSession session)
    {
    	

    	model.addAttribute("title", "Add Contact  -Smart_Contact_Manager");
    	
    	
//    	Checking for the exceptions 
    	try {
    		
    		if(result.hasErrors())
        	{
//    			Throwing exception if any error occcurs
        		throw new Exception("Validation Exception");
        	}
     	
//        	Getting The User Information
        	String name= principal.getName();
        	User user = userService.getUserByUserName(name);
        	
//        	Adding User In The Contact
        	contact.setUser(user);
        	
//        	Adding The Contact In The ArrayList
        	user.getContacts().add(contact);
        	
//        	Updating The User
        	this.userService.save(user);
        	
//        	Printing The User For Debugging
        	System.out.println("Data: "+ contact);
        	
        	System.out.println("Added To DataBase");
        	
        	
//        	Adding successful message to the session
        	session.setAttribute("message", new Message("Contact has been added !! add more..","success"));
        	
        	
        	model.addAttribute("contact", new Contact());
    		
		} catch (Exception e) {
			
//			Catching and printing the exception
			e.printStackTrace();
			
//			adding fail message to the session
			session.setAttribute("message", new Message("Something went wrong !! try again","danger"));
			
		}
    	
    	
    	return "user/user_add_contact_form";
    }


    
    @GetMapping("user_show-contacts")
    public String showContacts(Model model, Principal principal)
    {
    	model.addAttribute("title", "Contacts  -Smart_Contact_Manager");
//    	Getting the current user's Username(email)
    	String username= principal.getName();
    	
//    	getting user by their email
    	User user = userService.getUserByUserName(username);
    	
//    	Getting all the contacts of that user
    	List<Contact> contacts = contactService.findAllByUserId(user.getId());
    	
//    	Adding contacts to the model to use it in the WebPage
    	model.addAttribute("contacts", contacts);
    	

    	return "user/user_show_contacts_form";
    }
    

    
    @GetMapping("/contact/{cId}")
    public String showContact(@PathVariable("cId") Integer cId, Model model, Principal principal)
    {

    	
//    	Getting the contact 
    	Contact contact= contactService.findById(cId);
   
    	model.addAttribute("title", contact.getName()+"  -Smart_Contact_Manager");
    	
//    	Getting the current User by searching from their email
    	User user = userService.getUserByUserName(principal.getName());
    	
    	
//    	If the contact belongs to the current user Then the contact will be served
    	if(user.getId() == contact.getUser().getId())
    	{
    		model.addAttribute("contact", contact);    		
    	}
    		
    	System.out.println(cId);
    	return "user/user_show_contact";
    }

    
    @GetMapping("delete/{cId}")
    public String deleteContact(@PathVariable("cId") Integer cId, Principal principal)
    {
//    	Getting contact by its id
    	Contact contact = contactService.findById(cId);    	
    	
//    	Getting the user that belongs to that contact
    	User user = userService.getUserByUserName(principal.getName());
    	
//    	Removing connection of user from the contact
    	contact.setUser(null);
    	
//    	Deleting the contact from the user
    	user.deleteContact(contact);
    	
//    	Deleting the contact from the database
    	this.contactService.delete(contact);
    	
    	return "redirect:/user/user_show-contacts";
    }

    
    @PostMapping("/update/{cId}")
    public String updateContact(@PathVariable("cId") Integer cId, Model model)
    {

    	model.addAttribute("title", "Update Contact  -Smart_Contact_Manager");
    	
    	Contact contact = contactService.findById(cId);
    	
    	model.addAttribute("contact", contact);
    	
    	
    	return "user/user_update_contact_form";
    }


    @PostMapping("/process_update-contact")
    public String processUpdateContact(@Valid @ModelAttribute Contact contact,BindingResult result, Principal principal, Model model)
    {
    	model.addAttribute("title", "Update Contact  -Smart_Contact_Manager");
    	
    	try {
    		
    		if(result.hasErrors())
    		{
    			throw new Exception("Something went wrong !!");
    		}
    		
//        	Getting the Current User
        	User user = userService.getUserByUserName(principal.getName());
        	
//        	Setting The User Again Because Html Page doesn't Map It
        	contact.setUser(user);
        	
        	
//        	Updating The Contact
        	contactService.update(contact);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

    	
//    	redirecting to  the same contact 
    	return "redirect:/user/contact/"+contact.getcId();
    }
    
    @GetMapping("/user_profile")
    public String userProfile(Model model)
    {
    	model.addAttribute("title", "Profile  -Smart_Contact_Manager");
    	return "/user/user_profile";
    }   
    
    @GetMapping("/user_setting")
    public String userSetting(Model model)
    {
    	model.addAttribute("title", "Setting  -Smart_Contact_Manager");
    	return "user/user_setting";
    }
    
    @GetMapping("/change-password")
    public String changePassword(Model model)
    {
    	model.addAttribute("title", "Change Password  -Smart_Contact_Manager");
    	model.addAttribute("message",new Message("Password has been changed successfully !!","success"));
    	return "user/user_change-password";
    }
    
    @PostMapping("/process_change-password")
    public String processChangePassword(
    		@RequestParam("currentPassword") String currentPassword,
    		@RequestParam("newPassword") String newPassword,
    		@RequestParam("confirmNewPassword") String confirmNewPassword, 
    		Principal principal,
    		HttpSession session,
    		Model model)
    {
    	
    	model.addAttribute("title", "Change Password  -Smart_Contact_Manager");
    	
    	try {
    		
//    		Getting the current user object
    		User user = userService.getUserByUserName(principal.getName());
    		
    		
//    		Matching the passwords 
    		if(passwordEncoder.matches(currentPassword, user.getPassword()))
    		{
    			if(newPassword.equals(confirmNewPassword))
    			{
    				user.setPassword(passwordEncoder.encode(newPassword));
    				userService.save(user);
    				
    				model.addAttribute("user", user);
    				session.setAttribute("message",new Message("Password has been changed successfully !!","alert-success"));

    			}
    			else
    			{
    				session.setAttribute("message",new Message("Both new password doesn't match","alert-danger"));    				
    			}  			
    			
    			
    		}
    		else
    		{
    			session.setAttribute("message",new Message("Current password is not correct !!","alert-danger"));
    		}
    		
    		
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	return "user/user_change-password";

    }
    

}

