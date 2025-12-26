package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Bin;

import java.util.List;

public interface BinService {

    Bin createBin(Bin bin) throws BadRequestException;

    Bin getBinById(Long id) throws ResourceNotFoundException;

    List<Bin> getAllBins();

    Bin updateBin(Long id, Bin bin) throws ResourceNotFoundException;

    void deactivateBin(Long id) throws ResourceNotFoundException;
}
