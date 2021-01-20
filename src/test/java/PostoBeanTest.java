import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.uniseats.model.beans.PostoBean;
import org.junit.jupiter.api.Test;

class PostoBeanTest {

  /**
   * Constructors Testing.
   */

  @Test
  void testEmptyPostoConstructor() {
    PostoBean posto = new PostoBean();
    assertNotNull(posto);
  }

  @Test
  void testPostoConstructor() {
    PostoBean posto = new PostoBean("A1-01", "A1");
    assertNotNull(posto);
  }

  /**
   * Getter Methods Testing.
   */

  @Test
  void testGetCodice() {
    PostoBean posto = new PostoBean("A1-01", "A1");
    assertEquals("A1-01", posto.getCodice());
  }

  @Test
  void testGetCodiceAula() {
    PostoBean posto = new PostoBean("A1-01", "A1");
    assertEquals("A1", posto.getCodiceAula());
  }

  /**
   * Setter Metods Testing.
   */

  @Test
  void testSetCodice() {
    PostoBean posto = new PostoBean();
    posto.setCodice("A1-01");
    assertEquals("A1-01", posto.getCodice());
  }

  @Test
  void testSetCodiceAula() {
    PostoBean posto = new PostoBean();
    posto.setCodiceAula("A1");
    assertEquals("A1", posto.getCodiceAula());
  }

}