package com.edutech.progressive.dao;

import com.edutech.progressive.entity.Supplier;

import java.util.List;

public interface SupplierDAO {
    int addSupplier(Supplier supplier);
    Supplier getSupplierById(int supplierId);
    void updateSupplier(Supplier supplier);
    void deleteSupplier(int supplierId) ;
    List<Supplier> getAllSuppliers();
}
