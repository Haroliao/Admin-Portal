package com.website.demo.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//model layer

@Entity
@Table(name = "Users")
public class User {
	//username is primary key
	@Id
    @Column(name = "Username", nullable = false)
	 private String username;
	 @Column(name = "Name", nullable = false)
	    private String name;
	 @Column(name = "Phone", nullable = false)
	    private String phone;
	 @Column(name = "Location", nullable = false)
	    private String location;

	 @Column(name = "RegistrationDATE", nullable = false)
	    private String registrationDate;
	 @Column(name = "Organization", nullable = false)
	    private String organization;
	 @Column(name = "Role", nullable = false)
	    private String role;
	 
	 @Override
	 public String toString() {
	     return "User{" +
	             "username='" + username + '\'' +
	             ", name='" + name + '\'' +
	             ", phone='" + phone + '\'' +
	             ", location='" + location + '\'' +
	             ", registrationDate='" + registrationDate + '\'' +
	             ", organization='" + organization + '\'' +
	             ", role='" + role + '\'' +
	             '}';
	 }


	    
	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	   
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    
	    public String getPhone() {
	        return phone;
	    }

	    public void setPhone(String phone) {
	        this.phone = phone;
	    }

	   
	    public String getLocation() {
	        return location;
	    }

	    public void setLocation(String location) {
	        this.location = location;
	    }

	    
	    public String getRegistrationDate() {
	        return registrationDate;
	    }

	    public void setRegistrationDate(String registrationDate) {
	        this.registrationDate = registrationDate;
	    }

	    public String getOrganization() {
	        return organization;
	    }

	    public void setOrganization(String organization) {
	        this.organization = organization;
	    }

	    
	    public String getRole() {
	        return role;
	    }

	    public void setRole(String role) {
	        this.role = role;
	    }

}
