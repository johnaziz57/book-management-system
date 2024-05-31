# Todos

## Observation
- Good the db normalizations like counts of remaining books and so on but we need more mappings
Better handling of how is going to borrow the book. 
User must have some kind of link to the book they borrowed. When they borrowed and when they returned.
- Authentication is fine but Authorization is lacking or more control
- We need more test and understand what to test
## To add

- Complex queries like statistics: (No more JPA)
  -  Task: Write a complex SQL query to generate a report, such as a list of the most popular books by genre. Include database indexing to optimize the query
        - Most borrowed book
        - Average time of borrow
        - Book not returned
  - Add Test with Zonky to test this logic

- Add environments variables (Dev, Staging, Production)
  - Task: Create separate configurations for development, testing, and production environments. This includes setting up different database connections and logging levels for each environment.
- Add database versioning (flyway or liquidbase)
  - Task: Implement Flyway or Liquibase in the project and create migration scripts to handle schema changes in the database.
- Implement Caching strategies
  - Task: Implement caching for frequently accessed data, such as book details. Use Spring’s caching annotations to cache the results of methods that fetch book information.
  - Learn about Spring caching and 3 level of hibernate caching
- Asynchronous processing and scheduling (@Async and @Scheduled)
  - Task: Implement an asynchronous task that periodically checks for and updates book information from an external API. Use @Async and @Scheduled annotations.
  - Schedule a process that check every Xhours if all the books have been returned
  - Schedule a process that every morning check if a users is due to return a book and in case today or tomorrow is the due date, send an email
- Integration with external services
  - Task: Integrate an external API to fetch additional book information, such as reviews or ratings, and display it in the application.
  - Integrate with developer.google.books a search via ISBN to fill the thumbnail of the cover of the book, this can be a 
  - https://developers.google.com/books/docs/v1/reference/volumes/list?apix_params=%7B%22q%22%3A%22Das%20Kapital%22%7D
- Message Queue and Event Driven (RabbitMQ)
  - Task: Set up a message queue (like RabbitMQ) to handle tasks such as sending notification emails to users when a new book is added.
- CI/CD Pipeline
  - Task: Set up a CI/CD pipeline using GitHub Actions to automate the testing and deployment of the application.
- Monitoring and Logging
  - Task: Implement application monitoring using a tool like Prometheus or ELK stack. Set up meaningful alerts and dashboards to monitor the health of the application.
- Documentation (Swagger)
  - Task: Document the API using Swagger or OpenAPI. Ensure that the API adheres to RESTful design principles.
- Code quality monitoring
  - Task: Integrate a code quality tool like SonarQube into the development process. Refactor a part of the application to resolve code smells and improve maintainability.
