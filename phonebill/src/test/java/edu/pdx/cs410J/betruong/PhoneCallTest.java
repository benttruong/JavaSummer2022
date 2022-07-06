package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class PhoneCallTest {

  /**
   * Test to check that <code>getBeginTime</code> method is currently
   * return null value
   */
  @Test
  void forProject1ItIsOkayIfGetBeginTimeReturnsNull() {
    PhoneCall call = new PhoneCall();
    assertThat(call.getBeginTime(), is(nullValue()));
  }

  // beginning of my own work

  /**
   * Test creating a PhoneCall with correct arguments
   * generating a new phone call with correct caller number
   */
  @Test
  void createPhoneCallGetsCorrectCaller()  {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginTime = "10:39";
    String beginDate = "3/15/2022";
    String endTime = "1:03";
    String endDate = "03/2/2022";
    PhoneCall call = new PhoneCall(caller,callee, beginTime, beginDate, endTime, endDate);
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
    PhoneCall call = new PhoneCall(caller,callee, beginTime, beginDate, endTime, endDate);
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
    PhoneCall call = new PhoneCall(caller,callee, beginTime, beginDate, endTime, endDate);
    assertThat(call.getBeginTimeString(), equalTo("3/15/2022 10:39"));
  }

  /**
   * Test creating a PhoneCall with correct arguments
   * generating a new phone call with correct end time
   */
  @Test
  void createPhoneCallGetsCorrectEndTime() {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginTime = "10:39";
    String beginDate = "3/15/2022";
    String endTime = "1:03";
    String endDate = "03/2/2022";
    PhoneCall call = new PhoneCall(caller,callee, beginTime, beginDate, endTime, endDate);
    assertThat(call.getEndTimeString(), equalTo("03/2/2022 1:03"));
  }

}
