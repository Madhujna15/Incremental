package com.edutech.progressive.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edutech.progressive.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>{
    @Modifying
    @Transactional
    @Query("delete from Supplier s where s.supplierId = :supplierId")
    void deleteBySupplierId(@Param("supplierId") int supplierId);
    
    @Query("select s from Supplier s where s.supplierId = :supplierId")
    Supplier findBySupplierId(@Param("supplierId") int supplierId);
    
    @Query("select s from Supplier s where s.supplierName = :supplierName")
    Supplier findByUsername(@Param("supplierName") String username);
     
    @Query("select s from Supplier s where s.email = :email")
    Supplier findByEmail(@Param("email") String email);
}