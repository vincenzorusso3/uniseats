package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.uniseats.utils.Sha512Utils;
import org.junit.jupiter.api.Test;

class Sha512UtilsTest {

  @Test
  void getSha512() {
    String s = Sha512Utils.getSHA512("IS2021");
    assertEquals(
        "5df6971c10674881c7e7aacd00c76c9ac1ed98d5b2116c3301d510d07ccb8122d709fb49cfc4b85"
            + "f8707d9ac75abb94212a3b2dee2d106c98d8d25b906f0fff5",
        s);
  }

}