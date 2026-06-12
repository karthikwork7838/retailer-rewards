package com.retailer.rewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point and main Spring Boot application class for the Retailer Rewards
 * System.
 * 
 * This application is a RESTful API service built with Spring Boot that manages
 * customer
 * reward programs. It provides endpoints for customer management and calculates
 * reward points
 * based on customer purchase transactions. The application implements a
 * comprehensive rewards
 * calculation engine that dynamically processes transactions and generates
 * monthly reward reports.
 * 
 * <p>
 * <b>Application Features:</b>
 * <ul>
 * <li><b>Customer Management:</b> Retrieve all customers or look up specific
 * customers by ID</li>
 * <li><b>Reward Calculation:</b> Automatically calculate reward points based on
 * transaction amounts</li>
 * <li><b>Monthly Aggregation:</b> Generate monthly reward summaries showing
 * rewards earned each month</li>
 * <li><b>Date-Based Filtering:</b> Support configurable transaction lookback
 * periods for relevant reward analysis</li>
 * <li><b>RESTful API:</b> Provide standardized JSON endpoints for customer and
 * reward data</li>
 * <li><b>Error Handling:</b> Centralized exception handling with meaningful
 * error responses</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Reward Points Calculation Rules:</b>
 * <ul>
 * <li>Transactions $0-$50: 0 reward points</li>
 * <li>Transactions $50-$100: 1 reward point per $1 spent above $50</li>
 * <li>Transactions above $100: 50 points for $50-$100 range + 2 points per $1
 * above $100</li>
 * </ul>
 * Example: A $120 purchase yields (100-50)*1 + (120-100)*2 = 50 + 40 = 90
 * reward points
 * </p>
 * 
 * <p>
 * <b>Application Architecture:</b>
 * The application follows a layered architecture pattern:
 * 
 * <pre>
 * Presentation Layer
 *     ↓ (REST Endpoints)
 * Controller Layer (@RestController)
 *     ↓
 * Service Layer (Business Logic)
 *     ↓
 * DAO Layer (Data Access Abstraction)
 *     ↓
 * Repository Layer (Spring Data JPA)
 *     ↓
 * Persistence Layer (Database)
 * </pre>
 * </p>
 * 
 * <p>
 * <b>Key Components:</b>
 * <ul>
 * <li>{@code CustomerController}: REST endpoints for customer operations</li>
 * <li>{@code CustomerService}: Business logic for reward calculations</li>
 * <li>{@code CustomerRepository}: Spring Data JPA repository for database
 * queries</li>
 * <li>{@code GlobalExceptionHandler}: Centralized exception handling for all
 * REST endpoints</li>
 * <li>{@code CustomerEntityMapper}: Entity-to-DTO conversion logic</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Configuration:</b>
 * The application requires the following property to be configured in
 * application.properties:
 * <ul>
 * <li>{@code transaction.months}: Number of months to look back when retrieving
 * transactions
 * for reward calculations (e.g., 3 for the last 3 months)</li>
 * <li>{@code cross.origin}: CORS configuration for allowed origins</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Database:</b>
 * The application uses a relational database with the following primary tables:
 * <ul>
 * <li>{@code customer}: Stores customer information (customer_id,
 * customer_name)</li>
 * <li>{@code transactions}: Stores customer transactions (transaction_id,
 * customer_id, amount, transaction_date)</li>
 * </ul>
 * Database initialization scripts (schema.sql and data.sql) are executed
 * automatically on startup.
 * </p>
 * 
 * <p>
 * <b>Starting the Application:</b>
 * 
 * <pre>
 * {@code
 * java -jar rewards-application.jar
 * // or
 * mvn spring-boot:run
 * }
 * </pre>
 * 
 * The application will start on port 8080 by default (configurable via
 * server.port property).
 * </p>
 * 
 * <p>
 * <b>API Endpoints:</b>
 * <ul>
 * <li>{@code GET /api/v1/allCustomers}: Retrieve all customers with their
 * reward information</li>
 * <li>{@code GET /api/v1/customers/{customerId}}: Retrieve a specific customer
 * with their rewards</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Technologies Used:</b>
 * <ul>
 * <li><b>Framework:</b> Spring Boot 3.x</li>
 * <li><b>Web:</b> Spring Web (REST APIs)</li>
 * <li><b>Data Access:</b> Spring Data JPA with Hibernate</li>
 * <li><b>Database:</b> H2 (embedded, for development) or configurable
 * relational database</li>
 * <li><b>Build Tool:</b> Maven</li>
 * <li><b>Java Version:</b> JDK 17 or higher</li>
 * </ul>
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 * 
 * @see com.retailer.rewards.controller.CustomerController
 * @see com.retailer.rewards.service.CustomerService
 * @see com.retailer.rewards.repository.CustomerRepository
 * @see com.retailer.rewards.exception.GlobalExceptionHandler
 */
@SpringBootApplication
public class RewardsApplication {

    /**
     * Main method to bootstrap and launch the Spring Boot application.
     * 
     * <p>
     * This method serves as the entry point for the Retailer Rewards application.
     * It initializes the Spring application context, configures all components,
     * and starts the embedded web server (typically Tomcat) to listen for incoming
     * HTTP requests on the configured port.
     * </p>
     * 
     * <p>
     * <b>Initialization Process:</b>
     * <ol>
     * <li>{@code SpringApplication.run()} creates a new Spring application
     * context</li>
     * <li>Component scanning automatically detects and registers Spring beans</li>
     * <li>Configuration properties are loaded from application.properties</li>
     * <li>Database schema is initialized (schema.sql)</li>
     * <li>Sample data is loaded (data.sql)</li>
     * <li>Embedded web server starts and begins listening for requests</li>
     * <li>Application is ready to receive API requests</li>
     * </ol>
     * </p>
     * 
     * <p>
     * <b>Command Line Arguments:</b>
     * Additional Spring Boot command-line arguments can be passed to customize
     * runtime behavior:
     * <ul>
     * <li>{@code --server.port=8081}: Override the default port</li>
     * <li>{@code --spring.datasource.url=jdbc:...}: Override database URL</li>
     * <li>{@code --transaction.months=6}: Override transaction lookback period</li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Example Usage:</b>
     * 
     * <pre>
     * {@code
     * // Standard startup
     * java -jar rewards-application.jar
     * 
     * // Custom port
     * java -jar rewards-application.jar --server.port=9000
     * 
     * // Custom configuration
     * java -jar rewards-application.jar --transaction.months=12 --server.port=8081
     * }
     * </pre>
     * </p>
     * 
     * <p>
     * <b>Shutdown:</b>
     * The application can be gracefully shut down using:
     * <ul>
     * <li>Ctrl+C in the terminal where the application is running</li>
     * <li>Sending a SIGTERM signal to the process</li>
     * </ul>
     * Spring Boot automatically handles cleanup and resource deallocation on
     * shutdown.
     * </p>
     * 
     * @param args command-line arguments passed to the application. These override
     *             properties
     *             from application.properties and environment variables. Can be
     *             empty array.
     *             Spring Boot specific arguments are processed automatically.
     * 
     * @see org.springframework.boot.SpringApplication#run(Class, String[])
     */
    public static void main(String[] args) {
        SpringApplication.run(RewardsApplication.class, args);
    }

}
