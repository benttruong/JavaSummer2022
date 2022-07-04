package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.regex.*;

public class PhoneCall extends AbstractPhoneCall {

  private final String caller;
  private final String callee;
  private final String beginTime;
  private final String beginDate;
  private final String endTime;
  private final String endDate;


  // method to construct PhoneCall with information from command lines:
  @VisibleForTesting
  public PhoneCall(String caller, String callee, String beginTime, String beginDate, String endTime, String endDate) {
    this.caller = caller;
    this.callee = callee;
    this.beginTime = beginTime;
    this.beginDate = beginDate;
    this.endTime = endTime;
    this.endDate = endDate;
  }

  public PhoneCall() {
    this.caller = null;
    this.callee = null;
    this.beginTime = null;
    this.beginDate = null;
    this.endTime = null;
    this.endDate = null;
  }


  @Override
  public String getCaller() {
    return this.caller;
  }

  @Override
  public String getCallee() {
    return this.callee;
  }

  @Override
  public String getBeginTimeString() {
    return this.beginDate + " " + this.beginTime;
  }

  @Override
  public String getEndTimeString() {
    return this.endDate + " " + this.endTime;
  }


}
