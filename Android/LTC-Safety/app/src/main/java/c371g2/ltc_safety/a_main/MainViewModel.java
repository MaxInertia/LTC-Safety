package c371g2.ltc_safety.a_main;

import java.util.ArrayList;

import c371g2.ltc_safety.local.Concern;

/**
 * This class acts as an interface between the app view in the main activity and the model. It loads
 * the previously submitted concerns from memory and stores them in concernList.
 * @Invariants none
 * @HistoryProperties none
 */
class MainViewModel implements ViewModelObserver {

    private static ArrayList<Concern> concernList;

    /**
     * Initialize model; loads the concerns from device memory and stores them in concernList.
     * @preconditions none
     * @modifies concernList; adds all concerns stored in device memory to the list.
     */
    static void initialize() {}

    /**
     * Retrieves the list of previously submitted concerns. The list is sorted with the most recent
     * concerns at lower indices.
     * @preconditions none
     * @modifies nothing
     * @return The sorted list of previously submitted concerns
     */
    static ArrayList<Concern> getSortedConcernList(){
        // Method used when generating ListView of concerns in MainActivity. Not required for concern submission.
        return null;
    }

    /**
     * Loads all concerns stored in the device memory, returns them in an ArrayList.
     * @preconditions none
     * @modifies nothing
     * @return ArrayList of all concerns stored in device memory. Empty ArrayList if no concerns exist.
     */
    static ArrayList<Concern> loadConcerns() {
        //TODO: Load concerns from device memory
        return null;
    }

    /**
     * Saves a newly submitted concern in device memory.
     * @preconditions newConcern is not null.
     * @modifies concernList; adds newConcern to the list.
     * @param newConcern The newly submitted concern.
     */
    static void saveNewConcern(Concern newConcern) {
        //TODO: Store new submitted concern in device memory
    }

    @Override
    public void newConcernSubmitted(Concern newConcern) {}
}
