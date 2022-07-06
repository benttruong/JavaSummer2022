package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project1.class, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }


    @Test
    void onlyCustomerNameReturnsMissingCommand(){
        MainMethodResult result = invokeMain("Brian Griffin");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    void only2ArgumentsReturnMissingCommand(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }
    @Test
    void only3ArgumentsReturnMissingCommand(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }
    @Test
    void only4ArgumentsReturnMissingCommand(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }
    @Test
    void only5ArgumentsReturnMissingCommand(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }
    @Test
    void only6ArgumentsReturnMissingCommand(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39", "03/2/2022");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }
    @Test
    void provide7CorrectArgumentsReturnsPhoneCallCreated(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39", "03/2/2022", "1:03");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone Call Created"));
    }
    @Test
    void provide8ArgumentsReturnTooManyArguments(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39", "03/2/2022", "1:03", "an extra argument");
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many arguments"));
    }

    @Test
    void wrongCallerNumberFormatReturnsInvalidPhoneNumberError(){
        MainMethodResult result = invokeMain("Brian Griffin", "1234567890", "133-456-7890", "3/15/2022", "10:39", "03/2/2022", "1:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Phone Number"));
    }
    @Test
    void wrongCalleeNumberFormatReturnsInvalidPhoneNumberError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "1334567890", "3/15/2022", "10:39", "03/2/2022", "1:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Phone Number"));
    }

    @Test
    void wrongBeginDateFormatReturnsInvalidDateError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3152022", "10:39", "03/2/2022", "1:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date"));
    }
    @Test
    void wrongEndDateFormatReturnsInvalidDateError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39", "0322022", "1:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date"));
    }
    @Test
    void wrongBeginTimeFormatReturnsInvalidTimeError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "1039", "03/2/2022", "1:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time"));
    }
    @Test
    void wrongEndTimeFormatReturnsInvalidTimeError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39", "03/2/2022", "103");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time"));
    }

    @Test
    void inputOptionalPrintCommandWithNoOtherArgumentReturnsPrintCommandRecognized(){
        MainMethodResult result = invokeMain("-print");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }
    @Test
    void inputOptionalREADMECommandReturnsREADMECommandRecognized(){
        MainMethodResult result = invokeMain("-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Project1 - Ben Truong"));
    }



}