package com.tripmaker.ai.openai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenAiClient {
  private final ObjectMapper objectMapper;
  private final HttpClient httpClient;
  private final String apiKey;
  private final URI responsesUri;
  private final String model;
  private final Duration timeout;
  private final int maxOutputTokens;

  public OpenAiClient(
      ObjectMapper objectMapper,
      @Value("${tripmaker.openai.api-key:}") String apiKey,
      @Value("${tripmaker.openai.base-url:https://api.openai.com}") String baseUrl,
      @Value("${tripmaker.openai.model:gpt-4.1}") String model,
      @Value("${tripmaker.openai.timeout-ms:20000}") long timeoutMs,
      @Value("${tripmaker.openai.max-output-tokens:250}") int maxOutputTokens) {
    this.objectMapper = objectMapper;
    this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(timeoutMs)).build();
    this.apiKey = apiKey == null ? "" : apiKey.trim();
    this.responsesUri = URI.create(baseUrl.replaceAll("/+$", "") + "/v1/responses");
    this.model = model;
    this.timeout = Duration.ofMillis(timeoutMs);
    this.maxOutputTokens = maxOutputTokens;
  }

  public boolean isConfigured() {
    return !apiKey.isBlank();
  }

  /**
   * Calls POST /v1/responses and extracts all `output_text` content parts into a single string.
   *
   * <p>We avoid relying on SDK-only `output_text` and instead parse `output[].content[].text`.
   */
  public String createJsonResponse(String system, String user, ObjectNode jsonSchema) throws Exception {
    if (apiKey.isBlank()) {
      throw new IllegalStateException("OPENAI_API_KEY is missing");
    }

    ObjectNode body = objectMapper.createObjectNode();
    body.put("model", model);

    ArrayNode input = objectMapper.createArrayNode();
    input.add(message("system", system));
    input.add(message("user", user));
    body.set("input", input);

    ObjectNode text = objectMapper.createObjectNode();
    ObjectNode format = objectMapper.createObjectNode();
    format.put("type", "json_schema");
    format.put("name", jsonSchema.path("name").asText("tripmaker_result"));
    format.put("strict", true);
    format.set("schema", jsonSchema.path("schema"));
    text.set("format", format);
    body.set("text", text);

    body.put("max_output_tokens", maxOutputTokens);

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(responsesUri)
            .timeout(timeout)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + apiKey)
            .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
            .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode() < 200 || response.statusCode() >= 300) {
      throw new IllegalStateException(
          "OpenAI /v1/responses failed: HTTP " + response.statusCode() + " body=" + response.body());
    }

    JsonNode root = objectMapper.readTree(response.body());
    if (root.hasNonNull("error")) {
      throw new IllegalStateException("OpenAI error: " + root.get("error").toString());
    }

    StringBuilder textOut = new StringBuilder();
    JsonNode output = root.path("output");
    if (output.isArray()) {
      for (JsonNode item : output) {
        if (!"message".equals(item.path("type").asText())) {
          continue;
        }
        JsonNode content = item.path("content");
        if (!content.isArray()) {
          continue;
        }
        for (JsonNode part : content) {
          if ("output_text".equals(part.path("type").asText())) {
            textOut.append(part.path("text").asText());
          }
        }
      }
    }

    String out = textOut.toString().trim();
    if (out.isBlank()) {
      throw new IllegalStateException("OpenAI response had no output_text content.");
    }
    return out;
  }

  private ObjectNode message(String role, String content) {
    ObjectNode msg = objectMapper.createObjectNode();
    msg.put("role", role);
    msg.put("content", content);
    return msg;
  }
}
