package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Bin;
import com.example.demo.model.UsagePatternModel;

@Repository
public interface UsagePatternModelRepository
        extends JpaRepository<UsagePatternModel, Long> {

    Optional<UsagePatternModel> findTop1ByBinOrderByLastUpdatedDesc(Bin bin);
}
