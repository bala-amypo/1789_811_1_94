package com.example.demo.controller;

import com.example.demo.model.Zone;
import com.example.demo.service.ZoneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    // POST /api/zones
    @PostMapping
    public Zone createZone(@RequestBody Zone zone) {
        return zoneService.createZone(zone);
    }

    // PUT /api/zones/{id}
    @PutMapping("/{id}")
    public Zone updateZone(@PathVariable Long id,
                           @RequestBody Zone zone) {
        return zoneService.updateZone(id, zone);
    }

    // GET /api/zones/{id}
    @GetMapping("/{id}")
    public Zone getZone(@PathVariable Long id) {
        return zoneService.getZoneById(id);
    }

    // GET /api/zones
    @GetMapping
    public List<Zone> getAllZones() {
        return zoneService.getAllZones();
    }

    // PUT /api/zones/{id}/deactivate
    @PutMapping("/{id}/deactivate")
    public void deactivateZone(@PathVariable Long id) {
        zoneService.deactivateZone(id);
    }
}
