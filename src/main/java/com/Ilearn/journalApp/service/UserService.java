package com.Ilearn.journalApp.service;

import com.Ilearn.journalApp.Entity.User;
import com.Ilearn.journalApp.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;
	private final PasswordEncoder passwordEncoder =
	        new BCryptPasswordEncoder();

	// private static final Logger logger =
	// LoggerFactory.getLogger(UserService.class); used slf4j instead of creating
	// instance

	public boolean saveNewUser(User user) {

	    try {

	        System.out.println("USERNAME => " + user.getUsername());

	        System.out.println("PASSWORD => " + user.getPassword());

	        user.setPassword(
	                passwordEncoder.encode(
	                        user.getPassword()
	                )
	        );

	        user.setRoles(
	        		Arrays.asList("USER")
	        );

	        User savedUser =
	                userRepository.save(user);

	        System.out.println("SAVED USER => " + savedUser);

	        return true;

	    } catch (Exception e) {

	        e.printStackTrace();

	        throw new RuntimeException(e);
	    }
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(ObjectId id) {
		return userRepository.findById(id);
	}

	public void deleteUseryId(ObjectId id) {
		userRepository.deleteById(id);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void saveAdmin(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER", "ADMIN"));
		userRepository.save(user);
	}
}
