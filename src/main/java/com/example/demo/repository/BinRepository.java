package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Bin;
import com.example.demo.model.Zone;

@Repository
public interface BinRepository extends JpaRepository<Bin, Long> {

    Optional<Bin> findByIdentifier(String identifier);

    List<Bin> findByZoneAndActiveTrue(Zone zone);
}
