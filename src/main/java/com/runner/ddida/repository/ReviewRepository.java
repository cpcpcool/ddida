package com.runner.ddida.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runner.ddida.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
