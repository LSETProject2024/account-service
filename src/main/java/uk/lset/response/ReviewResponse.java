package uk.lset.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import uk.lset.model.Review;

@Getter @Setter 
public class ReviewResponse {
	private List<Review> reviews;
}
