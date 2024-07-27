package com.csvdatanatwest.natwestcsvdata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csvdatanatwest.natwestcsvdata.models.Criteria;

public interface CriteriaRepository extends JpaRepository<Criteria, Long> {
}

