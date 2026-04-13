package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.edutech.progressive.entity.Product;
import com.edutech.progressive.repository.ProductRepository;
import com.edutech.progressive.service.ProductService;

public class ProductServiceImplJpa implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() throws SQLException {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int productId) throws SQLException {
        return productRepository.findById(productId).get();
    }

    @Override
    public int addProduct(Product product) throws SQLException {
        Product p = productRepository.save(product);
        return p.getProductId();
    }

    @Override
    public void updateProduct(Product product) throws SQLException {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(int productId) throws SQLException {
        productRepository.deleteById(productId);
    }

    

}