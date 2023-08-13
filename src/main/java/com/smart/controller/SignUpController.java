package com.smart.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.service.EmailApiService;
import com.smart.service.UserService;

@Controller
public class SignUpController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailApiService emailApiService;
	
	@Autowired
	private static User tempUser;
	
	private static String otp;
	private static String email;
	
	
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user,
			BindingResult result,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
			HttpSession session,
			Model model) 
	{
	
		model.addAttribute("title", "Signup  -Smart_Contact_Manager");

		try {

			if (!agreement)
			{
//				Throwing exception if user did not agree to the terms
				throw new Exception("You have not agreed the terms and conditions");
			}
			else if (result.hasErrors()) 
			{
//				Adding user object to the webPage
				model.addAttribute("user", user);
				return "signup";
			}
			else if(userService.userExist(user.getEmail()))
			{
//				Sending message if the user with this email already exists
				session.setAttribute("message", new Message("This Email Is Already Registered", "alert-danger"));
				return "signup";
			}
			else 
			{
//				Setting user information
				user.setEnabled(true);
				user.setImage("default.img");
				user.setRole("ROLE_USER");
//				Setting password with encryption
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				
//				assigning the user to a variable so it can be used in the next webPage
				tempUser = user;
				
//				Getting the user email & OTP
				email = user.getEmail();
				otp = generateOTP();
				
//				Message that will be sent to the user
				String message = "Dear User,\r\n"
                        + "\r\n"
                        + "Welcome to our platform! We're thrilled to have you on board. To ensure the security of your account, we have generated a unique 6-digit verification code for you. Please use the code provided below to complete the email verification process:\r\n"
                        + "\r\n"
                        + "Verification Code: " + otp + "\r\n"
                        + "\r\n"
                        + "Please enter this code on the verification page to confirm your email address. For your safety, this code will expire after a certain period of time, so kindly use it promptly.\r\n"
                        + "\r\n"
                        + "If you didn't initiate this verification process, no action is required. Your account's security is our priority.\r\n"
                        + "\r\n"
                        + "Should you encounter any questions or difficulties, please feel free to reach out to our support team at smartcontactmanager77@gmail.com.\r\n"
                        + "\r\n"
                        + "Thank you for joining us!\r\n"
                        + "\r\n"
                        + "Best regards,\r\n"
                        + "Haris Ahmed\r\n"
                        + "Smart Contact Manager\r\n"; 

				emailApiService.sendEmail(email, "Email Verification For Smart_Contact_Manager Account", message);

				return "redirect:/verify-email";

			}

		} catch (Exception e) {

			e.printStackTrace();

			model.addAttribute("user", user);
			String message = "Something Went Wrong !! ";

			session.setAttribute("message", new Message(message + e.getMessage(), "alert-danger"));

			return "signup";
		}

	}
	
	@GetMapping("/verify-email")
	public String verifyEmail(HttpSession session, Model model)
	{	
		model.addAttribute("title", "Verify Email  -Smart_Contact_Manager");
		
		session.setAttribute("message", new Message("We have sent an OTP to your email", "alert-success"));	
		
		return "verify_email";
	}
	
	@PostMapping("/verify-email-otp")
	public String verifyEmailOTP(@RequestParam(name= "otp") String otp2, HttpSession session, Model model)
	{
		model.addAttribute("title", "Verify Email  -Smart_Contact_Manager"); 
		
		try 
		{
//			Checking if the OTP is correct
			if(otp.equals(otp2))
			{
//				If OTP is correct then saving the user in the Database
				userService.save(tempUser);
				
//				Setting the User variable to null so it can be used later as well
				tempUser = null;
				session.setAttribute("message", new Message("Account Successfully Registered, You Can Login now!!", "alert-success"));
			}
			else 
			{
				session.setAttribute("message", new Message("Incorrect OTP!! Enter Again..", "alert-danger"));
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return "verify_email";
	}
	
//	Method for generating OTP
	public static String generateOTP() 
	{
		Random random = new Random();
		int min = 100_000; // Minimum value for a 6-digit number
		int max = 999_999; // Maximum value for a 6-digit number
		int generatedPin = random.nextInt(max - min + 1) + min;
		return String.format("%06d", generatedPin); // Format as 6-digit string with leading zeros
	}
	
	
	
}
