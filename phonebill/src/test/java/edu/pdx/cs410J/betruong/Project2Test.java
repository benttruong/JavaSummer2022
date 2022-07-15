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
class Project2Test {

  /**
   * Test reading README as Resource prints README to standard out
   */
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

  /**
   * Tests <code>isValidDate</code> with different dates returns expected values
   */
  @Test
  void inputCorrectDate(){
    boolean testDate = Project2.isValidDate("03/03/2022");
    boolean testDate2 = Project2.isValidDate("05/04/2022");

    assertTrue(testDate);
    assertTrue(testDate2);
  }

  /**
   * Tests <code>isValidFilePath</code> with different correct files returns true values
   */
  @Test
  void validFilePathReturnsTrue(){
    boolean path1 = Project2.isValidFilePath("TestDir/test.com");
    assertTrue(path1);
    boolean path2 = Project2.isValidFilePath("test.com");
    assertTrue(path2);
    boolean path3 = Project2.isValidFilePath("TestDir/aNotherdir/test.com");
    assertTrue(path3);
  }

  /**
   * Tests <code>isValidFilePath</code> with different malformed files returns false values
   */
  @Test
  void invalidFilePathReturnsInvalidFilePath(){
    assertFalse(Project2.isValidFilePath("TestDir/"));
    assertFalse(Project2.isValidFilePath("/text.csv"));
    assertFalse(Project2.isValidFilePath("/text.doc"));
    assertFalse(Project2.isValidFilePath("//text.doc"));
    assertFalse(Project2.isValidFilePath("text"));
    assertFalse(Project2.isValidFilePath("text.doc/"));
  }

  /**
   * Tests <code>isValidDate</code> with different dates formats returns expected values
   */
  @Test
  void isValidDateReturnsCorrectValues(){
    assertFalse(Project2.isValidDate("13/01/1993"));
    assertFalse(Project2.isValidDate("12/35/1993"));
    assertFalse(Project2.isValidDate("14/3/51993"));
    assertTrue(Project2.isValidDate("1/3/2022"));
    assertTrue(Project2.isValidDate("07/08/2022"));
  }

  /**
   * Tests <code>isValidTime</code> with different time formats returns expected values
   */
  @Test
  void isValidTimeReturnsCorrecValues(){
    assertTrue(Project2.isValidTime("12:59"));
    assertTrue(Project2.isValidTime("1:09"));
    assertFalse(Project2.isValidTime("25:1"));
    assertFalse(Project2.isValidTime("1:60"));
  }

  /**
   * Tests <code>getPath</code> with different files returns expected values
   */
  @Test
  void fileReturnsCorrectFilePath(){
    assertEquals(Project2.getPath("TestDir/AnotherDir/Ben.doc"), "TestDir/AnotherDir");
    assertNull(Project2.getPath("Ben.doc"));
  }

  /**
   * Tests <code>getFileName</code> with different files returns expected values
   */
  @Test
  void fileReturnsCorrectFileName(){
    assertEquals(Project2.getFileName("TestDir/AnotherDir/Ben.doc"), "Ben.doc");
    assertEquals(Project2.getFileName("Ben.doc"), "Ben.doc");
  }



}
