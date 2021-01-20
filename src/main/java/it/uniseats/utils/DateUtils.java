package it.uniseats.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

  private static final DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);

  public static Date parseDate(String date) throws ParseException {
    date = date.replace("-", "/");
    return df.parse(date);
  }

  public static String dateToString(Date date) {
    return df.format(date).replace("-", "/");
  }

  public static String englishToItalian(String s) {

    String year = (String) s.subSequence(0, 4);
    String month = (String) s.subSequence(5, 7);
    String day = (String) s.subSequence(8, 10);
    return day + "/" + month + "/" + year;

  }

}
