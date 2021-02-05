package system;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class PrenotarePostoGiornoCorrenteTest {
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
    //TC_1.4_01
    @Test
    public void DataNonSelezionata() throws Exception {
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
    //TC_1.4_02
    @Test
    public void DataNonValida() throws Exception {
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
    //TC_1.4_03
    @Test
    public void DataValidaTipoNonSelezionato() throws Exception {
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
        driver.findElement(By.id("calendar")).sendKeys("2021-02-04");
    }
    //TC_1.4_04
    @Test
    public void DataValidaTipoSelezionato() throws Exception {
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
        driver.findElement(By.id("calendar")).sendKeys("2021-07-04");
        driver.findElement(By.id("btnPrenotazioneSingola")).click();
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
