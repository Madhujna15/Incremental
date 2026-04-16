package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Shipment;
import com.edutech.progressive.service.ShipmentService;

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
@RequestMapping("/shipment")
public class ShipmentController {
    
    @Autowired
    private ShipmentService shipmentService;

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        try {
            return ResponseEntity.ok(shipmentService.getAllShipments());
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @GetMapping("/{shipmentId}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable int shipmentId) {
        try {
            Shipment shipment = shipmentService.getShipmentById(shipmentId);
            if(shipment == null){
                return ResponseEntity.status(404).build();
            }
            return ResponseEntity.ok(shipment);
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
            
        }
    }
    
    @PostMapping
    public ResponseEntity<Integer> addShipment(@RequestBody Shipment shipment) {
        try {
            return ResponseEntity.status(201).body(shipmentService.addShipment(shipment));
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @PutMapping("/{shipmentId}")
    public ResponseEntity<Void> updateShipment(@PathVariable int shipmentId, @RequestBody Shipment shipment) {
        try {
            shipmentService.updateShipment(shipment);
            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(500).build();
        }
        
    }
    
    @DeleteMapping("/{shipmentId}")
    public ResponseEntity<Void> deleteShipment(@PathVariable int shipmentId) {
        try {
            shipmentService.deleteShipment(shipmentId);
            return ResponseEntity.status(204).build();
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
        
    }
}