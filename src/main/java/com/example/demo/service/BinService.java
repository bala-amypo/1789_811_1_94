package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Bin;

@Service
public interface BinService {

    Bin createBin(Bin bin);

    Bin updateBin(Long id, Bin bin);

    Bin getBinById(Long id);

    List<Bin> getAllBins();

    void deactivateBin(Long id);
}
