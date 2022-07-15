package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.regex.*;

/**
 * This call represents a <code>PhoneCall</code>
 */
public class PhoneCall extends AbstractPhoneCall {


  private final String caller;
  private final String callee;
  private final String beginTime;
  private final String beginDate;
  private final String endTime;
  private final String endDate;

  /**
   * Creates a new <code>PhoneCall</code>
   *
   * @param caller
   *        The caller's phone number of this phone call
   * @param callee
   *        The phone number which receives this phone call
   * @param beginTime
   *        The time when this phone call starts
   * @param beginDate
   *        The date when this phone call starts
   * @param endTime
   *        The time when this phone ends
   * @param endDate
   *        The date when this phone call ends
   */
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

  /**
   * Creates a new <code>PhoneCall</code> with no arguments
   */
  public PhoneCall() {
    this.caller = null;
    this.callee = null;
    this.beginTime = null;
    this.beginDate = null;
    this.endTime = null;
    this.endDate = null;
  }

  /**
   * Get phone number of caller
   * @return <code>String</code> phone number
   */
  @Override
  public String getCaller() {
    return this.caller;
  }

  /**
   * Get phone number of receiver
   * @return <code>String</code> phone number
   */
  @Override
  public String getCallee() {
    return this.callee;
  }

  /**
   * Get begin date of the phone call
   * @return <code>String</code> begin date
   */
  public String getBeginDate(){
    return this.beginDate;
  }

  /**
   * Get end date of the phone call
   * @return <code>String</code> end date
   */
  public String getEndDate(){
    return this.endDate;
  }

  /**
   * Get begin time of the phone call
   * @return <code>String</code> begin time
   */
  public String getBeginTimeLiterals(){
    return this.beginTime;
  }


  /**
   * Get end time of the phone call
   * @return <code>String</code> end time
   */
  public String getEndTimeLiterals(){
    return this.endTime;
  }

  /**
   * Starting date and time of the phone call
   * @return <code>String</code> date and time
   */
  @Override
  public String getBeginTimeString() {
    return this.beginDate + " " + this.beginTime;
  }

  /**
   * Ending date and time of the phone call
   * @return <code>String</code> date and time
   */
  @Override
  public String getEndTimeString() {
    return this.endDate + " " + this.endTime;
  }


}
