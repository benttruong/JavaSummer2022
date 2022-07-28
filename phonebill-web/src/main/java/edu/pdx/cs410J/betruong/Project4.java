package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        String word = null;
        String definition = null;
        boolean search = false;
        boolean printCommand = false;
        String customer = null;
        String callerNumber = null;
        String calleeNumber = null;
        String begin = null;
        String end = null;
        PhoneCall call = null;

        // variable to keep track of position of first required argument
        int firstArg = 0;

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
            } else if (Objects.equals(args[i], "-host")) {
                if (args.length - i == 1){
                    System.err.println("Command is missing host name");
                    return;
                }
                hostName = args[i+1];
                ++ i;

            } else if (Objects.equals(args[i], "-port")) {
                if (args.length - i == 1) {
                    System.err.println("Command is missing port");
                    return;
                }
                portString = args[i + 1];
                ++i;
            } else if (Objects.equals(args[i], "-search")){
                search = true;

            }else if (m.matches()) {
                System.err.println("Unknown Command Line: " + args[i]);
                return;
            } else{
                firstArg = i;
                break;
            }
        }

        if (hostName == null || args.length - firstArg < 3) {
            usage( MISSING_ARGS );

        } else if ( portString == null) {
            usage( "Missing port" );
        } else if (args.length - firstArg > 5){
            usage("Extra arguments");
        }

        customer = args[firstArg];
        if (args.length - firstArg == 3){
            begin = args[firstArg + 1];
            end = args[firstArg + 2];
        } else if (args.length - firstArg == 5){
            callerNumber = args[firstArg + 1];
            calleeNumber = args[firstArg + 2];
            begin = args[firstArg +3];
            end = args[firstArg + 4];
            call = new PhoneCall(callerNumber, calleeNumber, begin, end);
        }


        int port;
        try {
            port = Integer.parseInt( portString );
            
        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        String message;

        if (call != null){
            try {
                client.addPhoneCallEntry(customer, callerNumber, calleeNumber, begin, end);
            } catch (IOException e) {
                usage(String.valueOf(e));
                return;
            }
        }

        PhoneBill bill;
        try {
            bill = client.getCall(customer);
        } catch (IOException | ParserException e) {
            usage(String.valueOf(e));
            return;
        }

        if (args.length - firstArg == 1) {
            System.out.println(bill);
            for (PhoneCall callToPrint : bill.getPhoneCalls()) {
                System.out.println(callToPrint.getPrettyCallString());
            }
        }
        if (search && args.length - firstArg >= 3){
            Date beginTime = getTime(begin);
            Date endTime = getTime(end);
            System.out.println("Search command recognized");
            for (PhoneCall callToCheck : bill.getPhoneCalls()){
                Date timeToCheck = callToCheck.getBeginTime();
                if (timeToCheck.compareTo(beginTime) >= 0 &&
                    timeToCheck.compareTo(endTime) <= 0){
                    System.out.println(callToCheck.getPrettyCallString());
                }
            }
        }

        if (printCommand && call != null){
            System.out.println("The newly added phone call:\n"
                               + call.getPrettyCallString());
        }
        // System.out.println(message);
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
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();
    }

    @VisibleForTesting
    static void printReadme() throws IOException {
        // System.out.println("README Command Recognized");
        InputStream readme = Project4.class.getResourceAsStream("README.txt");
        assert readme != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
        String output;
        while ((output = reader.readLine()) != null)
            System.out.print(output + '\n');
    }

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
}