package com.tripmaker.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "city")
public class City {
  @Id
  @Column(name = "name", nullable = false, updatable = false)
  private String name;

  @Column(name = "center_lat", nullable = false)
  private double centerLat;

  @Column(name = "center_lng", nullable = false)
  private double centerLng;

  protected City() {}

  public City(String name, double centerLat, double centerLng) {
    this.name = name;
    this.centerLat = centerLat;
    this.centerLng = centerLng;
  }

  public String getName() {
    return name;
  }

  public double getCenterLat() {
    return centerLat;
  }

  public double getCenterLng() {
    return centerLng;
  }
}

