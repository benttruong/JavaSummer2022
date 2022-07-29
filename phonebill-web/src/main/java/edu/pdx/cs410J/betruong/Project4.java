package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        String hostName = null;
        String portString = null;
        boolean search = false;
        boolean printCommand = false;
        String customer = null;
        String callerNumber = null;
        String calleeNumber = null;
        String beginDate = null;
        String beginTime = null;
        String beginMeridiem = null;
        String endDate = null;
        String endTime = null;
        String endMeridiem = null;
        PhoneCall call = null;

        // variable to keep track of position of first required argument
        int firstArg = 0;

        if (args.length < 1){
            usage(MISSING_ARGS);
            return;
        }

        // looking for optional command
        for (int i = 0; i < args.length; ++i) {
            // regex logic to look for unknown command line
            Pattern unknownCommandPattern = Pattern.compile("^-.*$");
            Matcher m = unknownCommandPattern.matcher(args[i]);

            if (Objects.equals(args[i], "-print")) {
                printCommand = true;
                firstArg = i +1;
            } else if (Objects.equals(args[i], "-README")) {
                System.out.println(README);
                return;
            } else if (Objects.equals(args[i], "-host")) {
                if (args.length - i == 1){
                    System.err.println("Command is missing host name");
                    return;
                }
                hostName = args[i+1];
                ++ i;
                firstArg = i +1;
            } else if (Objects.equals(args[i], "-port")) {
                if (args.length - i == 1) {
                    System.err.println("Command is missing port");
                    return;
                }
                portString = args[i + 1];
                ++i;
                firstArg = i+1;
            } else if (Objects.equals(args[i], "-search")){
                search = true;
                firstArg = i+1;
            }else if (m.matches()) {
                System.err.println("Unknown Command Line: " + args[i]);
                return;
            } else {
                firstArg = i;
                break;
            }
        }

        if (args.length - firstArg >= 1){
            customer = args[firstArg];
        }

        if (hostName == null) {
            usage( MISSING_ARGS );
            return;

        } else if ( portString == null) {
            usage( "Missing port" );
            return;
        } else if (customer == null){
            System.err.println("Program requires at least a customer's name");
            return;
        } else if (args.length - firstArg > 9){
            usage("Extra arguments");
            return;
        }
        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);
        // customer = args[firstArg];

        PhoneBill bill;
        try {
            bill = client.getCall(customer);
        } catch (IOException | ParserException e) {
            usage(String.valueOf(e));
            return;
        }

        if (args.length - firstArg == 1){
            if (bill != null && bill.getPhoneCalls().isEmpty()){
                System.out.println("Phone bill for customer " + customer + " is empty");
                return;
            } else {
                System.out.println(bill.getPrettyBillString());
            }
        } else if (args.length - firstArg == 7){
            beginDate = args[firstArg + 1];
            beginTime = args[firstArg + 2];
            beginMeridiem = args[firstArg + 3];
            endDate = args[firstArg + 4];
            endTime = args[firstArg + 5];
            endMeridiem = args[firstArg + 6];
        } else if (args.length - firstArg == 9){
            callerNumber = args[firstArg + 1];
            calleeNumber = args[firstArg + 2];
            beginDate = args[firstArg + 3];
            beginTime = args[firstArg + 4];
            beginMeridiem = args[firstArg + 5];
            endDate = args[firstArg + 6];
            endTime  = args[firstArg + 7];
            endMeridiem = args[firstArg + 8];
            try {
                call = new PhoneCall(callerNumber, calleeNumber, beginDate, beginTime, beginMeridiem, endDate, endTime, endMeridiem);
            } catch (PhoneCall.PhoneCallException e) {
                usage("Data for new Phone Call is malformed");
                return;
            }
        }


        if (call != null){
            try {
                client.addPhoneCallEntry(customer, callerNumber, calleeNumber, beginDate +" "+ beginTime +" "+ beginMeridiem, endDate +" "+ endTime +" "+ endMeridiem);
                System.out.println("A new phone call added to " + customer + "'s phone bill");
            } catch (IOException e) {
                usage(String.valueOf(e));
                return;
            }
        }

        try {
            bill = client.getCall(customer);
        } catch (IOException | ParserException e) {
            usage(String.valueOf(e));
            return;
        }

        if (args.length - firstArg == 1) {
            System.out.println(bill.getPrettyBillString());
        } else if  (search && call == null){
            Date from = getTime(beginDate + " " + beginTime + " " + beginMeridiem);
            Date to = getTime(endDate + " " + endTime + " " + endMeridiem);
            if (from.compareTo(to) >= 0){
                System.err.println("Invalid time range to search, begin time is after end time");
                return;
            }
            int count = 0;
            for (PhoneCall callToCheck : bill.getPhoneCalls()){
                Date timeToCheck = callToCheck.getBeginTime();
                if (timeToCheck.compareTo(from) >= 0 &&
                    timeToCheck.compareTo(to) <= 0){
                    ++count;
                    System.out.println(callToCheck.getPrettyCallString());
                }
            }
            if (count == 0){
                System.out.println("There is no phone call from phone bill that started within that range");
            }
        }

        if (printCommand){
            if (call == null){
                usage("Command line is missing sufficient arguments for a new phone call");
                return;
            }
            else{
                System.out.println("The newly added phone call:\n"
                                    + call.getPrettyCallString());
            }
        }
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getHttpStatusCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getHttpStatusCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 -host host -port port [customer] [callerNumber] [calleeNumber] [begin] [end]");
        err.println("  host             Host of web server");
        err.println("  port             Port of web server");
        err.println("  customer         Name of customer");
        err.println("  callerNumber     Phone number of caller");
        err.println("  calleeNumber     Phone number of callee");
        err.println("  begin            Time when phone call started");
        err.println("  end              Time when phone call ended");
        err.println();
        err.println("This program add a phone call to a phone bill");
        err.println("to the server.");
        err.println("If no phone call is specified, then the phone bill of");
        err.println("the customer is printed.");
        err.println("The program expects at least the host and port to ");
        err.println("establish connection with the server and a customer's name.");
        err.println();
    }

   /* @VisibleForTesting
    static void printReadme() throws IOException {
        InputStream readme = Project4.class.getResourceAsStream("README.txt");
        assert readme != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
        String output;
        while ((output = reader.readLine()) != null)
            System.out.print(output + '\n');
    }*/

     private static Date getTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy hh:mm aa");
        Date result = null;
        try {
            result = sdf.parse(time);
        } catch (ParseException e) {
            System.err.println("Could not parse the time: " + time);
        }
        return result;
    }

    private static final String README =
            "Project4 - Ben Truong\n" +
            "This this a client application that communicates with" +
            "a running server that can record a new phone call and assign\n" +
            "that phone call to a phone bill with a customer name\n" +
            "with information provided from command line arguments.\n" +
            "\n" +
            "A phone call is initiated by a person with a given phone\n" +
            "number at a given time, is received by a person with a\n" +
            "given phone number, and terminates at a given time.\n" +
            "\n" +
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
            "aa: could be AM or PM";
}