package com.tripmaker.api.dto;

import java.util.List;

public record RouteResponse(
    String requestId,
    String city,
    List<PlaceSummary> attractions,
    List<PlaceSummary> restaurants,
    List<PlaceSummary> accommodations) {

  public record PlaceSummary(String id, String name, String description, double lat, double lng) {}
}

