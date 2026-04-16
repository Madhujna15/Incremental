package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.service.impl.SupplierServiceImplArraylist;
import com.edutech.progressive.service.impl.SupplierServiceImplJpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    
    @Autowired
    private SupplierServiceImplArraylist supplierServiceImplArraylist;

    @Autowired
    private SupplierServiceImplJpa supplierServiceImplJpa;
    
    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        return ResponseEntity.ok(supplierServiceImplJpa.getAllSuppliers());
    }
    
    @GetMapping("/{supplierId}")
    public ResponseEntity<Supplier> getSupplierById(int supplierId) {
        return ResponseEntity.ok(supplierServiceImplJpa.getSupplierById(supplierId));
    } 

    @PostMapping
    public ResponseEntity<Integer> addSupplier(@RequestBody Supplier supplier) {
        return ResponseEntity.status(201).body(supplierServiceImplJpa.addSupplier(supplier));
    }
    
    @PutMapping("/{supplierId}")
    public ResponseEntity<Void> updateSupplier(@PathVariable int supplierId, @RequestBody Supplier supplier) {
        supplierServiceImplJpa.updateSupplier(supplierId,supplier);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{supplierId}")
    public ResponseEntity<Void> deleteSupplier(int supplierId) {
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/fromArrayList")
    public ResponseEntity<List<Supplier>> getAllSuppliersFromArrayList() {
        return ResponseEntity.ok(supplierServiceImplArraylist.getAllSuppliers());
    }
    
    @PostMapping("/toArrayList")
    public ResponseEntity<Integer> addSupplierToArrayList(Supplier supplier) {
        return ResponseEntity.status(201).body(supplierServiceImplArraylist.addSupplier(supplier));
    }
    
    @GetMapping("/fromArrayList/all")
    public ResponseEntity<List<Supplier>> getAllSuppliersSortedByNameFromArrayList() {
        return ResponseEntity.ok(supplierServiceImplArraylist.getAllSuppliersSortedByName());
    }
}