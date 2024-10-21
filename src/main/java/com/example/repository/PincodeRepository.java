package com.example.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Pincode;

@Repository
public interface PincodeRepository extends JpaRepository<Pincode, Long> {
    Pincode findByPincode(String pincode);
}
