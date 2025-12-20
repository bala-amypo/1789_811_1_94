package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Zone;

public interface ZoneRepository extends JpaRepository<Zone, Long> {

    Optional<Zone> findByZoneName(String zoneName);
}
