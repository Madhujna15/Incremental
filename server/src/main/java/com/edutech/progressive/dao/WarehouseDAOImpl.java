package com.edutech.progressive.dao;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAOImpl implements WarehouseDAO {

    @Override
    public int addWarehouse(Warehouse warehouse) throws SQLException {
        String sql = "INSERT INTO warehouse (supplier_id, warehouse_name, location, capacity) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, warehouse.getSupplierId());
            ps.setString(2, warehouse.getWarehouseName());
            ps.setString(3, warehouse.getLocation());
            ps.setInt(4, warehouse.getCapacity());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }

        return -1;
    }

    @Override
    public Warehouse getWarehouseById(int warehouseId) throws SQLException {
        String sql = "SELECT * FROM warehouse WHERE warehouse_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, warehouseId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Warehouse warehouse = new Warehouse();
                    warehouse.setWarehouseId(rs.getInt("warehouse_id"));
                    warehouse.setSupplierId(rs.getInt("supplier_id"));
                    warehouse.setWarehouseName(rs.getString("warehouse_name"));
                    warehouse.setLocation(rs.getString("location"));
                    warehouse.setCapacity(rs.getInt("capacity"));
                    return warehouse;
                }
            }
        }

        return null;
    }

    @Override
    public void updateWarehouse(Warehouse warehouse) throws SQLException {
        String sql = "UPDATE warehouse SET supplier_id = ?, warehouse_name = ?, location = ?, capacity = ? WHERE warehouse_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, warehouse.getSupplierId());
            ps.setString(2, warehouse.getWarehouseName());
            ps.setString(3, warehouse.getLocation());
            ps.setInt(4, warehouse.getCapacity());
            ps.setInt(5, warehouse.getWarehouseId());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteWarehouse(int warehouseId) throws SQLException {
        String sql = "DELETE FROM warehouse WHERE warehouse_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, warehouseId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Warehouse> getAllWarehouse() throws SQLException {
        String sql = "SELECT * FROM warehouse";
        List<Warehouse> warehouses = new ArrayList<>();

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseId(rs.getInt("warehouse_id"));
                warehouse.setSupplierId(rs.getInt("supplier_id"));
                warehouse.setWarehouseName(rs.getString("warehouse_name"));
                warehouse.setLocation(rs.getString("location"));
                warehouse.setCapacity(rs.getInt("capacity"));

                warehouses.add(warehouse);
            }
        }

        return warehouses;
    }
}