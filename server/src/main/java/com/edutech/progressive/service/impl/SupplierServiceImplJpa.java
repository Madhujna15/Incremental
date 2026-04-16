package com.edutech.progressive.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.repository.SupplierRepository;
import com.edutech.progressive.service.SupplierService;

@Service
public class SupplierServiceImplJpa  implements SupplierService{

    
    private SupplierRepository supplierRepository;

    
    @Autowired
    public SupplierServiceImplJpa(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers(){
        return supplierRepository.findAll();
    }

    public int addSupplier(Supplier supplier){
        Supplier savedSupplier = supplierRepository.save(supplier);
        return savedSupplier.getSupplierId();
    }

    public List<Supplier> getAllSuppliersSortedByName(){
        List<Supplier> result = supplierRepository.findAll();
        Collections.sort(result);
        return result;
    }

    public void updateSupplier(int supplierId, Supplier supplier){
        Supplier updatedSupplier = supplierRepository.findBySupplierId(supplierId);
        if(updatedSupplier != null){
            
            updatedSupplier.setSupplierName(supplier.getSupplierName());
            updatedSupplier.setEmail(supplier.getEmail());
            updatedSupplier.setPhone(supplier.getPhone());
            updatedSupplier.setAddress(supplier.getAddress());
            updatedSupplier.setUsername(supplier.getUsername());
            updatedSupplier.setPassword(supplier.getPassword());
            updatedSupplier.setRole(supplier.getRole());
            supplierRepository.save(supplier);
        }
        
    }

    public void deleteSupplier(int supplierId){
        supplierRepository.deleteById(supplierId);
    }

    public Supplier getSupplierById(int supplierId){
        return supplierRepository.findBySupplierId(supplierId);
    }



}