 # 🛒 E-Commerce Database System

This project demonstrates a basic relational database design for an e-commerce platform using MySQL. It includes an ERD, schema diagram, SQL scripts to create and populate tables, sample reporting queries, and a denormalized version for performance testing.

---

## 📌 Project Structure

- `diagrams/`: Contains the ERD and schema diagram images.
- `scripts/`: SQL files to:
  - Create the tables
  - Insert sample data
  - Generate reports
  - Define a denormalized version and insert into it

---

## 📚 Features

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

Swagger UI is available at:
`http://localhost:8080/swagger-ui/index.html`

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

--- 
## Reports
- **Optimized Reports with analysis**:
  - [📅 Daily Revenue Report](scripts/reports/1-daily-revenue.md)
  - [📈 Monthly Top-Selling Products](scripts/reports/2-monthly-top-products.md)
  - [💰 Customers with High Total Purchases](scripts/reports/3-high-value-customers.md)
  - [📷 products-contains-camera-word](scripts/reports/4-products-contains-camera-word.md)
  - [🧩 Popular products recommendations for the same category](scripts/reports/5-popular-products-recommendations.md)
  - [🔢 Total number of products for each category](scripts/reports/8-products-foreach-category.md)
  - [💰 Top customers with total spendings](scripts/reports/9-customers-ordered-by-total-spendings.md)
  - [✨ Most recent 1000 Order with customer information](scripts/reports/10-recent-1000-order-with-customer-info.md)
  - [⏬ Products that have low stock quantity that less than 10](scripts/reports/11-products-have-low-stock-quantity.md)
  - [💲 Revenue earned for each product ](scripts/reports/12-revenue-earned-for-each-product.md)
  
- **userinfo optimization**:
  - [📦 Categories hirarchey using recursive CTE](scripts/reports/6-display-all-categories-recursive.md)
  - [🔒 Adding lock on row and field level](scripts/reports/7-lock-field-quantity-update-by-Id.md)
  - [📅 Index Performance Comparison in MySQL](benchmark/index-effect-examples.md)
  - 🔄 [ user info table feed and data generation using procedure](benchmark/scripts/userinfo-feed-procedure.sql)
  - 🐌 [ different indeces effect benchmark](benchmark/scripts/different-indeces-benchmark.sql)
  - 🧠 [ redundant indces benchmark](benchmark/redundant-indeces.md)
- **Denormalization version of entities**:
  -  [🔧 version on customer and order entities ](scripts/denormalized-tables/8.Customer_Order_Denormalized-Script.md)
---


## 📊 Database Tables & Indexes Overview

| Table Name   | Row Count | Used Indexes |
|--------------|-----------|--------------|
| `Customer`  | ~1M     | `PRIMARY (Customer_Id)`, `idx_email`, `idx_lastname` |
| `Orders`     | ~2M       | `PRIMARY (Order_Id)`, `idx_orders_customer_date (Customer_Id, Order_Date)`, `idx_orders_customer_amount(Customer_Id,Total_Amount)` ,`idx_orders_date_amount(order_date,Total_Amount)` , `idx_orders_date_customer_amount(order_date,Customer_Id,Total_Amount)`|
| `order_details`| ~5M       | `PRIMARY (OrderItem_Id)`, `idx_order (Order_Id)`, `idx_product (Product_Id)` , `idx_order_details_product(Product_Id,Quantity,Unit_Price)` |
| `Products`   | ~1M       | `PRIMARY (Product_Id)`, `idx_name`, `idx_category`, `ft_name_desc (FULLTEXT Name, Description)`,`idx_product_stock_quantity` , `idx_product_low_stock_covering(Stock_Quantity,Name ,Price)` |
| `Category` | ~1M       | `PRIMARY (Category_Id)` |



## 🚀 Report Benchmarking Results

| Report                                                                 | Query Description | Before Optimization | After Optimization | Optimization Technique |
|------------------------------------------------------------------------|------------------|---------------------|--------------------|------------------------|
| [📅 Daily Revenue Report](scripts/reports/1-daily-revenue.md)          | Aggregate revenue by day (`SUM(Total_Amount)` grouped by `Order_Date`) | ❌ ~1.85s (full table scan, 2M rows) | ✅ ~0.82s (covering index scan) | Composite covering index `(Order_Date, Total_Amount)` |
| [📈 Monthly Top-Selling Products](scripts/reports/2-monthly-top-products.md) | Top products per month with `SUM(Quantity)` | ❌ Timeout after 10+ min (non-sargable `DATE_FORMAT`) | ✅ ~1.5s (date range filter + indexes) overall query time`~ 12 sec` | Replaced `DATE_FORMAT` with range filter + composite indexes |
| [💰 Customers with High Total Purchases](scripts/reports/3-high-value-customers.md) | Find customers with total purchases > 500 | ❌ Timeout after 10+ (~5M rows scanned) | ✅ ~4–5s (covering index scan) | Pre-aggregated `Orders.Total_Amount` + composite index `(Order_Date, Customer_Id, Total_Amount)` |
| [📷 Products contains 'camera' word](scripts/reports/4-products-contains-camera-word.md) | Search products with "camera" in name | ❌ ~2.5s (LIKE `%camera%`, 1M rows) | ✅ ~50ms (Full-Text search, ~5k rows) | Full-text index + `MATCH ... AGAINST` |
| [🧩 Popular products recommendations](scripts/reports/5-popular-products-recommendations.md) | Recommend products frequently bought together | ❌ Timeout after 10+ / very slow (CTEs + NOT IN) | ✅ ~1.5s (~200k rows scanned) | Query rewrite (`NOT EXISTS` instead of `NOT IN`) + indexing |
| [🔢 Products per category](scripts/reports/8-products-foreach-category.md) | Count products grouped by category | ❌ Timeout after 10+ min (full scan + GROUP BY) | ✅ ~80ms | Index on `Category_Id` + optimized GROUP BY |
| [💰 Top customers by spendings](scripts/reports/9-customers-ordered-by-total-spendings.md) | Rank customers by total spending | ❌ ~3.5s (aggregate scan of orders) | ✅ ~0.95s | Composite index `(Customer_Id, Total_Amount)` + LIMIT |
| [✨ Recent 1000 Orders](scripts/reports/10-recent-1000-order-with-customer-info.md) | Get latest 1000 orders with customer details | ❌ ~2.8s (full join scan, 2M rows) | ✅ ~120ms | Index on `Order_Date DESC` + JOIN optimization + LIMIT |
| [⏬ Low stock products](scripts/reports/11-products-have-low-stock-quantity.md) | Find products where stock < threshold | ❌ ~1.0s (table scan, 1M rows) | ✅ ~40ms | Index on `Stock_Quantity` + selective WHERE clause |
| [💲 Revenue per product](scripts/reports/12-revenue-earned-for-each-product.md) | Aggregate revenue grouped by product | ❌ ~2.2s (scanning all orders + join) | ✅ ~0.75s | Index on `Product_Id` + pre-aggregated revenue |

---
## 💾 Tools
*   Java 17+
*   Maven 3.x
*   MySQL 8.0+ (or compatible database engine)
*   SQL client or IDE (e.g., MySQL Workbench)


