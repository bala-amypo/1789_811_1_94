package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Zone;

public interface ZoneService {

    Zone createZone(Zone zone);

    Zone updateZone(Long id, Zone zone);

    Zone getZoneById(Long id);

    List<Zone> getAllZones();

    void deactivateZone(Long id);
}
