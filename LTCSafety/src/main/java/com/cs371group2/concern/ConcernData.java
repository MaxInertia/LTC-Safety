package com.cs371group2.concern;

import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;

/**
 * The concern data class is used to store the data submitted by a user when reporting a concern.
 * This data is not part of the concern class to separate the object that is sent using Cloud
 * Endpoints from the object that will be stored in the data store.
 *
 * Created on 2017-01-17.
 */
public final class ConcernData implements Validatable {

    private static final String CONCERN_NATURE_ERROR = "The nature of the concern must be specified when a concern is submitted";
    private static final String REPORTER_ERROR = "A reporter must be specified when a concern is submitted";
    private static final String LOCATION_ERROR = "A location must be specified when a concern is submitted";

    /**
     * The category that the concern falls under. Examples of these are falls, equipment failures,
     * environment, and aggressive resident behaviors. This must be non-null
     * when submitted by a user.
     */
    String concernNature;

    /**
     * A description of any actions taken by the reporter regarding the submitted concern. This may
     * be null if no actions have been taken.
     */
    String actionsTaken;

    /**
     * The name and contact information of the reporter. A reporter must be present for all
     * submitted concerns.
     */
    Reporter reporter;

    /**
     * The location the concern occurred at. A location must be present for all submitted concerns.
     */
    Location location;

    public String getConcernNature() {
        return concernNature;
    }

    public String getActionsTaken() {
        return actionsTaken;
    }

    public Reporter getReporter() {
        return reporter;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public ValidationResult validate() {
        if (concernNature == null) {
            return new ValidationResult(CONCERN_NATURE_ERROR);
        }
        if (reporter == null) {
            return new ValidationResult(REPORTER_ERROR);
        }
        if (location == null) {
            return new ValidationResult(LOCATION_ERROR);
        }
        ValidationResult reporterResult = reporter.validate();
        if (!reporterResult.isValid()) {
            return reporterResult;
        }
        ValidationResult locationResult = location.validate();
        if (!locationResult.isValid()) {
            return locationResult;
        }
        return new ValidationResult();
    }
}
