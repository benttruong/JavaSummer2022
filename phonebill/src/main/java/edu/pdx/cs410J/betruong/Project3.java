package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.time.DateTimeException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * The main class for the CS410J Phone Bill Project
 * Student: Ben Truong
 *
 */
public class Project3 {

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
        System.err.println("Missing command line arguments\n" +
                "This is how you use it from the command line:\n" +
                "java -jar target/phonebill-2022.0.0.jar [options] <args>\n" +
                "\n" +
                "args are (in this order):\n" +
                "    customer        : Person whose phone bill we’re modeling\n" +
                "    callerNumber    : Phone number of caller\n" +
                "    calleeNumber    : Phone number of person who was called\n" +
                "    begin           : Date and time (am/pm) call began\n" +
                "    end             : Date and time (am/pm) call ended\n" +
                "\n" +
                "options are (options may appear in any order):\n" +
                "    -pretty file    : Pretty print the phone bill to a text file\n" +
                "                      or standard out (file -).\n" +
                "    -textFile file  : could be just a file name or\n" +
                "                      a path with a file name included\n" +
                "                      where to read/write the phone bill\n" +
                "    -print          : Prints a description of the new phone call\n" +
                "    -README         : Prints a README for this project and exits\n" +
                "\n" +
                "Date and time should be in the format: mm/dd/yyyy hh:mm aa\n" +
                "aa: could be AM or PM");
        return;
      }

      // variable to keep track of position of first required argument
      int firstArg = 0;

      // variables to check if optional commands provided
      boolean printCommand = false;
      boolean textFileCommand = false;
      boolean prettyCommand = false;
      boolean prettyFile = false;
      boolean prettyStdOut = false;
      String file = "";
      String prettyFileDesination = "";


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
        } else if (Objects.equals(args[i], "-pretty")){
          if (args.length - i == 1){
            System.err.println("Command is missing file");
            return;
          }
          prettyCommand = true;
          if (isValidFilePath(args[i+1]) || Objects.equals(args[i+1], "-")){
            if (isValidFilePath(args[i+1])){
              prettyFile = true;
              prettyFileDesination = args[i + 1];
              ++ i;
            }
            else{
              prettyStdOut = true;
              ++ i;
            }
          } else {
            System.err.println("Pretty Command is missing output destination");
            return;
          }

        }else if (m.matches()) {
          System.err.println("Unknown Command Line: " + args[i]);
          return;
        } else{
          firstArg = i;
          break;
        }
      }


      // expecting a total of 7 required arguments
      if (args.length - firstArg < 9) {
        System.err.println("Missing command line arguments");
        return;
      }
      if (args.length - firstArg > 9) {
        System.err.println("Too many arguments");
        return;
      }

      PhoneCall call = null;
      PhoneBill bill = null;
      // validating arguments to make sure they are provided in the right order
      boolean caller = PhoneCall.isValidPhoneNumber(args[firstArg + 1]);
      boolean callee = PhoneCall.isValidPhoneNumber(args[firstArg + 2]);
      boolean beginDate = PhoneCall.isValidDate(args[firstArg + 3]);
      boolean beginTime = PhoneCall.isValidTime(args[firstArg + 4]);
      boolean beginMeridiem = PhoneCall.isValidMeridiem(args[firstArg + 5]);
      boolean endDate = PhoneCall.isValidDate(args[firstArg + 6]);
      boolean endTime = PhoneCall.isValidTime(args[firstArg + 7]);
      boolean endMeridiem = PhoneCall.isValidMeridiem(args[firstArg + 8]);

      if (caller && callee && beginDate && beginTime && beginMeridiem && endDate && endTime && endMeridiem) {
        bill = new PhoneBill(args[firstArg]);
        try {
            call = new PhoneCall(args[firstArg + 1], args[firstArg + 2], args[firstArg + 3], args[firstArg + 4], args[firstArg + 5], args[firstArg + 6], args[firstArg + 7], args[firstArg + 8]);
        } catch (PhoneCall.PhoneCallException e) {
            System.err.println("Phone call error: begin time happens after end time");
            return;
        }

      }
      else{
        System.err.println("Program terminated because of the info for phone call is malformed");
        return;
      }

      boolean textFileCommandExecuted = false;
      String customer = args[firstArg];
      boolean fileFound = true;
      if (textFileCommand) {
        printTextFile(file, call, customer, bill, fileFound, prettyCommand, prettyStdOut, prettyFile, prettyFileDesination);
        textFileCommandExecuted = true;
      }

      if (printCommand) {
        System.out.println(call);
      }


      if (prettyCommand && !textFileCommandExecuted) {
        bill.addPhoneCall(call);
        if (prettyStdOut){
          System.out.println(bill.getPrettyBillString());
        }
        if (prettyFile){
          printPrettyFile(prettyFileDesination, bill);
        }
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
    InputStream readme = Project3.class.getResourceAsStream("README.txt");
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
    return m.matches();
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

  private static void printPrettyFile(String file, PhoneBill bill) {
    String filename = getFileName(file);
    String path = getPath(file);
    File textFile;
    if (path == null){
      textFile = new File(filename);
    }
    else {
      textFile = new File(path, filename);
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
    dumper.dumpPretty(bill);
    return;
  }

  private static void printTextFile(String file, PhoneCall call, String customer, PhoneBill bill, boolean fileFound, boolean prettyCommand, boolean prettyStdOut, boolean prettyFile, String prettyFileDesination) {
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

    if (prettyCommand) {

      if (prettyStdOut){
        System.out.println(bill.getPrettyBillString());
      }
      if (prettyFile){
        String prettyFilename = getFileName(prettyFileDesination);
        String prettyPath = getPath(prettyFileDesination);
        File file_for_pretty;
        if (path == null){
          file_for_pretty = new File(prettyFilename);
        }
        else {
          file_for_pretty = new File(prettyPath, prettyFilename);
        }
        try {
          dumper = new TextDumper(new FileWriter(file_for_pretty));
        } catch (IOException e) {
          System.err.println("Something wrong while writing files");
          return;
        }
        bill.addPhoneCall(call);
        dumper.dumpPretty(bill);
      }
    }


  }
}

