package com.demeatrix.VaciCure.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class ReasonMappingServiceImpl implements ReasonMappingService {

    private static final String PRIMARY_CACHE_KEY = "reason:";
    private final RedisTemplate<String, String> redisTemplate;

    public String getDepartmentByReason(String reason) {
        return "";
    }

    @Override
    public String updateReason(String reason, String department) {
        return "";
    }
}
