package com.twitter.tweet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.twitter.tweet.dao.UserRepository;
import com.twitter.tweet.dto.TweetersList;
import com.twitter.tweet.model.User;
import com.twitter.tweet.responses.ApiResponse;
import com.twitter.tweet.service.UserCrudService;

@Controller
@RequestMapping("/tweet")
public class UserController {
	
	@Autowired
	private UserRepository R_User;
	@Autowired
	private UserCrudService userService;
	@Autowired
	private ApiResponse apiResponse;
	
	@GetMapping(path = "/user/list", produces = "application/json")
	public ResponseEntity<Object> listOfUsers(Authentication authentication)
	{
		TweetersList userList = userService.listUsers(authentication);
		apiResponse.setData(userList);
		apiResponse.setMessage("User List");
		
		return new ResponseEntity<>(apiResponse.getBodyResponse(),HttpStatus.OK);
	}
	
	@PostMapping(path = "/user/change_password", produces = "application/json")
	public ResponseEntity<Object> changePassword(Authentication authentication, @RequestBody User user) throws Exception
	{
        User LoggedInUser = R_User.findByUsername(authentication.getName());
        user.setUser_id(LoggedInUser.getUser_id());
        user.setEmail(LoggedInUser.getEmail());
        user.setUsername(LoggedInUser.getUsername());
		User updatedUser = userService.changePassword(user);
		apiResponse.setData(updatedUser);
		apiResponse.setMessage("Password changes");
		
		return new ResponseEntity<>(apiResponse.getBodyResponse(),HttpStatus.OK);
	}
}
