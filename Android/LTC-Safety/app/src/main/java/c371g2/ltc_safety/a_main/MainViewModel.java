package c371g2.ltc_safety.a_main;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * This class acts as an interface between the app view in the main activity and the model. It loads
 * the previously submitted concerns from memory and stores them in concernList.
 * @Invariants none
 * @HistoryProperties none
 */
class MainViewModel implements ViewModelObserver {

    private static final String CONCERN_SHARED_PREF_KEY = "concerns";
    private static ArrayList<ConcernWrapper> concernList;

    /**
     * Initialize model; loads the concerns from device memory and stores them in concernList.
     * @preconditions none
     * @modifies concernList; adds all concerns stored in device memory to the list.
     */
    static void initialize(Context context) {
        concernList = loadConcerns(context);
        assert(concernList!=null);
    }

    /**
     * Retrieves the list of previously submitted concerns. The list is sorted with the most recent
     * concerns at lower indices.
     * @preconditions none
     * @modifies nothing
     * @return The sorted list of previously submitted concerns
     */
    static ArrayList<ConcernWrapper> getSortedConcernList(){
        // Method used when generating ListView of concerns in MainActivity. Not required for concern submission.
        Collections.sort(concernList);
        return concernList;
    }

    /**
     * Loads all concerns stored in the device memory, returns them in an ArrayList.
     * @preconditions none
     * @modifies nothing
     * @return ArrayList of all concerns stored in device memory. Empty ArrayList if no concerns exist.
     */
    static ArrayList<ConcernWrapper> loadConcerns(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONCERN_SHARED_PREF_KEY,Context.MODE_PRIVATE);
        Collection all_json_concerns = sharedPreferences.getAll().values();

        Gson gson = new Gson();
        ArrayList<ConcernWrapper> concerns = new ArrayList<>();
        for(Object json_concern: all_json_concerns) {
            ConcernWrapper loadedConcern = gson.fromJson((String) json_concern, ConcernWrapper.class);
            concerns.add(loadedConcern);
        }

        return concerns;
    }

    /**
     * Saves a newly submitted concern in device memory.
     * @preconditions newConcern is not null.
     * @modifies concernList; adds newConcern to the list.
     * @param newConcern The newly submitted concern.
     */
    static void saveConcern(Context context, ConcernWrapper newConcern) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONCERN_SHARED_PREF_KEY,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonConcern = gson.toJson(newConcern);
        sharedPreferences.edit().putString(newConcern.getOwnerToken().getToken(),jsonConcern).apply();
    }

    @Override
    public void newConcernSubmitted(Context context, ConcernWrapper newConcern) {
        saveConcern(context, newConcern);
    }

    @Override
    public ConcernWrapper getConcernAtIndex(int index) {
        return concernList.get(index);
    }

    @Override
    public void concernRetracted(Context context, ConcernWrapper concern, int index) {
        ConcernWrapper oldConcern = concernList.get(index);
        assert(oldConcern.getReporterName().equals(concern.getReporterName()));
        assert(oldConcern.getReporterEmail().equals(concern.getReporterEmail()));
        assert(oldConcern.getReporterPhone().equals(concern.getReporterPhone()));

        assert(oldConcern.getConcernType().equals(concern.getConcernType()));
        assert(oldConcern.getActionsTaken().equals(concern.getActionsTaken()));
        assert(oldConcern.getReporterName().equals(concern.getReporterName()));

        assert(oldConcern.getOwnerToken().equals(concern.getOwnerToken()));
        concernList.set(index,concern);
        saveConcern(context, concern);
    }


    /**
     * Any methods or fields can be added to this class to aid testing. To use it:
     * 1) Add method headers to the interface 'MainViewModel_TestHook'
     * 2) Implement those methods in this class (Test_Hook)
     * 3) Call on those methods via the Interface MainViewModel_TestHook.
     *
     * Ex: MainViewModel_TestHook.instance.setAsOnlyConcern();
     *
     * Ex2: MainViewModel_TestHook.instance.methodName();
     */
    static class Test_Hook implements MainViewModel_TestHook {
        @Override
        public void setAsOnlyConcern(ConcernWrapper concern) {
            concernList = new ArrayList<>();
            concernList.add(concern);
        }
    }
}
