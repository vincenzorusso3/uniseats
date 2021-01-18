import it.uniseats.utils.QrCodeGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QrCodeGeneratorTest {

    @Test
    void generateCode() {
        String code = QrCodeGenerator.generateCode("0111111111");
        assertNotNull(code);
    }

}