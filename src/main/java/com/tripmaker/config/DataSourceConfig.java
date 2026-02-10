package com.tripmaker.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

@Configuration
public class DataSourceConfig {
  private final ObjectMapper objectMapper;

  public DataSourceConfig(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Bean
  public DataSource dataSource(
      @Value("${tripmaker.local:true}") boolean localMode,
      @Value("${tripmaker.local-secrets.path:config/local.secrets.json}") String localSecretsPath,
      @Value("${tripmaker.secrets.enabled:false}") boolean secretsEnabled,
      @Value("${tripmaker.secrets.aws-region:}") String awsRegion,
      @Value("${tripmaker.secrets.secret-id:}") String secretId,
      @Value("${spring.datasource.url:}") String datasourceUrl,
      @Value("${spring.datasource.username:}") String datasourceUsername,
      @Value("${spring.datasource.password:}") String datasourcePassword)
      throws Exception {
    if (localMode) {
      if (datasourceUrl != null && !datasourceUrl.isBlank()) {
        return DataSourceBuilder.create()
            .url(datasourceUrl)
            .username(datasourceUsername)
            .password(datasourcePassword)
            .build();
      }
      try {
        return buildFromLocalSecretsFile(localSecretsPath);
      } catch (IllegalStateException e) {
        return buildEmbeddedH2();
      }
    }

    if (!secretsEnabled) {
      if (datasourceUrl == null || datasourceUrl.isBlank()) {
        throw new IllegalStateException(
            "tripmaker.local=false and tripmaker.secrets.enabled=false, but spring.datasource.url is missing");
      }
      return DataSourceBuilder.create()
          .url(datasourceUrl)
          .username(datasourceUsername)
          .password(datasourcePassword)
          .build();
    }

    if (secretId == null || secretId.isBlank()) {
      throw new IllegalStateException(
          "tripmaker.secrets.enabled=true but tripmaker.secrets.secret-id is missing");
    }

    Region region =
        Optional.ofNullable(awsRegion).filter((value) -> !value.isBlank()).map(Region::of).orElse(null);
    if (region == null) {
      throw new IllegalStateException(
          "tripmaker.secrets.enabled=true but tripmaker.secrets.aws-region is missing");
    }

    try (SecretsManagerClient client = SecretsManagerClient.builder().region(region).build()) {
      String secretString =
          client
              .getSecretValue(GetSecretValueRequest.builder().secretId(secretId).build())
              .secretString();

      JsonNode json = objectMapper.readTree(secretString);
      String host = required(json, "host");
      int port = json.hasNonNull("port") ? json.get("port").asInt(5432) : 5432;
      String dbName = required(json, "dbname");
      String username = required(json, "username");
      String password = required(json, "password");

      String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;
      return DataSourceBuilder.create().url(url).username(username).password(password).build();
    }
  }

  private DataSource buildFromLocalSecretsFile(String localSecretsPath) throws Exception {
    Path path = Path.of(localSecretsPath);
    if (!path.isAbsolute()) {
      path = Path.of(System.getProperty("user.dir")).resolve(path).normalize();
    }

    if (!Files.exists(path)) {
      throw new IllegalStateException(
          "tripmaker.local=true but local secrets file not found at: " + path);
    }

    JsonNode json = objectMapper.readTree(Files.readString(path));

    if (json.hasNonNull("url")) {
      String url = required(json, "url");
      String username = required(json, "username");
      String password = required(json, "password");
      return DataSourceBuilder.create().url(url).username(username).password(password).build();
    }

    String host = required(json, "host");
    int port = json.hasNonNull("port") ? json.get("port").asInt(5432) : 5432;
    String dbName = required(json, "dbname");
    String username = required(json, "username");
    String password = required(json, "password");

    String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;
    return DataSourceBuilder.create().url(url).username(username).password(password).build();
  }

  private DataSource buildEmbeddedH2() {
    // File-based H2 DB so data survives restarts; PostgreSQL mode keeps SQL/JPA behavior closer.
    String url =
        "jdbc:h2:file:./.data/tripmaker;"
            + "MODE=PostgreSQL;"
            + "DATABASE_TO_LOWER=TRUE;"
            + "DEFAULT_NULL_ORDERING=HIGH";
    return DataSourceBuilder.create().url(url).username("sa").password("").build();
  }

  private static String required(JsonNode json, String field) {
    if (!json.hasNonNull(field) || json.get(field).asText().isBlank()) {
      throw new IllegalStateException("Missing required secret field: " + field);
    }
    return json.get(field).asText();
  }
}
