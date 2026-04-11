package com.benefits.policy.validator;

import com.benefits.policy.model.BenefitPolicy;
import com.benefits.policy.exception.PolicyValidationException;

/**
 * Chain of Responsibility interface for policy validation.
 *
 * BEHAVIOURAL PATTERN - Chain of Responsibility:
 * When a policy is submitted, it must pass through a series
 * of validation checks — salary band, employment type,
 * waiting period, and coverage rules. Each check is an
 * independent link in a chain.
 *
 * If a check fails, it throws an exception and stops the chain.
 * If it passes, it hands the policy to the next validator.
 *
 * This means:
 *   - Each validator has one job (SRP)
 *   - New validators can be added without modifying existing ones (OCP)
 *   - Validators are completely decoupled from each other (Low Coupling)
 *
 * Without Chain of Responsibility, all validation logic would
 * live in one bloated method — hard to read, test, and extend.
 *
 * SOLID - Open/Closed Principle (OCP):
 * New validation rules are added by creating a new class that
 * implements this interface and inserting it into the chain.
 * Zero changes to existing validators.
 *
 * SOLID - Interface Segregation Principle (ISP):
 * Small, focused interface — two methods only.
 *
 * SOLID - Dependency Inversion Principle (DIP):
 * The facade depends on this abstraction, not on any concrete
 * validator class.
 *
 * GRASP - Low Coupling:
 * Each validator only knows about this interface and the next
 * validator in the chain. No direct dependencies between links.
 */
public interface PolicyValidator {

    /**
     * Sets the next validator in the chain.
     * If this validator passes, it delegates to the next one.
     *
     * @param next The next PolicyValidator in the chain.
     * @return The next validator (enables fluent chaining).
     */
    PolicyValidator setNext(PolicyValidator next);

    /**
     * Validates the given policy against this validator's rule.
     * If validation passes and a next validator exists, delegates
     * to the next link in the chain.
     *
     * @param policy The BenefitPolicy to validate.
     * @throws PolicyValidationException if this rule is violated.
     */
    void validate(BenefitPolicy policy) throws PolicyValidationException;
}
