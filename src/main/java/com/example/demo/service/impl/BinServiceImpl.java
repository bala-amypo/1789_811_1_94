package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Bin;
import com.example.demo.model.Zone;
import com.example.demo.repository.BinRepository;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.BinService;

import java.util.List;

public class BinServiceImpl implements BinService {

    private final BinRepository binRepository;
    private final ZoneRepository zoneRepository;

    public BinServiceImpl(BinRepository binRepository,
                          ZoneRepository zoneRepository) {
        this.binRepository = binRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Bin createBin(Bin bin) {

        if (bin.getCapacityLiters() == null || bin.getCapacityLiters() <= 0) {
            throw new BadRequestException("Capacity must be positive");
        }

        Zone zone = zoneRepository.findById(bin.getZone().getId())
                .orElseThrow(() -> new BadRequestException("Zone not found"));

        if (!Boolean.TRUE.equals(zone.getActive())) {
            throw new BadRequestException("Zone is inactive");
        }

        if (bin.getActive() == null) {
            bin.setActive(true); // TEST EXPECTS DEFAULT TRUE
        }

        bin.setZone(zone);
        return binRepository.save(bin);
    }

    @Override
    public Bin updateBin(Long id, Bin update) {

        Bin existing = binRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bin not found"));

        if (update.getLocationDescription() != null) {
            existing.setLocationDescription(update.getLocationDescription());
        }

        if (update.getLatitude() != null) {
            existing.setLatitude(update.getLatitude());
        }

        if (update.getLongitude() != null) {
            existing.setLongitude(update.getLongitude());
        }

        return binRepository.save(existing);
    }

    @Override
    public Bin getBinById(Long id) {
        return binRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bin not found"));
    }

    @Override
    public List<Bin> getAllBins() {
        return binRepository.findAll();
    }

    @Override
    public void deactivateBin(Long id) {
        Bin bin = binRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bin not found"));
        bin.setActive(false);
        binRepository.save(bin);
    }
}
