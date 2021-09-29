package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.openqa.selenium.By.cssSelector;

public class DebitCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        //System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestV1() {
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Иванов Иван");
        textFields.get(1).sendKeys("+70010010203");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=order-success]"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldTestWithCssSelector() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("Иванов Иван");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+79998887766");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=order-success]"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldTestWithDoubleSurname() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("Грумм-Гржимайло Иван");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+79998887766");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=order-success]"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldTestDoubleName() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("Иванова Венера- Вероника");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+79998887766");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=order-success]"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldTestNonRussianLettersName() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("Ivanov Ivan");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+79999999999");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=name] .input__sub"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldTestOnlyName() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("Иван");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+79999999999");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=name] .input__sub]"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }


    @Test
    void shouldTestUnacceptableName() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("Иванов Фффф");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+79999999999");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=name] .input__sub]"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldTestUnacceptableSurname() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("Ффффф Иван");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+79999999999");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=name] .input__sub]"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldTestMaxLetters() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("Иванов Иваааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааан");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+79999999999");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=name] .input__sub]"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Укажите точно как в паспорте";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldTestMinLetters() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("И");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+79999999999");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=name] .input__sub]"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Укажите точно как в паспорте";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldEmptyFirstField() {
        driver.findElement(cssSelector("[type=text]")).sendKeys(" ");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+79999999999");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=name] .input__sub"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldEmptySecondField() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("Иванов Иван");
        driver.findElement(cssSelector("[type=tel]")).sendKeys(" ");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=phone] .input__sub"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldTestPhoneNumber() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("Иванов Иван");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+70001112233");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=order-success]"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldTestLongPhoneNumber() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("Иванов Иван");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+79999999999999999999999999999");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=phone] .input__sub"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldTestShortPhoneNumder() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("Иван Иванов");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+7");
        driver.findElement(cssSelector(".checkbox")).click();
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=phone] .input__sub"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldTestEmptyCheckBox() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("Иван Иванов");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("+79991112233");
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector(".input_invalid"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldEmptyForm() {
        driver.findElement(cssSelector("[type=text]")).sendKeys("");
        driver.findElement(cssSelector("[type=tel]")).sendKeys("");
        driver.findElement(cssSelector("button")).click();
        WebElement messageElement = driver.findElement(cssSelector("[data-test-id=name] .input__sub"));
        String actualMessage = messageElement.getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }
}

