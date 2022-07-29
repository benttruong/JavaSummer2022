package edu.pdx.cs410J.betruong;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class PhoneBillServletTest {

  @Test
  void initiallyServletContainsNoPhoneCallEntries() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    // Nothing is written to the response's PrintWriter
    verify(pw, never()).println(anyString());
    verify(response).setStatus(HttpServletResponse.SC_OK);
  }

  @Test
  void addOnePhoneCall() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "Ben Truong";
    String caller = "123-456-7890";
    String callee = "111-222-3333";
    String begin = "7/28/2022 8:30 AM";
    String end = "7/28/2022 8:45 AM";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("customer")).thenReturn(customer);
    when(request.getParameter("caller")).thenReturn(caller);
    when(request.getParameter("callee")).thenReturn(callee);
    when(request.getParameter("begin")).thenReturn(begin);
    when(request.getParameter("end")).thenReturn(end);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    System.out.println("StringWriter: " + stringWriter.toString());
    System.out.println("Message: " + Messages.phoneCallFormat(callee, callee, begin, end));

    // assertThat(stringWriter.toString(), containsString(Messages.phoneCallFormat(caller, callee, begin, end)));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

   /* PhoneBill tempBill = new PhoneBill(customer);
    PhoneCall tempCall = new PhoneCall(caller, callee, begin, end);
    tempBill.addPhoneCall(tempCall);

    assertThat(servlet.getDefinition(customer), equalTo(tempBill));*/
  }



}
