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
import java.util.concurrent.TimeUnit;

public class ProductOrdering {

    public static WebDriver driver;

    @BeforeTest
    public void driverConfiguration() {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://www.selenium-shop.pl");
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

    }

    @Test (priority = 1)
    public void goToShoptab() {
        System.out.println("URL strony: " + driver.getCurrentUrl());
        Assert.assertEquals(driver.getCurrentUrl(), "http://www.selenium-shop.pl/");
        WebElement shop = driver.findElement(By.xpath("//li[@id='menu-item-137']/a"));
        shop.click();
        System.out.println("URL strony: " + driver.getCurrentUrl());
        Assert.assertEquals(driver.getCurrentUrl(), "http://www.selenium-shop.pl/sklep/");

    }

    @Test (priority = 2)
    public void chooseProduct() throws InterruptedException {
        driver.get("http://www.selenium-shop.pl/sklep/");
        WebElement product = driver.findElement(By.xpath("/html/body/div[1]/section/div/ul/li[9]/a/div"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(12));
        // Actions class with moveToElement()
        Actions builder = new Actions(driver);
        builder.moveToElement(product).click().perform();

    }

    @AfterMethod
    public void screenshot() {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(src, new File("src/main/resources/" + System.currentTimeMillis() + " screenshot.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterClass
    public void closure() {
        driver.close();
    }


}
