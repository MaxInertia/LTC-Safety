package c371g2.ltc_safety.a_new;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the classes that extend the abstract Verifier class. The purpose of those classes
 * is to verify user input intended for concern submission.
 *
 * The current plan is to only ensure that the length of the value is greater than 0
 */
public class VerifierTests {

    private static Verifier phoneNumberVerifier = new PhoneNumberVerifier();
    private static Verifier emailAddressVerifier = new EmailAddressVerifier();
    private static Verifier nameVerifier = new NameVerifier();

    @Test
    public void test_PhoneNumberVerifier_validInputs() {
        String[] numbers = {"3063611234", "4033229111", "1231231234"}; //Each has 10 digits

        for(int i=0; i<numbers.length; i++) {
            assertTrue(phoneNumberVerifier.verify(numbers[i]));
        }
    }

    @Test
    public void test_EmailAddressVerifier_validInputs() {
        String[] numbers = {"valid.email@hotmail.ca", "abc@xyz.ca", "abc@xyz.com"}; // Three strings, the first two separated by '@', the second two separated by '.'

        for(int i=0; i<numbers.length; i++) {
            assertTrue(emailAddressVerifier.verify(numbers[i]));
        }
    }

    @Test
    public void test_NameVerifier_validInputs() {
        String[] numbers = {"Barack Obama", "Donald Trump", "Elvis"}; // Three strings, the first two separated by '@', the second two separated by '.'

        for(int i=0; i<numbers.length; i++) {
            assertTrue(nameVerifier.verify(numbers[i]));
        }
    }

}
