The server is running on http://localhost:8080.

The swagger UI can be reached at http://localhost:8080/swagger-ui.html.

The API docs are available at  http://localhost:8080/api-docs.

The actuator endpoints are available on http://localhost:8081/actuator.

Under http://localhost:8081/actuator/prometheus applications metrics can be checked. There are two custom metrics:
- uploaded_csv
- current_csv_lines

The api definition is located under src/main/java/resources/api.yaml. It is generated when building.