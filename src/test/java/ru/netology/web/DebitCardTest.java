package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class DebitCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
        @Test
        void shouldTestV1 () {
            driver.get("http://localhost:9999");
            List<WebElement> textFields = driver.findElements(By.className("input__control"));
            textFields.get(0).sendKeys("Иванов Иван");
            textFields.get(1).sendKeys("+70010010203");
            driver.findElement(By.className("checkbox__box")).click();
            driver.findElement(By.className("button")).click();
            WebElement messageElement = driver.findElement(By.className("paragraph"));
            String actualMessage = messageElement.getText();
            String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
            Assertions.assertEquals(expectedMessage, actualMessage);
        }
}

