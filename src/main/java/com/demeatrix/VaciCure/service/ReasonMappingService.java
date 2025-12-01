package com.demeatrix.VaciCure.service;

public interface ReasonMappingService {
    public String getDepartmentByReason(String reason);
    public String updateReason(String reason, String department);
}
