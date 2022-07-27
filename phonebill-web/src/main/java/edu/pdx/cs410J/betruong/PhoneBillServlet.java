package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet
{

    static final String DEFINITION_PARAMETER = "definition";
    static final String CUSTOMER_PARAMETER = "customer";
    static final String CALLER_PARAMETER = "caller";
    static final String CALLEE_PARAMETER = "callee";
    static final String BEGIN_PARAMETER = "begin";
    static final String END_PARAMETER = "end";

    private final Map<String, String> dictionary = new HashMap<>();
    private final Map<String, PhoneBill> bills = new HashMap<>();
    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/plain" );
        String customer = getParameter( CUSTOMER_PARAMETER, request );
        String begin = getParameter( BEGIN_PARAMETER, request );
        String end = getParameter( END_PARAMETER, request );

        if (customer != null) {
            if (begin != null && end != null){
                Date beginTime = getTime(begin);
                Date endTime = getTime(end);
                writeCalls(customer, beginTime, endTime, response);
            } else if (begin != null){
               missingRequiredParameter(response, END_PARAMETER);
            } else if (end != null) {
                missingRequiredParameter(response, BEGIN_PARAMETER);
            } else{
                writeBill(customer, response);
            }

        } else {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
        }
        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/plain" );
        String customer = getParameter( CUSTOMER_PARAMETER, request );
        if (customer == null){
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
        }
        String caller = getParameter( CALLER_PARAMETER, request);
        if (caller == null){
            missingRequiredParameter(response, CALLER_PARAMETER);
        }
        String callee = getParameter( CALLEE_PARAMETER, request);
        if (callee == null){
            missingRequiredParameter(response, CALLEE_PARAMETER);
        }
        String begin = getParameter( BEGIN_PARAMETER, request);
        if (begin == null){
            missingRequiredParameter(response, BEGIN_PARAMETER);
        }
        String end = getParameter( END_PARAMETER, request);
        if (end == null){
            missingRequiredParameter(response, END_PARAMETER);
        }

       /* POST creates a new call from the HTTP request parameters customer, callerNumber,
        calleeNumber, begin, and end. If the phone bill does not exist, a new one should be
        created.*/


        PhoneBill bill = this.bills.get(customer);
        if (bill == null){
            this.bills.put(customer, new PhoneBill(customer));
        }

        PhoneCall call = new PhoneCall(caller, callee, begin, end);

        this.bills.get(customer).addPhoneCall(call);

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        this.dictionary.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes all the phone calls of the phone bill given customer to the HTTP response.
     *
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writeBill(String customer, HttpServletResponse response) throws IOException {
        PhoneBill bill = this.bills.get(customer);

        if (bill == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter pw = response.getWriter();

            Map<String, PhoneBill> billDetail = Map.of(customer, bill);
            TextDumper dumper = new TextDumper(pw);
            dumper.billdump(billDetail);

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Writes all the phone calls that started within a window of time
     * of the phone bill given customer to the HTTP response.
     *
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writeCalls(String customer, Date begin, Date end, HttpServletResponse response) throws IOException {
        PhoneBill bill = this.bills.get(customer);

        if (bill == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter pw = response.getWriter();
            TextDumper dumper = new TextDumper(pw);
            for (PhoneCall call : bill.getPhoneCalls()){
                Date startedTime = getTime(call.getBeginTimeLiterals());
                if (startedTime.compareTo(begin) >= 0 && startedTime.compareTo(end) <= 0){
                    dumper.calldump(call);
                }
            }
            // Map<String, PhoneBill> billDetail = Map.of(customer, bill);
            // dumper.billdump(billDetail);

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Writes all of the dictionary entries to the HTTP response.
     *
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writeAllDictionaryEntries(HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        TextDumper dumper = new TextDumper(pw);
        dumper.dump(dictionary);

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

    @VisibleForTesting
    String getDefinition(String word) {
        return this.dictionary.get(word);
    }

    public Date getTime(String time){
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
