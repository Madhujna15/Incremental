package com.edutech.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edutech.progressive.entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer>{
    Shipment findByShipmentId(int shipmentId);
    
    @Query("delete from Shipment s where s.warehouse.warehouseId = :warehouseId")
    void deleteByWarehouse(@Param("warehouseId") int warehouseId);
    
    @Query("delete from Shipment s where s.product.productId = :productId")
    void deleteByProduct(@Param("productId") int productId);
    
    @Query("delete from Shipment s where s.warehouse.supplier.supplierId = :supplierId")
    void deleteBySupplierId(@Param("supplierId") int supplierId);
}