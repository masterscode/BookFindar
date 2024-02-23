# Online Bookstore RESTful API
This document provides an overview of the implementation details for each requirement outlined in the take-home test for the online bookstore RESTful API.


This project implements a simple RESTful API for an online bookstore using Java with the Spring Boot framework. The API allows users to perform CRUD (Create, Read, Update, Delete) operations on books in the store and manage their availability status.

#### REQUIREMENT
- [Docker](https://docs.docker.com/engine/install/)
- [Postman](https://www.postman.com/downloads/)


## API Implementation
- **Java with Spring Boot Framework**: The API is developed using Java with the Spring Boot framework. Spring Boot offers a simplified development experience, providing auto-configuration and dependency management out of the box.

- #### Functionality:

  - View Books: Implemented GET /api/v1/books endpoint to retrieve a list of available books.
  - Add Books: Added POST /api/v1/books endpoint to allow users to add new books to the store.
  - Update Book Details: Implemented PUT /api/v1/books/{id} endpoint for updating book details.
  - Delete Books: Added DELETE /api/v1/books/{id} endpoint to delete books from the store.


Each endpoint supports JSON request and response formats.

## Database Integration
- **Relational Database**: Utilized MySQL as the relational database for storing book data. MySQL provides a robust and scalable solution for managing relational data

- **JPA for Database Interactions** : Used SpringDataJPA an implementation of the Java Persistence API for database interactions, providing an object-relational mapping solution for managing entities.



## Error Handling
Implemented error handling mechanisms to return appropriate HTTP status codes and error messages in case of invalid requests or errors. This ensures clarity and ease of troubleshooting for clients consuming the API.

## Testing
Developed a suite of unit tests to validate the functionality of the API endpoints logic and ensure correctness under various scenarios. Unit tests had a coverage of over 70%.
## Security
**Authentication**: Authentication can be added to API endpoints for enhanced security. This feature is optional but recommended for production environments.


# Setup Instructions

1. Clone the repository to your local machine:
```bash
    git clone git@github.com:masterscode/BookFindar.git              
```
2. Navigate to project directory
```bash
    cd BookFindar              
```
3. Build and run the project using Docker:
```bash
    docker-compose up --build              
```

4. The API will be accessible at `http://localhost:8080/api/v1/book`

### Default User
 Please note that a default user is seeded during application first run. The user log-in credentials are

```json
{
  "username": "findar@findar.com",
  "password": "password"
}
```

#### Documentation
- [Swagger Documentation](http://localhost:8080/api/v1/swagger-ui/index.html)
- [Postman Documentation](https://documenter.getpostman.com/view/17590499/2sA2rCU2ST)



Thank you for reviewing the implementation of the online bookstore RESTful API. If you have any further questions or require additional information, please feel free to reach out.