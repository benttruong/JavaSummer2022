package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  @VisibleForTesting
  static boolean isValidPhoneNumber(String phoneNumber) {
    Pattern p = Pattern.compile("^\\d{3}-\\d{3}-\\d{4}$");
    Matcher m = p.matcher(phoneNumber);
    if (m.matches())
      return true;
    else {
      System.err.println(phoneNumber + " is an Invalid Phone Number");
      return false;
    }
  }

  @VisibleForTesting
  static boolean isValidDate(String date) {
    Pattern p = Pattern.compile("^(0?[1-9]|1[0-2])/([1-9]|[1-2]\\d|3[0-1])/(\\d{4})$");
    Matcher m = p.matcher(date);
    if (m.matches())
      return true;
    else {
      System.err.println(date + " is an Invalid Date");
      return false;
    }
  }

  @VisibleForTesting
  static boolean isValidTime(String time) {
    Pattern p = Pattern.compile("^(0?\\d|[1-2][0-4]):(0\\d|[1-5]\\d)$");
    Matcher m = p.matcher(time);
    if (m.matches())
      return true;
    else {
      System.err.println(time + " is an Invalid Time");
      return false;
    }
  }

  public static void main(String[] args) {
    // PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    if (args.length < 7) {
      System.err.println("Missing command line arguments");
    } else if (args.length > 7) {
      System.err.println("Too many arguments");
    } else {
      boolean caller = isValidPhoneNumber(args[1]);
      boolean callee = isValidPhoneNumber(args[2]);
      boolean beginDate = isValidDate(args[3]);
      boolean beginTime = isValidTime(args[4]);
      boolean endDate = isValidDate(args[5]);
      boolean endTime = isValidTime(args[6]);
      if (caller && callee && beginDate && beginTime && endDate && endTime) {
        PhoneCall call = new PhoneCall(args[1], args[2], args[3], args[4], args[5], args[6]);
        System.out.println("Phone Call Created");
        System.out.println(call);
      }

    }
    for (String arg : args) {
      System.out.println(arg);
    }


     //  System.out.println(call.toString());

  }
}

