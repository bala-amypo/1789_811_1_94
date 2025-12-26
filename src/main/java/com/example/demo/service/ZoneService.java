package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Zone;

import java.util.List;

public interface ZoneService {

    Zone createZone(Zone zone);

    Zone getZoneById(Long id) throws ResourceNotFoundException;

    Zone updateZone(Long id, Zone zone) throws ResourceNotFoundException;

    void deactivateZone(Long id) throws ResourceNotFoundException;

    List<Zone> getAllZones();
}
