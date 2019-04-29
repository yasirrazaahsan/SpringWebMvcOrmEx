package com.app.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.model.Employee;
import com.app.service.IEmployeeService;
import com.app.validator.EmployeeValidator;

@Controller
public class EmployeeController {
	@Autowired
	private IEmployeeService service;
	@Autowired
	private EmployeeValidator validator;
	@Autowired
	private MessageSource message;
	
	/* #1 Show EmployeeRegister JSP, 
	 * when /reg is entered in browser
	 */
	@RequestMapping("/reg")
	public String showRegPage(ModelMap map) {
		System.out.println("This is showRegPage() method");
		map.addAttribute("employee", new Employee());
		return "EmployeeRegister";
	}
	
	/**
	 * 2. On click submit read ModelAttribute
	 * validate, if no errors save else
	 * return to same page
	 */
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	//***read modelAttribute, next param must be Errors
	public String saveEmp(@ModelAttribute("employee")Employee emp,Errors errors,ModelMap map,Locale locale ) {
		//check validation errors
		validator.validate(emp, errors);
		//if no errors
		if(!errors.hasErrors()) {
			//save data to DB
			int empId=service.saveEmployee(emp);
			//show success message
			String msg=message.getMessage("success", new Object[] {empId}, locale);
			map.addAttribute("message", msg);
			//clear form
			map.addAttribute("employee", new Employee());
		}else {//if errors exist
			String msg=message.getMessage("fail", null, locale);
			map.addAttribute("message", msg);
		}
		//finally goto UI page
		return "EmployeeRegister";
	}
	
	
	
	
	
	
	
	
	
}
