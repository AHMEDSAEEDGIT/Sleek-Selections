# SleekSelects E-Commerce API

A comprehensive, production-ready E-commerce Backend REST API built with Spring Boot.

## Features

*   **User Management**: Registration, Login, Role-based Access (Admin/Customer), JWT Authentication.
*   **Product Management**: CRUD for Products and Categories, Image handling, Inventory tracking.
*   **Order Management**: Cart system (User-linked), Order placement, Order history.
*   **Search & Filtering**: Filter products by category, brand, name.
*   **Security**: Secured endpoints using Spring Security and JWT.
*   **API Documentation**: Integrated Swagger UI.

## Technologies

*   **Java 17**
*   **Spring Boot 3.x**
*   **Spring Security & JWT**
*   **Spring Data JPA (Hibernate)**
*   **MySQL Database**
*   **Lombok**
*   **ModelMapper**

## Getting Started

### Prerequisites

*   Java 17+
*   Maven 3.x
*   MySQL Server

### Configuration

1.  Update `src/main/resources/application.properties` with your MySQL credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/sleek_selects
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

### Running the Application

1.  Clone the repository.
2.  Run the following command to build and run:
    ```bash
    mvn spring-boot:run
    ```

The API will be available at `http://localhost:8080`.

## API Documentation

- Swagger UI is available at: `http://localhost:8181/swagger-ui/index.html`
- Swagger UI live on github is available at: `https://ahmedsaeedgit.github.io/Sleek-Selections-OpenAPI/`

### Key Endpoints

*   **Auth**:
    *   `POST /api/v1/auth/register` - Register a new user
    *   `POST /api/v1/auth/login` - Login and receive JWT
*   **Products**:
    *   `GET /api/v1/products/all` - List all products
    *   `POST /api/v1/products/add` - Add product (Admin only)
*   **Categories**:
    *   `GET /api/v1/categories/all` - List all categories
*   **Orders**:
    *   `POST /api/v1/orders/user/{userId}/place-order` - Place an order from cart
    *   `GET /api/v1/orders/user/{userId}/orders` - Get user order history

## Security

*   Endpoints under `/api/v1/auth/**`, `/api/v1/products/**` (GET), and `/api/v1/categories/**` (GET) are public.
*   All other endpoints require a valid Bearer Token in the `Authorization` header.
*   Admin-only endpoints require a user with `ROLE_ADMIN`.
