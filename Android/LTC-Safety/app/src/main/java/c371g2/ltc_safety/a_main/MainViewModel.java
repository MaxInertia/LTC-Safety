package c371g2.ltc_safety.a_main;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * This class acts as an interface between the app view in the main activity and the model. It loads
 * the previously submitted concerns from memory and stores them in concernList.
 *
 * This class is different from the two other View-Model classes:
 * - It does not contain a network operation
 * - All Fields and Methods not inherited by the ViewModelObserver are static
 * - The ViewModelObserver is used by the other two View-Model classes to update the concernList with
 * changes resulting from the network operations performed in each.
 *
 * Contains a static inner-class 'Test_Hook' to aid testing.
 *
 * @Invariants none
 * @HistoryProperties
 * - The concernList is the same ArrayList instance for the duration of the time the application is
 * running. However, this is not the case when performing tests. The Test_Hook contains a method
 * which can reinitialize it.
 */
class MainViewModel implements ViewModelObserver {

    /**
     * The key used to access the list of concerns in device memory.
     */
    private static final String CONCERN_SHARED_PREF_KEY = "concerns";

    /**
     * The list of all concerns stored on the device.
     * Retracted concerns persist.
     */
    private static ArrayList<ConcernWrapper> concernList;

    /**
     * Initialize model; loads the concerns from device memory and stores them in concernList.
     * @preconditions context is not null.
     * @modifies concernList; adds all concerns stored in device memory to the list.
     * @param context The base context of the activity that called this method (typically MainActivity)
     */
    static void initialize(@NonNull Context context) {
        concernList = loadConcerns(context);
        assert(concernList!=null);
    }

    /**
     * Retrieves the list of previously submitted concerns. The list is sorted with the most recent
     * concerns at lower indices.
     * @preconditions initialize() was called prior to this method.
     * @modifies concernList is sorted; more recent concerns at lower indices.
     * @return The sorted list of previously submitted concerns
     */
    static ArrayList<ConcernWrapper> getSortedConcernList(){
        assert(concernList != null);
        Collections.sort(concernList);
        return concernList;
    }

    /**
     * Loads all concerns stored in the device memory, returns them in an ArrayList.
     * @preconditions context is not null.
     * @modifies nothing
     * @param context The base context of the activity calling this method.
     * @return ArrayList of all concerns stored in device memory. Empty ArrayList if no concerns exist.
     */
    static ArrayList<ConcernWrapper> loadConcerns(@NonNull Context context) {
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
     * Saves a concern in device memory. If a concern with the same submission date already exists,
     * this concern overrides it. This occurs when a concern is retracted; the concern instance with
     * the "RETRACTED" status overwrites the previous instance.
     * @preconditions context and newConcern are not null.
     * @modifies concernList; adds newConcern to the list.
     * @param context The context of the activity calling this method (Typically NewConcernActivity
     *                or ConcernDetailActivity via the ViewModelObserver interface).
     * @param newConcern The concern to be saved.
     */
    static void saveConcern(@NonNull Context context, @NonNull ConcernWrapper newConcern) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONCERN_SHARED_PREF_KEY,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonConcern = gson.toJson(newConcern);
        sharedPreferences.edit().putString(newConcern.getOwnerToken().getToken(),jsonConcern).apply();
    }

    @Override
    public void newConcernSubmitted(@NonNull Context context,@NonNull ConcernWrapper newConcern) {
        saveConcern(context, newConcern);
        concernList.add(newConcern);
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

    // ---------------------------------------------------------------------------------------------

    /**
     * Any methods or fields can be added to this static subclass to aid testing. To use it:
     * 1) Add method headers to the interface 'MainViewModel_TestHook'.
     * 2) Implement those methods in this class (Test_Hook).
     * 3) Call on those methods from a text class via the Interface MainViewModel_TestHook.
     *
     * Examples:
     * MainViewModel_TestHook.instance.setAsOnlyConcern();
     * MainViewModel_TestHook.instance.otherMethodName();
     */
    static class Test_Hook implements MainViewModel_TestHook {
        @Override
        public void clearConcernList() {
            concernList = new ArrayList<>();
        }

        @Override
        public void addConcern(ConcernWrapper concern) throws NullPointerException {
            concernList.add(concern);
        }
    }
}
