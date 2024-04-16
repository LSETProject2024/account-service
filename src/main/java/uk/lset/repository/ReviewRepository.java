package uk.lset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.lset.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	Optional<Review> findById(String id);
}
