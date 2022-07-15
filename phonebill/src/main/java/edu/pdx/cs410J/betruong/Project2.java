package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * The main class for the CS410J Phone Bill Project
 * Student: Ben Truong
 *
 */
public class Project2 {

  /**
   * Validating phone number has the format nnn-nnn-nnnn using regex
   *
   * @param phoneNumber Phone number to validate
   * @return <code>boolean</code>
   * true if phone number is in the correct format
   * false if not in the correct format
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
   * Validating time to be in the format hh:mm using regex
   *
   * @param time Time to validate
   * @return <code>boolean</code>
   * true if format is correct
   * false if format is not correct
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
   *
   * @throws IOException <code>IOException</code>
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
      return false;
    }
  }

  @VisibleForTesting
  static String getFileName(String file) {
    int beginningIndexOfFileName = file.lastIndexOf('/') + 1;
    if (beginningIndexOfFileName < 0)
      return file;
    else
      return file.substring(beginningIndexOfFileName);
  }

  @VisibleForTesting
  static String getPath(String file) {
    int endingIndexOfPath = file.lastIndexOf('/');
    if (endingIndexOfPath < 0)
      return null;
    else
      return file.substring(0, endingIndexOfPath);
  }




    /**
     * Main program that parses the command line, creates a <code>PhoneCall</code>
     * and print a description of the phone call to standard out by invoking <code>toString</code>
     * also provides option to print README file if optional command line <code>-README</code>
     * is detected
     *
     * @param args Command line arguments
     * @throws IOException <code>IOException</code>
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
      boolean textFileCommand = false;
      String file = "";


      // looking for optional command
      for (int i = 0; i < args.length; ++i) {
        // regex logic to look for unknown command line
        Pattern unknownCommandPattern = Pattern.compile("^-.*$");
        Matcher m = unknownCommandPattern.matcher(args[i]);

        if (Objects.equals(args[i], "-print")) {
          printCommand = true;
        } else if (Objects.equals(args[i], "-README")) {
          try {
            printReadme();
          } catch (IOException e) {
            System.err.println("Something wrong happened while trying to load README");
          }
          return;
        } else if (Objects.equals(args[i], "-textFile")) {
          if (args.length - i == 1){
            System.err.println("Command is missing file");
            return;
          }
          if (isValidFilePath(args[i + 1])) {
            textFileCommand = true;
            file = args[i + 1];
            ++ i;
          } else{
            System.err.println(args[firstArg + 1] + " is not a valid file");
            return;
          }
        } else if (m.matches()) {
          System.err.println("Unknown Command Line: " + args[i]);
          return;
        } else{
          firstArg = i;
          break;
        }
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
      if (caller && callee && beginDate && beginTime && endDate && endTime) {
        call = new PhoneCall(args[firstArg + 1], args[firstArg + 2], args[firstArg + 3], args[firstArg + 4], args[firstArg + 5], args[firstArg + 6]);
      }
      else{
        System.err.println("Program terminated because of the info for phone call is malformed");
        return;
      }

      String customer = args[firstArg];
      PhoneBill bill = null;
      boolean fileFound = true;
      if (textFileCommand) {

        String filename = getFileName(file);
        String path = getPath(file);
        File textFile;
        if (path == null){
          textFile = new File(filename);
        }
        else {
          textFile = new File(path, filename);
        }

        TextParser parser = null;
        try {
          parser = new TextParser(new FileReader(textFile));
        } catch (FileNotFoundException FNFe) {
          // if file not found, we are creating a new phone bill
          fileFound = false;
          bill = new PhoneBill(customer);
        }
        if (fileFound) {
          try {
            // when file is found, we parse the file into phone bill
            bill = parser.parse();
          } catch (ParserException Pe) {
            System.err.println("Something wrong happened while loading file");
            return;
          }
          // then check if the customer name from bill match with one from file
          if (!Objects.equals(bill.getCustomer(), customer)) {
            System.err.println("Provided customer name does not match with existing bill");
            return;
          }

          // unless the customer names are not matching
          // we are adding the new phone call to the bill here
         // bill.addPhoneCall(call);
        }

        TextDumper dumper = null;
        if (path != null) {
          File folder = new File(path);
          folder.mkdirs();
        }
        try {
          dumper = new TextDumper(new FileWriter(textFile));
        } catch (IOException e) {
          System.err.println("Something wrong while writing files");
          return;
        }
        bill.addPhoneCall(call);
        dumper.dump(bill);
        if (fileFound){
          System.out.println(file + " updated.");
        }else {
          System.out.println("New file written at " + file);
        }
      }


      if (printCommand) {
        System.out.println(call);
      }
    }
}

