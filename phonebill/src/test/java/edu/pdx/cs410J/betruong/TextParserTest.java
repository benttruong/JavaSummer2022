package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {

  @Test
  void validTextFileCanBeParsed() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-phonebill.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    PhoneBill bill = parser.parse();
    assertThat(bill.getCustomer(), equalTo("Test Phone Bill"));
  }

  @Test
  void invalidTextFileThrowsParserException() {
    InputStream resource = getClass().getResourceAsStream("empty-phonebill.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  @Test
  @Disabled
  void validNon_ReSourceTextFileReturnsCorrectValues() throws ParserException {
    String tempDir = "TestDir/AnotherDir";
    String fileName = "newfile.txt";

    File textFile = new File(tempDir, fileName);
    TextParser parser = null;
    try {
      parser = new TextParser(new FileReader(textFile));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    PhoneBill bill = parser.parse();
    assertThat(bill.getCustomer(), containsString("Test Phone Bill"));
    ArrayList<PhoneCall> calls = (ArrayList<PhoneCall>) bill.getPhoneCalls();
    PhoneCall call = calls.get(0);
    assertThat(call.getCaller(), containsString("123-456-789"));
  }



}
