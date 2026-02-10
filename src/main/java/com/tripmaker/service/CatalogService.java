package com.tripmaker.service;

import com.tripmaker.domain.Place;
import com.tripmaker.repo.CityRepository;
import com.tripmaker.repo.PlaceRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {
  private final CityRepository cityRepository;
  private final PlaceRepository placeRepository;

  public CatalogService(CityRepository cityRepository, PlaceRepository placeRepository) {
    this.cityRepository = cityRepository;
    this.placeRepository = placeRepository;
  }

  public List<String> listCities() {
    return cityRepository.findAll().stream().map((city) -> city.getName()).sorted().toList();
  }

  public List<Place> placesByCityAndCategory(String city, Place.Category category) {
    return placeRepository.findByCity_NameAndCategoryOrderByNameAsc(city, category);
  }
}

