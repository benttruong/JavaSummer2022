package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration test that tests the REST calls made by {@link PhoneBillRestClient}
 */
@TestMethodOrder(MethodName.class)
class PhoneBillRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private PhoneBillRestClient newPhoneBillRestClient() {
    int port = Integer.parseInt(PORT);
    return new PhoneBillRestClient(HOSTNAME, port);
  }

  @Test
  void test1EmptyServerContainsNoPhoneCallEntries() throws IOException, ParserException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    PhoneBill bill = client.getCall("Kevin");
    assertThat(bill.toString(), containsString("0 phone call"));
  }

  @Test
  void test2ManuallyAddNewPhoneCallReturnTheSamePhoneCall() throws IOException, ParserException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    String customer = "Ben Truong";
    String caller = "123-456-7890";
    String callee = "111-222-4444";
    String begin = "7/28/2022 8:00 AM";
    String end = "7/28/2022 8:15 AM";
    client.addPhoneCallEntry(customer, caller, callee, begin, end);
    PhoneCall tempCall = new PhoneCall(caller, callee, begin, end);
    PhoneBill tempBill = new PhoneBill(customer);
    tempBill.addPhoneCall(tempCall);

    PhoneBill testItBill = client.getCall(customer);
    assertThat(tempBill.toString(), equalTo(testItBill.toString()));
  }

  @Test
  void test3EmptyCustomerThrowsException() {
    PhoneBillRestClient client = newPhoneBillRestClient();
    String emptyString = "";

    RestException ex =
      assertThrows(RestException.class, () -> client.getCall(emptyString));
    assertThat(ex.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
    assertThat(ex.getMessage(), equalTo(Messages.missingRequiredParameter("customer")));
  }

}
