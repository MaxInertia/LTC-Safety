package c371g2.ltc_safety.a_new;

/**
 * Abstract class which is extended by classes that verify user input at concern creation.
 * @Invariants none
 * @HistoryProperties none
 */
abstract class Verifier {
    /**
     * Used to verify that user input is valid.
     * @param input User input.
     * @preconditions input is not null
     * @modifies nothing
     */
    boolean verify(String input) {
        if(input==null) return false;
        return input.length()>0;
    };
}
