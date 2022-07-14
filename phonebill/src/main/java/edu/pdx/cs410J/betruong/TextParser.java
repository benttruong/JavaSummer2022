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

  /*@Override
  public PhoneBill parse() throws ParserException {
    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {

      String customer = br.readLine();

      if (customer == null) {
        throw new ParserException("Missing customer");
      }

      return new PhoneBill(customer);

    } catch (IOException e) {
      throw new ParserException("While parsing phone bill text", e);
    }
  }*/

  // a new parse method to test functionality of reading phonebills
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
     /* while (br.ready()){
        String caller = br.readLine();
        String callee = br.readLine();
        String beginTimeString = br.readLine();
        String beginDate = beginTimeString.substring(0, beginTimeString.indexOf(" "));
        String beginTime = beginTimeString.substring(beginTimeString.indexOf(" "));
        String endTimeString = br.readLine();
        String endDate = endTimeString.substring(0, endTimeString.indexOf(" "));
        String endTime = endTimeString.substring(endTimeString.indexOf(" "));
        bill.addPhoneCall(new PhoneCall(caller, callee, beginDate, beginTime, endDate, endTime));
      }*/
      return bill;

    } catch (IOException e) {
      throw new ParserException("While parsing phone bill text", e);
    }
  }


}
