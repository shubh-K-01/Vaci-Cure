package com.demeatrix.VaciCure.controller;

import com.demeatrix.VaciCure.dto.DoseScheduleTemplateDTO;
import com.demeatrix.VaciCure.dto.VaccineDTO;
import com.demeatrix.VaciCure.service.VaccineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API for the vaccine master catalog.
 *
 * Base path: /api/v1/vaccines
 *
 * Access rules (enforced via @PreAuthorize):
 *   POST/PUT/DELETE  → ADMIN only
 *   GET              → DOCTOR, NURSE, ADMIN
 */
@RestController
@RequestMapping("/vaccines")
@RequiredArgsConstructor
public class VaccineController {

    private final VaccineService vaccineService;

    // ──────────────────────────────────────────────
    // CREATE
    // ──────────────────────────────────────────────

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VaccineDTO> createVaccine(@Valid @RequestBody VaccineDTO vaccineDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vaccineService.createVaccine(vaccineDTO));
    }

    @PostMapping("/{vaccineId}/dose-templates")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VaccineDTO> addDoseTemplate(
            @PathVariable Long vaccineId,
            @Valid @RequestBody DoseScheduleTemplateDTO templateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vaccineService.addDoseScheduleTemplate(vaccineId, templateDTO));
    }

    // ──────────────────────────────────────────────
    // READ
    // ──────────────────────────────────────────────

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'ADMIN', 'PHARMACIST')")
    public ResponseEntity<List<VaccineDTO>> getAllVaccines(
            @RequestParam(defaultValue = "false") boolean includeInactive) {
        List<VaccineDTO> result = includeInactive
                ? vaccineService.getAllVaccines()
                : vaccineService.getAllActiveVaccines();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{vaccineId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'ADMIN', 'PHARMACIST')")
    public ResponseEntity<VaccineDTO> getVaccineById(@PathVariable Long vaccineId) {
        return ResponseEntity.ok(vaccineService.getVaccineById(vaccineId));
    }

    // ──────────────────────────────────────────────
    // UPDATE
    // ──────────────────────────────────────────────

    @PutMapping("/{vaccineId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VaccineDTO> updateVaccine(
            @PathVariable Long vaccineId,
            @Valid @RequestBody VaccineDTO vaccineDTO) {
        return ResponseEntity.ok(vaccineService.updateVaccine(vaccineId, vaccineDTO));
    }

    // ──────────────────────────────────────────────
    // DEACTIVATE (soft delete)
    // ──────────────────────────────────────────────

    @DeleteMapping("/{vaccineId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateVaccine(@PathVariable Long vaccineId) {
        vaccineService.deactivateVaccine(vaccineId);
        return ResponseEntity.noContent().build();
    }
}
