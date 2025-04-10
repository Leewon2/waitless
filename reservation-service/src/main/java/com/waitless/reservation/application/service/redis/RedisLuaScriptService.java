package com.waitless.reservation.application.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class RedisLuaScriptService {

    private final ResourceLoader resourceLoader;

    @Cacheable(value = "luaScriptCache", key = "#path")
    public String loadScript(String path) {
        try {
            Resource resource = resourceLoader.getResource(path);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Lua 스크립트를 불러오는 데 실패했습니다: " + path, e);
        }
    }
}