package by.teachmeskills.sharelane;

import by.teachmeskills.sharelane.model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class RegisterTest {

/*    public User register() {
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

        String email = driver.findElement(By.xpath("//tr[./td[.='Email']]/td[2]")).getText();
        String password = driver.findElement(By.xpath("//tr[./td[.='Password']]/td[2]")).getText();

        User user = new User(email, password);
        return user;
    }

    @Test
    public void test2() {
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
    }*/
}
