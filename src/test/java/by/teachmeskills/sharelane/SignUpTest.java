package by.teachmeskills.sharelane;

import by.teachmeskills.sharelane.model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.List;
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

    @AfterMethod
    public void tearDown() {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        if (driver.findElements(By.linkText("Logout")).size() > 0) {
            driver.findElements(By.linkText("Logout")).get(0).click();
        }
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
        Assert.assertTrue(greetings.isDisplayed(), "Приветствие зарегистрированного пользователя не появилось!");
    }

    @Test
    public void registerValidationErrorShouldAppearWhenFirstNameIsEmpty() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        WebElement el = driver.findElement(By.name("zip_code"));
        el.sendKeys("11111");
        driver.findElement(By.cssSelector("form[action='./register.py']")).submit();

        driver.findElement(By.name("last_name")).sendKeys("Buko");
        driver.findElement(By.name("email")).sendKeys("test@test.com");
        driver.findElement(By.name("password1")).sendKeys("11111");
        driver.findElement(By.name("password2")).sendKeys("11111");

        driver.findElement(By.cssSelector("[value='Register']")).click();

        String errorMessage = driver.findElement(By.className("error_message")).getText();
        Assert.assertEquals(errorMessage, "Oops, error on page. Some of your fields have invalid data or email was previously used",
                "Валидационное сообщение формы регистрации не верно!");
    }

    @Test
    public void registerValidationErrorShouldAppearWhenEmailIsEmpty() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        WebElement el = driver.findElement(By.name("zip_code"));
        el.sendKeys("11111");
        driver.findElement(By.cssSelector("form[action='./register.py']")).submit();

        driver.findElement(By.name("first_name")).sendKeys("Vadim");
        driver.findElement(By.name("last_name")).sendKeys("Buko");
        driver.findElement(By.name("password1")).sendKeys("11111");
        driver.findElement(By.name("password2")).sendKeys("11111");

        driver.findElement(By.cssSelector("[value='Register']")).click();

        String errorMessage = driver.findElement(By.className("error_message")).getText();
        Assert.assertEquals(errorMessage, "Oops, error on page. Some of your fields have invalid data or email was previously used",
                "Валидационное сообщение формы регистрации не верно!");
    }

    @Test
    public void registerValidationErrorShouldAppearWhenPasswordIsEmpty() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        WebElement el = driver.findElement(By.name("zip_code"));
        el.sendKeys("11111");
        driver.findElement(By.cssSelector("form[action='./register.py']")).submit();

        driver.findElement(By.name("first_name")).sendKeys("Vadim");
        driver.findElement(By.name("last_name")).sendKeys("Buko");
        driver.findElement(By.name("email")).sendKeys("test@test.com");
        driver.findElement(By.name("password2")).sendKeys("11111");

        driver.findElement(By.cssSelector("[value='Register']")).click();

        String errorMessage = driver.findElement(By.className("error_message")).getText();
        Assert.assertEquals(errorMessage, "Oops, error on page. Some of your fields have invalid data or email was previously used",
                "Валидационное сообщение формы регистрации не верно!");
    }

    @Test
    public void registerValidationErrorShouldAppearWhenConfirmPasswordIsEmpty() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        WebElement el = driver.findElement(By.name("zip_code"));
        el.sendKeys("11111");
        driver.findElement(By.cssSelector("form[action='./register.py']")).submit();

        driver.findElement(By.name("first_name")).sendKeys("Vadim");
        driver.findElement(By.name("last_name")).sendKeys("Buko");
        driver.findElement(By.name("email")).sendKeys("test@test.com");
        driver.findElement(By.name("password1")).sendKeys("11111");

        driver.findElement(By.cssSelector("[value='Register']")).click();

        String errorMessage = driver.findElement(By.className("error_message")).getText();
        Assert.assertEquals(errorMessage, "Oops, error on page. Some of your fields have invalid data or email was previously used",
                "Валидационное сообщение формы регистрации не верно!");
    }

    @Test
    public void passwordFieldShouldBeMasked() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        WebElement el = driver.findElement(By.name("zip_code"));
        el.sendKeys("11111");
        driver.findElement(By.cssSelector("form[action='./register.py']")).submit();

        driver.findElement(By.name("first_name")).sendKeys("Vadim");
        driver.findElement(By.name("last_name")).sendKeys("Buko");
        driver.findElement(By.name("email")).sendKeys("test@test.com");
        String passwordType = driver.findElement(By.name("password2")).getAttribute("type");

        Assert.assertEquals(passwordType, "password", "Поле пароль не не имеет маскировки");
    }

    @Test
    public void bookPageShouldBeOpened() {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");

        List<WebElement> bookElements = driver.findElements(By.xpath("//table[not(@class) and @align='center']//table"));
        WebElement firstBook = bookElements.get(0);

        String bookTitle = firstBook.findElement(By.xpath(".//tr[3]")).getText();
        driver.findElement(By.name("keyword")).sendKeys(bookTitle);
        driver.findElement(By.cssSelector("form[action='./search.py']")).submit();

        boolean isBookOpened = driver.findElement(By.xpath("//p[normalize-space()='" + bookTitle + "']")).isDisplayed();
        Assert.assertTrue(isBookOpened, "Страница книги не отркылась!");
    }

    @Test
    public void bookShouldBeAddedToShoppingCart() {
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
        Assert.assertTrue(greetings.isDisplayed(), "Приветствие зарегистрированного пользователя не появилось!");

        List<WebElement> bookElements = driver.findElements(By.xpath("//table[not(@class) and @align='center']//table"));
        WebElement firstBook = bookElements.get(0);
        String bookTitle = firstBook.findElement(By.xpath(".//tr[3]")).getText();
        firstBook.click();

        boolean isBookOpened = driver.findElement(By.xpath("//p[normalize-space()='" + bookTitle + "']")).isDisplayed();
        Assert.assertTrue(isBookOpened, "Страница книги не открылась!");

        driver.findElement(By.cssSelector("a[href*='./add_to_cart.py']")).click();
        driver.findElement(By.cssSelector("a[href*='./shopping_cart.py']")).click();

        String actualAddedBook = driver.findElement(By.xpath("//table[@align='center']//tr[2]/td[2]")).getText();
        Assert.assertEquals(actualAddedBook, bookTitle, "Неверная книга добавлена");
    }
}