package com.example.localcuisine.Cuisine;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.example.localcuisine.Cuisine.domain.CuisineRepository;
import com.example.localcuisine.Cuisine.service.CuisineService;
import com.example.localcuisine.Cuisine.api.CuisineResponse;
import com.example.localcuisine.cache.CacheService;
import com.example.localcuisine.entity.Cuisine;
import com.example.localcuisine.entity.Region;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CuisineServiceTest {

    @Mock
    private CuisineRepository cuisineRepository;

    @Mock
    private CacheService cacheService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CuisineService cuisineService;

    private Cuisine ramen;

    @BeforeEach
    void setup() {
        Region region = new Region();
        ramen = new Cuisine("Ramen", "Japanese noodle soup", region);
    }

    // ----------------------------------------------------
    // getCuisinesByRegion
    // ----------------------------------------------------

    @Test
    void shouldReturnFromCache_whenCacheHit() {

        when(cacheService.get("region:Tokyo:cuisines"))
                .thenReturn(List.of("Ramen", "Sushi"));

        List<String> result =
                cuisineService.getCuisinesByRegion("Tokyo");

        assertEquals(2, result.size());
        verify(cuisineRepository, never())
                .findByRegion_RegionName(any());
    }

    @Test
    void shouldFetchRegionCuisinesFromRepositoryAndCache_whenCacheMiss() {

        when(cacheService.get("region:Tokyo:cuisines"))
                .thenReturn(null);

        when(cuisineRepository.findByRegion_RegionName("Tokyo"))
                .thenReturn(List.of(ramen));

        List<String> result =
                cuisineService.getCuisinesByRegion("Tokyo");

        assertEquals(1, result.size());
        assertEquals("Ramen", result.get(0));

        verify(cuisineRepository, times(1))
                .findByRegion_RegionName("Tokyo");

        verify(cacheService, times(1))
                .put(eq("region:Tokyo:cuisines"), any(), eq(3600L));
    }

    // ----------------------------------------------------
    // getCuisineByName
    // ----------------------------------------------------

    @Test
    void shouldReturnCuisineFromCache_whenCacheHit() {

        CuisineResponse response =
                CuisineResponse.from(ramen);

        when(cacheService.get("cuisine:name:ramen"))
                .thenReturn(response);

        when(objectMapper.convertValue(response,
                CuisineResponse.class))
                .thenReturn(response);

        CuisineResponse result =
                cuisineService.getCuisineByName("Ramen");

        assertNotNull(result);

        verify(cuisineRepository, never())
                .findByCuisineNameIgnoreCase(any());
    }

    @Test
    void shouldFetchCuisineByNameFromRepositoryAndCache_whenCacheMiss() {

        when(cacheService.get("cuisine:name:ramen"))
                .thenReturn(null);

        when(cuisineRepository
                .findByCuisineNameIgnoreCase("Ramen"))
                .thenReturn(Optional.of(ramen));

        CuisineResponse result =
                cuisineService.getCuisineByName("Ramen");

        assertNotNull(result);

        verify(cacheService, times(1))
                .put(eq("cuisine:name:ramen"), any(), eq(3600L));
    }

    @Test
    void shouldThrowException_whenCuisineNotFound() {

        when(cacheService.get(any()))
                .thenReturn(null);

        when(cuisineRepository
                .findByCuisineNameIgnoreCase("Bibimbap"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                cuisineService.getCuisineByName("Bibimbap"));
    }

    // ----------------------------------------------------
    // getCuisinesByCountry
    // ----------------------------------------------------

    @Test
    void shouldReturnCuisineNamesByCountry() {

        when(cuisineRepository.findByRegion_Country("Japan"))
                .thenReturn(List.of(ramen));

        List<String> result =
                cuisineService.getCuisinesByCountry("Japan");

        assertEquals(1, result.size());
        assertEquals("Ramen", result.get(0));
    }
}