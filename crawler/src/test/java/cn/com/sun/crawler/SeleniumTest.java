package cn.com.sun.crawler;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

/**
 * @Description : Selenium测试
 * @Author : mockingbird
 * @Date : 2021/3/9 10:49
 */
public class SeleniumTest {

    @Test
    public void test() {

        ChromeOptions options = new ChromeOptions();
        options.setBinary(new File("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe"));
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000).getSeconds());
        try {
            driver.get("https://v.kuaishou.com/bQY4VS");

//            driver.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);
//            WebElement firstResult = wait.until(presenceOfElementLocated(By.cssSelector("h3>div")));
//            System.out.println(firstResult.getAttribute("textContent"));
            Thread.sleep(10000);
            String html = driver.getPageSource();
            System.out.println(html);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
