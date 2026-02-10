package com.tripmaker.api;

import com.tripmaker.api.dto.RouteRequest;
import com.tripmaker.api.dto.RouteResponse;
import com.tripmaker.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {
  private final RouteService routeService;

  public RouteController(RouteService routeService) {
    this.routeService = routeService;
  }

  @PostMapping({"/", "/api/route"})
  public RouteResponse createRoute(@Valid @RequestBody RouteRequest request) {
    return routeService.createRoute(request);
  }
}

