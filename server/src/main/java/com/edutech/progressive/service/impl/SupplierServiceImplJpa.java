package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.repository.SupplierRepository;
import com.edutech.progressive.service.SupplierService;

@Service
public class SupplierServiceImplJpa implements SupplierService{
    private SupplierRepository supplierRepository;
    

    public SupplierServiceImplJpa(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> getAllSuppliers() throws SQLException {
        return supplierRepository.findAll();
    }

    @Override
    public int addSupplier(Supplier supplier) throws SQLException {
        Supplier s = supplierRepository.save(supplier);
        return s.getSupplierId();
    }

    @Override
    public List<Supplier> getAllSuppliersSortedByName() throws SQLException {
        List<Supplier> existing = supplierRepository.findAll();
        Collections.sort(existing);
        return existing;
    }

    public void updateSupplier(Supplier supplier) throws SQLException{
        supplierRepository.save(supplier);
    }

    public void deleteSupplier(int supplierId)throws SQLException {
        supplierRepository.deleteById(supplierId);
    }

    public Supplier getSupplierById(int supplierId) throws SQLException{
        return supplierRepository.findById(supplierId).get();
    }

}