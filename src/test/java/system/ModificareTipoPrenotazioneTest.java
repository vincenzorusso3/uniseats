package system;


import static org.junit.jupiter.api.Assertions.fail;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class ModificareTipoPrenotazioneTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  //TC_1.3.01
  @Test
  public void tipoNonSelezionato() throws Exception {
    driver.get("http://localhost:2222/UniSeats_war_exploded/view/login/LoginView.jsp");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("d.salerno8@studenti.unisa.it");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("carrello");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Visualizza")).click();
    driver.findElement(By.name("modifica")).click();
    driver.findElement(By.linkText("Modifica tipo")).click();
    driver.findElement(By.id("tipo1")).click();
  }

  //TC_1.3_02
  @Test
  public void giornoCorrenteTipologiaGruppo() throws Exception {
    driver.get("http://localhost:2222/UniSeats_war_exploded/view/login/LoginView.jsp");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("d.salerno8@studenti.unisa.it");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("carrello");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Visualizza")).click();
    driver.findElement(By.xpath("(//input[@name='modifica'])[5]")).click();
    driver.findElement(By.linkText("Modifica tipo")).click();
    driver.findElement(By.id("tipo1")).click();
    new Select(driver.findElement(By.id("tipo1"))).selectByVisibleText("Gruppo");
    driver.findElement(By.id("tipo1")).click();
    driver.findElement(By.id("btnTipo")).click();
  }

  //TC_1.2_03
  @Test
  public void modificaTipoOk() throws Exception {
    driver.get("http://localhost:2222/UniSeats_war_exploded/view/login/LoginView.jsp");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("s.silvestri15@studenti.unisa.it");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("admin");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Visualizza")).click();
    driver.findElement(By.xpath("(//input[@name='modifica'])[3]")).click();
    driver.findElement(By.linkText("Modifica tipo")).click();
    driver.findElement(By.id("btnTipo")).click();
  }

  //TC_1.2_04
  @Test
  public void modificaTipoOk2() throws Exception {
    driver.get("http://localhost:2222/UniSeats_war_exploded/view/login/LoginView.jsp");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("d.salerno8@studenti.unisa.it");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("carrello");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Visualizza")).click();
    driver.findElement(By.name("modifica")).click();
    driver.findElement(By.linkText("Modifica tipo")).click();
    driver.findElement(By.id("tipo1")).click();
    new Select(driver.findElement(By.id("tipo1"))).selectByVisibleText("Gruppo");
    driver.findElement(By.id("tipo1")).click();
    driver.findElement(By.id("btnTipo")).click();
  }

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
