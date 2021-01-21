package it.uniseats.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Un oggetto DateUtils serve per convertire una data in vari formati
 */

public class DateUtils {

  private static final DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);

  /**
   * Metodo per trasfomare una stringa in Date
   * @param date una <b>data</b> in formato stringa
   * @return <b>data</b>trasformata
   * @throws ParseException
   */
  public static Date parseDate(String date) throws ParseException {
    date = date.replace("-", "/");
    return df.parse(date);
  }

  /**
   * Metodo per trasformare la data in una stringa
   * @param date <b>data</b>
   * @return <b>data</b> in stringa
   */
  public static String dateToString(Date date) {
    return df.format(date).replace("-", "/");
  }

  /**
   * Metodo per trasformare una stringa (data con formato inglese) in stringa (data con formato italiano)
   * @param s <b>stringa</b> da convertire
   * @return <b>stringa</b> convertita
   */
  public static String englishToItalian(String s) {

    String year = (String) s.subSequence(0, 4);
    String month = (String) s.subSequence(5, 7);
    String day = (String) s.subSequence(8, 10);
    return day + "/" + month + "/" + year;

  }

}
