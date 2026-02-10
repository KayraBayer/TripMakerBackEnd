package com.tripmaker.repo;

import com.tripmaker.domain.RouteRequestEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRequestRepository extends JpaRepository<RouteRequestEntity, UUID> {}

