package com.csvdatanatwest.natwestcsvdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.csvdatanatwest.natwestcsvdata.model.Criteria;
import com.csvdatanatwest.natwestcsvdata.service.CriteriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/criteria")
@Tag(name = "Criteria APIs", description = "Criteria APIs to update the criteria anytime")
public class CriteriaController {

    @Autowired
    private CriteriaService criteriaService;

    @GetMapping
    @Operation(summary = "Get current Critera", description = "Default will be the criteria that is provided in mail. It will be created once the upload csv is ran. Otherwise can be created/updated before running the upload api")
    public Criteria getCriteria() {
        return criteriaService.getCriteria();
    }

    @PostMapping
    @Operation(summary = "Create or update Criteria")
    public Criteria createOrUpdateCriteria(@RequestBody Criteria criteria) {
        return criteriaService.createOrUpdateCriteria(criteria);
    }
}
