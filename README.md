# TripMakerBackEnd

Spring Boot backend for TripMaker (Postgres + pgvector + Flyway).

## Local dev

### 1) Local DB (no Docker)

Default local mode uses an embedded H2 database (no DB setup, no DB secrets).

Optional (if you want local Postgres instead of H2):
- Set `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, or
- Create `TripMakerBackEnd/config/local.secrets.json` (copy from `TripMakerBackEnd/config/local.secrets.example.json`).

### 2) Run the API

```bash
cd TripMakerBackEnd
./mvnw spring-boot:run
```

The frontend already POSTs to a single URL (`VITE_API_URL`). Point it to:
- `http://localhost:8080/api/route` (recommended), or
- `http://localhost:8080/` (also supported).

## OpenAI (optional)

This backend can use OpenAI (Responses API, `POST /v1/responses`) to auto-select attractions when the user provides a prompt but didn’t manually pick attraction IDs.

Set these as Codespaces secrets (or env vars):
- `OPENAI_API_KEY`
- (optional) `OPENAI_MODEL` (default: `gpt-4.1`)

Disable AI selection:
- `TRIPMAKER_AI_ENABLED=false`

## AWS Secrets Manager (optional)

Set:
- `SPRING_PROFILES_ACTIVE=cloud`
- `TRIPMAKER_SECRETS_ENABLED=true`
- `TRIPMAKER_AWS_REGION=...`
- `TRIPMAKER_AWS_SECRET_ID=...`

Secret value JSON shape:
```json
{"host":"...","port":5432,"dbname":"tripmaker","username":"...","password":"..."}
```
