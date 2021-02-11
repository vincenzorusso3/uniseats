package system;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * System testing Prenotazione.
 */
public class PrenotarePostoTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  /**
   * Before.
   *
   * @throws Exception exception
   */
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  /**
   * Test data non selezionata.
   *
   * @throws Exception exception
   */
  //TC_1.4_01
  @Test
  public void dataNonSelezionata() throws Exception {
    driver.get("http://localhost:2222/UniSeats_war_exploded/view/login/LoginView.jsp");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("d.salerno8@studenti.unisa.it");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("carrello");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.xpath("(//a[contains(text(),'Prenota')])[3]")).click();
    driver.findElement(By.id("calendar")).click();
    driver.findElement(By.xpath("//div[3]")).click();
  }

  /**
   * Test data selezionata non valida.
   *
   * @throws Exception exception
   */
  //TC_1.4_02
  @Test
  public void dataSelezionataNonValida() throws Exception {
    driver.get("http://localhost:2222/UniSeats_war_exploded/view/login/LoginView.jsp");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("d.salerno8@studenti.unisa.it");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("carrello");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.xpath("(//a[contains(text(),'Prenota')])[3]")).click();
    driver.findElement(By.id("calendar")).click();
  }

  /**
   * Test data valida ma tipo non selezionato.
   *
   * @throws Exception exception
   */
  //TC_1.2_03
  @Test
  public void dataValidaTipoNonSelezionato() throws Exception {
    driver.get("http://localhost:2222/UniSeats_war_exploded/view/login/LoginView.jsp");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("d.salerno8@studenti.unisa.it");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("carrello");
    driver.findElement(By.xpath("(//input[@value='Login'])[2]")).click();
    driver.findElement(By.xpath("(//a[contains(text(),'Prenota')])[3]")).click();
    driver.findElement(By.id("calendar")).click();
    driver.findElement(By.id("calendar")).clear();
    driver.findElement(By.id("calendar")).sendKeys("2021-02-05");
  }

  /**
   * Test data valida e tipo selezionato.
   *
   * @throws Exception exception
   */
  //TC_1.2_04
  @Test
  public void dataValidaTipoSelezionato() throws Exception {
    driver.get("http://localhost:2222/UniSeats_war_exploded/view/login/LoginView.jsp");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("d.salerno8@studenti.unisa.it");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("carrello");
    driver.findElement(By.xpath("(//input[@value='Login'])[2]")).click();
    driver.findElement(By.xpath("(//a[contains(text(),'Prenota')])[3]")).click();
    driver.findElement(By.id("calendar")).click();
    driver.findElement(By.id("calendar")).clear();
    driver.findElement(By.id("calendar")).sendKeys("2021-02-12");
    driver.findElement(By.id("btnPrenotazioneGruppo")).click();
  }

  /**
   * After.
   *
   * @throws Exception exception
   */
  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}