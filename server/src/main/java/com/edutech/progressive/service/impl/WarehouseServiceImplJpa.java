package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Warehouse;
import com.edutech.progressive.repository.WarehouseRepository;
import com.edutech.progressive.service.WarehouseService;

@Service
public class WarehouseServiceImplJpa implements WarehouseService{
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public List<Warehouse> getAllWarehouses() throws SQLException {
        return warehouseRepository.findAll();
    }

    @Override
    public int addWarehouse(Warehouse warehouse) throws SQLException {
        Warehouse newWarehouse = warehouseRepository.save(warehouse);
        return newWarehouse.getWarehouseId();
    }

    @Override
    public List<Warehouse> getWarehousesSortedByCapacity() throws SQLException {
        return new ArrayList<>();
    }

    public void updateWarehouse(Warehouse warehouse)throws SQLException {
        warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(int warehouseId) throws SQLException{
        warehouseRepository.deleteById(warehouseId);
    }

    public Warehouse getWarehouseById(int warehouseId) throws SQLException{
        return warehouseRepository.findById(warehouseId).get();
    }

    public List<Warehouse> getWarehouseBySupplier(int supplierId)throws SQLException {
        return null;
    }

}