package uk.lset.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.lset.model.Review;
import uk.lset.repository.ReviewRepository;

@Service
public class ReviewService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);
	
	@Autowired
	private ReviewRepository bankAccountTypeRepository;
	
	public Review findById(String id)  {
		return bankAccountTypeRepository.findById(id).orElse(null);
	}
	
	public Review save(Review review) {
		return bankAccountTypeRepository.save(review);
	}
	
}