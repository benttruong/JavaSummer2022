package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;

import java.io.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J Phone Bill Project
 * Student: Ben Truong
 */
public class Project2 {

  /**
   * Validating phone number has the format nnn-nnn-nnnn using regex
   * @param phoneNumber
   *        Phone number to validate
   * @return <code>boolean</code>
   *        true if phone number is in the correct format
   *        false if not in the correct format
   */
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

  /**
   *
   * Validating a date has the format mm/dd/yyyy using regex
   * @param date
   *        Date to validate
   * @return <code>boolean</code>
   *        true if format is correct
   *        false if format is not correct
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
   * Validating time to be in the format hh:mm using regex
   * @param time
   *        Time to validate
   * @return <code>boolean</code>
   *        true if format is correct
   *        false if format is not correct
   */
  @VisibleForTesting
  static boolean isValidTime(String time) {
    Pattern p = Pattern.compile("^(\\d|[0-1]\\d|2[0-3]):([0-5]\\d)$");
    Matcher m = p.matcher(time);
    if (m.matches())
      return true;
    else {
      System.err.println(time + " is an Invalid Time");
      return false;
    }
  }

  /**
   * Print the README file
   * @throws IOException
   *          <code>IOException</code>
   */
  @VisibleForTesting
   static void printReadme() throws IOException {
    // System.out.println("README Command Recognized");
    InputStream readme = Project2.class.getResourceAsStream("README.txt");
    assert readme != null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
    String output;
    while ((output = reader.readLine()) != null)
      System.out.print(output + '\n');
  }
  @VisibleForTesting
  static boolean isValidFilePath(String file) {
    Pattern filePath = Pattern.compile("^([a-zA-Z0-9_-]+/)*[a-zA-Z0-9_-]+\\.[a-z]+$");
    Matcher m = filePath.matcher(file);
    if (m.matches())
      return true;
    else {
      System.err.println("Invalid file name");
      return false;
    }
  }

  /**
   * Main program that parses the command line, creates a <code>PhoneCall</code>
   * and print a description of the phone call to standard out by invoking <code>toString</code>
   * also provides option to print README file if optional command line <code>-README</code>
   * is detected
   * @param args
   *        Command line arguments
   * @throws IOException
   *         <code>IOException</code>
   */
  public static void main(String[] args) throws IOException {

    // Check if no command line argument is provided
    if (args.length == 0) {
      System.err.println("Missing command line arguments");
      return;
    }

    // variable to keep track of position of first required argument
    int firstArg = 0;

    // variable to check if optional commands provided
    boolean printCommand = false;
    String file = "";
    boolean textFileCommand = false;

    // looking for optional command
    for (String arg : args) {
      // regex logic to look for unknown command line
      Pattern unknownCommandPattern = Pattern.compile("^-.*$");
      Matcher m = unknownCommandPattern.matcher(arg);

      if (Objects.equals(arg, "-print")) {
        printCommand = true;
        ++firstArg;
      } else if (Objects.equals(arg, "-README")) {
        printReadme();
        return;
      } else if (Objects.equals(arg, "-textFile")) {
        // not a very good check but can do for now
        /*if (args[firstArg + 1] == null) {
          System.err.println("Missing file name");
          return;*/
        if (args.length - firstArg + 1 < 8) {
          System.err.println("Missing command line argument");
          return;
        }
        int filePath = firstArg + 1;
        if (isValidFilePath(args[filePath])) {
          textFileCommand = true;
          file = args[firstArg + 1];
          firstArg += 2;
        }
      } else if (m.matches()) {
        System.err.println("Unknown Command Line: " + arg);
        return;
      } else
        break;
    }


    // expecting a total of 7 required arguments
    if (args.length - firstArg < 7) {
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
    PhoneCall call = null;
    PhoneBill bill = new PhoneBill(args[firstArg]);
    if (caller && callee && beginDate && beginTime && endDate && endTime) {
      call = new PhoneCall(args[firstArg + 1], args[firstArg + 2], args[firstArg + 3], args[firstArg + 4], args[firstArg + 5], args[firstArg + 6]);
      bill.addPhoneCall(call);
    }
    if (printCommand)
      System.out.println(call);
    if (textFileCommand) {
      TextDumper dumper = null;
      try {
        dumper = new TextDumper(new FileWriter(file));
      } catch (IOException e) {
        System.err.println("Something wrong while writing files");
        return;
      }
      dumper.dump(bill);
      System.out.println("New file written at: " + file);
    }

  }

}

