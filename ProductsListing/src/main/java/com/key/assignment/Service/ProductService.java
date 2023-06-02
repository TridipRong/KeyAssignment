package com.key.assignment.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.assignment.Entity.Product;
import com.key.assignment.Exception.ResourceNotFoundException;
import com.key.assignment.Repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private  ProductRepository productRepository;
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(int productId) throws ResourceNotFoundException {
		Optional<Product> productOptional = productRepository.findById(productId);

		if (productOptional.isPresent()) {
			return productOptional.get();
		} else {
			throw new ResourceNotFoundException("Product not found with ID: " + productId);
		}
	}

	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	public Product updateProduct(int productId, Product updatedProduct) throws ResourceNotFoundException {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

		product.setProductName(updatedProduct.getProductName());
		product.setProductDescription(updatedProduct.getProductDescription());
		product.setPrice(updatedProduct.getPrice());
		product.setQuantity(updatedProduct.getQuantity());
		product.setCategory(updatedProduct.getCategory());

		return productRepository.save(product);
	}

	public String deleteProduct(int productId) throws ResourceNotFoundException {
		if (!productRepository.existsById(productId)) {
			throw new ResourceNotFoundException("Product not found with ID: " + productId);
		}
		productRepository.deleteById(productId);
		return "Delete Successfull";
	}
}
