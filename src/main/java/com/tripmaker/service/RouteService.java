package com.tripmaker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.tripmaker.api.dto.RouteRequest;
import com.tripmaker.api.dto.RouteResponse;
import com.tripmaker.api.dto.RouteResponse.PlaceSummary;
import com.tripmaker.ai.AttractionSelectionService;
import com.tripmaker.domain.Place;
import com.tripmaker.domain.RouteRequestEntity;
import com.tripmaker.repo.CityRepository;
import com.tripmaker.repo.PlaceRepository;
import com.tripmaker.repo.RouteRequestRepository;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RouteService {
  private final CityRepository cityRepository;
  private final PlaceRepository placeRepository;
  private final RouteRequestRepository routeRequestRepository;
  private final ObjectMapper objectMapper;
  private final AttractionSelectionService attractionSelectionService;

  public RouteService(
      CityRepository cityRepository,
      PlaceRepository placeRepository,
      RouteRequestRepository routeRequestRepository,
      ObjectMapper objectMapper,
      AttractionSelectionService attractionSelectionService) {
    this.cityRepository = cityRepository;
    this.placeRepository = placeRepository;
    this.routeRequestRepository = routeRequestRepository;
    this.objectMapper = objectMapper;
    this.attractionSelectionService = attractionSelectionService;
  }

  @Transactional
  public RouteResponse createRoute(RouteRequest request) {
    String city = requiredTrimmed(request.city(), "city");
    if (!cityRepository.existsById(city)) {
      throw new IllegalArgumentException("Unknown city: " + city);
    }

    var selectedAttractions =
        resolveSelected(
            city, Place.Category.ATTRACTION, request.selections().attractions(), request.prompt(), 4);
    var selectedRestaurants =
        resolveSelected(
            city,
            Place.Category.RESTAURANT,
            request.selections().restaurants(),
            null,
            4);
    var selectedAccommodations =
        resolveSelected(
            city,
            Place.Category.ACCOMMODATION,
            request.selections().accommodations(),
            null,
            4);

    UUID id = UUID.randomUUID();
    routeRequestRepository.save(
        new RouteRequestEntity(
            id,
            OffsetDateTime.now(),
            city,
            request.startingPointId(),
            request.startingPoint() == null ? null : objectMapper.valueToTree(request.startingPoint()),
            request.prompt(),
            objectMapper.valueToTree(request.selections()),
            idsToJson(selectedAttractions),
            idsToJson(selectedRestaurants),
            idsToJson(selectedAccommodations)));

    return new RouteResponse(
        id.toString(),
        city,
        summarize(selectedAttractions),
        summarize(selectedRestaurants),
        summarize(selectedAccommodations));
  }

  private List<Place> resolveSelected(
      String city,
      Place.Category category,
      Map<String, Boolean> selections,
      String prompt,
      int fallbackLimit) {
    List<String> selectedIds =
        selections == null
            ? List.of()
            : selections.entrySet().stream()
                .filter((entry) -> Boolean.TRUE.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .toList();

    if (!selectedIds.isEmpty()) {
      List<Place> places = placeRepository.findByCity_NameAndCategoryAndExternalIdIn(city, category, selectedIds);
      Map<String, Place> byExternalId =
          places.stream().collect(Collectors.toMap(Place::getExternalId, (p) -> p));
      return selectedIds.stream()
          .map(byExternalId::get)
          .filter((p) -> p != null)
          .toList();
    }

    if (prompt != null && !prompt.isBlank() && category == Place.Category.ATTRACTION) {
      List<Place> candidates = placeRepository.findByCity_NameAndCategoryOrderByNameAsc(city, category);
      List<String> selectedIds =
          attractionSelectionService.selectAttractionIds(city, prompt, candidates, fallbackLimit);
      if (!selectedIds.isEmpty()) {
        List<Place> selectedPlaces =
            placeRepository.findByCity_NameAndCategoryAndExternalIdIn(city, category, selectedIds);
        Map<String, Place> byExternalId =
            selectedPlaces.stream().collect(Collectors.toMap(Place::getExternalId, (p) -> p));
        return selectedIds.stream().map(byExternalId::get).filter((p) -> p != null).toList();
      }

      List<Place> searched =
          placeRepository.searchInCity(city, category, prompt.trim(), PageRequest.of(0, fallbackLimit));
      if (!searched.isEmpty()) {
        return searched;
      }
    }

    return placeRepository
        .findByCity_NameAndCategoryOrderByNameAsc(city, category)
        .stream()
        .limit(fallbackLimit)
        .toList();
  }

  private ArrayNode idsToJson(List<Place> places) {
    ArrayNode node = objectMapper.createArrayNode();
    for (Place place : places) {
      node.add(place.getExternalId());
    }
    return node;
  }

  private static String requiredTrimmed(String value, String field) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Missing field: " + field);
    }
    return value.trim();
  }

  private static List<PlaceSummary> summarize(List<Place> places) {
    List<PlaceSummary> out = new ArrayList<>();
    for (Place place : places) {
      out.add(
          new PlaceSummary(
              place.getExternalId(),
              place.getName(),
              place.getDescription(),
              place.getLat(),
              place.getLng()));
    }
    return out;
  }
}
