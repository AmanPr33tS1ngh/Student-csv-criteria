package com.csvdatanatwest.natwestcsvdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.csvdatanatwest.natwestcsvdata.model.Criteria;
import com.csvdatanatwest.natwestcsvdata.service.CriteriaService;

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
