package main.java.com.benefits.claims;

import main.java.com.benefits.exceptions.InvalidClaimRequestException;

/*
 * Class: PolicyHandler
 * ------------------------------------------------------------
 * Responsibility:
 * This class represents the final step in the validation chain.
 * It checks policy-related aspects of the claim but DOES NOT
 * make approval/rejection decisions.
 *
 * Design Decision (VERY IMPORTANT):
 * → This handler does NOT reject the claim even if the policy is invalid.
 * → Policy validity is treated as a business decision, not a validation failure.
 * → The actual decision (APPROVED / REJECTED) is handled later
 *   using the State Pattern in the workflow layer.
 *
 * Design Pattern Used:
 * → Chain of Responsibility (Behavioural Pattern)
 *   - Acts as the last validation step in the pipeline.
 *   - Ensures policy data is checked before workflow begins.
 *
 * GRASP Principles:
 * → Information Expert:
 *   - Handles policy-related information from the Claim object.
 *
 * → Low Coupling:
 *   - Does not depend on workflow or decision logic.
 *   - Simply forwards the request after its responsibility.
 *
 * SOLID Principles:
 * → Single Responsibility Principle (SRP):
 *   - Responsible only for policy validation check (not decision-making).
 *
 * → Open/Closed Principle (OCP):
 *   - Can be extended with additional policy checks without modifying other handlers.
 *
 * Important Note:
 * → Unlike other handlers (Document, Amount), this class does NOT throw exceptions.
 * → This ensures:
 *      Validation Layer → only checks correctness of data
 *      Workflow Layer   → handles business decisions (approve/reject)
 *
 * Flow:
 * 1. Log validation step
 * 2. (Optional) Check policy conditions
 * 3. Forward request to next handler (if any)
 */
public class PolicyHandler extends ClaimHandler {

    @Override
    public void process(Claim claim) throws Exception {

        // Logging step to indicate policy validation stage
        System.out.println("[Validation] Checking policy...");

        /*
         * NOTE:
         * We intentionally do NOT throw an exception here even if:
         *   claim.isPolicyValid() == false
         *
         * Reason:
         * → This is not a validation failure but a business decision.
         * → The workflow (State Pattern) will handle approval/rejection.
         */

        // Pass control to next handler (if exists)
        super.process(claim);
    }
}