package it.uniseats.utils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class QrCodeGenerator {

    public static String generateCode(String s) {

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);

        Random random = new Random(10000);

        int codice = random.nextInt();
        String date = df.format(new Date());
        date = date.replace("/","");

        return codice+"-"+s+"-"+date;

    }
}
