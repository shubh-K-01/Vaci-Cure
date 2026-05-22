package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.entity.Doctor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DoctorRecommendationServiceImpl implements DoctorRecommendationService {

    Doctor doctors;
    private static final Map<String, String> keywordToDepartment = Map.ofEntries(
            Map.entry("fever", "Pediatrics"),
            Map.entry("cough", "Pediatrics"),
            Map.entry("cold", "Pediatrics"),
            Map.entry("vaccination", "Pediatrics"),
            Map.entry("child", "Pediatrics"),
            Map.entry("pregnancy", "Gynecology"),
            Map.entry("delivery", "Gynecology"),
            Map.entry("menstrual", "Gynecology"),
            Map.entry("baby", "Gynecology"),
            Map.entry("skin", "Dermatology"),
            Map.entry("rash", "Dermatology"),
            Map.entry("acne", "Dermatology"),
            Map.entry("fracture", "Orthopedics"),
            Map.entry("joint", "Orthopedics"),
            Map.entry("bone", "Orthopedics"),
            Map.entry("tooth", "Dentistry"),
            Map.entry("cavity", "Dentistry"),
            Map.entry("gums", "Dentistry"),
            Map.entry("headache", "Neurology"),
            Map.entry("migraine", "Neurology"),
            Map.entry("dizziness", "Neurology")
    );

    @Override
    public Optional<String> getDepartment(String reason) {
        if (reason == null || reason.isBlank()) return Optional.empty();

        String lower = reason.toLowerCase();

        Set<String> matchedDepartments = keywordToDepartment.entrySet().stream()
                .filter(entry -> lower.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());

        if(matchedDepartments.isEmpty()) {
            return Optional.empty();
        }

        return matchedDepartments.stream().findFirst();
    }

    @Override
    public Optional<Doctor> getAvailableDoctor(String reason, List<Doctor> doctorList) {
        return Optional.empty();
    }

//    @Override
//    public Optional<Doctor> getAvailableDoctor(String reason, List<Doctor> doctorList) {
//        Optional<String> department = getDepartment(reason);
//
//        if(department.isEmpty()) {
//            return null;
//        }
//
//        String departmentName = department.get();
//
//        return doctorList.stream()
//                .filter(doc -> doc.getDepartments() != null
//                        && doc.getDepartments().equals(department))
//                .findFirst()
//                .orElse(null);
//    }
}
