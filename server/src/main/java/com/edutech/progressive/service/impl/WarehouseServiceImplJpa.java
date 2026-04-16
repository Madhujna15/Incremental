package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Warehouse;
import com.edutech.progressive.repository.WarehouseRepository;
import com.edutech.progressive.service.WarehouseService;

@Service
public class WarehouseServiceImplJpa implements WarehouseService {


    
    private WarehouseRepository warehouseRepository;
    

    
    
    @Autowired
    public WarehouseServiceImplJpa(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @Override
    public int addWarehouse(Warehouse warehouse) {
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        return savedWarehouse.getWarehouseId();
    }

    @Override
    public List<Warehouse> getWarehousesSortedByCapacity(){
        List<Warehouse> warehouses = warehouseRepository.findAll();
        Collections.sort(warehouses);
        return warehouses;
    }

    public void updateWarehouse(Warehouse warehouse){
        warehouseRepository.save(warehouse);

    }

    public void deleteWarehouse(int warehouseId){
        Warehouse warehouse = warehouseRepository.findByWarehouseId(warehouseId);
        warehouseRepository.delete(warehouse);

    }

    public Warehouse getWarehouseById(int warehouseId){
        return warehouseRepository.findByWarehouseId(warehouseId);

    }

    public List<Warehouse> getWarehouseBySupplier(int supplierId){
        return warehouseRepository.findAllBySupplier_SupplierId(supplierId);
    }

}