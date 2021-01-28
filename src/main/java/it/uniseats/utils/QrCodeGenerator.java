package it.uniseats.utils;
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

    Random random = new Random();
    int codice = random.nextInt(10000);

    return codice + "-" + matricola + "-" + data;

  }

}
