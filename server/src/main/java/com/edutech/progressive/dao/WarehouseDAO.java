package com.edutech.progressive.dao;

import com.edutech.progressive.entity.Warehouse;

import java.util.List;

public interface WarehouseDAO {
    int addWarehouse(Warehouse warehouse);
    Warehouse getWarehouseById(int warehouseId);
    void updateWarehouse (Warehouse warehouse);
    void deleteWarehouse (int warehouseId);
    List<Warehouse> getAllWarehouse();
}