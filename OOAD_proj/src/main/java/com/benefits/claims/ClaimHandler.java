package main.java.com.benefits.claims;

/*
 * Class: ClaimHandler (Abstract Base Class)
 * ------------------------------------------------------------
 * Responsibility:
 * This class defines the structure for all claim validation handlers.
 * It acts as the base class in the Chain of Responsibility pattern,
 * allowing multiple validation steps to be linked together dynamically.
 *
 * Design Pattern Used:
 * → Chain of Responsibility (Behavioural Pattern)
 *   - Each handler processes a part of the request (Claim).
 *   - After processing, it forwards the request to the next handler.
 *   - This creates a flexible validation pipeline.
 *
 * GRASP Principles:
 * → Low Coupling:
 *   - Each handler is independent and unaware of other handlers' logic.
 *   - Communication is only through the abstract interface.
 *
 * → Polymorphism:
 *   - Subclasses override the process() method to define specific behavior.
 *
 * SOLID Principles:
 * → Open/Closed Principle (OCP):
 *   - New validation steps (handlers) can be added without modifying existing ones.
 *
 * → Single Responsibility Principle (SRP):
 *   - This class only manages chaining logic, not validation rules.
 *
 * Flow:
 * 1. A handler performs its validation (in subclass).
 * 2. Calls super.process(claim)
 * 3. Request is forwarded to the next handler (if present)
 *
 * Usage:
 * Example chain:
 *   DocumentHandler → AmountHandler → PolicyHandler
 *
 * Each handler:
 *   - Validates its own concern
 *   - Passes control forward
 */
public abstract class ClaimHandler {

    // Reference to the next handler in the chain
    protected ClaimHandler next;

    /*
     * Method: setNext
     * ------------------------------------------------------------
     * Links the current handler to the next handler in the chain.
     * This allows dynamic construction of the validation pipeline.
     */
    public void setNext(ClaimHandler next) {
        this.next = next;
    }

    /*
     * Method: process
     * ------------------------------------------------------------
     * Default implementation of request forwarding.
     * Subclasses perform their validation and then call this method
     * to pass the request to the next handler.
     *
     * If no next handler exists, the chain ends here.
     */
    public void process(Claim claim) throws Exception {
        if (next != null) {
            next.process(claim);
        }
    }
}