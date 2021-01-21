package utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.uniseats.utils.QrCodeGenerator;
import org.junit.jupiter.api.Test;


class QrCodeGeneratorTest {

  @Test
  void generateCode() {
    String code = QrCodeGenerator.generateCode("0111111111", "21/01/2021");
    assertNotNull(code);
  }

}