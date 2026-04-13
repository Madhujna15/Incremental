package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.service.impl.SupplierServiceImplArraylist;
import com.edutech.progressive.service.impl.SupplierServiceImplJpa;

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
@RequestMapping("/supplier")
public class SupplierController {
    
    @Autowired
    private SupplierServiceImplJpa supplierServiceImplJpa;
    
    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() throws SQLException{
        return ResponseEntity.ok(supplierServiceImplJpa.getAllSuppliers());
    }
    
    @GetMapping("/{supplierId}")
    public ResponseEntity<Supplier> getSupplierById(int supplierId) throws SQLException{
        Supplier s = supplierServiceImplJpa.getSupplierById(supplierId);
        if(s != null){
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } 

    @PostMapping
    public ResponseEntity<Integer> addSupplier(@RequestBody Supplier supplier)throws SQLException {
        return ResponseEntity.status(201).body(supplierServiceImplJpa.addSupplier(supplier));
    }
    
    @PutMapping("/{supplierId}")
    public ResponseEntity<Void> updateSupplier(@PathVariable int supplierId,@RequestBody Supplier supplier) throws SQLException{
        supplierServiceImplJpa.updateSupplier(supplier);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping("/{supplierId}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable int supplierId) throws SQLException{
        supplierServiceImplJpa.deleteSupplier(supplierId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/fromArrayList")
    public ResponseEntity<List<Supplier>> getAllSuppliersFromArrayList()throws SQLException {
        return ResponseEntity.ok(supplierServiceImplJpa.getAllSuppliers());
    }
    
    @PostMapping("/toArrayList")
    public ResponseEntity<Integer> addSupplierToArrayList(Supplier supplier)throws SQLException {
        return ResponseEntity.status(201).body(supplierServiceImplJpa.addSupplier(supplier));
    }
    
    @GetMapping("/fromArrayList/all")
    public ResponseEntity<List<Supplier>> getAllSuppliersSortedByNameFromArrayList() throws SQLException{
        return ResponseEntity.ok(supplierServiceImplJpa.getAllSuppliersSortedByName());
    }
}