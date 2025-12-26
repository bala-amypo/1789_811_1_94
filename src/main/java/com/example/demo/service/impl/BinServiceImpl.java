package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.exception.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;

import java.util.List;

@Service
public class BinServiceImpl {

    private final BinRepository binRepo;
    private final ZoneRepository zoneRepo;

    public BinServiceImpl(BinRepository binRepo, ZoneRepository zoneRepo) {
        this.binRepo = binRepo;
        this.zoneRepo = zoneRepo;
    }

    public Bin createBin(Bin bin) {
        if (bin.getCapacityLiters() == null || bin.getCapacityLiters() <= 0)
            throw new BadRequestException("Invalid capacity");

        Zone zone = zoneRepo.findById(bin.getZone().getId())
                .orElseThrow(() -> new BadRequestException("Zone missing"));

        if (!zone.getActive())
            throw new BadRequestException("Zone inactive");

        bin.setZone(zone);
        if (bin.getActive() == null) bin.setActive(true);
        return binRepo.save(bin);
    }

    public Bin getBinById(Long id) {
        return binRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bin not found"));
    }

    public Bin updateBin(Long id, Bin update) {
        Bin b = getBinById(id);
        if (update.getLocationDescription() != null)
            b.setLocationDescription(update.getLocationDescription());
        return binRepo.save(b);
    }

    public void deactivateBin(Long id) {
        Bin b = getBinById(id);
        b.setActive(false);
        binRepo.save(b);
    }

    public List<Bin> getAllBins() {
        return binRepo.findAll();
    }
}
