package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.edutech.progressive.dao.WarehouseDAO;
import com.edutech.progressive.entity.Warehouse;
import com.edutech.progressive.service.WarehouseService;

public class WarehouseServiceImplJdbc implements WarehouseService  {
    private WarehouseDAO warehouseDAO;

    public WarehouseServiceImplJdbc(WarehouseDAO warehouseDAO) {
        this.warehouseDAO = warehouseDAO;
    }

    @Override
    public List<Warehouse> getAllWarehouses() {
        try {
            return warehouseDAO.getAllWarehouse();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addWarehouse(Warehouse warehouse) {
        try {
            return warehouseDAO.addWarehouse(warehouse);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Warehouse> getWarehousesSortedByCapacity() {
        List<Warehouse> sortWarehouses;
        try {
            sortWarehouses = warehouseDAO.getAllWarehouse();
            Collections.sort(sortWarehouses, Comparator.comparing(Warehouse::getCapacity));
        return sortWarehouses;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateWarehouse(Warehouse warehouse) {
        try {
            warehouseDAO.updateWarehouse(warehouse);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWarehouse(int warehouseId) {
        try {
            warehouseDAO.deleteWarehouse(warehouseId);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Warehouse getWarehouseById(int warehouseId) {
        try {
            return warehouseDAO.getWarehouseById(warehouseId);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Warehouse> getWarehouseBySupplier(int supplierId) {
        return null;
    }

}