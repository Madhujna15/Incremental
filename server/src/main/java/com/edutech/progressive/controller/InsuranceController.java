package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Insurance;
import com.edutech.progressive.service.InsuranceService;

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

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/insurance")
public class InsuranceController {

    @Autowired
    private InsuranceService insuranceService;

    @GetMapping
    public ResponseEntity<List<Insurance>> getAllInsurances() {
        try {
            return ResponseEntity.ok(insuranceService.getAllInsurances());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(500).build();
        }
        
    }
    
    @GetMapping("/{insuranceId}")
    public ResponseEntity<Insurance> getInsuranceById(@PathVariable int insuranceId) {
        try {
            Insurance insurance = insuranceService.getInsuranceById(insuranceId);
            if(insurance == null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(insurance);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(500).build();
        }
        
        
    }
    
    @PostMapping
    public ResponseEntity<Integer> createInsurance(@RequestBody Insurance insurance) {
        try {
            return ResponseEntity.status(201).body(insuranceService.addInsurance(insurance));
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @PutMapping("/{insuranceId}")
    public ResponseEntity<Void> updateInsurance(@PathVariable int insuranceId, @RequestBody Insurance insurance) {
        try {
            insuranceService.updateInsurance(insurance);
            return ResponseEntity.status(204).build();
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
        
        
    }
    
    @DeleteMapping("/{insuranceId}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable int insuranceId) {
        try {
            insuranceService.deleteInsurance(insuranceId);
            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }
}