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
  private final String beginDate;
  private final String beginTime;
  private final String beginMeridiem;

  private final String endDate;
  private final String endTime;
  private final String endMeridiem;

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
   * @param beginMeridiem
   *        The Meridiem Indicator of the beginning time (AM/PM)
   * @param endMeridiem
   *        The Meridiem Indicator of the ending time (AM/PM)
   */
  // method to construct PhoneCall with information from command lines:
  @VisibleForTesting
  public PhoneCall(String caller, String callee, String beginDate, String beginTime, String beginMeridiem, String endDate, String endTime, String endMeridiem) {
    this.caller = caller;
    this.callee = callee;
    this.beginDate = beginDate;
    this.beginTime = beginTime;
    this.beginMeridiem = beginMeridiem;
    this.endDate = endDate;
    this.endTime = endTime;
    this.endMeridiem = endMeridiem;
  }

  /**
   * Creates a new <code>PhoneCall</code> with no arguments
   */
  public PhoneCall() {
    this.caller = null;
    this.callee = null;
    this.beginDate = null;
    this.beginTime = null;
    this.beginMeridiem = null;
    this.endDate = null;
    this.endTime = null;
    this.endMeridiem = null;
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
    return this.beginDate + " " + this.beginTime + " " + this.beginMeridiem;
  }

  /**
   * Ending date and time of the phone call
   * @return <code>String</code> date and time
   */
  @Override
  public String getEndTimeString() {
    return this.endDate + " " + this.endTime + " " + this.endMeridiem;
  }


}
