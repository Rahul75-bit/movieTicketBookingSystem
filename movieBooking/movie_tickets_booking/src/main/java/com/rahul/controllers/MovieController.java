package com.rahul.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rahul.entities.Movie;
import com.rahul.repositories.MovieRepository;
import com.rahul.response_wrapper.ResponseWrapper;
import com.rahul.response_wrapper.UniversalResponse;
import com.rahul.services.MovieService;

import tools.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class MovieController {
	
	private final String IMAGE_UPLOAD_DIR =
	        System.getProperty("user.dir") + "/uploads/images";
	
	
	@Autowired
	MovieService movieService;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	UniversalResponse response;
	
	@Autowired
	ResponseWrapper responseWrapper;
	
	
	@GetMapping("/movies")
	public ResponseEntity<ResponseWrapper> getAllMovies()
	{
		return movieService.getAllMovies();
	}  
	

	
	@GetMapping("/movies/{id}")
	public ResponseEntity<ResponseWrapper> getMovieById(@PathVariable Long id)
	{
		return movieService.getMovieById(id);
	}
	
	
	
//	@PostMapping("/movies")
//	public ResponseEntity<ResponseWrapper> addMovie(@RequestBody Movie movie)
//	{
//		return movieService.addMovie(movie);
//	}
	
	@PostMapping(
		    value = "/movies",
		    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
		)
	public ResponseEntity<ResponseWrapper> addMovie(
	        @RequestPart("movieObject") String movieObject,
	        @RequestPart("movieImage") MultipartFile movieImage
	) throws IOException {

	    ObjectMapper mapper = new ObjectMapper();

	    Movie movie = mapper.readValue(movieObject, Movie.class);
	    
	    // create uploads folder
	    File directory = new File(IMAGE_UPLOAD_DIR);

	    if (!directory.exists()) {
	        directory.mkdirs();
	    }

	    // image filename
	    String fileName = movieImage.getOriginalFilename();

	    // save image in folder
	    Path path = Paths.get(IMAGE_UPLOAD_DIR, fileName);

	    Files.copy(
	            movieImage.getInputStream(),
	            path,
	            StandardCopyOption.REPLACE_EXISTING
	    );

	    // save filename in db
	    movie.setMovieImgUrl(fileName);

	    System.out.println(movie);

	    movieRepository.save(movie);

//	    return ResponseEntity.ok(
//	            new ResponseWrapper("Movie Added", movie, HttpStatus.OK.value())
//	    );
	    
	    ResponseWrapper response = new ResponseWrapper();

	    response.setMessage("Movie Added");
	    response.setData(movie);
	    response.setStatus(HttpStatus.OK.value());

	    return ResponseEntity.ok(response);
	}
	
	
	
//	@PutMapping("/movies/{id}")
//	public ResponseEntity<ResponseWrapper> updateMovieById(@RequestBody Movie movie,@PathVariable long id)
//	{
//		return movieService.updateMovieById(movie, id);
//	}
	
	@PutMapping(
	        value="/movies/{movieId}",
	        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<ResponseWrapper> updateMovie(
	        @PathVariable Long movieId,
	        @RequestPart("movieObject") String movieObject,
	        @RequestPart(value="movieImage", required = false)
	        MultipartFile movieImage
	) throws IOException {

	    ObjectMapper mapper = new ObjectMapper();

	    Movie updatedMovie =
	            mapper.readValue(movieObject, Movie.class);

	    Movie existingMovie =
	            movieRepository.findById(movieId).get();

	    existingMovie.setTitle(updatedMovie.getTitle());
	    existingMovie.setGenre(updatedMovie.getGenre());
	    existingMovie.setDescription(updatedMovie.getDescription());
	    existingMovie.setDuration(updatedMovie.getDuration());
	    existingMovie.setLanguage(updatedMovie.getLanguage());
	    existingMovie.setRating(updatedMovie.getRating());
	    existingMovie.setReleaseDate(updatedMovie.getReleaseDate());

	    // image update
	    if (movieImage != null && !movieImage.isEmpty()) {

	        String fileName =
	                movieImage.getOriginalFilename();

	        Path path = Paths.get(
	                IMAGE_UPLOAD_DIR,
	                fileName
	        );

	        Files.copy(
	                movieImage.getInputStream(),
	                path,
	                StandardCopyOption.REPLACE_EXISTING
	        );

	        existingMovie.setMovieImgUrl(fileName);
	    }

	    movieRepository.save(existingMovie);

	    ResponseWrapper response = new ResponseWrapper();

	    response.setMessage("Movie Updated");
	    response.setData(existingMovie);
	    response.setStatus(HttpStatus.OK.value());

	    return ResponseEntity.ok(response);
	}
	
	
	
	@DeleteMapping("/movies/{id}")
	public ResponseEntity<ResponseWrapper> deleteMovieById(@PathVariable Long id)
	{
		return movieService.deleteMovieById(id);
	}
	

	

}
