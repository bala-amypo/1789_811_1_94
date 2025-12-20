package com.example.demo.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Bin;
import com.example.demo.model.Zone;
import com.example.demo.repository.BinRepository;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.BinService;

@Service
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
            throw new BadRequestException("Bin capacity must be greater than 0");
        }

        binRepository.findByIdentifier(bin.getIdentifier())
                .ifPresent(b -> {
                    throw new BadRequestException("Bin identifier already exists");
                });

        Zone zone = zoneRepository.findById(bin.getZone().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zone not found"));

        Timestamp now = Timestamp.from(Instant.now());

        bin.setZone(zone);
        bin.setActive(true);
        bin.setCreatedAt(now);
        bin.setUpdatedAt(now);

        return binRepository.save(bin);
    }

    @Override
    public Bin updateBin(Long id, Bin bin) {
        Bin existing = getBinById(id);

        if (bin.getCapacityLiters() != null && bin.getCapacityLiters() <= 0) {
            throw new BadRequestException("capacity must be greater than 0");
        }

        existing.setLocationDescription(bin.getLocationDescription());
        existing.setLatitude(bin.getLatitude());
        existing.setLongitude(bin.getLongitude());
        existing.setCapacityLiters(bin.getCapacityLiters());
        existing.setUpdatedAt(Timestamp.from(Instant.now()));

        return binRepository.save(existing);
    }

    @Override
    public Bin getBinById(Long id) {
        return binRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bin not found"));
    }

    @Override
    public List<Bin> getAllBins() {
        return binRepository.findAll();
    }

    @Override
    public void deactivateBin(Long id) {
        Bin bin = getBinById(id);
        bin.setActive(false);
        bin.setUpdatedAt(Timestamp.from(Instant.now()));
        binRepository.save(bin);
    }
}
