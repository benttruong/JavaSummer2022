package edu.pdx.cs410J.betruong;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A unit test for code in the <code>Project2</code> class.  This is different
 * from <code>Project2IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project2Test {

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project2.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("Project2 - Ben Truong"));
    }
  }
  @Test
  void inputCorrectDate(){
    Project2 result = new Project2();
    boolean testDate = result.isValidDate("03/03/2022");
    boolean testDate2 = result.isValidDate("05/04/2022");

    assertTrue(testDate);
    assertTrue(testDate2);
  }

 /* @Test
  void createCorrectPhoneCallReturnsCorrectPhoneBill(){
    String name = "Pat Geo";
    Project2 result = new Project2();
    PhoneCall callTest = new PhoneCall("123-456-7890", "133-456-7899", "10/12/2022", "15:12", "05/03/2022", "16:25");
  }*/


}
