package com.edutech.progressive.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edutech.progressive.entity.Warehouse;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long>{
    
    Warehouse findByWarehouseId(int warehouseId);
    
    @Query("select w from Warehouse w where w.supplier.supplierId = :supplierId")
    List<Warehouse> findAllBySupplier_SupplierId(@Param("supplierId")int supplierId);

    @Modifying
    @Transactional
    @Query("delete from Warehouse w where w.supplier.supplierId = :supplierId")
    void deleteBySupplierId(@Param("supplierId")int supplierId);
}
