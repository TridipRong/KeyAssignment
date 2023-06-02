package com.key.assignment.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.assignment.Entity.Category;
import com.key.assignment.Exception.CategoryAlreadyExistsException;
import com.key.assignment.Exception.ResourceNotFoundException;
import com.key.assignment.Repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public Category createCategory(Category category) throws CategoryAlreadyExistsException {
		String categoryName = category.getCategoryName();

		// Check if a category with the same name already exists
		if (categoryRepository.findByCategoryName(categoryName).isPresent()) {
			throw new CategoryAlreadyExistsException("Category with name " + categoryName + " already exists.");
		}

		return categoryRepository.save(category);
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public Category getCategoryById(int categoryId) throws ResourceNotFoundException {
		Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

		if (categoryOptional.isPresent()) {
			return categoryOptional.get();
		} else {
			throw new ResourceNotFoundException("Category not found with ID: " + categoryId);
		}
	}

	public Category updateCategory(int categoryId, Category updatedCategory) throws ResourceNotFoundException {
		Category category = getCategoryById(categoryId);

		category.setCategoryName(updatedCategory.getCategoryName());

		return categoryRepository.save(category);
	}

	public String deleteCategory(int categoryId) throws ResourceNotFoundException {
		if (!categoryRepository.existsById(categoryId)) {
			throw new ResourceNotFoundException("Category not found with ID: " + categoryId);
		}
		categoryRepository.deleteById(categoryId);
		return "Delete Successfull";
	}
}