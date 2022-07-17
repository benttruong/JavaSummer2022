package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
  void createPhoneCallGetsCorrectCaller()  {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginDate = "3/15/2022";
    String beginTime = "10:39";
    String beginMeridiem = "am";
    String endDate = "03/2/2022";
    String endTime = "1:03";
    String endMeridiem = "pm";
    PhoneCall call = new PhoneCall(caller,callee, beginDate, beginTime, beginMeridiem, endTime, endDate, endMeridiem);
    assertThat(call.getCaller(), containsString("123-456-7890"));
  }

  /**
   * Test creating a PhoneCall with correct arguments
   * generating a new phone call with correct callee number
   */
  @Test
  void createPhoneCallGetsCorrectCallee() {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginTime = "10:39";
    String beginDate = "3/15/2022";
    String endTime = "1:03";
    String endDate = "03/2/2022";
    PhoneCall call = new PhoneCall(caller,callee, beginDate, beginTime, "AM", endDate, endTime, "PM");
    assertThat(call.getCallee(), containsString("113-456-7890"));
  }

  /**
   * Test creating a PhoneCall with correct arguments
   * generating a new phone call with correct begin time
   */
  @Test
  void createPhoneCallGetsCorrectBeginTime() {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginTime = "10:39";
    String beginDate = "3/15/2022";
    String endTime = "1:03";
    String endDate = "03/2/2022";
    PhoneCall call = new PhoneCall(caller,callee, beginDate, beginTime, "AM", endDate, endTime, "PM");
    assertThat(call.getBeginTimeString(), containsString("10:39 AM"));
  }


  /**
   * Test that adding phone call to phone bill
   * returns correct number of phone call
   */
  @Test
  void provide7CorrectArgumentsReturnsPhoneCallCreated(){
    String customer = "Brian Griffin";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call = new PhoneCall("123-456-7890", "133-456-7890", "3/15/2022", "10:39", "PM", "03/2/2022", "1:03", "PM");
    bill.addPhoneCall(call);
    assertEquals(bill.getPhoneCalls().size(), 1);
  }

  @Test
  void beginTimeFromPhoneCallUsingConstructorIsCorrectlyParsed(){
    String input = "12/05/2022 11:15 AM";
    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy H:mm a");
    Date time = null;
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
  void getEndTimeStringReturnsCorrectTime(){
    PhoneCall call = new PhoneCall("123", "456", "12/5/2022", "11:15", "AM", "12/16/2022", "12:30", "PM");
    assertThat(call.getEndTimeString(), containsString("12:30 PM"));
    System.out.println(call.getEndTimeString());
    System.out.println(call);
  }

}
