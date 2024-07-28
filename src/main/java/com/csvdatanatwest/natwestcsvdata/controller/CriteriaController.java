package com.csvdatanatwest.natwestcsvdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.csvdatanatwest.natwestcsvdata.model.Criteria;
import com.csvdatanatwest.natwestcsvdata.service.CriteriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/criteria")
@Tag(name = "Criteria APIs", description = "Criteria APIs to update the criteria anytime")
public class CriteriaController {

    @Autowired
    private CriteriaService criteriaService;

    private static final Logger logger = LoggerFactory.getLogger(CriteriaController.class);

    @GetMapping
    @Operation(summary = "Get current Critera", description = "Default will be the criteria that is provided in mail. It will be created once the upload csv is ran. Otherwise can be created/updated before running the upload api")
    public Criteria getCriteria() {
        logger.info("Getting Criteria");
        return criteriaService.getCriteria();
    }

    @PostMapping
    @Operation(summary = "Create or update Criteria")
    public Criteria createOrUpdateCriteria(@RequestBody Criteria criteria) {
        logger.info("Creating or Updating Criteria");
        return criteriaService.createOrUpdateCriteria(criteria);
    }
}
