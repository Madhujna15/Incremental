package com.edutech.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edutech.progressive.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>{
    @Query("DELETE FROM Supplier s WHERE s.supplierId =:supplierId")
    void deleteBySupplierId(@Param("supplierId") int supplierId);

    @Query("SELECT s FROM Supplier s WHERE s.supplierId =:supplierId")
    Supplier findBySupplierId(@Param("supplierId") int supplierId);
}
