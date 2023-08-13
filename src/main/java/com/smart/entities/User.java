package com.smart.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.*;


@Entity
@Table(name= "user")
public class User {

	@Id
	@Column(name= "User_ID")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	
	@Column(name= "User_Name")
	@NotBlank(message= "Name Can Not Be Empty !!")
	@Size(min= 3, max= 20 , message= "Name Should Be Between 3-20 Characters")
	private String name;
	
	@Column(name= "Email_Address", unique= true)
	@NotBlank(message= "Email Address Can't Be Empty !!")
	@Email(message="Invalid Format")
	private String email;
	
	@Column(name= "Password")
	@Size( min=3, max=225, message="Length error")
	@NotBlank(message= "Password Can't Be Empty !!")
	private String password;
	
	@Column(name= "Role")
	private String role;
	
	@Column(name= "About")
	@NotBlank(message= "About Can't Be Empty !!")
	private String about;
	
	@Column(name= "Image")
	private String image;
	
	@Column(name= "Enable")
	private boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<Contact> contacts = new ArrayList<>();

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int id, String name, String email, String password, String role, String about, String image, boolean enabled, List<Contact> contacts) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.about = about;
		this.image = image;
		this.enabled = enabled;
		this.contacts = contacts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Contact> getContacts() {
		return contacts;
	}


	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	
	public void deleteContact(Contact contact)
	{
		contacts.remove(contacts.indexOf(contact));
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", about=" + about + ", image=" + image + ", enabled=" + enabled + ", contacts=" + contacts + "]";
	}
	
	
}
