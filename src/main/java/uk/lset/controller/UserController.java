package uk.lset.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uk.lset.model.User;
import uk.lset.request.UserRequest;
import uk.lset.response.UserResponse;
import uk.lset.service.BankAccountService;
import uk.lset.service.UserService;

@RestController
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@GetMapping("/user/ping")
	public Boolean ping()  {
		logger.debug("Ping request");
		return true;
	}
	
	@GetMapping("/user/{id}")
	public User getForCognitoID(@PathVariable("id") String id)  {
		logger.debug("User access requested for id : " + id);
		return userService.findById(id);
	}
	
	@GetMapping("/user/byemail/{email}")
	public User getForEmail(@PathVariable("email") String email)  {
		logger.info("User access requested for email : " + email);
		User user = userService.findByEmail(email);
		return user;
	}
	
	@PostMapping(path = "/user/add", consumes = {"application/json"}, produces = "application/json")
	public UserResponse addUserWithoutImage(@RequestBody UserRequest user) {
		logger.debug("adding new User without image");
		return this.addUserObj(user);
	}
	
	@PutMapping(path = "/user/update", consumes = {"application/json"}, produces = "application/json")
	public UserResponse updateUser(@RequestBody UserRequest userRequest) {
		logger.debug("update user without image");
		return this.updateUserObj(userRequest);
	}
	
	private UserResponse addUserObj(UserRequest userRequest) {
		UserResponse userResponse = new UserResponse();
		User user = new User();
		
		user.setCognitoid(userRequest.getCognitoid());
		user.setEmail(userRequest.getEmail());
		user.setName(userRequest.getName());
		user.setDateofbirth(userRequest.getDateofbirth());
		user.setAddress(userRequest.getAddress());
		user.setNationalinsurancenumber(userRequest.getNationalinsurancenumber());
		
		User updatedUser = userService.save(user);
		
		if(updatedUser != null) {
			userResponse.setCognitoid(updatedUser.getCognitoid());
			userResponse.setAddress(updatedUser.getAddress());
			userResponse.setDateofbirth(updatedUser.getDateofbirth());
			userResponse.setEmail(updatedUser.getEmail());
			userResponse.setName(updatedUser.getName());
			userResponse.setNationalinsurancenumber(updatedUser.getNationalinsurancenumber());
		}
		
		return userResponse;
	}
	
	private UserResponse updateUserObj(UserRequest userRequest) {

		User user = userService.findByEmail(userRequest.getEmail());
		UserResponse userResponse = new UserResponse();
		
		User updatedUser = null;
		if(user != null) {
			user.setCognitoid(userRequest.getCognitoid());
			user.setEmail(userRequest.getEmail());
			user.setName(userRequest.getName());
			user.setDateofbirth(userRequest.getDateofbirth());
			user.setAddress(userRequest.getAddress());
			user.setNationalinsurancenumber(userRequest.getNationalinsurancenumber());
			
			updatedUser = userService.save(user);
			
			if(updatedUser != null) {
				userResponse.setCognitoid(updatedUser.getCognitoid());
				userResponse.setAddress(updatedUser.getAddress());
				userResponse.setDateofbirth(updatedUser.getDateofbirth());
				userResponse.setEmail(updatedUser.getEmail());
				userResponse.setName(updatedUser.getName());
				userResponse.setNationalinsurancenumber(updatedUser.getNationalinsurancenumber());
			}
		}
		
		return userResponse;
	}
}
                       