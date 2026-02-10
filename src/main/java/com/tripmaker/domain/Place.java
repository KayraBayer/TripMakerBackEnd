package com.tripmaker.domain;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "place")
public class Place {
  public enum Category {
    STARTING_POINT,
    RESTAURANT,
    ACCOMMODATION,
    ATTRACTION
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pk", nullable = false, updatable = false)
  private Long pk;

  @Column(name = "external_id", nullable = false, updatable = false)
  private String externalId;

  @ManyToOne(optional = false)
  @JoinColumn(name = "city_name", nullable = false)
  private City city;

  @Enumerated(EnumType.STRING)
  @Column(name = "category", nullable = false)
  private Category category;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "lat", nullable = false)
  private double lat;

  @Column(name = "lng", nullable = false)
  private double lng;

  @Column(name = "food_type")
  private String foodType;

  @Column(name = "accommodation_type")
  private String accommodationType;

  @Column(name = "attraction_type")
  private String attractionType;

  @Column(name = "history_summary")
  private String historySummary;

  @Column(name = "stars")
  private Double stars;

  @Column(name = "price_range")
  private String priceRange;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "image_urls", columnDefinition = "jsonb")
  private JsonNode imageUrls;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "reviews", columnDefinition = "jsonb")
  private JsonNode reviews;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "location", columnDefinition = "jsonb")
  private JsonNode location;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "price_comparisons", columnDefinition = "jsonb")
  private JsonNode priceComparisons;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  protected Place() {}

  public Place(
      String externalId,
      City city,
      Category category,
      String name,
      String description,
      double lat,
      double lng) {
    this.externalId = externalId;
    this.city = city;
    this.category = category;
    this.name = name;
    this.description = description;
    this.lat = lat;
    this.lng = lng;
  }

  public Long getPk() {
    return pk;
  }

  public String getExternalId() {
    return externalId;
  }

  public City getCity() {
    return city;
  }

  public Category getCategory() {
    return category;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public String getFoodType() {
    return foodType;
  }

  public void setFoodType(String foodType) {
    this.foodType = foodType;
  }

  public String getAccommodationType() {
    return accommodationType;
  }

  public void setAccommodationType(String accommodationType) {
    this.accommodationType = accommodationType;
  }

  public String getAttractionType() {
    return attractionType;
  }

  public void setAttractionType(String attractionType) {
    this.attractionType = attractionType;
  }

  public String getHistorySummary() {
    return historySummary;
  }

  public void setHistorySummary(String historySummary) {
    this.historySummary = historySummary;
  }

  public Double getStars() {
    return stars;
  }

  public void setStars(Double stars) {
    this.stars = stars;
  }

  public String getPriceRange() {
    return priceRange;
  }

  public void setPriceRange(String priceRange) {
    this.priceRange = priceRange;
  }

  public JsonNode getImageUrls() {
    return imageUrls;
  }

  public void setImageUrls(JsonNode imageUrls) {
    this.imageUrls = imageUrls;
  }

  public JsonNode getReviews() {
    return reviews;
  }

  public void setReviews(JsonNode reviews) {
    this.reviews = reviews;
  }

  public JsonNode getLocation() {
    return location;
  }

  public void setLocation(JsonNode location) {
    this.location = location;
  }

  public JsonNode getPriceComparisons() {
    return priceComparisons;
  }

  public void setPriceComparisons(JsonNode priceComparisons) {
    this.priceComparisons = priceComparisons;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
