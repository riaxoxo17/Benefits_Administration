package main.java.com.benefits.exceptions;

/**
 * Custom Exception: SystemIntegrationException
 *
 * PURPOSE:
 * This exception represents a general failure during integration
 * with external or internal systems.
 *
 * It acts as a higher-level abstraction for integration-related issues,
 * especially when the failure is not specific to a single subsystem
 * (e.g., payroll, third-party APIs, external services).
 *
 * WHERE IT IS USED:
 * - Integration layer (API calls, external services, third-party systems)
 * - When communication between systems fails
 * - As a generic fallback for integration-related errors
 *
 * EXAMPLES OF FAILURE SCENARIOS:
 * - External API is unavailable
 * - Invalid response from another system
 * - Network failures during communication
 * - Authentication/authorization issues with external services
 *
 * DESIGN PATTERN CONTEXT:
 * - Facade Pattern (Structural Pattern):
 *   Integration services may use a facade to simplify interactions
 *   with external systems, and this exception is thrown when those
 *   interactions fail.
 *
 * - Adapter Pattern (Structural Pattern):
 *   If different external systems are adapted into a common interface,
 *   this exception can represent failures within those adapters.
 *
 * - Chain of Responsibility (Behavioral Pattern):
 *   If integration is part of a processing pipeline, this exception
 *   can terminate the chain upon failure.
 *
 * GRASP PRINCIPLES:
 * - Low Coupling:
 *   Core modules do not depend on specific external system details.
 *
 * - Protected Variations:
 *   Changes in external systems (APIs/vendors) are isolated,
 *   and higher-level modules rely on this stable exception abstraction.
 *
 * SOLID PRINCIPLES:
 * - SRP (Single Responsibility Principle):
 *   This class only represents system integration failures.
 *
 * - OCP (Open/Closed Principle):
 *   Specific integration exceptions (e.g., PayrollSyncException)
 *   can extend or complement this without modifying it.
 *
 * - DIP (Dependency Inversion Principle):
 *   Business logic depends on abstractions, not on concrete external systems.
 *
 * EXCEPTION HANDLING STRATEGY:
 * - This is a checked exception (extends Exception)
 *   → Ensures integration failures are explicitly handled
 *   → Supports retry, fallback, or escalation mechanisms
 *
 * DESIGN DECISION:
 * - Works as a generic integration exception
 * - Can be used when a more specific exception is not applicable
 * - Complements specialized exceptions like PayrollSyncException
 *
 * REAL-WORLD ANALOGY:
 * - Similar to a system failing to communicate with an external service,
 *   such as a payment gateway or third-party provider.
 */
public class SystemIntegrationException extends Exception {

    /**
     * Constructor to initialize the exception with a meaningful message.
     *
     * @param msg Description of the integration failure
     *
     * WHY IMPORTANT:
     * - Helps identify the source of failure (network, API, auth, etc.)
     * - Improves debugging and system monitoring
     * - Useful for logging and alerting in distributed systems
     */
    public SystemIntegrationException(String msg) {
        super(msg);
    }
}