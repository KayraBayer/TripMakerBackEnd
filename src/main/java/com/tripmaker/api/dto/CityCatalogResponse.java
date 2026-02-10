package com.tripmaker.api.dto;

import java.util.List;

public record CityCatalogResponse(
    String city,
    List<PlaceDto> possibleStartingPoint,
    List<PlaceDto> restaurants,
    List<PlaceDto> accommodations,
    List<PlaceDto> touristAttractions) {}

