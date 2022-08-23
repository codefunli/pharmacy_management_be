package com.nineplus.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nineplus.pharmacy.exception.ErrorDetails;
import com.nineplus.pharmacy.model.User;
import com.nineplus.pharmacy.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v2")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	
	@PostMapping("/users")
	public User createUser(@Validated @RequestBody User user){		
		return userRepository.save(user);
	}
	
	@PostMapping()
	public ResponseEntity<?> login(@Validated @RequestBody User user) {
		User user2 = userRepository.findByUserName(user.getUserName());
		if (user2==null) {
		return	new ResponseEntity<>(new ErrorDetails(null, "error not found", null),HttpStatus.NOT_FOUND);
		}
		
		if (!user2.getPassword().equals(user.getPassword())) {
			return	new ResponseEntity<>(new ErrorDetails(null, "user name or password not match", null),HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(user2,HttpStatus.OK);
	}
}
