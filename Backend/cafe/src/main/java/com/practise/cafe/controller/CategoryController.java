package com.practise.cafe.controller;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practise.cafe.model.entity.Category;
import com.practise.cafe.repo.CategoryRepo;
import com.practise.cafe.security.JwtFilter;
import com.practise.cafe.service.CategoryService;
import com.practise.cafe.service.UserService;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired 
	JwtFilter jwtFilter;

	@Autowired 
	UserService userService;
	
	@PostMapping
	public ResponseEntity<String> addCategory(@RequestBody Map<String,String>requestMap) {
		return categoryService.addCategory(requestMap);
	}
	
	@GetMapping
	public ResponseEntity<List<Category>> getCategory(@RequestParam(required = false,name = "onlyAvailable")Boolean onlyAvailable){
	return categoryService.getCategory(onlyAvailable);
	}
	
	@PutMapping
	public ResponseEntity<String> updateCategory(@RequestBody Map<String,String>requestMap){
		return categoryService.updateCategory(requestMap);
	}
	@DeleteMapping
	public ResponseEntity<String> deleteCategory(@RequestParam Long id){
		categoryRepo.deleteById(id);
		return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
	}	

}