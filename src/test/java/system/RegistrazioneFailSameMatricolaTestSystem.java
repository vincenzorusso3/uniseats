package system;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class RegistrazioneFailSameMatricolaTestSystem {
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

  @Test
  public void testRegistrazioneFailSameMatricolaTestSystem() throws Exception {
    driver.get("http://localhost:2222/UniSeats_war_exploded/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.linkText("Registrati")).click();
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("test@studenti.unisa.it");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("123456789");
    driver.findElement(By.xpath("//form[@id='registrazione']/div[3]")).click();
    driver.findElement(By.id("nome")).click();
    driver.findElement(By.id("nome")).clear();
    driver.findElement(By.id("nome")).sendKeys("test");
    driver.findElement(By.id("cognome")).clear();
    driver.findElement(By.id("cognome")).sendKeys("test");
    driver.findElement(By.id("matricola")).click();
    driver.findElement(By.id("matricola")).clear();
    driver.findElement(By.id("matricola")).sendKeys("0512105881");
    driver.findElement(By.id("dipartimento")).click();
    new Select(driver.findElement(By.id("dipartimento"))).selectByVisibleText("Informatica");
    driver.findElement(By.id("dipartimento")).click();
    driver.findElement(By.xpath("//input[@value='Registrati']")).click();
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
