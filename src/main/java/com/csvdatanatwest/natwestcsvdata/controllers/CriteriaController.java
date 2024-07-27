package com.csvdatanatwest.natwestcsvdata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.csvdatanatwest.natwestcsvdata.models.Criteria;
import com.csvdatanatwest.natwestcsvdata.services.CriteriaService;

@RestController
@RequestMapping("/criteria")
public class CriteriaController {

    @Autowired
    private CriteriaService criteriaService;

    @GetMapping
    public Criteria getCriteria() {
        return criteriaService.getCriteria();
    }

    @PostMapping
    public Criteria createOrUpdateCriteria(@RequestBody Criteria criteria) {
        return criteriaService.createOrUpdateCriteria(criteria);
    }
}
