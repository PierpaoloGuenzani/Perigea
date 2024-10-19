package it.perigea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.perigea.model.User;

public interface UserRepository extends JpaRepository<User, String>
{
	
}
