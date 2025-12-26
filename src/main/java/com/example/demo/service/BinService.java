package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Bin;


public interface BinService {

    Bin createBin(Bin bin);

    Bin updateBin(Long id, Bin bin);

    Bin getBinById(Long id);

    List<Bin> getAllBins();

    void deactivateBin(Long id);
}
