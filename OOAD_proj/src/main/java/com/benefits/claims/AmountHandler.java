package main.java.com.benefits.claims;

import main.java.com.benefits.exceptions.InvalidClaimRequestException;

/*
 * Class: AmountHandler
 * ------------------------------------------------------------
 * Responsibility:
 * This class is responsible for validating the claim amount.
 * It ensures that the claim amount is within acceptable limits
 * before the request proceeds further in the processing pipeline.
 *
 * Design Pattern Used:
 * → Chain of Responsibility (Behavioural Pattern)
 *   - This handler is one link in the validation chain.
 *   - It performs its specific validation and then forwards
 *     the request to the next handler in the chain.
 *
 * GRASP Principles:
 * → Information Expert:
 *   - This class handles validation related to "amount",
 *     which is the information it specializes in.
 *
 * → Low Coupling:
 *   - This handler does not know about other handlers in detail.
 *   - It only forwards the request using 'super.process()'.
 *
 * SOLID Principles:
 * → Single Responsibility Principle (SRP):
 *   - This class is only responsible for validating claim amount.
 *
 * → Open/Closed Principle (OCP):
 *   - New validation handlers (e.g., fraud check, limit check)
 *     can be added without modifying this class.
 *
 * Exception Handling:
 * → Throws InvalidClaimRequestException when:
 *   - Claim amount is less than or equal to zero
 *   - This ensures invalid claims are caught early in the pipeline
 *
 * Flow:
 * 1. Log validation step
 * 2. Validate claim amount
 * 3. If invalid → throw exception
 * 4. If valid → pass to next handler in chain
 */
public class AmountHandler extends ClaimHandler {

    @Override
    public void process(Claim claim) throws Exception {

        // Logging step to make validation pipeline visible in execution
        System.out.println("[Validation] Checking amount...");

        // Business rule: Claim amount must be greater than zero
        if (claim.getAmount() <= 0) {
            throw new InvalidClaimRequestException("INVALID_CLAIM_REQUEST");
        }

        // Delegates control to the next handler in the chain
        // This ensures sequential validation (Chain of Responsibility)
        super.process(claim);
    }
}