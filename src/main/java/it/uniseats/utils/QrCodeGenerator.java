package it.uniseats.utils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Classe per la creazione di una stringa per generare un QRCode.
 */
public class QrCodeGenerator {

  /**
   * Metodo statico per generare una stringa unica.
   *
   * @param matricola e' la matricola dello studente
   * @return la stringa per generare il QRCode
   */
  public static String generateCode(String matricola, String data) {

    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);

    Random random = new Random(10000);
    int codice = random.nextInt();

    return codice + "-" + matricola + "-" + data;

  }
}
