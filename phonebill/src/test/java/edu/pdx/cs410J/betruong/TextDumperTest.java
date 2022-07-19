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
/**
 * A unit test for code in the <code>TextDumperTest</code> class.
 */
public class TextDumperTest {

  /**
   * Test that the <code>TextDumper</code> would correctly
   * dump files in correct text format
   */
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

  /**
   * Test that the <code>TextParser</code> can
   * correctly parse text files generate by <code>TextDumper</code>
   * @param tempDir
   * @throws IOException
   * @throws ParserException
   */
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

  /**
   * Test that a newly created phone bill can be dumped by <code>TextDumper</code>
   * and its member fields would be correctly parsed by <code>TextParser</code>
   * @param tempDir
   *        is used as a new file path to dump phone bill
   * @throws IOException
   *        IOException can be thrown when creating new <code>FileWriter</code>
   * @throws ParserException
   *        ParserException can be thrown when parsing an empty text file
   */
  @Test
  void newPhoneBillWithPhoneCallCanBeDumpedInNewFile(@TempDir File tempDir) throws IOException, ParserException {

    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);
    String caller = "123-456-7890";
    String callee = "333-456-7890";
    String beginDate = "12/15/2022";
    String beginTime = "6:00";
    String endDate = "12/16/2022";
    String endTime = "7:15";
    PhoneCall call = new PhoneCall(caller, callee, beginDate, beginTime, "AM", endDate, endTime, "PM");
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
    assertThat(readCall.getBeginTimeLiterals(), containsString(beginDate));
    assertThat(readCall.getBeginTimeLiterals(), containsString(beginTime));
    assertThat(readCall.getEndTimeLiterals(), containsString(endDate));
    assertThat(readCall.getEndTimeLiterals(), containsString(endTime));
  }

  /**
   * Test that a new phone bill can be dumped by <code>TextDumper</code>
   * in a specific directory and can be correctly parsed by <code>TextParser</code>
   * @throws ParserException
   *         ParserException can be thrown if parsing from an empty file
   */
  @Test
  @Disabled
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

  /**
   * More tests when a newly created phone bill can be correctly dumped
   * and parsed and file is not in memory
   * @throws IOException
   *         IOException can be thrown when creating new <code>FileWriter</code>
   * @throws ParserException
   *         ParserException can be thrown when parsing an empty text file
   */
  @Test
  @Disabled
  void newPhoneBillWithPhoneCallCanBeDumpedInNewRealFile() throws IOException, ParserException {

    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);
    String caller = "123-456-7890";
    String callee = "333-456-7890";
    String beginDate = "12/15/2022";
    String beginTime = "6:00";
    String endDate = "12/16/2022";
    String endTime = "7:15";
    PhoneCall call = new PhoneCall(caller, callee, beginDate, beginTime, "AM", endDate, endTime, "PM");
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
    assertThat(readCall.getBeginTimeLiterals(), containsString(beginDate));
    assertThat(readCall.getBeginTimeLiterals(), containsString(beginTime));
    assertThat(readCall.getEndTimeLiterals(), containsString(endDate));
    assertThat(readCall.getEndTimeLiterals(), containsString(endTime));
  }


}
