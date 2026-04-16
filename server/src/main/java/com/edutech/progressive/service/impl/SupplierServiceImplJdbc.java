package com.edutech.progressive.service.impl;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.edutech.progressive.dao.SupplierDAO;
import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.service.SupplierService;



public class SupplierServiceImplJdbc implements SupplierService 
{
     private SupplierDAO supplierDAO;

    public SupplierServiceImplJdbc(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        try {
            return supplierDAO.getAllSuppliers();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public int addSupplier(Supplier supplier){
        try {
            return supplierDAO.addSupplier(supplier);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    @Override
public List<Supplier> getAllSuppliersSortedByName() {
    try {
        List<Supplier> suppliers = supplierDAO.getAllSuppliers();
        Collections.sort(suppliers, Comparator.comparing(Supplier::getSupplierName));
        return suppliers;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return new ArrayList<>();
}

    @Override
    public void updateSupplier(Supplier supplier) {
        try {
            supplierDAO.updateSupplier(supplier);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSupplier(int supplierId) {
        try {
            supplierDAO.deleteSupplier(supplierId);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Supplier getSupplierById(int supplierId) {
        try {
            return supplierDAO.getSupplierById(supplierId);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    

}
