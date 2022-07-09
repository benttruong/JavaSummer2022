package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextDumperTest {

  @Test
  void appointmentBookOwnerIsDumpedInTextFormat() {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(bill);

    String text = sw.toString();
    assertThat(text, containsString(customer));
  }

  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);

    File textFile = new File(tempDir, "apptbook.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    TextParser parser = new TextParser(new FileReader(textFile));
    PhoneBill read = parser.parse();
    assertThat(read.getCustomer(), equalTo(customer));
  }

  @Test
  void newPhoneBillWithPhoneCallCanBeDumpedInNewFile(@TempDir File tempDir) throws IOException, ParserException {

    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);
    String caller = "123-456-7890";
    String callee = "333-456-7890";
    String beginDate = "12/15/2022";
    String beginTime = "16:00";
    String endDate = "12/16/2022";
    String endTime = "17:15";
    PhoneCall call = new PhoneCall(caller, callee, beginDate, beginTime, endDate, endTime);
    bill.addPhoneCall(call);

    File textFile = new File(tempDir, "test.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    TextParser parser = new TextParser(new FileReader(textFile));
    PhoneBill readBill = parser.parse();
    PhoneCall readCall = (PhoneCall) readBill.getPhoneCalls().toArray()[0];
    assertThat(readCall.toString(), containsString(caller));
    assertThat(readCall.toString(), containsString(callee));
    assertThat(readCall.toString(), containsString(endDate));
    assertThat(readCall.toString(), containsString(endTime));
    assertThat(readCall.toString(), containsString(beginDate));
    assertThat(readCall.toString(), containsString(beginTime));
  }



}
