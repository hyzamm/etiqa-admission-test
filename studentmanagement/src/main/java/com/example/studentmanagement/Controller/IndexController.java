package com.example.studentmanagement.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index()
	{
		return "index";
	}
	
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public String viewStudentPage()
	{		
        return "student";
	}	
	
	@RequestMapping(value = "/course", method = RequestMethod.GET)
	public String viewCoursePage()
	{		
        return "course";
	}	
}
