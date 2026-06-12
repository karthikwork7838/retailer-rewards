package com.retailer.rewards.exception;

/**
 * Custom exception thrown when a requested customer is not found in the
 * database.
 * 
 * This exception is an unchecked exception that extends
 * {@code RuntimeException}. It is thrown
 * by the service and DAO layers when attempting to retrieve a customer by ID
 * that does not exist
 * in the database. This exception provides clear indication that the requested
 * customer resource
 * could not be found.
 * 
 * <p>
 * <b>Key Features:</b>
 * <ul>
 * <li>Carries both an error message and an HTTP status code</li>
 * <li>Enables flexible error responses with different HTTP status codes</li>
 * <li>Defaults to HTTP 404 (Not Found) if no status code is explicitly
 * provided</li>
 * <li>Integrates with the global exception handler for consistent error
 * responses</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>When This Exception is Thrown:</b>
 * <ul>
 * <li>When calling {@code CustomerService.getCustomerById()} with a
 * non-existent customer ID</li>
 * <li>When the DAO layer receives an empty Optional from the repository
 * query</li>
 * <li>When attempting to access customer details for reward calculations for a
 * non-existent customer</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Exception Handling:</b>
 * This exception is caught and handled by the global exception handler
 * ({@code GlobalExceptionHandler}),
 * which extracts the status code and message from the exception and creates an
 * appropriate HTTP
 * response with the specified status code and error details for the client.
 * </p>
 * 
 * <p>
 * <b>HTTP Status Codes:</b>
 * <ul>
 * <li>404 Not Found: The customer was not found (default)</li>
 * <li>400 Bad Request: Invalid customer ID parameter</li>
 * <li>500 Internal Server Error: Unexpected error retrieving customer</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Usage Examples:</b>
 * 
 * <pre>
 * {@code
 * // With default status code (404)
 * throw new CustomerNotFoundException("Customer with ID 123 not found");
 * 
 * // With custom status code
 * throw new CustomerNotFoundException("Invalid customer ID", 400);
 * }
 * </pre>
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 * @see com.retailer.rewards.exception.GlobalExceptionHandler
 * @see com.retailer.rewards.service.impl.CustomerServiceImpl
 */
public class CustomerNotFoundException extends RuntimeException {

    /**
     * The HTTP status code associated with this exception.
     * 
     * <p>
     * This status code is used by the global exception handler to determine the
     * HTTP
     * response status sent to the client. Typical values include:
     * <ul>
     * <li>404 - Not Found (customer doesn't exist)</li>
     * <li>400 - Bad Request (invalid parameters)</li>
     * <li>500 - Internal Server Error (unexpected error)</li>
     * </ul>
     * </p>
     */
    private final int statusCode;

    /**
     * Constructs a new {@code CustomerNotFoundException} with the specified detail
     * message
     * and a default HTTP status code of 404 (Not Found).
     * 
     * <p>
     * The message should clearly describe which customer was not found and any
     * relevant context
     * for debugging purposes. For example: "Customer with ID 123 not found" or
     * "No customer found with the provided customer ID".
     * </p>
     * 
     * <p>
     * The default HTTP status code is 404 (Not Found), which is appropriate for
     * resource
     * not found scenarios. Use the {@link #CustomerNotFoundException(String, int)}
     * constructor
     * if a different status code is needed.
     * </p>
     * 
     * @param message the detail message explaining why the customer was not found.
     *                This message will be displayed in error responses to clients.
     *                Must not be null.
     */
    public CustomerNotFoundException(String message) {
        this(message, 404);
    }

    /**
     * Constructs a new {@code CustomerNotFoundException} with the specified detail
     * message
     * and HTTP status code.
     * 
     * <p>
     * This constructor allows both the error message and HTTP status code to be
     * specified,
     * providing flexibility for different error scenarios. The status code is
     * extracted and
     * returned by the global exception handler to create an appropriate HTTP
     * response.
     * </p>
     * 
     * <p>
     * <b>Common Status Codes:</b>
     * <ul>
     * <li>404 - Not Found: The requested customer does not exist (most common)</li>
     * <li>400 - Bad Request: Invalid customer ID parameter or format</li>
     * <li>403 - Forbidden: Customer data is restricted or inaccessible</li>
     * <li>500 - Internal Server Error: Unexpected error during customer
     * retrieval</li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Example Usage:</b>
     * 
     * <pre>
     * {@code
     * // Resource not found
     * throw new CustomerNotFoundException("Customer with ID 123 not found", 404);
     * 
     * // Bad request (invalid ID)
     * throw new CustomerNotFoundException("Invalid customer ID format", 400);
     * 
     * // Forbidden access
     * throw new CustomerNotFoundException("Access to customer data denied", 403);
     * }
     * </pre>
     * </p>
     * 
     * @param message    the detail message explaining the error. This message will
     *                   be displayed
     *                   in the error response sent to the client. Must not be null.
     * @param statusCode the HTTP status code associated with this error. Typically
     *                   one of:
     *                   404 (Not Found), 400 (Bad Request), 403 (Forbidden), 500
     *                   (Internal Server Error).
     *                   Must be a valid HTTP status code (100-599).
     */
    public CustomerNotFoundException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Gets the HTTP status code associated with this exception.
     * 
     * <p>
     * The global exception handler uses this status code to determine the HTTP
     * response
     * status code sent to the client. This allows different error scenarios to be
     * communicated
     * with appropriate HTTP status codes.
     * </p>
     * 
     * @return the HTTP status code (e.g., 404, 400, 403, 500)
     */
    public int getStatusCode() {
        return statusCode;
    }
}