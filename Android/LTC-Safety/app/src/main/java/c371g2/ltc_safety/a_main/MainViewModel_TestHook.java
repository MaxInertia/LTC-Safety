package c371g2.ltc_safety.a_main;

import c371g2.ltc_safety.local.ConcernWrapper;

/**
 *
 */
public interface MainViewModel_TestHook {
    MainViewModel_TestHook instance = new MainViewModel.Test_Hook();

    void setAsOnlyConcern(ConcernWrapper concern);
}
