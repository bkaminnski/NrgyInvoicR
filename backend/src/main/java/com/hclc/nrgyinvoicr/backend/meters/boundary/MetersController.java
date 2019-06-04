package com.hclc.nrgyinvoicr.backend.meters.boundary;

import com.hclc.nrgyinvoicr.backend.meters.control.MetersService;
import com.hclc.nrgyinvoicr.backend.meters.entity.Meter;
import com.hclc.nrgyinvoicr.backend.meters.entity.MetersSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meters")
public class MetersController {
    private final MetersService metersService;

    public MetersController(MetersService metersService) {
        this.metersService = metersService;
    }

    @GetMapping
    public Page<Meter> findMeters(MetersSearchCriteria metersSearchCriteria) {
        return metersService.findMeters(metersSearchCriteria);
    }
}
