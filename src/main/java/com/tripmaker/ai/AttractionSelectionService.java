package com.tripmaker.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tripmaker.ai.openai.OpenAiClient;
import com.tripmaker.domain.Place;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AttractionSelectionService {
  private final boolean aiEnabled;
  private final OpenAiClient openAiClient;
  private final ObjectMapper objectMapper;

  public AttractionSelectionService(
      @Value("${tripmaker.ai.enabled:true}") boolean aiEnabled,
      OpenAiClient openAiClient,
      ObjectMapper objectMapper) {
    this.aiEnabled = aiEnabled;
    this.openAiClient = openAiClient;
    this.objectMapper = objectMapper;
  }

  public List<String> selectAttractionIds(String city, String userPrompt, List<Place> candidates, int limit) {
    if (!aiEnabled || !openAiClient.isConfigured()) {
      return List.of();
    }
    if (userPrompt == null || userPrompt.isBlank() || candidates == null || candidates.isEmpty()) {
      return List.of();
    }

    String system =
        "You select tourist attractions for a trip. "
            + "You must only select IDs from the provided candidates. "
            + "Return JSON that matches the provided schema.";

    StringBuilder user = new StringBuilder();
    user.append("City: ").append(city).append("\n");
    user.append("User prompt: ").append(userPrompt.trim()).append("\n\n");
    user.append("Candidates (choose the best ").append(limit).append("):\n");
    for (Place place : candidates) {
      user.append("- id: ").append(place.getExternalId()).append("\n");
      user.append("  name: ").append(place.getName()).append("\n");
      user.append("  description: ").append(place.getDescription()).append("\n");
      user.append("  lat: ").append(place.getLat()).append(" lng: ").append(place.getLng()).append("\n");
    }

    ObjectNode schema = objectMapper.createObjectNode();
    schema.put("name", "selected_attractions");
    ObjectNode schemaNode = schema.putObject("schema");
    schemaNode.put("type", "object");
    ObjectNode properties = schemaNode.putObject("properties");
    ObjectNode ids = properties.putObject("ids");
    ids.put("type", "array");
    ObjectNode items = ids.putObject("items");
    items.put("type", "string");
    ids.put("minItems", 1);
    ids.put("maxItems", limit);
    ArrayList<String> required = new ArrayList<>();
    required.add("ids");
    schemaNode.set("required", objectMapper.valueToTree(required));
    schemaNode.put("additionalProperties", false);

    try {
      String jsonText = openAiClient.createJsonResponse(system, user.toString(), schema);
      JsonNode parsed = objectMapper.readTree(jsonText);
      JsonNode idsNode = parsed.path("ids");
      if (!idsNode.isArray()) {
        return List.of();
      }

      Set<String> allowed = new HashSet<>();
      for (Place place : candidates) {
        allowed.add(place.getExternalId());
      }

      List<String> out = new ArrayList<>();
      for (JsonNode idNode : idsNode) {
        String id = idNode.asText("").trim();
        if (!id.isBlank() && allowed.contains(id) && !out.contains(id)) {
          out.add(id);
        }
        if (out.size() >= limit) {
          break;
        }
      }
      return out;
    } catch (Exception e) {
      return List.of();
    }
  }
}

