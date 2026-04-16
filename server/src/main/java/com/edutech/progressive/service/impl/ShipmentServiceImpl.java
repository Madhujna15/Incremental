package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Shipment;
import com.edutech.progressive.repository.ShipmentRepository;
import com.edutech.progressive.service.ShipmentService;
@Service
public class ShipmentServiceImpl implements ShipmentService {
    
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public List<Shipment> getAllShipments() throws SQLException {
       return shipmentRepository.findAll();
    }

    @Override
    public Shipment getShipmentById(int shipmentId) throws SQLException {
        return shipmentRepository.findByShipmentId(shipmentId);
    }

    @Override
    public int addShipment(Shipment shipment) throws SQLException {
        Shipment savedShipment = shipmentRepository.save(shipment);
        return savedShipment.getShipmentId();
    }

    @Override
    public void updateShipment(Shipment shipment) throws SQLException {
        shipmentRepository.save(shipment);
    }

    @Override
    public void deleteShipment(int shipmentId) throws SQLException {
        shipmentRepository.deleteById(shipmentId);
    }

}