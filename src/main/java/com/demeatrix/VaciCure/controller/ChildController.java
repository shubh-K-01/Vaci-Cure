package com.demeatrix.VaciCure.controller;

import com.demeatrix.VaciCure.dto.ChildPatientDTO;
import com.demeatrix.VaciCure.service.ChildPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("profile/patients")
public class ChildController {

    private final ChildPatientService childPatientService;

    @PostMapping("/add")
    public ResponseEntity<Long> addChildPatient(@Valid @RequestBody ChildPatientDTO childPatientDTO ) {
        return ResponseEntity.ok(childPatientService.addChildPatient(childPatientDTO));
    }

    @GetMapping("/get/{childPatientId}")
    public ResponseEntity<ChildPatientDTO> getChildPatientById(@PathVariable Long childPatientId) {
        return ResponseEntity.ok(childPatientService.getChildPatientById(childPatientId));
    }



}


