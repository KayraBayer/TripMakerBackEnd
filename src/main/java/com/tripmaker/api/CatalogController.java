package com.tripmaker.api;

import com.tripmaker.api.dto.CityCatalogResponse;
import com.tripmaker.api.dto.PlaceDto;
import com.tripmaker.domain.Place;
import com.tripmaker.service.CatalogService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CatalogController {
  private final CatalogService catalogService;

  public CatalogController(CatalogService catalogService) {
    this.catalogService = catalogService;
  }

  @GetMapping("/cities")
  public List<String> listCities() {
    return catalogService.listCities();
  }

  @GetMapping("/cities/{city}/catalog")
  public CityCatalogResponse cityCatalog(@PathVariable("city") String city) {
    return new CityCatalogResponse(
        city,
        toDtos(catalogService.placesByCityAndCategory(city, Place.Category.STARTING_POINT)),
        toDtos(catalogService.placesByCityAndCategory(city, Place.Category.RESTAURANT)),
        toDtos(catalogService.placesByCityAndCategory(city, Place.Category.ACCOMMODATION)),
        toDtos(catalogService.placesByCityAndCategory(city, Place.Category.ATTRACTION)));
  }

  private static List<PlaceDto> toDtos(List<Place> places) {
    return places.stream()
        .map(
            (place) ->
                new PlaceDto(
                    place.getExternalId(),
                    place.getName(),
                    place.getDescription(),
                    place.getLat(),
                    place.getLng()))
        .toList();
  }
}
