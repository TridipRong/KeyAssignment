package com.key.assignment.TestService;
import com.key.assignment.Entity.Category;
import com.key.assignment.Entity.Product;
import com.key.assignment.Exception.ResourceNotFoundException;
import com.key.assignment.Repository.ProductRepository;
import com.key.assignment.Service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    private Category category = new Category(1, "Category 1");
    @Test
    public void testGetAllProducts() {
      
        List<Product> products = new ArrayList<>();
       
        products.add(new Product(1, "Product 1", "Description 1", new BigDecimal(10.00), 10,"",category));
        products.add(new Product(2, "Product 2", "Description 2", new BigDecimal(20.00), 20,"",category));
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        Assertions.assertEquals(products.size(), result.size());
        Assertions.assertEquals(products, result);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductById() throws ResourceNotFoundException {
       
        Product product = new Product(2, "Product 2", "Description 2", new BigDecimal(20.00), 20,"",category);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1);

        Assertions.assertEquals(product, result);
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    public void testGetProductById_ThrowsResourceNotFoundException() {
     
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(1);
        });
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    public void testCreateProduct() {
   
    	 Product product = new Product(2, "Product 2", "Description 2", new BigDecimal(20.00), 20,"",category);

        when(productRepository.save(any(Product.class))).thenReturn(product);

     
        Product result = productService.createProduct(product);

        Assertions.assertEquals(product, result);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testUpdateProduct() throws ResourceNotFoundException {
     
    	
    	 Product existingProduct = new Product(2, "Product 2", "Description 2", new BigDecimal(20.00), 20,"",category);
        Product updatedProduct = new Product(1, "Updated Product", "Updated Description", new BigDecimal(20.00), 20,"",category);

        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(1, updatedProduct);

        Assertions.assertEquals(updatedProduct, result);
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    public void testUpdateProduct_ThrowsResourceNotFoundException() {
  
        Product updatedProduct = new Product(1, "Updated Product", "Updated Description", new BigDecimal(20.00), 20,"",category);

        when(productRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(1, updatedProduct);
        });
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void testDeleteProduct() throws ResourceNotFoundException {
    	 Product product = new Product(1, "Updated Product", "Updated Description", new BigDecimal(20.00), 20,"",category);

        when(productRepository.existsById(1)).thenReturn(true);
        productService.deleteProduct(1);
        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteProduct_ThrowsResourceNotFoundException() {
       
        when(productRepository.existsById(1)).thenReturn(false);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct(1);
        });
        verify(productRepository, never()).deleteById(anyInt());
    }
}

