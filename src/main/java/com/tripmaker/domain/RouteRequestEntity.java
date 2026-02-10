package com.tripmaker.domain;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "route_request")
public class RouteRequestEntity {
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "city_name", nullable = false)
  private String cityName;

  @Column(name = "starting_point_id")
  private String startingPointId;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "starting_point", columnDefinition = "jsonb")
  private JsonNode startingPoint;

  @Column(name = "prompt")
  private String prompt;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "selections", columnDefinition = "jsonb")
  private JsonNode selections;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "selected_attraction_ids", columnDefinition = "jsonb")
  private JsonNode selectedAttractionIds;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "selected_restaurant_ids", columnDefinition = "jsonb")
  private JsonNode selectedRestaurantIds;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "selected_accommodation_ids", columnDefinition = "jsonb")
  private JsonNode selectedAccommodationIds;

  protected RouteRequestEntity() {}

  public RouteRequestEntity(
      UUID id,
      OffsetDateTime createdAt,
      String cityName,
      String startingPointId,
      JsonNode startingPoint,
      String prompt,
      JsonNode selections,
      JsonNode selectedAttractionIds,
      JsonNode selectedRestaurantIds,
      JsonNode selectedAccommodationIds) {
    this.id = id;
    this.createdAt = createdAt;
    this.cityName = cityName;
    this.startingPointId = startingPointId;
    this.startingPoint = startingPoint;
    this.prompt = prompt;
    this.selections = selections;
    this.selectedAttractionIds = selectedAttractionIds;
    this.selectedRestaurantIds = selectedRestaurantIds;
    this.selectedAccommodationIds = selectedAccommodationIds;
  }

  public UUID getId() {
    return id;
  }
}

