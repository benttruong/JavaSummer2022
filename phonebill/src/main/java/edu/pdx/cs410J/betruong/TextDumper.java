package edu.pdx.cs410J.betruong;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AppointmentBookDumper;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 *
 */
public class TextDumper implements PhoneBillDumper<PhoneBill> {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }
  @VisibleForTesting
  @Override
  public void dump(PhoneBill bill) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ) {
      pw.println(bill.getCustomer());
      for (PhoneCall call: bill.getPhoneCalls()){
        // pw.println(call);
        pw.println("Phone call from " + call.getCaller() + " to " + call.getCallee() + " from " + call.getBeginTimeLiterals() + " to " + call.getEndTimeLiterals());
      }
      pw.flush();
    }
  }
  @VisibleForTesting
  public void dumpPretty(PhoneBill bill) {
    try (
            PrintWriter pw = new PrintWriter(this.writer)
    ) {
      pw.println(bill.getPrettyBillString());
      pw.flush();
    }
  }
}
