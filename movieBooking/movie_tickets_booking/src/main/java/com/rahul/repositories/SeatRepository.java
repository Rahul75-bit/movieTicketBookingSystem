package com.rahul.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rahul.entities.Movie;

@Repository
public interface SeatRepository extends JpaRepository<Movie, Long> {

}
