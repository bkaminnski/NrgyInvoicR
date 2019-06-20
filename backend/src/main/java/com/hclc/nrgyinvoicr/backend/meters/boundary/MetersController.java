package com.hclc.nrgyinvoicr.backend.meters.boundary;

import com.hclc.nrgyinvoicr.backend.EntityNotFoundException;
import com.hclc.nrgyinvoicr.backend.ErrorResponse;
import com.hclc.nrgyinvoicr.backend.meters.control.MeterAlreadyRegisteredException;
import com.hclc.nrgyinvoicr.backend.meters.control.MetersService;
import com.hclc.nrgyinvoicr.backend.meters.entity.Meter;
import com.hclc.nrgyinvoicr.backend.meters.entity.MetersSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/meters")
public class MetersController {
    private final MetersService metersService;

    public MetersController(MetersService metersService) {
        this.metersService = metersService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Meter> createMeter(@RequestBody Meter meter) throws MeterAlreadyRegisteredException {
        final Meter savedMeter = metersService.createMeter(meter);
        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(savedMeter.getId()).toUri();
        return ResponseEntity.created(uri).body(savedMeter);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Meter> getMeter(@PathVariable Long id) {
        return metersService.getMeter(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(Meter.class, id));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public Page<Meter> findMeters(MetersSearchCriteria metersSearchCriteria) {
        return metersService.findMeters(metersSearchCriteria);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Meter> updateMeter(@PathVariable Long id, @RequestBody Meter meter) throws MeterAlreadyRegisteredException {
        meter.setId(id);
        return metersService.updateMeter(meter)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(Meter.class, id));
    }

    @ExceptionHandler({MeterAlreadyRegisteredException.class})
    protected ResponseEntity<ErrorResponse> handleException(MeterAlreadyRegisteredException e) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
}
