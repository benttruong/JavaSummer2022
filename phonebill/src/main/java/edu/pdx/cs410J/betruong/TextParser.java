package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class TextParser implements PhoneBillParser<PhoneBill> {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }


  @Override
  public PhoneBill parse() throws ParserException {
    try (
            BufferedReader br = new BufferedReader(this.reader)
    ) {

      String customer = br.readLine();

      if (customer == null) {
        throw new ParserException("Missing customer");
      }

      PhoneBill bill = new PhoneBill(customer);

      String callDetails = br.readLine();

      while (callDetails != null){
        String [] values = callDetails.split("Phone call from | to | from | ");

        bill.addPhoneCall(new PhoneCall(values[1], values[2], values[3], values[4], values[5], values[6]));

        callDetails = br.readLine();
      }
      return bill;

    } catch (IOException e) {
      throw new ParserException("While parsing phone bill text", e);
    }
  }


}
