package edu.pdx.cs410J.betruong;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * This class represents a <code>TextParser</code>
 */

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
        if (values.length != 9){
          throw new ParserException("Data is malformed");
        }
        if (PhoneCall.isValidPhoneNumber(values[1]) &&
            PhoneCall.isValidPhoneNumber(values[2]) &&
            PhoneCall.isValidDate(values[3]) &&
            PhoneCall.isValidTime(values[4]) &&
            PhoneCall.isValidMeridiem(values[5])&&
            PhoneCall.isValidDate(values[6]) &&
            PhoneCall.isValidTime(values[7]) &&
            PhoneCall.isValidMeridiem(values[8])){
          try {
            bill.addPhoneCall(new PhoneCall(values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]));
          } catch (PhoneCall.PhoneCallException e) {
            System.err.println(e.getMessage());
            throw new PhoneCall.PhoneCallException();
          }
        }
        else {
          throw new ParserException("Phone call from file has malformed data");
        }
        callDetails = br.readLine();
      }
      return bill;

    } catch (IOException | PhoneCall.PhoneCallException e) {
      throw new ParserException("While parsing phone bill text", e);
    }
  }


}
