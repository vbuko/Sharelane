package by.teachmeskills.sharelane;

import by.teachmeskills.sharelane.model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class SignUpTest {

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

    @Test
    public void zipCodeIsAcceptedWhen5Digits() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("11111");
        driver.findElement(By.cssSelector("[value='Continue']")).click();

        WebElement registerButton = driver.findElement(By.cssSelector("[value='Register']"));
        Assert.assertTrue(registerButton.isDisplayed(), "Форма регистрации не открылась");
    }

    @Test
    public void zipCodeValidationErrorAppearsWhenDigitsLessThan5() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("1111");
        driver.findElement(By.cssSelector("[value='Continue']")).click();

        String errorMessage = driver.findElement(By.className("error_message")).getText();
        Assert.assertEquals(errorMessage, "Oops, error on page. ZIP code should have 5 digits",
                "Ошибка валидации zip code не верна!");
    }

    @Test
    public void userShouldBeRegistered() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        WebElement el = driver.findElement(By.name("zip_code"));
        el.sendKeys("11111");
        driver.findElement(By.cssSelector("form[action='./register.py']")).submit();

        driver.findElement(By.name("first_name")).sendKeys("Vadim");
        driver.findElement(By.name("last_name")).sendKeys("Buko");
        driver.findElement(By.name("email")).sendKeys("test@test.com");
        driver.findElement(By.name("password1")).sendKeys("11111");
        driver.findElement(By.name("password2")).sendKeys("11111");

        driver.findElement(By.cssSelector("[value='Register']")).click();

        String confirmationMessage = driver.findElement(By.className("confirmation_message")).getText();
        Assert.assertEquals(confirmationMessage, "Account is created!", "Сообщение подтверждения " +
                "регистрации не верно");
    }

    @Test
    public void userShouldLogIn() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        WebElement el = driver.findElement(By.name("zip_code"));
        el.sendKeys("11111");
        driver.findElement(By.cssSelector("form[action='./register.py']")).submit();

        driver.findElement(By.name("first_name")).sendKeys("Vadim");
        driver.findElement(By.name("last_name")).sendKeys("Buko");
        driver.findElement(By.name("email")).sendKeys("test@test.com");
        driver.findElement(By.name("password1")).sendKeys("11111");
        driver.findElement(By.name("password2")).sendKeys("11111");

        driver.findElement(By.cssSelector("[value='Register']")).click();

        String confirmationMessage = driver.findElement(By.className("confirmation_message")).getText();
        Assert.assertEquals(confirmationMessage, "Account is created!", "Сообщение подтверждения " +
                "регистрации не верно");

        String email = driver.findElement(By.xpath("//tr[./td[.='Email']]/td[2]")).getText();
        String password = driver.findElement(By.xpath("//tr[./td[.='Password']]/td[2]")).getText();

        User user = new User(email, password);

        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        driver.findElement(By.name("email")).sendKeys(user.getEmail());
        driver.findElement(By.name("password")).sendKeys(user.getPassword());
        driver.findElement(By.cssSelector("[value='Login']")).click();

        WebElement greetings = driver.findElement(By.className("user"));
        Assert.assertTrue(greetings.isDisplayed(), "Приветствие зарегистрированного пользователя не появилось");
    }
}

