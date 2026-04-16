package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Product;
import com.edutech.progressive.repository.ProductRepository;
import com.edutech.progressive.service.ProductService;

@Service
public class ProductServiceImplJpa implements ProductService {
    
    
    private ProductRepository productRepository;

    
    @Autowired
    public ProductServiceImplJpa(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() throws SQLException{
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int productId) throws SQLException {
        return productRepository.findById(productId).get();
    }
    
    @Override
    public int addProduct(Product product) throws SQLException {
        
        Product savedProduct = productRepository.save(product);
        return savedProduct.getProductId();
    }
    
    @Override
    public void updateProduct(Product product) throws SQLException {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(int productId) throws SQLException {
        productRepository.deleteById(productId);
    }

    public List<Product> getAllProductByWarehouse(int warehouseId) throws SQLException{
        return productRepository.findAllByWarehouse_WarehouseId(warehouseId);
    }



}