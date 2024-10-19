package it.perigea.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.perigea.DTO.UserDTO;
import it.perigea.mapper.UserMapper;
import it.perigea.model.User;
import it.perigea.repository.UserRepository;

/**
 * A service class for login
 */
@Service
public class LoginService
{
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserMapper mapper;
	
	/**
	 * Return true or false based on the presence of the user information on the db
	 * @param userDTO - user to authenticate
	 * @return boolean - true if present
	 */
	public boolean authentication(UserDTO userDTO)
	{
		User user = mapper.DTOToUser(userDTO);
		Optional<User> optional= repository.findById(user.getUsername());
		if(optional.isEmpty()) return false;
		return optional.get().getPassword().equals(user.getPassword());
	}
	
	/**
	 * Store the user data on the db
	 * @param userDTO
	 */
	public void createUser(UserDTO userDTO)
	{	
		User user = mapper.DTOToUser(userDTO);
		userControll(user);
		repository.save(user);
	}
	
	/**
	 * Control if some required data are missing
	 * @param user
	 */
	private void userControll(User user)
	{
		if(user == null)
			throw new IllegalArgumentException();
		if(user.getUsername() == null | user.getUsername().isBlank())
			throw new IllegalArgumentException();
		if(user.getPassword() == null | user.getPassword().isBlank())
			throw new IllegalArgumentException();
	}

}
