package com.cs371group2.concern;

import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The concern data class is used to store the data submitted by a user when reporting a concern.
 * This data is not part of the concern class to separate the object that is sent using Cloud
 * Endpoints from the object that will be stored in the data store.
 *
 * History properties: Instances of this class are immutable from the time they are created.
 *
 * Invariance properties: This class makes no assumptions about the information it is given, though to be considered
 * "valid" in our system, it is required that all fields and sub-fields are non-null.
 *
 * Created on 2017-01-17.
 */
public final class ConcernData implements Validatable {

    private static final Logger logger = Logger.getLogger(ConcernData.class.getName());

    private static final String CONCERN_NATURE_ERROR = "The nature of the concern must be specified when a concern is submitted";
    private static final String REPORTER_ERROR = "A reporter must be specified when a concern is submitted";
    private static final String LOCATION_ERROR = "A location must be specified when a concern is submitted";

    /**
     * The category that the concern falls under. Examples of these are falls, equipment failures,
     * environment, and aggressive resident behaviors. This must be non-null
     * when submitted by a user.
     */
    private String concernNature;

    /**
     * A description of any actions taken by the reporter regarding the submitted concern. This may
     * be null if no actions have been taken.
     */
    private String actionsTaken;

    /**
     * A description of the concern. This may be null if the user does not wish to provide a description.
     */
    private String description;


    /**
     * The name and contact information of the reporter. A reporter must be present for all
     * submitted concerns.
     */
    private Reporter reporter;

    /**
     * The location the concern occurred at. A location must be present for all submitted concerns.
     */
    private Location location;

    /**
     * TestHook_MutableConcernData is a test hook to make ConcernData testable without exposing its
     * members. An instance of TestHook_MutableConcernData can be used to construct new concern data
     * instances and set values for testing purposes.
     */
    public static class TestHook_MutableConcernData {

        /**
         * The concern data being built.
         */
        private ConcernData data = new ConcernData();

        /**
         * The mutable reporter to allow modification after being passed to the constructor.
         */
        private Reporter.TestHook_MutableReporter mutableReporter;

        /**
         * The mutable location to allow modification after being passed to the constructor.
         */
        private Location.TestHook_MutableLocation mutableLocation;

        public Reporter.TestHook_MutableReporter getMutableReporter() {
            return mutableReporter;
        }

        public Location.TestHook_MutableLocation getMutableLocation() {
            return mutableLocation;
        }

        /**
         * Constructs a new mutable concern data.
         *
         * @param concernNature The category the concern falls under.
         * @param actionsTaken Descriptions of any actions taken by the reporter.
         * @param description The description of the concern.
         * @param reporter The reporter of the concern.
         * @param location The location the concern occurred at.
         */
        public TestHook_MutableConcernData(String concernNature, String actionsTaken, String description,
                Reporter.TestHook_MutableReporter reporter,
                Location.TestHook_MutableLocation location) {
            setConcernNature(concernNature);
            setActionsTaken(actionsTaken);
            setDescription(description);
            this.mutableReporter = reporter;
            this.mutableLocation = location;
        }

        public void setConcernNature(String concernNature) {
            this.data.concernNature = concernNature;
        }

        public void setActionsTaken(String actionsTaken) {
            this.data.actionsTaken = actionsTaken;
        }

        public void setDescription(String description) {
            this.data.description = description;
        }


        /**
         * Converts the mutable concern data to a concern data instance to be used for testing. Once
         * built the concern data is immutable regardless of whether the mutable reporter it was
         * created with is modified.
         *
         * @return The immutable concern data reference containing the mutable concern data's data.
         */
        public ConcernData build() {
            ConcernData immutable = new ConcernData();
            immutable.concernNature = this.data.concernNature;
            immutable.actionsTaken = this.data.actionsTaken;
            immutable.description = this.data.description;
            immutable.location = this.mutableLocation.build();
            immutable.reporter = this.mutableReporter.build();
            return immutable;
        }
    }

    public String getConcernNature() {
        return concernNature;
    }

    public String getActionsTaken() {
        return actionsTaken;
    }

    public String getDescription() {
        return description;
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
            logger.log(Level.WARNING, "Validating unsuccessful: concernNature is null");
            return new ValidationResult(CONCERN_NATURE_ERROR);
        }
        if (reporter == null) {
            logger.log(Level.WARNING, "Validating unsuccessful: reporter is null");
            return new ValidationResult(REPORTER_ERROR);
        }
        if (location == null) {
            logger.log(Level.WARNING, "Validating unsuccessful: location is null");
            return new ValidationResult(LOCATION_ERROR);
        }
        ValidationResult reporterResult = reporter.validate();
        if (!reporterResult.isValid()) {
            logger.log(Level.WARNING, "Validating unsuccessful: reporter is not valid");
            return reporterResult;
        }
        ValidationResult locationResult = location.validate();
        if (!locationResult.isValid()) {
            logger.log(Level.WARNING, "Validating unsuccessful: location is not valid");
            return locationResult;
        }
        logger.log(Level.FINER,
                "Validation of Concern \"" + this.concernNature + "\" was successful.");
        return new ValidationResult();
    }

    @Override
    public String toString() {
        String returnString;
        if (this.getReporter() == null && this.getLocation() == null) {
            returnString = "Concern Nature: " + this.getConcernNature()
                    + "\nDescription: " + this.getDescription()
                    + "\nActions Taken: " + this.getActionsTaken()
                    + "\n" + "NO REPORTER\nNO LOCATION\n";
        } else if (this.getReporter() == null) {
            returnString = "Concern Nature: " + this.getConcernNature()
                    + "\nDescription: " + this.getDescription()
                    + "\nActions Taken: " + this.getActionsTaken()
                    + "\n" + "NO REPORTER" + this.getLocation().toString() + "\n";
        } else if (this.getLocation() == null) {
            returnString = "Concern Nature: " + this.getConcernNature()
                    + "\nDescription: " + this.getDescription()
                    + "\nActions Taken: " + this.getActionsTaken()
                    + "\n" + this.getReporter().toString() + "NO LOCATION" + "\n";
        } else {
            returnString = "Concern Nature: " + this.getConcernNature()
                    + "\nDescription: " + this.getDescription()
                    + "\nActions Taken: " + this.getActionsTaken()
                    + "\n" + this.getReporter().toString() + this.getLocation().toString() + "\n";
        }
        return returnString;
    }
}
