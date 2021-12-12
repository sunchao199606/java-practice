package cn.com.sun.crawler;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


import java.io.File;

/**
 * @Description : Selenium测试
 * @Author : mockingbird
 * @Date : 2021/3/9 10:49
 */
public class SeleniumTest {

    @Test
    public void test() {

        ChromeOptions options = new ChromeOptions();

        //options.addArguments("--disable-blink-features=AutomationControlled");
        //options.addArguments("--headless");
        options.setBinary(new File("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe"));
        WebDriver driver = new ChromeDriver(options);
        System.out.println(driver.getWindowHandle());
        //driver.manage().window().setSize(new Dimension(400,400));
        driver.manage().window().setPosition(new Point(-1000,-1000));
        WebDriverWait wait = new WebDriverWait(driver, 20);
        try {
            driver.get("http://91porn.com/view_video.php?viewkey=c439a7b579d47111d798&page=&viewtype=&category=");

//            driver.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);
//            WebElement firstResult = wait.until(presenceOfElementLocated(By.cssSelector("h3>div")));
//            System.out.println(firstResult.getAttribute("textContent"));
            wait.until(presenceOfElementLocated(By.ByTagName.tagName("source")));
            String html = driver.getPageSource();
            System.out.println(html);
        } catch (Exception e) {
            String html = driver.getPageSource();
            System.out.println(html);
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
