package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.runner.Request;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Map;

import static edu.pdx.cs410J.web.HttpRequestHelper.Response;
import static edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class PhoneBillRestClient {

    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

  private final HttpRequestHelper http;


    /**
     * Creates a client to the Phone Bil REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public PhoneBillRestClient( String hostName, int port )
    {
      this(new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET)));
    }

  @VisibleForTesting
  PhoneBillRestClient(HttpRequestHelper http) {
    this.http = http;
  }


  /**
   *
   * @param customer
   *        The parameter to passed in the method and trigger the get request
   *        using the customer name to look for the phone bill
   * @return A phone bill given a customer name
   * @throws IOException
   * @throws ParserException
   */
    public PhoneBill getCall(String customer) throws IOException, ParserException {
      Response response = http.get(Map.of("customer", customer));
      throwExceptionIfNotOkayHttpStatus(response);
      if (response == null){
        return null;
      }
      TextParser parser = new TextParser(new StringReader(response.getContent()));
      ArrayList<PhoneCall> calls = parser.billParse();
      PhoneBill bill = new PhoneBill(customer);
      for (PhoneCall call : calls){
        bill.addPhoneCall(call);
      }
      return bill;
    }

  /**
   * Client method to trigger the post request to add a new phone call
   * @param customer
   *        Customer's name
   * @param callerNumber
   *        Caller number of the phone call
   * @param calleeNumber
   *        Callee number of the phone call
   * @param begin
   *        Begin time of the phone call
   * @param end
   *        Ending time of the phone call
   * @throws IOException
   *        This method can throw IOExeption
   *        if the data of the phone call is malformed
   */
    public void addPhoneCallEntry(String customer, String callerNumber, String calleeNumber, String begin, String end) throws IOException {
      Response response = http.post(Map.of("customer", customer, "caller", callerNumber, "callee", calleeNumber, "begin", begin, "end", end));
      throwExceptionIfNotOkayHttpStatus(response);
    }

    private void throwExceptionIfNotOkayHttpStatus(Response response) {
      int code = response.getHttpStatusCode();
      if (code != HTTP_OK) {
        String message = response.getContent();
        throw new RestException(code, message);
      }
    }

}
