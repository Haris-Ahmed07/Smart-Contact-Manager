package com.smart.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name= "contact")
public class Contact{

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name= "Contact_ID")
	private int cId;

	@Column(name= "Contact_Name")
	@NotBlank(message="Name cant't be empty")
	private String name;

	@Column(name= "Contact_NickName")
	private String nickName;

	@Column(name= "Contact_Email")
	@Email(message="Invalid Format")
	@NotBlank(message="Email cant't be empty")
	private String email;

	@Column(name= "Contact_Phone", length=11)
	@NotBlank(message="Phone-No cant't be empty")
	private String phone;

	@Column(name= "Contact_Work")
	private String work;

	@Column(name= "Contact_Profile")
	private String image;

	@Column(name= "Contact_Description", length=5000)
	private String description;

	@ManyToOne
	@JsonIgnore
	private User user;

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contact(int cId, String name, String nickName, String email, String phone, String work, String image,
			String description, User user) {
		super();
		this.cId = cId;
		this.name = name;
		this.nickName = nickName;
		this.email = email;
		this.phone = phone;
		this.work = work;
		this.image = image;
		this.description = description;
		this.user = user;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//	@Override
//	public String toString() {
//		return "Contact [cId=" + cId + ", name=" + name + ", nickName=" + nickName + ", email=" + email + ", phone="
//				+ phone + ", work=" + work + ", image=" + image + ", description=" + description + ", user=" + user
//				+ "]";
//	}



}
