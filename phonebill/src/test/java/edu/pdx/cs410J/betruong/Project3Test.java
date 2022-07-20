package edu.pdx.cs410J.betruong;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A unit test for code in the <code>Project2</code> class.  This is different
 * from <code>Project2IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project3Test {

  /**
   * Test reading README as Resource prints README to standard out
   */
  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project3.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("Project3 - Ben Truong"));
    }
  }



  /**
   * Tests <code>isValidFilePath</code> with different correct files returns true values
   */
  @Test
  void validFilePathReturnsTrue(){
    boolean path1 = Project3.isValidFilePath("TestDir/test.com");
    assertTrue(path1);
    boolean path2 = Project3.isValidFilePath("test.com");
    assertTrue(path2);
    boolean path3 = Project3.isValidFilePath("TestDir/aNotherdir/test.com");
    assertTrue(path3);
  }

  /**
   * Tests <code>isValidFilePath</code> with different malformed files returns false values
   */
  @Test
  void invalidFilePathReturnsInvalidFilePath(){
    assertFalse(Project3.isValidFilePath("TestDir/"));
    assertFalse(Project3.isValidFilePath("/text.csv"));
    assertFalse(Project3.isValidFilePath("/text.doc"));
    assertFalse(Project3.isValidFilePath("//text.doc"));
    assertFalse(Project3.isValidFilePath("text"));
    assertFalse(Project3.isValidFilePath("text.doc/"));
  }





  /**
   * Tests <code>getPath</code> with different files returns expected values
   */
  @Test
  void fileReturnsCorrectFilePath(){
    assertEquals(Project3.getPath("TestDir/AnotherDir/Ben.doc"), "TestDir/AnotherDir");
    assertNull(Project3.getPath("Ben.doc"));
  }

  /**
   * Tests <code>getFileName</code> with different files returns expected values
   */
  @Test
  void fileReturnsCorrectFileName(){
    assertEquals(Project3.getFileName("TestDir/AnotherDir/Ben.doc"), "Ben.doc");
    assertEquals(Project3.getFileName("Ben.doc"), "Ben.doc");
  }

  @Test
  void testPrettyPhoneBillGetsCorrectFormat() throws PhoneCall.PhoneCallException {
    PhoneBill bill = new PhoneBill("Ben Truong");
    String caller = "111-111-1111";
    String beginDate = "7/19/2022";
    String beginTime = "10:30";
    String beginMeridiem = "AM";
    String endDate = "7/20/2022";
    String endTime = "11:00";
    String endMeridiem = "AM";
    PhoneCall call = new PhoneCall(caller, caller, beginDate, beginTime, beginMeridiem, endDate, endTime, endMeridiem);
    bill.addPhoneCall(call);
    bill.addPhoneCall(call);

    assertThat(bill.getPrettyBillString(), containsString("Customer's name: "));
    assertThat(bill.getPrettyBillString(), containsString("This phone bill contains "));
  }





}
