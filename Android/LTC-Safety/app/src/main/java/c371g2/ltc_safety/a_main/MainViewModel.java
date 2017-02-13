package c371g2.ltc_safety.a_main;

import java.util.ArrayList;

import c371g2.ltc_safety.local.Concern;

/**
 * This class acts as an interface between the app view in the main activity and the model. It loads
 * the previously submitted concerns from memory and stores them in concernList.
 * @Invariants none
 * @HistoryProperties none
 */
class MainViewModel {

    private static final ArrayList<Concern> concernList = new ArrayList<>();

    /**
     * Retrieves the list of previously submitted concerns. The list is sorted with the most recent
     * concerns at lower indices.
     * @return The sorted list of previously submitted concerns
     */
    static ArrayList<Concern> getSortedConcernList(){
        return null;
    }

    /**
     * Initializes the concerns arrayList and fills it with the concerns stored in the device memory
     */
    static void loadConcerns() {
        //TODO: Load concerns from device memory
    }

}
