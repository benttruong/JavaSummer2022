package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static edu.pdx.cs410J.betruong.PhoneCall.UnrecognizedPhoneNumberException;
import static edu.pdx.cs410J.betruong.PhoneCall.UnrecognizedDateFormatException;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class PhoneCallTest {

  @Test
  void forProject1ItIsOkayIfGetBeginTimeReturnsNull() {
    PhoneCall call = new PhoneCall();
    assertThat(call.getBeginTime(), is(nullValue()));
  }

  // beginning of my own work

  @Test
  void createPhoneCallGetsCorrectCaller() throws PhoneCall.UnrecognizedPhoneNumberException, UnrecognizedDateFormatException, PhoneCall.UnrecognizedTimeFormatException {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginTime = "10:39";
    String beginDate = "3/15/2022";
    String endTime = "1:03";
    String endDate = "03/2/2022";
    PhoneCall call = new PhoneCall(caller,callee, beginTime, beginDate, endTime, endDate);
    assertThat(call.getCaller(), is(caller));
  }
  @Test
  void createPhoneCallGetsCorrectCallee() throws PhoneCall.UnrecognizedPhoneNumberException, UnrecognizedDateFormatException, PhoneCall.UnrecognizedTimeFormatException {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginTime = "10:39";
    String beginDate = "3/15/2022";
    String endTime = "1:03";
    String endDate = "03/2/2022";
    PhoneCall call = new PhoneCall(caller,callee, beginTime, beginDate, endTime, endDate);
    assertThat(call.getCallee(), is(callee));
  }
  @Test
  void createPhoneCallGetsCorrectBeginTime() throws PhoneCall.UnrecognizedPhoneNumberException, UnrecognizedDateFormatException, PhoneCall.UnrecognizedTimeFormatException {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginTime = "10:39";
    String beginDate = "3/15/2022";
    String endTime = "1:03";
    String endDate = "03/2/2022";
    PhoneCall call = new PhoneCall(caller,callee, beginTime, beginDate, endTime, endDate);
    assertThat(call.getBeginTimeString(), equalTo(beginTime + " " + beginDate));
  }
  @Test
  void createPhoneCallGetsCorrectEndTime() throws PhoneCall.UnrecognizedPhoneNumberException, UnrecognizedDateFormatException, PhoneCall.UnrecognizedTimeFormatException {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginTime = "10:39";
    String beginDate = "3/15/2022";
    String endTime = "1:03";
    String endDate = "03/2/2022";
    PhoneCall call = new PhoneCall(caller,callee, beginTime, beginDate, endTime, endDate);
    assertThat(call.getEndTimeString(), equalTo(endTime + " " + endDate));
  }

  @Test
  void inputWrongPhoneNumberFormatThrowUnrecognizedPhoneNumberException(){
    String caller = "fdsf";
    assertThrows(PhoneCall.UnrecognizedPhoneNumberException.class, () -> PhoneCall.validatePhoneNumber(caller));
  }
  @Test
  void inputCorrectPhoneNumberFormatReturnsSamePhoneNumber() throws UnrecognizedPhoneNumberException {
    String caller = "123-456-7890";
    assertThat(caller, equalTo(PhoneCall.validatePhoneNumber(caller)));
  }

  @Test
  void inputWrongDateFormatThrowUnrecognizedDateFormatException(){
    String date = "13/31/2023";
    assertThrows(PhoneCall.UnrecognizedDateFormatException.class, () -> PhoneCall.validateDate(date));
  }

  @Test
  void inputCorrectDateFormatReturnsSameDate() throws UnrecognizedDateFormatException {
    String date = "03/20/1993";
    assertThat(date, equalTo(PhoneCall.validateDate(date)));
  }

  @Test
  void inputWrongTimeFormatThrowUnrecognizedTimeFormatException(){
    String time = "25:61";
    assertThrows(PhoneCall.UnrecognizedTimeFormatException.class, () -> PhoneCall.validateTime(time));
  }

  @Test
  void inputCorrectTimeFormatReturnsSameTime() throws PhoneCall.UnrecognizedTimeFormatException {
    String time = "10:59";
    assertThat(time, equalTo(PhoneCall.validateTime(time)));
  }



}
