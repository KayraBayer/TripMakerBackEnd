package com.tripmaker.repo;

import com.tripmaker.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, String> {}

