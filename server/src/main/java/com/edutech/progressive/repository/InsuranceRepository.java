package com.edutech.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.progressive.entity.Insurance;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer>{
    Insurance findByInsuranceId(int insuranceId);
    
    void deleteByShipment(int shipment);
}