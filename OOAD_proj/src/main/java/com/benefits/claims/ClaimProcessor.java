package main.java.com.benefits.claims;

import main.java.com.benefits.exceptions.ClaimProcessingException;
import main.java.com.benefits.workflow.*;
import main.java.com.benefits.payroll.*;

/*
 * Class: ClaimProcessor
 * ------------------------------------------------------------
 * Responsibility:
 * This class acts as the central orchestrator for processing claims.
 * It coordinates validation, workflow decisions, and payroll integration.
 *
 * Design Patterns Used:
 *
 * 1. Chain of Responsibility (Behavioural)
 *    - Used for validation pipeline:
 *      DocumentHandler → AmountHandler → PolicyHandler
 *    - Each handler validates a specific aspect and forwards the request.
 *
 * 2. State Pattern (Behavioural)
 *    - Used for workflow management:
 *      PENDING → APPROVED / REJECTED
 *    - ClaimContext manages state transitions dynamically.
 *
 * 3. Adapter Pattern (Structural)
 *    - PayrollAdapter is used to integrate payroll processing.
 *    - Decouples claim logic from payroll system implementation.
 *
 * GRASP Principles:
 *
 * → Controller:
 *   - This class controls the overall flow of claim processing.
 *
 * → Low Coupling:
 *   - Delegates responsibilities to handlers, workflow states, and adapter.
 *   - Does not tightly depend on internal implementations.
 *
 * → High Cohesion:
 *   - Focuses only on coordinating the claim process.
 *
 * SOLID Principles:
 *
 * → Single Responsibility Principle (SRP):
 *   - This class coordinates processing but does not implement validation,
 *     workflow logic, or payroll logic directly.
 *
 * → Open/Closed Principle (OCP):
 *   - New handlers or states can be added without modifying this class.
 *
 * → Dependency Inversion (DIP):
 *   - Depends on abstractions (PayrollService via adapter) rather than concrete implementations.
 *
 * Flow of Execution:
 *
 * 1. Initialize validation chain
 * 2. Execute validation (Chain of Responsibility)
 * 3. If validation passes → proceed to workflow
 * 4. Workflow uses State Pattern to determine approval/rejection
 * 5. If rejected → throw exception
 * 6. If approved → process payroll using Adapter
 * 7. Handle all exceptions centrally
 */
public class ClaimProcessor {

    /*
     * Method: processClaim
     * ------------------------------------------------------------
     * Processes a claim end-to-end:
     * → Validation
     * → Workflow decision
     * → Payroll integration
     *
     * @param claim - Claim object containing all necessary details
     * @throws ClaimProcessingException - wraps all internal failures
     */
    public void processClaim(Claim claim) throws ClaimProcessingException {
        try {
            // Step 1: Start processing
            System.out.println("[ClaimProcessor] Starting claim processing...");

            /*
             * Step 2: Build validation chain
             * Each handler validates one aspect of the claim
             */
            ClaimHandler doc = new DocumentHandler();
            ClaimHandler amt = new AmountHandler();
            ClaimHandler pol = new PolicyHandler();

            // Link handlers → Document → Amount → Policy
            doc.setNext(amt);
            amt.setNext(pol);

            /*
             * Step 3: Execute validation pipeline
             * If any validation fails, an exception is thrown
             */
            doc.process(claim);

            System.out.println("[ClaimProcessor] Validation successful");

            /*
             * Step 4: Workflow Engine (State Pattern)
             * Simulates manager approval process
             */
            System.out.println("[Workflow] Manager reviewing claim...");

            // Initial state: Pending
            ClaimContext context = new ClaimContext(new PendingState());
            context.process();

            /*
             * Step 5: Decision making
             * Based on policy validity → Approved or Rejected
             */
            if (claim.isPolicyValid()) {
                context.setState(new ApprovedState());
            } else {
                context.setState(new RejectedState());
            }

            // Execute state-specific behavior
            context.process();

            /*
             * Step 6: Handle rejection
             * If claim is rejected → stop processing
             */
            if (context.getStatus().equals("REJECTED")) {
                throw new Exception("APPROVAL_REJECTED");
            }

            /*
             * Step 7: Payroll Integration (Adapter Pattern)
             * Calculates deduction and updates payroll system
             */
            PayrollAdapter adapter = new PayrollAdapter(new PayrollServiceImpl());
            adapter.runPayroll(claim.getEmployeeId(), claim.getAmount());

            System.out.println("[ClaimProcessor] Claim completed successfully");

        } catch (Exception e) {
            /*
             * Step 8: Centralized exception handling
             * Wrap all exceptions into a unified exception type
             */
            throw new ClaimProcessingException("CLAIM_PROCESSING_ERROR: " + e.getMessage());
        }
    }
}