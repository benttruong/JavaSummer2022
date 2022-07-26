package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
    static final String CUSTOMER_PARAMETER = "customer";
    static final String DEFINITION_PARAMETER = "definition";

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
        if (customer != null) {
            writeCalls(customer, response);

        } else {
            response.setStatus( HttpServletResponse.SC_OK );
            // writeAllDictionaryEntries(response);
        }
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

        String calls = getParameter(CUSTOMER_PARAMETER, request );
        if (calls == null) {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
            return;
        }

        String definition = getParameter(DEFINITION_PARAMETER, request );
        if ( definition == null) {
            missingRequiredParameter( response, DEFINITION_PARAMETER );
            return;
        }

        this.dictionary.put(calls, definition);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.definedWordAs(calls, definition));
        pw.flush();

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
     * Writes the definition of the given word to the HTTP response.
     *
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writeCalls(String customer, HttpServletResponse response) throws IOException {
        PhoneBill bill = this.bills.get(customer);
       /* PhoneBill bill = new PhoneBill(customer);
        PhoneCall dummycall;
        try {
            dummycall = new PhoneCall("123-456-7890", "111-456-7890", "07/25/2022", "9:10", "AM", "07/25/2022", "9:15", "AM");
        } catch (PhoneCall.PhoneCallException e) {
            throw new RuntimeException(e);
        }
        bill.addPhoneCall(dummycall);*/

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

}
