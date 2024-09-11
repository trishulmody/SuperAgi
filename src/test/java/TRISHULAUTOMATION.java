import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class TRISHULAUTOMATION
{

    private static WebDriver driver;


    @Before
    public void setupTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @After
    public void teardownTest() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void trishulTest() throws IOException
    {
        driver.get("https://www.amazon.in");

        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("lg soundbar");
        searchBox.submit();

        List<WebElement> products = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
        Map<String, Integer> productPriceMap = new HashMap<>();

        for (WebElement product : products) {
            try {
                String productName = product.findElement(By.tagName("h2")).getText();

                String productPrice = "";
                try {
                    productPrice = product.findElement(By.className("a-price-whole")).getText().replace(",", "");
                } catch (Exception e) {
                    productPrice = "0";  // Set price to 0 if not present
                }

                productPriceMap.put(productName, Integer.parseInt(productPrice));
            } catch (Exception e) {
                System.out.println("e.getMessage() = " + e.getMessage());
            }
        }

        List<Map.Entry<String, Integer>> sortedProducts = new ArrayList<>(productPriceMap.entrySet());
        Collections.sort(sortedProducts, Comparator.comparing(Map.Entry::getValue));

        for (Map.Entry<String, Integer> entry : sortedProducts) {
            System.out.println(entry.getValue() + " " + entry.getKey());
            System.out.println("------------------------------------------------------------------------------");
        }
    }
}
