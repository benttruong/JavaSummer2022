package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;

import java.io.PrintStream;
import java.io.PrintWriter;

public class PrettyPrinter {
  private final PrintStream writer;

  @VisibleForTesting
  static String formatCallCount(int count )
  {
    return String.format( "This bill contains %d phone call(s)", count );
  }

  @VisibleForTesting
  static String formatPhoneCalls(PhoneCall call )
  {
    return call.getPrettyCallString();
  }


  public PrettyPrinter(PrintStream writer) {
    this.writer = writer;
  }

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
