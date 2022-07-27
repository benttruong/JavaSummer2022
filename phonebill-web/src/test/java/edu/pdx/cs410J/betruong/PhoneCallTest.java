package edu.pdx.cs410J.betruong;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class PhoneCallTest {

  // beginning of my own work

  /**
   * Test creating a PhoneCall with correct arguments
   * generating a new phone call with correct caller number
   */
  @Test
  void createPhoneCallGetsCorrectCaller() throws PhoneCall.PhoneCallException {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginDate = "3/15/2022";
    String beginTime = "10:39";
    String beginMeridiem = "AM";
    String endDate = "03/22/2022";
    String endTime = "1:03";
    String endMeridiem = "PM";
    PhoneCall call = new PhoneCall(caller,callee, beginDate, beginTime, beginMeridiem, endDate, endTime, endMeridiem);
    assertThat(call.getCaller(), containsString("123-456-7890"));
  }

  /**
   * Test creating a PhoneCall with correct arguments
   * generating a new phone call with correct callee number
   */
  @Test
  void createPhoneCallGetsCorrectCallee() throws PhoneCall.PhoneCallException {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginTime = "10:39";
    String beginDate = "3/15/2022";
    String endTime = "1:03";
    String endDate = "03/22/2022";
    PhoneCall call = new PhoneCall(caller,callee, beginDate, beginTime, "AM", endDate, endTime, "PM");
    assertThat(call.getCallee(), containsString("113-456-7890"));
  }

  /**
   * Test creating a PhoneCall with correct arguments
   * generating a new phone call with correct begin time
   */
  @Test
  void createPhoneCallGetsCorrectBeginTime() throws PhoneCall.PhoneCallException {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginDate = "3/15/2022";
    String beginTime = "10:39";
    String endDate = "03/22/2022";
    String endTime = "1:03";
    PhoneCall call = new PhoneCall(caller,callee, beginDate, beginTime, "AM", endDate, endTime, "PM");
    assertThat(call.getBeginTimeString(), containsString("10:39 AM"));
  }


  /**
   * Test that adding phone call to phone bill
   * returns correct number of phone call
   */
  @Test
  void provide7CorrectArgumentsReturnsPhoneCallCreated() throws PhoneCall.PhoneCallException {
    String customer = "Brian Griffin";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call = new PhoneCall("123-456-7890", "133-456-7890", "3/15/2022", "10:39", "PM", "03/22/2022", "1:03", "AM");
    bill.addPhoneCall(call);
    assertEquals(bill.getPhoneCalls().size(), 1);
  }

  @Test
  void beginTimeFromPhoneCallUsingConstructorIsCorrectlyParsed() throws PhoneCall.PhoneCallException {
    String input = "12/05/2022 11:15 AM";
    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy H:mm a");
    Date time;
    try {
      time = sdf.parse(input);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    PhoneCall call = new PhoneCall("123", "456", "12/5/2022", "11:15", "AM", "12/16/2022", "12:30", "PM");
    assertEquals(call.getBeginTime(), time);
  }

  /**
   * Test creating a PhoneCall with correct arguments
   * generating a new phone call with correct end time
   */
  @Test
  void getEndTimeStringReturnsCorrectTime() throws PhoneCall.PhoneCallException {
    PhoneCall call = new PhoneCall("123", "456", "12/5/2022", "11:15", "AM", "12/16/2022", "12:30", "PM");
    assertThat(call.getEndTimeString(), containsString("12:30 PM"));
    System.out.println(call.getEndTimeString());
    System.out.println(call);
  }
  /**
   * Tests <code>isValidDate</code> with different dates returns expected values
   */
  @Test
  void inputCorrectDate(){
    boolean testDate = PhoneCall.isValidDate("03/03/2022");
    boolean testDate2 = PhoneCall.isValidDate("05/04/2022");

    assertTrue(testDate);
    assertTrue(testDate2);
  }

  /**
   * Tests <code>isValidDate</code> with different dates formats returns expected values
   */
  @Test
  void isValidDateReturnsCorrectValues(){
    assertFalse(PhoneCall.isValidDate("13/01/1993"));
    assertFalse(PhoneCall.isValidDate("12/35/1993"));
    assertFalse(PhoneCall.isValidDate("14/3/51993"));
    assertTrue(PhoneCall.isValidDate("1/3/2022"));
    assertTrue(PhoneCall.isValidDate("07/08/2022"));
  }

  /**
   * Tests <code>isValidTime</code> with different time formats returns expected values
   */
  @Test
  void isValidTimeReturnsCorrecValues(){
    assertTrue(PhoneCall.isValidTime("12:59"));
    assertTrue(PhoneCall.isValidTime("1:09"));
    assertFalse(PhoneCall.isValidTime("25:1"));
    assertFalse(PhoneCall.isValidTime("1:60"));
  }

  @Test
  void comparingPhoneCalls() throws PhoneCall.PhoneCallException {
    String callee = "111-111-1111";
    String endDate = "1/1/2025";
    String endTime = "1:00";
    String endMeridiem = "AM";

    PhoneCall call1 = new PhoneCall("123-456-7890", callee, "7/18/2022", "7:00", "AM", endDate, endTime, endMeridiem);
    PhoneCall call2 = new PhoneCall("123-456-7890", callee, "7/18/2022", "8:00", "AM", endDate, endTime, endMeridiem);
    PhoneCall call3 = new PhoneCall("234-456-7890", callee, "7/18/2022", "8:00", "AM", endDate, endTime, endMeridiem);

    assertTrue(call1.compareTo(call2) < 0);
    assertTrue(call2.compareTo(call3) < 0);
    assertEquals(0, call3.compareTo(call3));
    assertTrue(call2.compareTo(call1) > 0);
    assertTrue(call3.compareTo(call2) > 0);
  }

  @Test
  void getDurationForPhoneCallReturnsCorrectDuration() throws PhoneCall.PhoneCallException {
    String caller = "111-111-1111";
    String beginDate = "7/19/2022";
    String beginTime = "10:30";
    String beginMeridiem = "AM";
    String endDate = "7/19/2022";
    String endTime = "11:00";
    String endMeridiem = "AM";
    PhoneCall call = new PhoneCall(caller, caller, beginDate, beginTime, beginMeridiem, endDate, endTime, endMeridiem);
    long duration = 30*60*1000;
    assertEquals(call.getDuration(), duration);
    assertEquals(call.getDurationString(), "30 minute(s) ");
  }



  @Test
  void testPhoneCallPrintingPrettyCallWithDuration() throws PhoneCall.PhoneCallException {
    String caller = "111-111-1111";
    String beginDate = "7/19/2022";
    String beginTime = "10:30";
    String beginMeridiem = "AM";
    String endDate = "7/20/2022";
    String endTime = "11:00";
    String endMeridiem = "AM";
    PhoneCall call = new PhoneCall(caller, caller, beginDate, beginTime, beginMeridiem, endDate, endTime, endMeridiem);
    assertThat(call.getPrettyCallString(), containsString("7/19/22"));
    assertThat(call.getPrettyCallString(), containsString("7/20/22"));
    System.out.println(call.getPrettyCallString());
  }

  @Test
  @Disabled
  void testingPhoneCallDateTimeParsing(){
    PhoneCall call = new PhoneCall("123-456-7890", "111-456-7890", "7/26/2022 7:15 PM", "7/26/2022 7:20 PM");
    assertThat(call.getCaller(), containsString("123-456-7890"));
    assertThat(call.getBeginTimeLiterals(), containsString("7/26/2022 7:15 PM"));
  }

}
