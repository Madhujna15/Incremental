package com.edutech.progressive.service;

import com.edutech.progressive.entity.Shipment;

import java.util.List;

public interface ShipmentService {
    List<Shipment> getAllShipments();

    Shipment getShipmentById(int shipmentId);

    int addShipment(Shipment shipment);

    void updateShipment(Shipment shipment);

    void deleteShipment(int shipmentId);

}
