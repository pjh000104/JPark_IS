package com.example.localcuisine.Location;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.example.localcuisine.Location.domain.LocationRepository;
import com.example.localcuisine.Location.service.LocationService;
import com.example.localcuisine.entity.Region;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    private Region region1;
    private Region region2;

    @BeforeEach
    void setup() {
        region1 = new Region("Tokyo", "Japan");
        region2 = new Region("Osaka", "Japan");
    }

    // ---------------------------
    // getAllRegions
    // ---------------------------
    @Test
    void shouldReturnAllRegionNames() {
        when(locationRepository.findAll()).thenReturn(List.of(region1, region2));

        List<String> result = locationService.getAllRegions();

        assertEquals(2, result.size());
        assertTrue(result.contains("Tokyo"));
        assertTrue(result.contains("Osaka"));

        verify(locationRepository).findAll();
    }

    // ---------------------------
    // searchRegions - results found
    // ---------------------------
    @Test
    void shouldReturnMatchingRegionNames_whenKeywordMatches() {
        when(locationRepository.findByRegionNameContainingIgnoreCase("to"))
                .thenReturn(List.of(region1));

        List<String> result = locationService.searchRegions("to");

        assertEquals(1, result.size());
        assertEquals("Tokyo", result.get(0));

        verify(locationRepository).findByRegionNameContainingIgnoreCase("to");
    }

    // ---------------------------
    // searchRegions - no results
    // ---------------------------
    @Test
    void shouldReturnEmptyList_whenNoRegionsMatchKeyword() {
        when(locationRepository.findByRegionNameContainingIgnoreCase("xyz"))
                .thenReturn(List.of());

        List<String> result = locationService.searchRegions("xyz");

        assertTrue(result.isEmpty());

        verify(locationRepository).findByRegionNameContainingIgnoreCase("xyz");
    }
}