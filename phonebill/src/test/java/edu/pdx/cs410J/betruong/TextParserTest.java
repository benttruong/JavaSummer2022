package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A unit test for code in the <code>TextParserTest</code> class.
 */
public class TextParserTest {

  /**
   * Test a valid-phonebill.txt from resource with customer's name "Test Phone Bill"
   * correctly parse the phone bill with matching name
   * @throws ParserException
   */
  @Test
  void validTextFileCanBeParsed() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-phonebill.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    PhoneBill bill = parser.parse();
    assertThat(bill.getCustomer(), equalTo("Test Phone Bill"));
  }

  /**
   * Test that parsing an empty phone bill would
   * @throws ParserException for missing customer
   */
  @Test
  void invalidTextFileThrowsParserException() {
    InputStream resource = getClass().getResourceAsStream("empty-phonebill.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  @Test
  void badYearTextFileThrowsParserException() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("bad-year.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }


}
