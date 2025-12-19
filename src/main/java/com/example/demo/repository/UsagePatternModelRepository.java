package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Bin;
import com.example.demo.model.UsagePatternModel;
@Repository
public interface UsagePatternModelRepository extends JpaRepository<UsagePatternModel, Long> {
    UsagePatternModel findTop1ByBinOrderByLastUpdatedDesc(Bin bin);
}
