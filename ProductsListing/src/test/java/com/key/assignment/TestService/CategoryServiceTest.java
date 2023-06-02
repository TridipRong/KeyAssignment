package com.key.assignment.TestService;

import com.key.assignment.Entity.Category;
import com.key.assignment.Exception.CategoryAlreadyExistsException;
import com.key.assignment.Exception.ResourceNotFoundException;
import com.key.assignment.Repository.CategoryRepository;
import com.key.assignment.Service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCategories() {
        // Arrange
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1"));
        categories.add(new Category(2, "Category 2"));

        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<Category> result = categoryService.getAllCategories();

        // Assert
        Assertions.assertEquals(categories.size(), result.size());
        Assertions.assertEquals(categories, result);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testGetCategoryById() throws ResourceNotFoundException {
        // Arrange
        Category category = new Category(1, "Category 1");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        // Act
        Category result = categoryService.getCategoryById(1);

        // Assert
        Assertions.assertEquals(category, result);
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    public void testGetCategoryById_ThrowsResourceNotFoundException() {
        // Arrange
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.getCategoryById(1);
        });
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    public void testCreateCategory() throws CategoryAlreadyExistsException {
        // Arrange
        Category category = new Category(1, "Category 1");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // Act
        Category result = categoryService.createCategory(category);

        // Assert
        Assertions.assertEquals(category, result);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testUpdateCategory() throws ResourceNotFoundException {
        // Arrange
        Category existingCategory = new Category(1, "Category 1");
        Category updatedCategory = new Category(1, "Updated Category");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        // Act
        Category result = categoryService.updateCategory(1, updatedCategory);

        // Assert
        Assertions.assertEquals(updatedCategory, result);
        verify(categoryRepository, times(1)).findById(1);
        verify(categoryRepository, times(1)).save(updatedCategory);
    }

    @Test
    public void testUpdateCategory_ThrowsResourceNotFoundException() {
        // Arrange
        Category updatedCategory = new Category(1, "Updated Category");

        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.updateCategory(1, updatedCategory);
        });
        verify(categoryRepository, times(1)).findById(1);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    public void testDeleteCategory() throws ResourceNotFoundException {
        // Arrange
        Category category = new Category(1, "Category 1");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        // Act
        categoryService.deleteCategory(1);

        // Assert
        verify(categoryRepository, times(1)).delete(category);
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteCategory_ThrowsResourceNotFoundException() {
        // Arrange
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.deleteCategory(1);
        });
        verify(categoryRepository, times(1)).findById(1);
        verify(categoryRepository, never()).delete(any(Category.class));
    }
}
