package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import it.uniseats.utils.DateUtils;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

  @Test
  void parseDateTest() throws ParseException {
    Date date = DateUtils.parseDate("12-01-2021");
    Date expected = new GregorianCalendar(2021, Calendar.JANUARY, 12).getTime();
    assertEquals(date, expected);
  }

  @Test
  void parseDateTestFail() {
    assertThrows(ParseException.class, () -> {
      DateUtils.parseDate("aaa");
    });
  }

  @Test
  void dateToStringTest() {
    Date date = new GregorianCalendar(2021, Calendar.JANUARY, 12).getTime();
    String dateString = DateUtils.dateToString(date);
    assertEquals(dateString, "12/01/21");
  }

  @Test
  void englishToItalianTestTest() {
    String dateString = DateUtils.englishToItalian("2021/01/21");
    assertEquals(dateString, "21/01/2021");
  }

}