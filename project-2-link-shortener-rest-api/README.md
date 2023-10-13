## Project 2 - A Link Shortener
- A basic link shortener with support for rate limiting based on client IP.
- Supports 100 requests/minute for a client

## Run Locally
```bash
# Make sure to have a redis instance up and running
# and modify the application.properties
mvn spring-boot:run

# Visit the browser at localhost:8080/api or
curl http://localhost:8080/api/random

```

## Run in container
```bash
# Run maven's packaging command
mvn package

docker compose up -d

# Visit the browser at localhost:8080/api or
curl http://localhost:8080/api/random

```

## API Documentation

Detailed API documentation is available at [/swagger-ui.html](/swagger-ui.html) or [/api-docs](/api-docs)

| Endpoint         | Method | Description                                |
|------------------|--------|--------------------------------------------| 
| /api/            | POST   | create a new shortened link                |
| /api/{shortLink} | GET    | returns long link for the given short link |


