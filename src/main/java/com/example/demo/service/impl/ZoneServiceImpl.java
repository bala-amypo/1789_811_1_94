package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Zone;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.ZoneService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;

    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Zone createZone(Zone zone) {
        return zoneRepository.save(zone);
    }

    @Override
    public Zone getZoneById(Long id) throws ResourceNotFoundException {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));
    }

    @Override
    public Zone updateZone(Long id, Zone zone) throws ResourceNotFoundException {
        Zone existing = getZoneById(id);
        if (zone.getZoneName() != null) existing.setZoneName(zone.getZoneName());
        if (zone.getDescription() != null) existing.setDescription(zone.getDescription());
        return zoneRepository.save(existing);
    }

    @Override
    public void deactivateZone(Long id) throws ResourceNotFoundException {
        Zone zone = getZoneById(id);
        zone.setActive(false);
        zoneRepository.save(zone);
    }

    @Override
    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }
}
