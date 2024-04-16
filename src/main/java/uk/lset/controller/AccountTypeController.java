package uk.lset.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uk.lset.model.BankAccountType;
import uk.lset.model.Review;
import uk.lset.request.BankAccountTypeRequest;
import uk.lset.request.ReviewRequest;
import uk.lset.response.BankAccountTypeResponse;
import uk.lset.response.ReviewResponse;
import uk.lset.service.BankAccountTypeService;
import uk.lset.service.ReviewService;
import uk.lset.service.UserService;

@RestController
public class AccountTypeController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountTypeController.class);
	
	@Autowired
	private BankAccountTypeService bankAccountTypeService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/accounttype/all")
	public List<BankAccountType> getAllAccountTypes()  {
		logger.debug("Get all account types ");
		return bankAccountTypeService.findAll();
	}
	
	@GetMapping("/accounttype/{id}")
	public BankAccountType getAccountType(@PathVariable("id") String id)  {
		logger.debug("Get the account type ");
		return bankAccountTypeService.findById(id);
	}
	
	@PostMapping(path = "/accounttype/add", consumes = {"application/json"}, produces = "application/json")
	public BankAccountTypeResponse addBankAccountType(@RequestBody BankAccountTypeRequest bankAccountTypeRequest) {
		logger.debug("adding new account type");
		BankAccountType bankAccountType = new BankAccountType();
		
		bankAccountType.setName(bankAccountTypeRequest.getName());
		bankAccountType.setDescription(bankAccountTypeRequest.getDescription());
		
		BankAccountType updatedBankAccountType = bankAccountTypeService.save(bankAccountType);
		
		BankAccountTypeResponse bankAccountResponse = new BankAccountTypeResponse();
		
		if(updatedBankAccountType != null) {
			bankAccountResponse.setName(updatedBankAccountType.getName());
			bankAccountResponse.setDescription(updatedBankAccountType.getDescription());
		}
		
		return bankAccountResponse;
	}
	
	@PostMapping(path = "/review/add", consumes = {"application/json"}, produces = "application/json")
	public ReviewResponse addReview(@RequestBody ReviewRequest reviewRequest) {
		logger.debug("adding new account type");
		Review review = new Review();
		
		review.setContent(reviewRequest.getContent());
		review.setRating(reviewRequest.getRating());
		review.setUser(userService.findByEmail(reviewRequest.getUser()));
		
		Review addedReview = reviewService.save(review);
		
		BankAccountType bankAccountType = bankAccountTypeService.findById(reviewRequest.getAccounttype());
		ReviewResponse reviewResponse = new ReviewResponse();
		
		if(bankAccountType != null) {
			List<Review> reviews = bankAccountType.getReviews();
			if(reviews == null) {
				reviews = new ArrayList<Review>();
			}
			reviews.add(addedReview);
			bankAccountType.setReviews(reviews);
			bankAccountTypeService.save(bankAccountType);
			reviewResponse.setReviews(bankAccountType.getReviews());
		}
		
		return reviewResponse;
	}
	

}
                       