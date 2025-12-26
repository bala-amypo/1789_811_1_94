package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Bin;
import com.example.demo.service.BinService;

@RestController
@RequestMapping("/api/bins")
public class BinController {

    private final BinService binService;

    public BinController(BinService binService) {
        this.binService = binService;
    }

    @PostMapping
    public Bin createBin(@RequestBody Bin bin) {
        return binService.createBin(bin);
    }

    @PutMapping("/{id}")
    public Bin updateBin(@PathVariable Long id, @RequestBody Bin bin) {
        return binService.updateBin(id, bin);
    }

    @GetMapping("/{id}")
    public Bin getBin(@PathVariable Long id) {
        return binService.getBinById(id);
    }

    @GetMapping
    public List<Bin> getAllBins() {
        return binService.getAllBins();
    }

    @PutMapping("/{id}/deactivate")
    public void deactivateBin(@PathVariable Long id) {
        binService.deactivateBin(id);
    }
}
