package c371g2.ltc_safety.a_main;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;

import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * This class handles saving and loading Concerns to and from the device memory.
 */
public class DeviceStorage {

    /**
     * The key used to access the list of concerns in device memory.
     */
    private static final String CONCERN_SHARED_PREF_KEY = "concerns";

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

}
