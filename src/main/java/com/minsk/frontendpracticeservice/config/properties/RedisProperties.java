package com.minsk.frontendpracticeservice.config.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.cache.redis")
public class RedisProperties {

    @NotNull
    private Long timeToLive;

}
