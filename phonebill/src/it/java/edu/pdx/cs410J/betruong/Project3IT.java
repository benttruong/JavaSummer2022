package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
class Project3IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project3.class, args);
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
     * Test that invoking main method with only 8 arguments
     * issues an error of too many arguments
     */
    @Test
    void provide8ArgumentsReturnTooManyArguments(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39", "PM", "03/2/2022", "1:03", "AM", "an extra argument");
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many arguments"));
    }

    /**
     * Test that invoking main method with all correct 7 arguments
     * but wrong caller phone number format issues an error
     */
    @Test
    void wrongCallerNumberFormatReturnsInvalidPhoneNumberError(){
        MainMethodResult result = invokeMain("Brian Griffin", "1234567890", "133-456-7890", "3/15/2022", "10:39", "AM", "03/2/2022", "1:03", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Phone Number"));
    }

    /**
     * Test that invoking main method with all correct 7 arguments
     * but wrong callee phone number format issues an error
     */
    @Test
    void wrongCalleeNumberFormatReturnsInvalidPhoneNumberError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "1334567890", "3/15/2022", "10:39", "AM", "03/2/2022", "1:03", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Phone Number"));
    }

    /**
     * Test that invoking main method with all correct 7 arguments
     * but wrong <code>beginDate</code> format issues an error
     */
    @Test
    void wrongBeginDateFormatReturnsInvalidDateError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3152022", "10:39", "AM", "03/2/2022", "1:03", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date"));
    }

    /**
     * Test that invoking main method with all correct 7 arguments
     * but wrong <code>endDate</code> format issues an error
     */
    @Test
    void wrongEndDateFormatReturnsInvalidDateError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39", "AM", "0322022", "1:03", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Date"));
    }

    /**
     * Test that invoking main method with all correct 7 arguments
     * but wrong <code>beginTime</code> format issues an error
     */
    @Test
    void wrongBeginTimeFormatReturnsInvalidTimeError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "1039", "AM", "03/2/2022", "1:03", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time"));
    }

    /**
     * Test that invoking main method with all correct 7 arguments
     * but wrong <code>endTime</code> format issues an error
     */
    @Test
    void wrongEndTimeFormatReturnsInvalidTimeError(){
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "133-456-7890", "3/15/2022", "10:39", "AM", "03/2/2022", "103", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid Time"));
    }

    /**
     *
     * Test that invoking main method with optional <code>-print</code> and no argument
     * issues error for missing command line arguments
     */
    @Test
    void inputOptionalPrintCommandWithNoOtherArgumentReturnsMissingCommandLine(){
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
    /**
     * Test that invoking main method with optional <code>-print</code> and correct arguments
     * should print to standard out phone call details
     */
    @Test
    void inputCorrectArgumentsWithPrintCommand(){
        MainMethodResult result = invokeMain("-print", "Test8", "123-456-7890", "234-567-8901", "03/03/2022", "11:00", "PM", "05/04/2022", "12:00", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone call from"));
    }

    /**
     * Test that invoking main method with unknown optional command <code>-unknown</code>
     * should print to standard error Unknown Command Line
     */
    @Test
    void unknownCommandLinePrintsUnknownCommandLine(){
        MainMethodResult result = invokeMain("-unknown", "Test8", "123-456-7890", "234-567-8901", "03/03/2022", "12:00", "05/04/2022", "16:00");
        assertThat(result.getTextWrittenToStandardError(), containsString("Unknown Command Line"));
    }

    /**
     * Test <code>PhoneBill</code> constructor with customer name <code>String</code>
     * initializes correct customer's name
     */
    @Test
    void createCorrectPhoneCallReturnsCorrectCustomer(){
        String name = "Pat Gel";
        PhoneBill testBill = new PhoneBill(name);
        assertThat(testBill.getCustomer(), containsString(name));
    }

    /**
     * Test that <code>addPhoneCall(String customer)</code> correctly add phone call to phone bill
     * and increment the number of phone calls in list
     */
    @Test
    void addNewPhoneCallToPhoneBillReturnsCorrectNumberOfPhoneCall(){
        PhoneCall testCall = new PhoneCall("123-456-7890", "333-456-7890", "12/15/2022", "10:30", "AM", "12/15/2022", "10:35", "PM");
        PhoneBill testBill = new PhoneBill("Customer");
        testBill.addPhoneCall(testCall);
        assertEquals(testBill.getPhoneCalls().size(), 1);
        PhoneCall testCall2 = new PhoneCall();
        testBill.addPhoneCall(testCall2);
        assertEquals(testBill.getPhoneCalls().size(), 2);
    }

    /**
     * Test that invoking main method with optional <code>-print</code> and correct arguments
     * should print to standard out phone call details with correct caller number
     */
    @Test
    void invokeCorrectCommandLineAddsNewPhoneCallToNewPhoneBill(){
        String name = "Pat Gel";
        String caller = "123-456-7890";
        MainMethodResult result = invokeMain("-print", name, caller, "234-567-8901", "03/03/2022", "10:00", "PM", "05/04/2022", "11:00", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString(caller));
    }

    /**
     * Test that invoking main method with optional <code>-textFile</code> and no argument
     * should print to standard error command is missing file
     */
    @Test
    void textFileCommandWithoutPathReturnsMissingFileName(){
        MainMethodResult result = invokeMain("-textFile");
        assertThat(result.getTextWrittenToStandardError(), containsString("Command is missing file"));
    }

    /**
     * Test that invoking main method with optional <code>-textFile</code>
     * and a file with parent file path correctly creates new file
     */
    @Test
    @Disabled
    void textFileCommandWithFilePathCreatesNewFile(){
        MainMethodResult result = invokeMain("-textFile", "TestDir/newfile.test", "Brian Nguyen","123-456-7890", "234-567-8901", "03/03/2022", "12:00", "PM", "05/04/2022", "4:00", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("New file written"));
    }


    /**
     * Test that invoking main method with optional <code>-textFile</code>
     * and a file with no parent file path correctly creates new file
     */
    @Test
    @Disabled
    void correctCommandLinesWithFileAndNoPathCreateFileAtCurrentDirectory(){
        MainMethodResult result = invokeMain("-textFile", "Ben.test", "Haylie Nguyen","123-456-7890", "234-567-8901", "03/03/2022", "12:00", "PM", "05/04/2022", "16:00", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("New file written"));
    }


    /**
     * Test that invoking main method with correct optional <code>-textFile</code>
     * and <code>-README</code> command correctly print README and issue no error
     */
    @Test
    void inputOptionalREADMECommandAfterTextFileReturnsREADMEPrinted(){
        MainMethodResult result = invokeMain("-textFile", "Ben.doc", "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Project2 - Ben Truong"));
    }

    /**
     * Tests with different file names returns expected values
     */
    @Test
    void isValidFilePathReturnsCorrectValues(){
        assertTrue(Project3.isValidFilePath("Ben.doc"));
        assertTrue(Project3.isValidFilePath("Temp/Ben.doc"));
        assertTrue(Project3.isValidFilePath("Temp_1/Temp2/Ben.doc"));
        assertFalse(Project3.isValidFilePath("/Ben.doc"));
        assertFalse(Project3.isValidFilePath("//Ben.doc"));
        assertFalse(Project3.isValidFilePath("/Temp/Ben.doc"));
        assertFalse(Project3.isValidFilePath("/Temp//Ben.doc"));
    }

    /**
     * Test that invoking main method with optional <code>-textFile</code>
     * with <code>-print</code> command recognizes the command and issues error of missing command lines
     *
     */
    @Test
    void inputOptionalPrintCommandAfterTextFileReturnsMissingCommandLines(){
        MainMethodResult result = invokeMain("-textFile", "Ben.doc", "-print");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line"));
    }

    /**
     * Test that invoking main method with correct optional <code>-textFile</code>
     * but malformed data for phone call would issue standard error and terminates program
     */
    @Test
    void correctCommandLinesWithMalformedDataPrintsError(){
        MainMethodResult result = invokeMain("-textFile", "Ben.test", "Haylie Nguyen","123-456-7890", "234-567-8901", "03/03/2022", "12:00", "PM", "05/04/2022", "400", "PM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Program terminated"));
    }

    @Test
    void prettyCommandRecognizedFromCommandLine() {
        MainMethodResult result = invokeMain("-pretty", "Haylie Nguyen", "123-456-7890", "234-567-8901", "03/03/2022", "12:00", "05/04/2022", "1600");
        assertThat(result.getTextWrittenToStandardError(), containsString("Pretty Command is missing output destination"));
    }

}