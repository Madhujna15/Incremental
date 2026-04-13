package com.edutech.progressive.dao;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public int addSupplier(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier (supplier_name, email, username, password, phone, address, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int gensuppid = -1;
        try (Connection connection = DatabaseConnectionManager.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, supplier.getSupplierName());
            ps.setString(2, supplier.getEmail());
            ps.setString(3, supplier.getUsername());
            ps.setString(4, supplier.getPassword());
            ps.setString(5, supplier.getPhone());
            ps.setString(6, supplier.getAddress());
            ps.setString(7, supplier.getRole());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    gensuppid = rs.getInt(1);
                    supplier.setSupplierId(gensuppid);
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return gensuppid;
    }

    @Override
    public Supplier getSupplierById(int supplierId) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE supplier_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, supplierId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Supplier supplier = new Supplier();
                    supplier.setSupplierId(rs.getInt("supplier_id"));
                    supplier.setSupplierName(rs.getString("supplier_name"));
                    supplier.setEmail(rs.getString("email"));
                    supplier.setUsername(rs.getString("username"));
                    supplier.setPassword(rs.getString("password"));
                    supplier.setPhone(rs.getString("phone"));
                    supplier.setAddress(rs.getString("address"));
                    supplier.setRole(rs.getString("role"));
                    return supplier;
                }
            }
        }

        return null;
    }

    @Override
    public void updateSupplier(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET supplier_name = ?, email = ?, username = ?, password = ?, phone = ?, address = ?, role = ? WHERE supplier_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, supplier.getSupplierName());
            ps.setString(2, supplier.getEmail());
            ps.setString(3, supplier.getUsername());
            ps.setString(4, supplier.getPassword());
            ps.setString(5, supplier.getPhone());
            ps.setString(6, supplier.getAddress());
            ps.setString(7, supplier.getRole());
            ps.setInt(8, supplier.getSupplierId());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteSupplier(int supplierId) throws SQLException {
        String sql = "DELETE FROM supplier WHERE supplier_id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, supplierId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Supplier> getAllSuppliers() throws SQLException {
        String sql = "SELECT * FROM supplier";
        List<Supplier> suppliers = new ArrayList<>();

        try (Connection connection = DatabaseConnectionManager.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(rs.getInt("supplier_id"));
                supplier.setSupplierName(rs.getString("supplier_name"));
                supplier.setEmail(rs.getString("email"));
                supplier.setUsername(rs.getString("username"));
                supplier.setPassword(rs.getString("password"));
                supplier.setPhone(rs.getString("phone"));
                supplier.setAddress(rs.getString("address"));
                supplier.setRole(rs.getString("role"));

                suppliers.add(supplier);
            }
        }

        return suppliers;
    }
}