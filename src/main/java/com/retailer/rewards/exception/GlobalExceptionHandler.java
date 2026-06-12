package com.retailer.rewards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global centralized exception handler for the retailer rewards application.
 * 
 * This class intercepts and handles exceptions thrown by REST controllers and
 * their
 * service layers throughout the application. By centralizing exception
 * handling,
 * it ensures consistent error responses are returned to API consumers
 * regardless
 * of which controller throws the exception.
 * 
 * <p>
 * <b>Architecture:</b>
 * The {@code @RestControllerAdvice} annotation makes this class a global
 * exception
 * handler that applies to all {@code @RestController} classes in the
 * application.
 * Spring's {@code ExceptionHandler} mechanism automatically routes exceptions
 * to the
 * appropriate handler method based on the exception type.
 * </p>
 * 
 * <p>
 * <b>Responsibilities:</b>
 * <ul>
 * <li>Catch application-specific exceptions (e.g.,
 * {@code CustomerNotFoundException})</li>
 * <li>Map exceptions to appropriate HTTP status codes</li>
 * <li>Create standardized error response objects ({@code ErrorResponse})</li>
 * <li>Return error responses with descriptive messages to API consumers</li>
 * <li>Log exception details for debugging and monitoring purposes</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Error Response Format:</b>
 * All error responses are returned as JSON objects containing:
 * <ul>
 * <li>{@code message}: Description of what went wrong</li>
 * <li>{@code timestamp}: When the error occurred</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Exception Handlers:</b>
 * <ul>
 * <li>{@code handleCustomerNotFound()}: Handles
 * {@code CustomerNotFoundException}
 * and returns HTTP 404 (Not Found)</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Usage Pattern:</b>
 * When any {@code @RestController} method throws an exception:
 * <ol>
 * <li>Spring checks if a handler method exists for that exception type</li>
 * <li>If found, the exception is passed to the matching handler method</li>
 * <li>The handler method creates and returns a {@code ResponseEntity} with
 * error details</li>
 * <li>The error response is serialized to JSON and sent to the client</li>
 * </ol>
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 * @see org.springframework.web.bind.annotation.RestControllerAdvice
 * @see org.springframework.web.bind.annotation.ExceptionHandler
 * @see com.retailer.rewards.exception.CustomerNotFoundException
 * @see com.retailer.rewards.exception.ErrorResponse
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@code CustomerNotFoundException} thrown when a requested customer is
     * not found.
     * 
     * <p>
     * This exception handler is invoked whenever a
     * {@code CustomerNotFoundException} is thrown
     * by any REST controller or service layer method. It converts the exception
     * into an HTTP
     * response with the appropriate status code and error message that is sent back
     * to the API consumer.
     * </p>
     * 
     * <p>
     * <b>When This Handler is Invoked:</b>
     * <ul>
     * <li>When {@code CustomerController.getCustomerById()} is called with a
     * non-existent customer ID</li>
     * <li>When {@code CustomerService.getCustomerById()} cannot find the customer
     * in the database</li>
     * <li>Any other method that throws {@code CustomerNotFoundException}</li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Dynamic Status Code Handling:</b>
     * The exception handler extracts the HTTP status code from the exception object
     * rather than
     * using a hardcoded status code. This allows different error scenarios to be
     * communicated
     * with appropriate HTTP status codes:
     * <ul>
     * <li>404 Not Found: The requested customer does not exist (most common)</li>
     * <li>400 Bad Request: Invalid customer ID parameter or format</li>
     * <li>403 Forbidden: Customer data is restricted or inaccessible</li>
     * <li>500 Internal Server Error: Unexpected error during customer
     * retrieval</li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Error Response Structure:</b>
     * <ul>
     * <li><b>HTTP Status:</b> Dynamic (extracted from exception, defaults to
     * 404)</li>
     * <li><b>Response Body:</b> {@code ErrorResponse} containing:
     * <ul>
     * <li>message: The exception message (e.g., "Customer with ID 123 not
     * found")</li>
     * <li>timestamp: The date and time the error occurred</li>
     * </ul>
     * </li>
     * <li><b>Content-Type:</b> application/json</li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Example Responses:</b>
     * </p>
     * 
     * <p>
     * 404 Not Found (default):
     * 
     * <pre>
     * HTTP/1.1 404 Not Found
     * Content-Type: application/json
     * 
     * {
     *   "message": "Customer with ID 123 not found",
     *   "timestamp": "2026-06-12T10:30:45.123456"
     * }
     * </pre>
     * </p>
     * 
     * <p>
     * 400 Bad Request (custom):
     * 
     * <pre>
     * HTTP/1.1 400 Bad Request
     * Content-Type: application/json
     * 
     * {
     *   "message": "Invalid customer ID format",
     *   "timestamp": "2026-06-12T10:30:45.123456"
     * }
     * </pre>
     * </p>
     * 
     * @param ex the {@code CustomerNotFoundException} that was thrown by a
     *           controller or service method.
     *           Contains both the error message describing what went wrong and the
     *           HTTP status code
     *           to return to the client.
     * 
     * @return a {@code ResponseEntity} containing:
     *         <ul>
     *         <li>HTTP Status: The status code extracted from the exception</li>
     *         <li>Body: {@code ErrorResponse} with the exception message and
     *         current timestamp</li>
     *         </ul>
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(new ErrorResponse(ex.getMessage()));
    }
}