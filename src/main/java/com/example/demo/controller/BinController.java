package com.example.demo.controller;

import com.example.demo.model.Bin;
import com.example.demo.service.BinService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bins")
public class BinController {

    private final BinService binService;

    public BinController(BinService binService) {
        this.binService = binService;
    }

    // POST /api/bins
    @PostMapping
    public Bin createBin(@RequestBody Bin bin) {
        return binService.createBin(bin);
    }

    // GET /api/bins/{id}
    @GetMapping("/{id}")
    public Bin getBin(@PathVariable Long id) {
        return binService.getBinById(id);
    }

    // PUT /api/bins/{id}
    @PutMapping("/{id}")
    public Bin updateBin(@PathVariable Long id,
                         @RequestBody Bin bin) {
        return binService.updateBin(id, bin);
    }

    // PUT /api/bins/{id}/deactivate
    @PutMapping("/{id}/deactivate")
    public void deactivateBin(@PathVariable Long id) {
        binService.deactivateBin(id);
    }

    // GET /api/bins
    @GetMapping
    public List<Bin> getAllBins() {
        return binService.getAllBins();
    }
}
