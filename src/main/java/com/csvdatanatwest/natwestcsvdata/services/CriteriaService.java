package com.csvdatanatwest.natwestcsvdata.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csvdatanatwest.natwestcsvdata.models.Criteria;
import com.csvdatanatwest.natwestcsvdata.repositories.CriteriaRepository;


@Service
public class CriteriaService {

    @Autowired
    private CriteriaRepository criteriaRepository;

    public Criteria getCriteria() {
        return criteriaRepository.findById(1L).orElse(null);
    }

    public Criteria createOrUpdateCriteria(Criteria criteria) {
        criteria.setId(1L);  // as there's only one record with ID 1
        return criteriaRepository.save(criteria);
    }

    public void deleteCriteria() {
        criteriaRepository.deleteById(1L);
    }
}
