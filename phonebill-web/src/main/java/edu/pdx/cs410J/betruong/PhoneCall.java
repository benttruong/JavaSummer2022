package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This call represents a <code>PhoneCall</code>
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable{


  private String caller;
  private String callee;
  private String beginDate;
  private String beginTime;
  private String beginMeridiem;

  private String endDate;
  private String endTime;
  private String endMeridiem;
  private long duration;



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
  public PhoneCall(String caller, String callee, String beginDate, String beginTime, String beginMeridiem, String endDate, String endTime, String endMeridiem) throws PhoneCallException {
    try {
      if (isValidPhoneNumber(caller))
        this.caller = caller;
    } catch (PhoneCallException e) {
      throw new RuntimeException(e);
    }
    try {
      if (isValidPhoneNumber(callee))
        this.callee = callee;
    } catch (PhoneCallException e) {
      throw new RuntimeException(e);
    }
    if (isValidDate(beginDate))
      this.beginDate = beginDate;
    if (isValidTime(beginTime))
      this.beginTime = beginTime;
    if (isValidMeridiem(beginMeridiem))
      this.beginMeridiem = beginMeridiem;
    if(isValidDate(endDate))
      this.endDate = endDate;
    if(isValidTime(endTime))
      this.endTime = endTime;
    if(isValidMeridiem(endMeridiem))
      this.endMeridiem = endMeridiem;
    this.duration = getDuration();

  }

  /**
   * Creates a new <code>PhoneCall</code> with no arguments
   */
  @VisibleForTesting
  public PhoneCall() {
    this.caller = null;
    this.callee = null;
    this.beginDate = null;
    this.beginTime = null;
    this.beginMeridiem = null;
    this.endDate = null;
    this.endTime = null;
    this.endMeridiem = null;
    this.duration = 0;
  }

  public PhoneCall(String caller, String callee, String begin, String end) {
    try {
      if (isValidPhoneNumber(caller))
        this.caller = caller;
    } catch (PhoneCallException e) {
      throw new RuntimeException(e);
    }
    try {
      if (isValidPhoneNumber(callee))
        this.callee = callee;
    } catch (PhoneCallException e) {
      throw new RuntimeException(e);
    }
    try {
      setBeginTime(begin);
    } catch (PhoneCallException e) {
      throw new RuntimeException(e);
    }
    try {
      setEndTime(end);
    } catch (PhoneCallException e) {
      throw new RuntimeException(e);
    }
    try {
      this.duration = getDuration();
    } catch (PhoneCallException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get phone number of caller
   * @return <code>String</code> phone number
   */
  @VisibleForTesting
  @Override
  public String getCaller() {
    return this.caller;
  }

  /**
   * Get phone number of receiver
   * @return <code>String</code> phone number
   */
  @VisibleForTesting
  @Override
  public String getCallee() {
    return this.callee;
  }

  /**
   * Get begin date of the phone call
   * @return <code>String</code> begin date
   */
  @VisibleForTesting
  public String getBeginDate(){
    return this.beginDate;
  }

  /**
   * Get end date of the phone call
   * @return <code>String</code> end date
   */
  @VisibleForTesting
  public String getEndDate(){
    return this.endDate;
  }

  /**
   * Get begin time of the phone call
   * @return <code>String</code> begin time
   */
  @VisibleForTesting
  public String getBeginTimeLiterals(){
    return this.beginDate + " " + this.beginTime + " " + this.beginMeridiem;
  }


  /**
   * Get end time of the phone call
   * @return <code>String</code> end time
   */
  @VisibleForTesting
  public String getEndTimeLiterals(){
    return this.endDate + " " + this.endTime + " " + this.endMeridiem;
  }

  /**
   * Starting date and time of the phone call
   * @return <code>String</code> date and time
   */
  @VisibleForTesting
  @Override
  public String getBeginTimeString() {
    Date date = this.getBeginTime();
    int f = DateFormat.SHORT;
    DateFormat df = DateFormat.getDateTimeInstance(f, f);
    return df.format(date);
  }

  @VisibleForTesting
  @Override
  public Date getBeginTime() {
    String time = this.beginDate + " " + this.beginTime + " " + this.beginMeridiem;
    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy hh:mm aa");
    Date result = null;
    try {
      result = sdf.parse(time);
    } catch (ParseException e) {
      System.err.println("Could not parse the time: " + time);
      System.exit(1);
    }
    return result;
  }

  /**
   * Ending date and time of the phone call
   * @return <code>String</code> date and time
   */
  @VisibleForTesting
  @Override
  public String getEndTimeString() {
    Date date = this.getEndTime();
    int f = DateFormat.SHORT;
    DateFormat df = DateFormat.getDateTimeInstance(f, f);
    return df.format(date);
  }

  @VisibleForTesting
  @Override
  public Date getEndTime(){
    String time = this.endDate + " " + this.endTime + " " + this.endMeridiem;
    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy hh:mm aa");
    Date result = null;
    try {
      result = sdf.parse(time);
    } catch (ParseException e) {
      System.err.println("Could not parse the time: " + time);
    }
    return result;
  }

  @VisibleForTesting
  @Override
  public int compareTo(Object o) {
    PhoneCall call = (PhoneCall) o;
    // we first compare the begin time for the 2 calls
    Date beginTime1 = this.getBeginTime();
    Date beginTime2 = call.getBeginTime();
    int dateCompared = this.getBeginTime().compareTo(call.getBeginTime());
    if (dateCompared == 0){
      // if begin time is the same, we look at the caller's number
      // by converting them to integers
      String caller1String = this.getCaller().replaceAll("[-]", "");
      String caller2String = call.getCaller().replaceAll("[-]", "");
      long caller1 = Long.parseLong(caller1String);
      long caller2 = Long.parseLong(caller2String);

      if (caller1 == caller2){
        return 0;
      } else if (caller1 < caller2){
        return -1;
      } else {
        return 1;
      }
    } else if (dateCompared < 0) {
      return -1;
    } else
      return 1;
  }

  @VisibleForTesting
  static boolean isValidTime(String time) {
    Pattern p = Pattern.compile("^(\\d|[0-1][0-2]|):([0-5]\\d)$");
    Matcher m = p.matcher(time);
    if (m.matches())
      return true;
    else {
      System.err.println(time + " is an Invalid Time");
      return false;
    }
  }

  /**
   * Validating a date has the format mm/dd/yyyy using regex
   *
   * @param date Date to validate
   * @return <code>boolean</code>
   * true if format is correct
   * false if format is not correct
   */
  @VisibleForTesting
  static boolean isValidDate(String date) {
    Pattern p = Pattern.compile("^(0?[1-9]|1[012])[- /.](0?[1-9]|[12]\\d|3[01])[- /.](19|20)\\d\\d$");
    Matcher m = p.matcher(date);
    if (m.matches())
      return true;
    else {
      System.err.println(date + " is an Invalid Date");
      return false;
    }
  }

  /**
   * Validating phone number has the format nnn-nnn-nnnn using regex
   *
   * @param phoneNumber Phone number to validate
   * @return <code>boolean</code>
   * true if phone number is in the correct format
   * false if not in the correct format
   */
  @VisibleForTesting
  static boolean isValidPhoneNumber(String phoneNumber) throws PhoneCallException {
    Pattern p = Pattern.compile("^\\d{3}-\\d{3}-\\d{4}$");
    Matcher m = p.matcher(phoneNumber);
    if (m.matches())
      return true;
    else {
      throw new PhoneCallException();
    }

  }



  @VisibleForTesting
  static boolean isValidMeridiem(String arg) {
    if (Objects.equals(arg.toLowerCase(Locale.ROOT), "am") || Objects.equals(arg.toLowerCase(Locale.ROOT), "pm")){
      return true;
    }
    return false;
  }

  @VisibleForTesting
  public long getDuration() throws PhoneCallException {
    Date begin = getBeginTime();
    Date end = getEndTime();
    long duration = end.getTime() - begin.getTime();
    if (duration < 0){
      throw new PhoneCallException ();
    }
    return duration;
  }
  @VisibleForTesting
  public String getDurationString() {
    long day = duration/(1000*60*60*24);
    long durationLeft = duration - day * 1000*60*60*24;
    long hour = durationLeft/(1000*60*60);
    durationLeft -= hour * 1000*60*60;
    long minute = durationLeft/(1000*60);
    durationLeft -= minute * 1000*60;
    long second = durationLeft/(1000);

    String result = String.valueOf(new StringBuilder());
    if (day != 0)
      result += day + " day(s) ";
    if (hour != 0)
      result += hour + " hour(s) ";
    if (minute != 0)
      result += minute + " minute(s) ";
    if (second != 0)
      result += second + " second(s)";
    return result;
  }

  @VisibleForTesting
  public String getPrettyCallString(){
    return "Phone call from " + this.getCaller() + " to " + this.getCallee() + " from " + this.getBeginTimeString() + " to " + this.getEndTimeString()
            + "\n\tDuration: " + this.getDurationString();
  }
  @VisibleForTesting
  void setBeginTime(String time) throws PhoneCallException {
    String [] values = time.split(" ");
    if (values.length != 3){
      throw new PhoneCallException();
    }
    if (PhoneCall.isValidDate(values[0]) &&
        PhoneCall.isValidTime(values[1]) &&
        PhoneCall.isValidMeridiem(values[2])){
      this.beginDate = values[0];
      this.beginTime = values[1];
      this.beginMeridiem = values[2];
    }
  }

  @VisibleForTesting
  void setEndTime(String time) throws PhoneCallException {
    String [] values = time.split(" ");
    if (values.length != 3){
      throw new PhoneCallException();
    }
    if (PhoneCall.isValidDate(values[0]) &&
        PhoneCall.isValidTime(values[1]) &&
        PhoneCall.isValidMeridiem(values[2])){
      this.endDate = values[0];
      this.endTime = values[1];
      this.endMeridiem = values[2];
    }
  }



  public static class PhoneCallException extends Throwable {
    public PhoneCallException() {
    }
  }


}
