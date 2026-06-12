package com.retailer.rewards.exception;

import java.time.LocalDateTime;

/**
 * Standard error response object for API error handling.
 * 
 * This class represents the structure of error responses returned to clients
 * when
 * an exception or error occurs in the rewards application. The {@code
 * GlobalExceptionHandler}
 * creates instances of this class to provide consistent error information to
 * API consumers.
 * 
 * <p>
 * <b>Response Structure:</b>
 * <ul>
 * <li>{@code message}: A descriptive error message explaining what went
 * wrong</li>
 * <li>{@code timestamp}: The exact date and time when the error occurred</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Usage:</b>
 * When an exception is caught by the global exception handler, it creates an
 * {@code ErrorResponse}
 * instance with:
 * <ul>
 * <li>A user-friendly error message describing the issue</li>
 * <li>An automatically captured timestamp of when the error occurred</li>
 * </ul>
 * This object is then serialized to JSON and returned to the client with an
 * appropriate
 * HTTP status code (e.g., 404 for NOT FOUND, 500 for INTERNAL SERVER ERROR).
 * </p>
 * 
 * <p>
 * <b>Example JSON Response:</b>
 * <pre>
 * {@code
 * {
 * "message": "Customer with ID 123 not found",
 * "timestamp": "2026-06-12T10:30:45.123456"
 * }
 * }
 * </pre>
 * </p>
 * 
 * <p>
 * <b>Immutability:</b> Both fields are declared as {@code final}, making
 * instances of this
 * class immutable once created. This ensures error responses cannot be
 * accidentally modified
 * and are thread-safe.
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 * @see com.retailer.rewards.exception.GlobalExceptionHandler
 */
public class ErrorResponse {

    /**
     * The error message describing what went wrong.
     * 
     * <p>
     * This message provides details about the error condition and is displayed to
     * the client.
     * It should be descriptive enough to help users understand the issue without
     * exposing
     * sensitive internal system information.
     * </p>
     * 
     * <p>
     * <b>Examples:</b>
     * <ul>
     * <li>"Customer with ID 123 not found"</li>
     * <li>"Invalid input parameters provided"</li>
     * <li>"Internal server error occurred"</li>
     * </ul>
     * </p>
     */
    private final String message;

    /**
     * The exact date and time when the error occurred.
     * 
     * <p>
     * This timestamp is automatically captured at the moment the
     * {@code ErrorResponse} instance
     * is created, providing clients with precise information about when the error
     * happened.
     * This is useful for logging, debugging, and correlating errors with server
     * logs.
     * </p>
     * 
     * <p>
     * <b>Format:</b> ISO-8601 format with nanosecond precision (e.g.,
     * 2026-06-12T10:30:45.123456)
     * </p>
     */
    private final LocalDateTime timestamp;

    /**
     * Constructs a new {@code ErrorResponse} with the provided error message.
     * 
     * <p>
     * The timestamp is automatically set to the current date and time using
     * {@code LocalDateTime.now()}.
     * This ensures each error response has an accurate record of when the error
     * occurred.
     * </p>
     * 
     * @param message the descriptive error message explaining what went wrong.
     *                This message will be included in the JSON response sent to the
     *                client.
     *                Must not be null.
     */
    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Gets the error message describing what went wrong.
     * 
     * @return the error message. Never null.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the date and time when the error occurred.
     * 
     * @return the timestamp in {@code LocalDateTime} format. Never null.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}