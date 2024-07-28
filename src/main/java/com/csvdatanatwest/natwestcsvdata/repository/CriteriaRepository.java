package com.csvdatanatwest.natwestcsvdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csvdatanatwest.natwestcsvdata.model.Criteria;

public interface CriteriaRepository extends JpaRepository<Criteria, Long> {
}

