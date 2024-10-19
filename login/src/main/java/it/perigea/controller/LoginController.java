package it.perigea.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.perigea.DTO.UserDTO;
import it.perigea.service.LoginService;

@CrossOrigin(origins = "*")
@RestController
public class LoginController
{
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * A Post request for authenticate a user
	 * @param user - the user to be authenticated
	 * @return ResponseEntity - an HTTP response 
	 */
	@PostMapping
	public ResponseEntity<String> authentication(@RequestBody UserDTO user)
	{
		logger.info("login request");
		if(loginService.authentication(user))
		{
			logger.info("authenticated user: "+user.getUsername());
			return ResponseEntity.ok("Successful");
		}
		else 
			return ResponseEntity.status(403).body("Denied");
	}
	
	/**
	 * A Post request for creating a new user
	 * @param user - the user to be created
	 * @return ResponseEntity - an HTTP response
	 */
	@PostMapping("/create")
	public ResponseEntity<String> createUser(@RequestBody UserDTO user)
	{
		logger.info("creating user: "+user.getUsername());
		loginService.createUser(user);
		return ResponseEntity.ok("Successful");
	}
	
	/**
	 * A function to handle all the IllegalArgumentException
	 * @param e - the IllegalArgumentException thrown
	 * @return an HTTP response with bad request code
	 */
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<String> IllegalArgumentExceptionHandler(IllegalArgumentException e)
	{
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	/**
	 * A function to handle all Exception
	 * @param e - the Exception thrown
	 * @return an HTTP response with internal server error code
	 */
	@ExceptionHandler
	public ResponseEntity<String> ExceptionHandler(Exception e)
	{
		return ResponseEntity.internalServerError().body(e.getMessage());
	}
}
