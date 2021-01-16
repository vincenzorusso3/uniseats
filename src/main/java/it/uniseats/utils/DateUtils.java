package it.uniseats.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);


    public static Date parseDate(String date) throws ParseException {
        date = date.replace("-","/");
        return df.parse(date);
    }

    public static String dateToString(Date date){
        return df.format(date).replace("-","/");
    }

}
