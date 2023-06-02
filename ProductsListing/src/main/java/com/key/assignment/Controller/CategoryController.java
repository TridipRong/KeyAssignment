package com.key.assignment.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.key.assignment.Entity.Category;
import com.key.assignment.Exception.CategoryAlreadyExistsException;
import com.key.assignment.Exception.ResourceNotFoundException;
import com.key.assignment.Service.CategoryService;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/createCategory")
	public ResponseEntity<Category> createCategory(@RequestBody Category category) throws CategoryAlreadyExistsException {
		Category createdCategory = categoryService.createCategory(category);
		return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
	}

	@PutMapping("/updateCategory/{categoryId}")
	public ResponseEntity<Category> updateCategory(@PathVariable int categoryId, @RequestBody Category updatedCategory)
			throws ResourceNotFoundException {
		Category category = categoryService.updateCategory(categoryId, updatedCategory);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@GetMapping("/categoryList")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<Category> getCategoryById(@PathVariable int categoryId) throws ResourceNotFoundException {
		Category category = categoryService.getCategoryById(categoryId);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable int categoryId) throws ResourceNotFoundException {
		String msg =categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
}
