import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.TakesScreenshot;
import java.util.concurrent.TimeUnit;

/* Tests objective: Tests verify if user is able to order the product on selenium-shop.pl store.
author: Karolina Bargieł
 */


public class ProductOrdering {

    public static WebDriver driver;

    @BeforeTest
    public void driverConfiguration() {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

    }

    @Test (priority = 1, testName = "Verify navigation to shop")
    public void goToShoptab() {
        driver.get("http://www.selenium-shop.pl");
        System.out.println("URL strony: " + driver.getCurrentUrl());
        //verify if url is correct
        Assert.assertEquals(driver.getCurrentUrl(), "http://www.selenium-shop.pl/");
        //find and click 'SKLEP' tab
        WebElement shop = driver.findElement(By.xpath("//li[@id='menu-item-137']/a"));
        shop.click();
        System.out.println("URL strony: " + driver.getCurrentUrl());
        //verify if 'SKLEP' tab was accessed successfully
        Assert.assertEquals(driver.getCurrentUrl(), "http://www.selenium-shop.pl/sklep/");
        takeScreenshot("test1");

    }

    @Test (priority = 2, testName = "Choose Product")
    public void chooseProduct() throws InterruptedException {
        driver.get("http://www.selenium-shop.pl");
        driver.findElement(By.id("menu-item-137")).click();
        //find product 'Koszulka West Ham United'
        WebElement product = driver.findElement(By.xpath("/html/body/div[1]/section/div/ul/li[9]/a/div"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(12));
        // Actions class with moveToElement()
        Actions builder = new Actions(driver);
        builder.moveToElement(product).click().perform();
        //Verify if product detail page is correct
        Assert.assertEquals(driver.getCurrentUrl(), "http://www.selenium-shop.pl/produkt/koszulka-west-ham-united/");
        takeScreenshot("test2");

    }

    // Take screenshot method
    public void takeScreenshot(String testName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(src, new File("src/main/resources/" + testName  + " screenshot.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterTest
    public void closure() {
        driver.close();
    }


}
