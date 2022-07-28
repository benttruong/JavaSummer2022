package edu.pdx.cs410J.betruong;


import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

public class TextDumper {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  public void dump(Map<String, String> dictionary) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ){
      for (Map.Entry<String, String> entry : dictionary.entrySet()) {
        pw.println(entry.getKey() + " : " + entry.getValue());
      }

      pw.flush();
    }
  }

  public void billdump(Map<String, PhoneBill> bills){
    try (
            PrintWriter pw = new PrintWriter(this.writer)
    ) {
      for (Map.Entry<String, PhoneBill> bill: bills.entrySet()){
        for (PhoneCall call: bill.getValue().getPhoneCalls())
        pw.println("Phone call from " + call.getCaller() + " to " + call.getCallee() + " from " + call.getBeginTimeLiterals() + " to " + call.getEndTimeLiterals());
      }
      pw.flush();
    }
  }


  public void calldump(PhoneCall call) {
    try (
            PrintWriter pw = new PrintWriter(this.writer)
    ) {
        pw.println("Phone call from " + call.getCaller() + " to " + call.getCallee() + " from " + call.getBeginTimeLiterals() + " to " + call.getEndTimeLiterals());
        // pw.println(call.getPrettyCallString());
        pw.flush();
    }
  }
}
