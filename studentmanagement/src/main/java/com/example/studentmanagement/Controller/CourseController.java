package com.example.studentmanagement.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.studentmanagement.Domain.Course;
import com.example.studentmanagement.Exception.ResourceNotFoundException;
import com.example.studentmanagement.Repository.CourseRepository;

@RestController
@RequestMapping("/api/course")
public class CourseController {
		 
	 	@Autowired
	 	private CourseRepository courseRepository;
	 
	 	//Get all courses
	 	@GetMapping("/courses")
	    public List<Course> getAllCourses(Model model) {
	    	return courseRepository.findAll();	       
	    }
	 	
	 	//Search course
	 	@GetMapping("/courses/{id}")
	    public ResponseEntity < Course > getCourseById(@PathVariable(value = "id") Long id)
	    throws ResourceNotFoundException {
	 		Course course = courseRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Course not found for this id :: " + id));
	        return ResponseEntity.ok().body(course);
	    }
	 	
	 	//Add new course
	 	@PostMapping("/courses")
	 	public Map < String, Boolean > createCourse(@Validated @RequestBody Course course) {
	        courseRepository.save(course);
	        Map < String, Boolean > response = new HashMap < > ();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }
	 	
	 	//Update course
	 	@PostMapping("/courses/{id}")
	 	public Map < String, Boolean > updateCourse(@PathVariable(value = "id") Long id,
	        @Validated @RequestBody Course courseDetails) throws ResourceNotFoundException {
	 		Course course = courseRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Course not found for this id :: " + id));

	 		course.setCoursename(courseDetails.getCoursename());
	 		course.setDuration(courseDetails.getDuration());
	        final Course updatedCourse = courseRepository.save(course);
	        //return ResponseEntity.ok(updatedCourse);
	        Map < String, Boolean > response = new HashMap < > ();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }
	 	
	 	//Delete course
	 	@DeleteMapping("/courses/{id}")
	    public Map < String, Boolean > deleteCourse(@PathVariable(value = "id") Long id)
	    throws ResourceNotFoundException {
	 		Course course = courseRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Course not found for this id :: " + id));

	 		courseRepository.delete(course);
	        Map < String, Boolean > response = new HashMap < > ();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }	
}