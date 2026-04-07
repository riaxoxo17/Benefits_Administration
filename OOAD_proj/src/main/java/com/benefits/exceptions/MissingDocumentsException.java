package main.java.com.benefits.exceptions;

/**
 * Custom Exception: MissingDocumentsException
 *
 * PURPOSE:
 * This exception is thrown when a claim request does not include
 * all the required supporting documents.
 *
 * It represents a specific type of validation failure focused on
 * document completeness.
 *
 * WHERE IT IS USED:
 * - During claim validation phase
 * - Before approval or processing begins
 * - In modules that verify document submission (e.g., DocumentValidator)
 *
 * EXAMPLES OF MISSING DOCUMENTS:
 * - Medical bills not attached
 * - Identity proof missing
 * - Prescription or reports not uploaded
 * - Required claim forms not submitted
 *
 * DESIGN PATTERN CONTEXT:
 * - Chain of Responsibility (Behavioral Pattern):
 *   A document validation handler can throw this exception to stop
 *   further processing if required documents are missing.
 *
 * GRASP PRINCIPLES:
 * - Information Expert:
 *   The validation component that knows which documents are required
 *   is responsible for throwing this exception.
 *
 * - Low Coupling:
 *   This exception can be used across different modules without
 *   tightly coupling them to document validation logic.
 *
 * SOLID PRINCIPLES:
 * - SRP (Single Responsibility Principle):
 *   This class only represents document-related validation failures.
 *
 * - OCP (Open/Closed Principle):
 *   Allows adding new specific validation exceptions (e.g., InvalidDocumentFormatException)
 *   without modifying existing code.
 *
 * EXCEPTION HANDLING STRATEGY:
 * - This is a checked exception (extends Exception)
 *   → Forces explicit handling of missing document scenarios
 *   → Ensures incomplete claims are not processed further
 *
 * DESIGN DECISION:
 * - Instead of using a generic validation exception, a dedicated exception
 *   improves clarity, debugging, and user feedback.
 *
 * REAL-WORLD ANALOGY:
 * - Similar to an insurance claim being rejected because required
 *   supporting papers were not submitted.
 */
public class MissingDocumentsException extends Exception {

    /**
     * Constructor to initialize the exception with a meaningful message.
     *
     * @param msg Description of which documents are missing
     *
     * WHY IMPORTANT:
     * - Helps identify exactly what is missing
     * - Improves user communication (e.g., "Upload medical bill")
     * - Useful for logging and debugging validation issues
     */
    public MissingDocumentsException(String msg) {
        super(msg);
    }
}