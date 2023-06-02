package com.key.assignment.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.key.assignment.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	Optional<Category> findByCategoryName(String categoryName);

}
