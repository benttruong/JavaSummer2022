package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneBillRestClientTest {

  @Test
  void getPhoneCallsEntriesPerformsHttpGetWithNoParameters() throws IOException {
    String customer = "Ben";
    Map<String, String> calls = Map.of("customer", customer);

    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.get(eq(Map.of()))).thenReturn(callsAsText(calls));

    PhoneBillRestClient client = new PhoneBillRestClient(http);

    assertThat(http.get(calls), equalTo(null));

  }

  private HttpRequestHelper.Response callsAsText(Map<String, String> calls) {
    StringWriter writer = new StringWriter();
    new TextDumper(writer).dump(calls);

    return new HttpRequestHelper.Response(writer.toString());
  }
}
