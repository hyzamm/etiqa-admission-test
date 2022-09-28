package com.example.studentmanagement.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagement.Domain.Student;
import com.example.studentmanagement.Domain.StudentDAO;
import com.example.studentmanagement.Exception.ResourceNotFoundException;
import com.example.studentmanagement.Repository.StudentRepository;

@RestController
@RequestMapping("/api/student")
public class StudentController {
		
		@Autowired
		private StudentRepository studentRepository;
		
		//Get students inner join with course
		@GetMapping("/students")
	    public List<StudentDAO> getAllStudents(Model model) {
			List<StudentDAO> li = new ArrayList<>();
			for (Object[] o : studentRepository.findStudent()) {
				StudentDAO student = new StudentDAO();
				student.setId(Long.parseLong(String.valueOf(o[0])));
				student.setStname((String) o[1]);
				student.setCoursename((String) o[2]);
				li.add(student);
			}       
			return li;
	    }
		
		//Get student by course ID
		@GetMapping("/studentsbycourseid/{id}")
	    public List<StudentDAO> getStudentByCourseId(@PathVariable(value = "id") Long id) {
			List<StudentDAO> li = new ArrayList<>();
			for (Object[] o : studentRepository.findStudentByCourseID(id)) {
				StudentDAO student = new StudentDAO();
				student.setId(Long.parseLong(String.valueOf(o[0])));
				student.setStname((String) o[1]);
				student.setCoursename((String) o[2]);
				li.add(student);
			}       
			return li;
	    }
		
		//Get all students
	 	/*@GetMapping("/students")
	    public List<Student> getAllStudents(Model model) {
	    	return studentRepository.findAll();	       
	    }*/
	 	
	 	//Search student
	 	@GetMapping("/students/{id}")
	    public ResponseEntity < Student > getStudentById(@PathVariable(value = "id") Long id)
	    throws ResourceNotFoundException {
	 		Student student = studentRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id));
	        return ResponseEntity.ok().body(student);
	    }
	 	
	 	//Add new student
	 	@PostMapping("/students")
	 	public Map < String, Boolean > createStudent(@Validated @RequestBody Student student) {
	        studentRepository.save(student);
	        Map < String, Boolean > response = new HashMap < > ();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }
	 	
	 	//Update student
	 	@PostMapping("/students/{id}")
	 	public Map < String, Boolean > updateStudent(@PathVariable(value = "id") Long id,
	        @Validated @RequestBody Student studentDetails) throws ResourceNotFoundException {
	 		Student student = studentRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id));

	 		student.setStname(studentDetails.getStname());
	 		student.setCourse(studentDetails.getCourse());
	 		student.setFee(studentDetails.getFee());
	        final Student updatedStudent = studentRepository.save(student);
	        //return ResponseEntity.ok(updatedStudent);
	        //return "updated";
	        Map < String, Boolean > response = new HashMap < > ();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }
	 	
	 	//Delete student
	 	@DeleteMapping("/students/{id}")
	 	public Map < String, Boolean > deleteStudent(@PathVariable(value = "id") Long id)
	    throws ResourceNotFoundException {
	 		Student student = studentRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id));

	 		studentRepository.delete(student);
	        Map < String, Boolean > response = new HashMap < > ();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }		
	 	
	 	//Search student by course ID
	 	/*@GetMapping("/students/bycourseid/{id}")
	    public List<StudentDAO> getStudentByCourseId(@PathVariable(value = "id") Long id)
	    {
	 		
	 		
	 		//List<StudentDAO> student = studentRepository.findStudentbyCourseID(id);
	        return studentRepository.findStudentbyCourseID(id);
	    }*/
}