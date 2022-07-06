package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
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
  @VisibleForTesting
   static void printReadme() throws IOException {
    // System.out.println("README Command Recognized");
    InputStream readme = Project1.class.getResourceAsStream("README.txt");
    assert readme != null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
    String output;
    while ((output = reader.readLine()) != null)
      System.out.print(output + '\n');
  }

  public static void main(String[] args) throws IOException {
    // PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    if (args.length == 0) {
      System.err.println("Missing command line arguments");
      return;
    }

    // variable to keep track of position of first required argument
    int firstArg = 0;

    // variable to check if optional commands provided
    boolean printCommand = false;
    // boolean readMeCommand = false;

    for (String arg:args){
      if (Objects.equals(arg, "-print")) {
        printCommand = true;
        ++firstArg;
      }
      else if (Objects.equals(arg, "-README")) {
        printReadme();
        return;
      }
      else
        break;
    }



    // expecting a total of 7 required arguments

    if (args.length - firstArg < 7){
      System.err.println("Missing command line arguments");
      return;
    }
    if (args.length - firstArg > 7) {
      System.err.println("Too many arguments");
      return;
    }

    // validating arguments to make sure they are provided in the right order
    boolean caller = isValidPhoneNumber(args[firstArg + 1]);
    boolean callee = isValidPhoneNumber(args[firstArg + 2]);
    boolean beginDate = isValidDate(args[firstArg + 3]);
    boolean beginTime = isValidTime(args[firstArg + 4]);
    boolean endDate = isValidDate(args[firstArg + 5]);
    boolean endTime = isValidTime(args[firstArg + 6]);
    if (caller && callee && beginDate && beginTime && endDate && endTime) {
      PhoneCall call = new PhoneCall(args[firstArg + 1], args[firstArg + 2], args[firstArg + 3], args[firstArg + 4], args[firstArg + 5], args[firstArg + 6]);
      System.out.println("Phone Call Created");
      if (printCommand)
        System.out.println(call);
    }


  }
}

