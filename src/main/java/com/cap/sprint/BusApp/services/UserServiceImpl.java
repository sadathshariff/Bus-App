package com.cap.sprint.BusApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cap.sprint.BusApp.entities.User;
import com.cap.sprint.BusApp.exception.UserAlreadyExistException;
import com.cap.sprint.BusApp.exception.UserNotFoundException;
import com.cap.sprint.BusApp.repos.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserRepository userRepository;
	
	@Override
	public void addUser(User user) {
		Optional<User> u = userRepository.findByUsername(user.getUsername());
		if(u.isPresent()) {
			throw new UserAlreadyExistException("User already exists!!!");
		} else {
			userRepository.save(user);
		}
	}
	
	@Override
	public void deleteUser(String username) {
		User newUser = null;
		Optional<User> u =userRepository.findByUsername(username);
		if(u.isPresent()) {
			newUser = u.get();
			userRepository.delete(newUser);
		} else {
			throw new UserNotFoundException("User with username "+username+" does not exist");
		}
	}
	
	@Override
	public void updateUser(String username, String password) {
		User newUser = null;
		Optional<User> u =userRepository.findByUsername(username);
		if(u.isPresent()) {
			newUser = u.get();
			newUser.setPassword(password);
		} else {
			throw new UserNotFoundException("User with username "+username+" does not exist");
		}
	}
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
}
