package com.edutech.progressive.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.service.SupplierService;

@Service
public class SupplierServiceImplArraylist  implements SupplierService{

    List<Supplier> suppliers = new ArrayList<>();

    @Override
    public List<Supplier> getAllSuppliers() {
        return suppliers;
    }

    @Override
    public int addSupplier(Supplier supplier) {
        suppliers.add(supplier);
        return suppliers.size();
       
    }

    @Override
    public List<Supplier> getAllSuppliersSortedByName() {
        Collections.sort(suppliers);
        return getAllSuppliers();
    }

    public void emptyArrayList(){
        suppliers.clear();
    }

}