package it.perigea.mapper;

import org.mapstruct.Mapper;

import it.perigea.DTO.UserDTO;
import it.perigea.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper
{
	public UserDTO userToDTO(User user);
	
	public User DTOToUser(UserDTO userDTO);
}
