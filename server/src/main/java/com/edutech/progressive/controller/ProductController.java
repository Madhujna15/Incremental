package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Product;
import com.edutech.progressive.service.impl.ProductServiceImplJpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductServiceImplJpa productServiceImplJpa;
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            return ResponseEntity.ok(productServiceImplJpa.getAllProducts());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
     

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) {
        try {
            return ResponseEntity.ok(productServiceImplJpa.getProductById(productId));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Integer> addProduct(@RequestBody Product product) {
         try {
            return ResponseEntity.ok(productServiceImplJpa.addProduct(product));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable int productId, @RequestBody Product product) {
         try {
            productServiceImplJpa.updateProduct(product);
            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } 
    } 
    
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int productId) {
        try {
            productServiceImplJpa.deleteProduct(productId);
            return ResponseEntity.status(204).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<Product>> getAllProductByWarehouse(@PathVariable int warehouseId) {
        try {
            return ResponseEntity.ok(productServiceImplJpa.getAllProductByWarehouse(warehouseId));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}