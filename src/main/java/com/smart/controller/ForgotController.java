package com.smart.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.service.EmailApiService;
import com.smart.service.UserService;

@Controller
public class ForgotController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailApiService emailApiService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private static String otp;
	private static String email;

	
	@GetMapping("/forgot-password")
	public String forgotPassword(Model model) 
	{
		model.addAttribute("title", "Forgot Password  -Smart_Contact_Manager");
		return "forgot_Password";
	}

//	Method for generating OTP
	public static String generateOTP() {
		Random random = new Random();
		int min = 100_000; // Minimum value for a 6-digit number
		int max = 999_999; // Maximum value for a 6-digit number
		int generatedPin = random.nextInt(max - min + 1) + min;
		return String.format("%06d", generatedPin); // Format as 6-digit string with leading zeros
	}

	@PostMapping("/send-otp")
	public String otpVerification(@RequestParam(name = "email") String email2, HttpSession session, Model model ){
			
		model.addAttribute("title", "Verify OTP  -Smart_Contact_Manager");
		
		try {
//			Checking if your exists else throwing exception
			if(!userService.userExist(email2)){
				
//				Passing error attribute in session if user does not exist
				session.setAttribute("errorMessage", new Message("User Not Found", "alert-danger"));
	            
//				Throwing exception to run the catch block
				throw new Exception("User Not Found");
			}
				
//			Assigning the OTP and Email to global variables
			otp = generateOTP();
			email = email2;

//			This is the Message that will be sent to the User
			String message = "Dear User,\r\n"
					+ "\r\n"
					+ "We're excited to help you verify your account. As part of our security measures, we've generated a unique 6-digit verification code for you. Please use the code provided below to complete the verification process:\r\n"
					+ "\r\n"
					+ "Verification Code: "+ otp +"\r\n"
					+ "\r\n"
					+ "Please enter this code on the verification page to proceed. This code will expire after a certain period of time, so be sure to use it promptly.\r\n"
					+ "\r\n"
					+ "If you didn't initiate this verification process, please disregard this email. Your account's security is important to us.\r\n"
					+ "\r\n"
					+ "If you have any questions or encounter any issues, please don't hesitate to contact our support team at smartcontactmanager77@gmail.com.\r\n"
					+ "\r\n"
					+ "Thank you for choosing us!\r\n"
					+ "\r\n"
					+ "Best regards,\r\n"
					+ "Haris Ahmed\r\n"
					+ "Smart Contact Manager";

//			Sending the parameters to the EmailApi Class
			emailApiService.sendEmail(email, "Reset Password For Smart_Contact_Manager Account", message);
			
		} catch (Exception e) {
//			Printing the exception if any occurs
			e.printStackTrace();
			
//			Redirecting to the page again if any exception occurs
			return "redirect:/forgot-password";
		}
		
//		Passing success attribute in session if OTP has been sent successfult to the email
		session.setAttribute("message", new Message("We have sent an OTP to your email","alert-success")); 
		
		return "verify_otp";
	}
	
	@RequestMapping("/verify-otp")
	public String validateOTP(@RequestParam(name="otp") String otp2, HttpSession session, Model model)
	{
		model.addAttribute("title", "Verify OTP  -Smart_Contact_Manager");
		
//		Validating if user has inserted the correct OTP
		if(otp.equals(otp2))
		{
//			If the OTP is correct then we will move to the change_password form 
			return "change_password";
		}
		else
		{
//			If the OTP is wrong we will pass the error attribute to the session
			session.setAttribute("message", new Message("Please enter the correct OTP","alert-danger"));
			
//			returning to the same page if the OTP is wrong
			return "verify_otp";
		}
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam(name="newPassword") String newPassword, @RequestParam(name="confirmNewPassword") String confirmNewPassowrd, HttpSession session, Model model)
	{
		model.addAttribute("title", "Change Password  -Smart_Contact_Manager");
		
		try {
				
//			Checking if newPassword & confirmNewPassword match
			if(!newPassword.equals(confirmNewPassowrd))
			{
//				If they don't match we will pass an error attribute to the session
				session.setAttribute("message", new Message("Password Doesn't Match","alert-danger"));
				
//				If both password does not match we will throw exception to run the catch block
				throw new Exception("Password Doesn't Match");
			}
			
			
//			Getting the user
			User user = userService.getUserByUserName(email);
//			Setting the new password
			user.setPassword(passwordEncoder.encode(newPassword));
//			updating the user in the database
			userService.save(user);
			
//			Passing the success attribute to the session if password has been changed successfully
			session.setAttribute("message", new Message("Password Successfully Changed","alert-success"));
			
//			return to the same page
			return "change_password";
			
		} catch (Exception e) {
			
//			Printing the exception if any occurs
			e.printStackTrace();
			
//			Returning to the same page
			return "change_password";
		}
			
		

	}

	
	
}
