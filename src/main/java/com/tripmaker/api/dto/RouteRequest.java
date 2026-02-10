package com.tripmaker.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record RouteRequest(
    @NotBlank String city,
    String startingPointId,
    StartingPoint startingPoint,
    String prompt,
    @NotNull @Valid Selections selections) {

  public record StartingPoint(
      @NotBlank String id,
      @NotBlank String label,
      @NotBlank String description,
      double lat,
      double lng) {}

  public record Selections(
      Map<String, Boolean> attractions,
      Map<String, Boolean> restaurants,
      Map<String, Boolean> accommodations) {}
}
