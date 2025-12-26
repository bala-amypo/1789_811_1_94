package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.exception.*;
import com.example.demo.model.Zone;
import com.example.demo.repository.ZoneRepository;

@Service
public class ZoneServiceImpl {

    private final ZoneRepository repo;

    public ZoneServiceImpl(ZoneRepository repo) {
        this.repo = repo;
    }

    public Zone createZone(Zone zone) {
        if (zone.getActive() == null) zone.setActive(true);
        return repo.save(zone);
    }

    public Zone getZoneById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));
    }

    public Zone updateZone(Long id, Zone update) {
        Zone z = getZoneById(id);
        if (update.getDescription() != null) z.setDescription(update.getDescription());
        return repo.save(z);
    }

    public void deactivateZone(Long id) {
        Zone z = getZoneById(id);
        z.setActive(false);
        repo.save(z);
    }
}
