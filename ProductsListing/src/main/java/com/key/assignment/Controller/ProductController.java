package com.key.assignment.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.key.assignment.Entity.Product;
import com.key.assignment.Exception.ResourceNotFoundException;
import com.key.assignment.Service.ProductService;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @PostMapping("/createProducts")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable int productId,
            @RequestBody Product updatedProduct
    ) throws ResourceNotFoundException {
        Product product = productService.updateProduct(productId, updatedProduct);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/productsList")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) throws ResourceNotFoundException {
        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId) throws ResourceNotFoundException {
        String msg=productService.deleteProduct(productId);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
