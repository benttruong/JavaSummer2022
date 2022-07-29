package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */
@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain(Project4.class);
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    void test2InvokeMainWithOnlyCustomerReturnsCorrectPhoneBill() {
        String customer = "Ben Truong";
        MainMethodResult result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, customer);
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, containsString(customer));
    }

    @Test
    void test3AddPhoneCall() {
        String customer = "Ben Truong";
        String caller = "123-456-7890";
        String callee = "111-222-3333";

        String beginDate = "7/28/2022";
        String beginTime = "12:30";
        String beginMeridiem = "PM";
        String endDate = "7/28/2022";
        String endTime = "12:45";
        String endMeridiem = "PM";

        MainMethodResult result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, customer);
        String out = result.getTextWrittenToStandardOut();
        int originalNumOfPhoneCalls = getNumberOfPhoneCallsFromBillString(out);

        invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, customer, caller, callee, beginDate, beginTime, beginMeridiem, endDate, endTime, endMeridiem);
        result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, customer);
        out = result.getTextWrittenToStandardOut();
        int numOfPhoneCallsAfterNewPhoneCallAdded = getNumberOfPhoneCallsFromBillString(out);

        assertEquals(originalNumOfPhoneCalls + 1, numOfPhoneCallsAfterNewPhoneCallAdded);
    }

    @Test
    void test4InvokeMainWithPrintCommandAndNoPhoneCallReturnsMissingArg() {
     String customer = "Ben Truong";
     MainMethodResult result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, "-print", customer);
     String err = result.getTextWrittenToStandardError();
     assertThat(err, containsString("Missing command line"));
    }

    @Test
    void test5InvokeMainWithPrintCommandAndCorrectPhoneCallReturnsPrettyPhoneCall() {
        String customer = "Ben Truong";
        String caller = "123-456-7890";
        String callee = "111-222-3333";

        String beginDate = "7/28/2022";
        String beginTime = "12:30";
        String beginMeridiem = "PM";
        String endDate = "7/28/2022";
        String endTime = "12:45";
        String endMeridiem = "PM";

        MainMethodResult result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, "-print", customer, caller, callee, beginDate, beginTime, beginMeridiem, endDate, endTime, endMeridiem);
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, containsString("The newly added phone call"));
    }


    private int getNumberOfPhoneCallsFromBillString(String out) {
        int num = -1;
        for (int i = 0; i < out.length(); ++i) {
            if (Character.isDigit(out.charAt(i))) {
                num = Integer.parseInt(String.valueOf(out.charAt(i)));
                break;
            }
        }
        return num;
    }
}

