package edu.pdx.cs410J.betruong;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    boolean testDate = Project2.isValidDate("03/03/2022");
    boolean testDate2 = Project2.isValidDate("05/04/2022");

    assertTrue(testDate);
    assertTrue(testDate2);
  }
  @Test
  void validFilePathReturnsTrue(){
    boolean path1 = Project2.isValidFilePath("TestDir/test.com");
    assertTrue(path1);
    boolean path2 = Project2.isValidFilePath("test.com");
    assertTrue(path2);
    boolean path3 = Project2.isValidFilePath("TestDir/aNotherdir/test.com");
    assertTrue(path3);
  }
  @Test
  void invalidFilePathReturnsInvalidFilePath(){
    assertFalse(Project2.isValidFilePath("TestDir/"));
    assertFalse(Project2.isValidFilePath("/text.csv"));
    assertFalse(Project2.isValidFilePath("/text.doc"));
    assertFalse(Project2.isValidFilePath("//text.doc"));
    assertFalse(Project2.isValidFilePath("text"));
    assertFalse(Project2.isValidFilePath("text.doc/"));
  }



}
