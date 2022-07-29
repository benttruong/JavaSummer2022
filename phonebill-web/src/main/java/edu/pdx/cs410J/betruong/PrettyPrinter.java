package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * A class used to pretty print a Phone Bill or a Phone Call to PrintStream
 */
public class PrettyPrinter {
  private final PrintStream writer;

  /**
   * Method to pretty print the number of phone calls from a phone bill
   * @param count
   *        Number of phone call
   * @return a <code>String</code> in a pretty format
   */
  @VisibleForTesting
  static String formatCallCount(int count )
  {
    return String.format( "This bill contains %d phone call(s)", count );
  }

  /**
   * Method to format the phone call to be pretty
   *
   * @param call
   *        A Phone Call
   * @return
   *        A <code>String</code> of the phone call in the pretty format
   */
  @VisibleForTesting
  static String formatPhoneCalls(PhoneCall call )
  {
    return call.getPrettyCallString();
  }


  public PrettyPrinter(PrintStream writer) {
    this.writer = writer;
  }

  /**
   * Method to format a phone bill to be pretty
   * @param bill
   *        A phone bill
   */
  @VisibleForTesting
  public void billDump(PhoneBill bill) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ) {
      pw.println("Customer: " + bill.getCustomer());
      pw.println(formatCallCount(bill.getPhoneCalls().size()));
      pw.println("===============================================");
      int i = 1;
      for (PhoneCall call : bill.getPhoneCalls()) {
        pw.print(i + ". ");
        pw.println(formatPhoneCalls(call));
      }
      pw.println("===============================================");

      pw.flush();
    }
  }
  @VisibleForTesting
  public void callDump(PhoneCall call) {
    try (
            PrintWriter pw = new PrintWriter(this.writer)
    ){
      pw.println(formatPhoneCalls(call));
      pw.flush();
    }

  }
}
