package com.cs371group2.concern;

/**
 * The concern status enum is used to determine the state of the concern within the system.
 * When concerns are first received they have a pending status. This status will then be changed to
 * send, responding, and resolved as the concern is acted up.
 *
 * Created on 2017-01-17.
 */
public enum ConcernStatusType {
    /**
     * When a concern is first received its status is set to pending.
     * This means that the concern has been submitted but has not been
     * acknowledged by an administrator yet.
     */
    PENDING,
    /**
     * When a concern has been received and opened by an administrator its
     * status will be changed to seen.
     */
    SEEN,
    /**
     * When a concern has been seen and will be acted upon within the next 24
     * hours its status will be changed to this.
     */
    RESPONDING24,
    /**
     * When a concern has been seen and will be acted upon within the next 48
     * hours its status will be changed to this. 48 hours is based on the
     * priority of the concern. This does NOT mean that the status will change
     * again to responding 24. It only means that the concern will be responded
     * to sometime in the next 48 hours.
     */
    RESPONDING48,
    /**
     * When a concern has been seen and will be acted upon within the next 72
     * hours its status will be changed to this. 72 hours is based on the
     * priority of the concern. This does NOT mean that the status will change
     * again to responding 24 or 48. It only means that the concern will be responded
     * to sometime in the next 72 hours.
     */
    RESPONDING72,
    /**
     * When a concern has been responded to its status will be changed to resolved
     * meaning the concern has been addressed.
     */
    RESOLVED,
    /**
     * If a reporter decides to retract their concern then the status will be changed to
     * retracted. Retracting a concern is only allowed if the status is pending or seen.
     */
    RETRACTED
}
