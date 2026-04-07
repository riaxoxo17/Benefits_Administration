package main.java.com.benefits.exceptions;

/**
 * Custom Exception: DatabaseConnectionException
 *
 * PURPOSE:
 * This exception is thrown when the system fails to establish or maintain
 * a connection with the database.
 *
 * It represents a system-level failure (infrastructure issue), not a business rule failure.
 *
 * WHERE IT IS USED:
 * - DAO layer (Data Access Object)
 * - Database utility classes (e.g., connection managers)
 * - Any place where DB connectivity is required
 *
 * DESIGN PATTERN CONTEXT:
 * - DAO Pattern (Structural Pattern):
 *   This exception is typically used within DAO implementations to indicate
 *   failures in persistence operations.
 *
 * GRASP PRINCIPLES:
 * - Low Coupling:
 *   Higher-level modules (services/controllers) do not depend on database-specific
 *   errors but instead handle this abstracted exception.
 *
 * - Protected Variations:
 *   Changes in database technology (MySQL → PostgreSQL → NoSQL) will not affect
 *   higher layers because this exception provides a stable interface for error handling.
 *
 * SOLID PRINCIPLES:
 * - SRP (Single Responsibility Principle):
 *   This class is only responsible for representing database connection failures.
 *
 * - DIP (Dependency Inversion Principle):
 *   Business logic depends on abstractions (DAO interfaces + generic exceptions),
 *   not on low-level database details.
 *
 * EXCEPTION HANDLING STRATEGY:
 * - This is a checked exception (extends Exception)
 *   → Forces explicit handling of database-related failures
 *   → Prevents silent crashes due to connectivity issues
 *
 * DESIGN DECISION:
 * - Separates infrastructure errors from business logic errors
 * - Helps in implementing retry mechanisms, fallbacks, or user notifications
 *
 * REAL-WORLD ANALOGY:
 * - Similar to a system being unable to connect to a bank server while processing a transaction
 */
public class DatabaseConnectionException extends Exception {

    /**
     * Constructor to initialize the exception with a meaningful message.
     *
     * @param msg Description of the connection failure
     *
     * WHY IMPORTANT:
     * - Helps identify whether the issue is network-related, credential-related,
     *   or server-related
     * - Useful for logging, debugging, and alerting systems
     */
    public DatabaseConnectionException(String msg) {
        super(msg);
    }
}