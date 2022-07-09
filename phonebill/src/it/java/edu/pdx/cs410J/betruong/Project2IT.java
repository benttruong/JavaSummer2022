package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project2.class, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    /**
     * Test that invoking main method with only 1 argument for customer name
     * issues an error
     */
    @Test
    void onlyCustomerNameReturnsMissingCommand(){
        MainMethodResult result = invokeMain("Brian Griffin");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    /**
     * Test that invoking main method with only 2 arguments
     * issues an error
     */
    @Test
    void only2ArgumentsReturnMissingCommand(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    /**
     * Test that invoking main method with only 3 arguments
     * issues an error
     */
    @Test
    void only3ArgumentsReturnMissingCommand(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    /**
     * Test that invoking main method with only 4 arguments
     * issues an error
     */
    @Test
    void only4ArgumentsReturnMissingCommand(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    /**
     * Test that invoking main method with only 5 arguments
     * issues an error
     */
    @Test
    void only5ArgumentsReturnMissingCommand(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    /**
     * Test that invoking main method with only 6 arguments
     * issues an error
     */
    @Test
    void only6ArgumentsReturnMissingCommand(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39", "03/2/2022");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    /**
     * Test that invoking main method with all correct 7 arguments
     * print "Phone Call Created" to standard out
     */
    @Test
    void provide7CorrectArgumentsReturnsPhoneCallCreated(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39", "03/2/2022", "1:03");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone Call Created"));
    }

    /**
     * Test that invoking main method with only 8 arguments
     * issues an error of too many arguments
     */
    @Test
    void provide8ArgumentsReturnTooManyArguments(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39", "03/2/2022", "1:03", "an extra argument");
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many arguments"));
    }

    /**
     * Test that invoking main method with all correct 7 arguments
     * but wrong caller phone number format issues an error
     */
    @Test
    void wrongCallerNumberFormatReturnsInvalidPhoneNumberError(){
        MainMethodResult result = invokeMain("Brian Griffin", "1234567890", "133-456-7890", "3/15/2022", "10:39", "03/2/2022", "1:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Phone Number"));
    }

    /**
     * Test that invoking main method with all correct 7 arguments
     * but wrong callee phone number format issues an error
     */
    @Test
    void wrongCalleeNumberFormatReturnsInvalidPhoneNumberError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "1334567890", "3/15/2022", "10:39", "03/2/2022", "1:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Phone Number"));
    }

    /**
     * Test that invoking main method with all correct 7 arguments
     * but wrong <code>beginDate</code> format issues an error
     */
    @Test
    void wrongBeginDateFormatReturnsInvalidDateError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3152022", "10:39", "03/2/2022", "1:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date"));
    }

    /**
     * Test that invoking main method with all correct 7 arguments
     * but wrong <code>endDate</code> format issues an error
     */
    @Test
    void wrongEndDateFormatReturnsInvalidDateError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39", "0322022", "1:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date"));
    }

    /**
     * Test that invoking main method with all correct 7 arguments
     * but wrong <code>beginTime</code> format issues an error
     */
    @Test
    void wrongBeginTimeFormatReturnsInvalidTimeError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "1039", "03/2/2022", "1:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time"));
    }

    /**
     * Test that invoking main method with all correct 7 arguments
     * but wrong <code>endTime</code> format issues an error
     */
    @Test
    void wrongEndTimeFormatReturnsInvalidTimeError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39", "03/2/2022", "103");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time"));
    }

    /**
     *
     * Test that invoking main method with optional <code>-print</code> and no argument
     * issues error for missing command line arguments
     */
    @Test
    void inputOptionalPrintCommandWithNoOtherArgumentReturnsPrintCommandRecognized(){
        MainMethodResult result = invokeMain("-print");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    /**
     * Test that invoking main method with optional <code>-print</code> and no argument
     * should print the README file to standard out which contains the first line "Project2 - Ben Truong"
     */
    @Test
    void inputOptionalREADMECommandReturnsREADMECommandRecognized(){
        MainMethodResult result = invokeMain("-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Project2 - Ben Truong"));
    }
    @Test
    void inputCorrectArgumentsWithPrintCommand(){
        MainMethodResult result = invokeMain("-print", "Test8", "123-456-7890", "234-567-8901", "03/03/2022", "12:00", "05/04/2022", "16:00");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone call from"));
    }

    @Test
    void unknownCommandLinePrintsUnknownCommandLine(){
        MainMethodResult result = invokeMain("-unknown", "Test8", "123-456-7890", "234-567-8901", "03/03/2022", "12:00", "05/04/2022", "16:00");
        assertThat(result.getTextWrittenToStandardError(), containsString("Unknown Command Line"));
    }



}