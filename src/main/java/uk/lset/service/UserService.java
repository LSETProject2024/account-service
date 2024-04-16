package uk.lset.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.lset.model.User;
import uk.lset.repository.UserRepository;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public User findById(String id)  {
		return userRepository.findById(id).orElse(null);
	}
	
	public User findByEmail(String email)  {
		return userRepository.findByEmail(email).orElse(null);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
}