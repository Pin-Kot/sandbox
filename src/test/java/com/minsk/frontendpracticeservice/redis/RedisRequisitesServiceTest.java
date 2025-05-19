package com.minsk.frontendpracticeservice.redis;

import com.minsk.frontendpracticeservice.config.IntegrationTestConfig;
import com.minsk.frontendpracticeservice.domain.entity.Requisites;
import com.minsk.frontendpracticeservice.domain.request.RequisitesRequestUpdateDto;
import com.minsk.frontendpracticeservice.domain.response.RequisitesResponseDto;
import com.minsk.frontendpracticeservice.service.RequisitesService;
import com.minsk.frontendpracticeservice.stubs.RequisitesRequestDtoStubs;
import com.minsk.frontendpracticeservice.stubs.RequisitesResponseDtoStubs;
import com.minsk.frontendpracticeservice.stubs.RequisitesStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ImportTestcontainers
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RedisRequisitesServiceTest extends IntegrationTestConfig {

    private static final String CACHE_VALUE_REQUISITES = "requisites";

    @Autowired
    private RequisitesService requisitesService;

    @Autowired
    private CacheManager cacheManager;

    private Requisites requisitesInBase;
    private Requisites updatedRequisites;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("requisites").clear();
        requisitesInBase = RequisitesStubs.createValidRequisitesStubs();
        updatedRequisites = RequisitesStubs.createUpdatedRequisitesStubs();
    }

    @Test
    void getRequisitesCacheableTest() {
        RequisitesResponseDto testResponseDtoFirst = requisitesService.readRequisites(requisitesInBase.getId().toString());
        RequisitesResponseDto testResponseDtoSecond = requisitesService.readRequisites(requisitesInBase.getId().toString());

        assertThat(testResponseDtoSecond).isEqualTo(testResponseDtoFirst);
        assertThat(getCacheValue(requisitesInBase.getId())).isEqualTo(testResponseDtoFirst);
    }

    @Test
    void putCacheRequisitesTest() {
        RequisitesRequestUpdateDto validRequisitesRequestUpdateDto = RequisitesRequestDtoStubs.createRequisitesRequestUpdateDtoStubs(updatedRequisites);
        RequisitesResponseDto responseRequisitesDtoInBase = RequisitesResponseDtoStubs.createRequisitesResponseDtoStubs(updatedRequisites);
        requisitesService.updateRequisites(validRequisitesRequestUpdateDto);

        assertThat(getCacheValue(updatedRequisites.getId())).isNotNull();
        assertThat(getCacheValue(updatedRequisites.getId())).isEqualTo(responseRequisitesDtoInBase);
    }

    @Test
    void evictCacheRequisitesTest() {
        requisitesService.readRequisites(requisitesInBase.getId().toString());

        assertThat(getCacheValue(requisitesInBase.getId())).isNotNull();

        requisitesService.deleteRequisites(requisitesInBase.getId().toString());

        assertThat(getCacheValue(requisitesInBase.getId())).isNull();
    }

    private RequisitesResponseDto getCacheValue(UUID requisitesId) {
        return cacheManager
                .getCache(CACHE_VALUE_REQUISITES)
                .get(requisitesId.toString(), RequisitesResponseDto.class);
    }

}
