package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.apache.tools.ant.Project;
import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
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
        assertThat(out, containsString("is empty"));

        invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, customer, caller, callee, beginDate, beginTime, beginMeridiem, endDate, endTime, endMeridiem);
        result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, customer);
        out = result.getTextWrittenToStandardOut();
        assertThat(out, containsString("1 phone call"));
    }

    @Test
    void test4InvokeMainWithPrintCommandAndNoPhoneCallReturnsMissingArg() {
     String customer = "Ben Truong";
     MainMethodResult result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, "-print", customer);
     String err = result.getTextWrittenToStandardError();
     assertThat(err, containsString("Command line is missing"));
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
    @Test
    void test6InvokeMainWithSearchCommand() {
        String customer = "Ben Truong";
        String caller = "123-456-7890";
        String callee = "111-222-3333";

        String beginDate = "7/28/2020";
        String beginTime = "12:30";
        String beginMeridiem = "PM";
        String endDate = "7/28/2020";
        String endTime = "12:45";
        String endMeridiem = "PM";

        // adding above phone call
        MainMethodResult result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, customer, caller, callee, beginDate, beginTime, beginMeridiem, endDate, endTime, endMeridiem);
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, containsString("A new phone call added"));

        // searching for phone calls from a month ago returns no phone call
        result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", customer, "6/01/2022", "1:00", "AM", "6/30/2022", "11:59", "PM");
        out = result.getTextWrittenToStandardOut();
        assertThat(out, containsString("There is no phone call"));

        // searching for phone calls within this month returns phone call with this begin time
        result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", customer, "1/01/2020", "1:00", "AM", "12/31/2020", "11:59", "PM");
        out = result.getTextWrittenToStandardOut();
        assertThat(out, containsString(beginTime));

    }
    @Test
    void test7InvokeMainWithHostAndPortButNoCustomerReturnRequiringAtLeastCustomer() {
        String customer = "Ben Truong";
        MainMethodResult result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT);
        String err = result.getTextWrittenToStandardError();
        assertThat(err, containsString("Program requires at least a customer's name"));
    }

    @Test
    void testingPrettyPrinting(){
        PhoneCall call = new PhoneCall("123-456-7890", "111-222-3333", "7/29/2022 2:00 AM", "7/29/2022 2:30 AM");
        PhoneBill bill = new PhoneBill("Ben");
        bill.addPhoneCall(call);

        // StringWriter sw = new StringWriter();
        PrettyPrinter pretty= new PrettyPrinter(System.out);
        pretty.billDump(bill);
        pretty.callDump(call);

    }

    @Test
    void testREADME(){
        MainMethodResult result = invokeMain(Project4.class, "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Project4 - Ben Truong"));
    }

    @Test
    void testingMissingCommandLinesAndMalformedData(){
        MainMethodResult result = invokeMain(Project4.class, "-host");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing host name"));

        result = invokeMain(Project4.class, "-port");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing port"));

        result = invokeMain(Project4.class, "-unknown");
        assertThat(result.getTextWrittenToStandardError(), containsString("Unknown Command Line"));

        result = invokeMain(Project4.class, "-host", "-port", "8080", "Ben", "111-222-3333", "222-333-4444", "7/29/2022", "3:40", "AM", "7/29/2022", "3:50", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing host name"));

        result = invokeMain(Project4.class, "-host", "localhost", "-port", "abcd", "Ben", "111-222-3333", "222-333-4444", "7/29/2022", "3:40", "AM", "7/29/2022", "3:50", "AM");
        assertThat(result.getTextWrittenToStandardError(), containsString("must be an integer"));
    }

}

