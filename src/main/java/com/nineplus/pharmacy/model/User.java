package com.nineplus.pharmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Users")
@Table(name = "users")
public class User {

	private long id;
	private String userName;
	private String password;
	private Integer role;
	private Boolean rememBer;

	public User() {

	}
	
	public User(long id, String userName, String password, Integer role, Boolean rememBer) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.rememBer = rememBer;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "user_name", nullable = false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "role_name", nullable = false)
	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	@Column(name = "remember", nullable = false)
	public Boolean getRememBer() {
		return rememBer;
	}

	public void setRememBer(Boolean rememBer) {
		this.rememBer = rememBer;
	}
}
