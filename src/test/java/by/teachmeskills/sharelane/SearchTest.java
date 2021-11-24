package by.teachmeskills.sharelane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchTest {

    WebDriver driver;

    @BeforeSuite
    public void beforeSuite() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @AfterSuite
    public void afterSuite() {
        driver.quit();
    }

    @BeforeMethod
    public void setUp() {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
    }

    @Test
    public void bookPageShouldBeOpened() {

        List<WebElement> bookElements = driver.findElements(By.xpath("//table[not(@class) and @align='center']//table"));
        WebElement firstBook = bookElements.get(0);

        String bookTitle = firstBook.findElement(By.xpath(".//tr[3]")).getText();
        driver.findElement(By.name("keyword")).sendKeys(bookTitle);
        driver.findElement(By.cssSelector("form[action='./search.py']")).submit();

        boolean isBookOpened = driver.findElement(By.xpath("//p[normalize-space()='" + bookTitle + "']")).isDisplayed();
        Assert.assertTrue(isBookOpened, "Страница книги не отркылась!");
    }
}
