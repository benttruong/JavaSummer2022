package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
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
    assertThat(readBill.getCustomer(), containsString(customer));
    PhoneCall readCall = (PhoneCall) readBill.getPhoneCalls().toArray()[0];
    assertThat(readCall.getCaller(), equalTo(caller));
    assertThat(readCall.getCallee(), equalTo(callee));
    assertThat(readCall.getBeginTimeString(), containsString(beginDate));
    assertThat(readCall.getBeginTimeString(), containsString(beginTime));
    assertThat(readCall.getEndTimeString(), containsString(endDate));
    assertThat(readCall.getEndTimeString(), containsString(endTime));
  }

  @Test
  void createNewDirParserCanFindDir() throws ParserException {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);

    String tempDir = "TestDir";
    String fileName = "apptbook.txt";
    File folder = new File(tempDir);

    folder.mkdirs();
    File textFile = new File(tempDir, fileName);
    TextDumper dumper = null;
    try {
      dumper = new TextDumper(new FileWriter(textFile));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    dumper.dump(bill);


    TextParser parser = null;
    try {
      parser = new TextParser(new FileReader(textFile));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    PhoneBill read = parser.parse();
    assertThat(read.getCustomer(), equalTo(customer));
  }

  @Test
  void newPhoneBillWithPhoneCallCanBeDumpedInNewRealFile() throws IOException, ParserException {

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

    String tempDir = "TestDir/AnotherDir";
    String fileName = "newfile.txt";
    File folder = new File(tempDir);

    folder.mkdirs();
    File textFile = new File(tempDir, fileName);
    TextDumper dumper = null;
    try {
      dumper = new TextDumper(new FileWriter(textFile));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    dumper.dump(bill);


    TextParser parser = null;
    try {
      parser = new TextParser(new FileReader(textFile));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    PhoneBill readBill = parser.parse();
    assertThat(readBill.getCustomer(), containsString(customer));
    PhoneCall readCall = (PhoneCall) readBill.getPhoneCalls().toArray()[0];
    assertThat(readCall.getCaller(), equalTo(caller));
    assertThat(readCall.getCallee(), equalTo(callee));
    assertThat(readCall.getBeginTimeString(), containsString(beginDate));
    assertThat(readCall.getBeginTimeString(), containsString(beginTime));
    assertThat(readCall.getEndTimeString(), containsString(endDate));
    assertThat(readCall.getEndTimeString(), containsString(endTime));
  }


}
