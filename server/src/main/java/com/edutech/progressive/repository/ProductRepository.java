package com.edutech.progressive.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edutech.progressive.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    @Query("select p from Product p where p.productId = :productId")
    Product findByProductId(@Param("productId") int productId);

    @Query("select p from Product p where p.warehouse.warehouseId = :warehouseId")
    List<Product> findAllByWarehouse_WarehouseId(@Param("warehouseId") int warehouseId);

    @Modifying
    @Transactional
    @Query("delete from Product p where p.warehouse.warehouseId = :warehouseId")
    void deleteByWarehouseId(@Param("warehouseId") int warehouseId);

    @Modifying
    @Transactional
    @Query("delete from Product p where p.warehouse.supplier.supplierId = :supplierId")
    void deleteBySupplierId(@Param("supplierId") int supplierId);
}