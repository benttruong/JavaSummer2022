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
  public PhoneCall(String caller, String callee, String beginTime, String beginDate, String endTime, String endDate) throws UnrecognizedPhoneNumberException, UnrecognizedDateFormatException, UnrecognizedTimeFormatException {
    this.caller = validatePhoneNumber(caller);
    this.callee = validatePhoneNumber(callee);
    this.beginTime = validateTime(beginTime);
    this.beginDate = validateDate(beginDate);
    this.endTime = validateTime(endTime);
    this.endDate = validateDate(endDate);
  }

  public PhoneCall() {
    caller = null;
    callee = null;
    beginTime = null;
    beginDate = null;
    endTime = null;
    endDate = null;
  }


  @Override
  public String getCaller() {
    return caller;
  }

  @Override
  public String getCallee() {
    return callee;
  }

  @Override
  public String getBeginTimeString() {
    return beginTime + " " + beginDate;
  }

  @Override
  public String getEndTimeString() {
    return endTime + " " + endDate;
  }
  @VisibleForTesting
  static String validatePhoneNumber(String number) throws UnrecognizedPhoneNumberException {
    Pattern p = Pattern.compile("^\\d{3}-\\d{3}-\\d{4}$");
    Matcher m = p.matcher(number);
    if (!m.matches())
      throw new UnrecognizedPhoneNumberException();
    else
      return number;
  }
  @VisibleForTesting
  static class UnrecognizedPhoneNumberException extends Exception{}


  @VisibleForTesting
  static String validateDate(String date) throws UnrecognizedDateFormatException {
    Pattern p = Pattern.compile("^(0?[1-9]|1[0-2])/([1-9]|[1-2]\\d|3[0-1])/(\\d{4})$");
    Matcher m = p.matcher(date);
    if (!m.matches())
      throw new UnrecognizedDateFormatException();
    else
      return date;
  }
  @VisibleForTesting
  static class UnrecognizedDateFormatException extends Exception{}

  @VisibleForTesting
  static String validateTime(String time) throws UnrecognizedTimeFormatException {
    Pattern p = Pattern.compile("^(0?\\d|[1-2][0-4]):(0\\d|[1-5]\\d)$");
    Matcher m = p.matcher(time);
    if (!m.matches())
      throw new UnrecognizedTimeFormatException();
    else
      return time;
  }
  @VisibleForTesting
  static class UnrecognizedTimeFormatException extends Exception{}

}
