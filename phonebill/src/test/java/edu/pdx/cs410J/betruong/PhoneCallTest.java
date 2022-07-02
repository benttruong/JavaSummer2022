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

  @Test
  void forProject1ItIsOkayIfGetBeginTimeReturnsNull() {
    PhoneCall call = new PhoneCall();
    assertThat(call.getBeginTime(), is(nullValue()));
  }

  // beginning of my own work

  @Test
  void createPhoneCallGetsCorrectCaller() {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginTime = "3/15/2022 10:39";
    String endTime = "03/2/2022 1:03";
    PhoneCall call = new PhoneCall(caller,callee, beginTime, endTime);
    assertThat(call.getCaller(), is(caller));
  }
  @Test
  void createPhoneCallGetsCorrectCallee() {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginTime = "3/15/2022 10:39";
    String endTime = "03/2/2022 1:03";
    PhoneCall call = new PhoneCall(caller,callee, beginTime, endTime);
    assertThat(call.getCallee(), is(callee));
  }
  @Test
  void createPhoneCallGetsCorrectBeginTime() {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginTime = "3/15/2022 10:39";
    String endTime = "03/2/2022 1:03";
    PhoneCall call = new PhoneCall(caller,callee, beginTime, endTime);
    assertThat(call.getBeginTimeString(), is(beginTime));
  }
  @Test
  void createPhoneCallGetsCorrectEndTime() {
    String caller = "123-456-7890";
    String callee = "113-456-7890";
    String beginTime = "3/15/2022 10:39";
    String endTime = "03/2/2022 1:03";
    PhoneCall call = new PhoneCall(caller,callee, beginTime, endTime);
    assertThat(call.getEndTimeString(), is(endTime));
  }




}
