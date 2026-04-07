package main.java.com.benefits.claims;

import main.java.com.benefits.exceptions.MissingDocumentsException;

/*
 * Class: DocumentHandler
 * ------------------------------------------------------------
 * Responsibility:
 * This class validates whether the claim has all required supporting documents.
 * It ensures that incomplete claims are rejected early in the validation pipeline.
 *
 * Design Pattern Used:
 * → Chain of Responsibility (Behavioural Pattern)
 *   - This handler is the first step in the validation chain.
 *   - It checks document-related conditions before forwarding the request.
 *
 * GRASP Principles:
 * → Information Expert:
 *   - Responsible for validating document-related data from the Claim object.
 *
 * → Low Coupling:
 *   - Does not depend on other handlers directly.
 *   - Uses abstract chaining mechanism via 'super.process()'.
 *
 * SOLID Principles:
 * → Single Responsibility Principle (SRP):
 *   - Only responsible for document validation.
 *
 * → Open/Closed Principle (OCP):
 *   - New validation handlers can be added without modifying this class.
 *
 * Exception Handling:
 * → Throws MissingDocumentsException when:
 *   - Claim does not contain required supporting documents
 *
 * Flow:
 * 1. Log validation step
 * 2. Check if documents are present
 * 3. If missing → throw exception
 * 4. If valid → pass to next handler in chain
 */
public class DocumentHandler extends ClaimHandler {

    @Override
    public void process(Claim claim) throws Exception {

        // Logging step to indicate validation stage in execution flow
        System.out.println("[Validation] Checking documents...");

        // Business rule: Claim must include supporting documents
        if (!claim.hasDocuments()) {
            throw new MissingDocumentsException("MISSING_SUPPORTING_DOCUMENTS");
        }

        // Delegate to next handler in the chain (if any)
        // Ensures sequential validation in Chain of Responsibility
        super.process(claim);
    }
}