package com.tripmaker.repo;

import com.tripmaker.domain.Place;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaceRepository extends JpaRepository<Place, Long> {
  List<Place> findByCity_NameAndCategoryOrderByNameAsc(String cityName, Place.Category category);

  List<Place> findByCity_NameAndCategoryAndExternalIdIn(
      String cityName, Place.Category category, List<String> externalIds);

  @Query(
      """
      select p from Place p
      where p.city.name = :cityName and p.category = :category
        and (lower(p.name) like lower(concat('%', :q, '%'))
          or lower(p.description) like lower(concat('%', :q, '%')))
      order by p.stars desc nulls last, p.name asc
      """)
  List<Place> searchInCity(
      @Param("cityName") String cityName,
      @Param("category") Place.Category category,
      @Param("q") String q,
      Pageable pageable);
}
