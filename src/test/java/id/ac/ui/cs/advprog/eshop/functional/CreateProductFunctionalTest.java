package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_fromHomePage_succeed(ChromeDriver driver) throws Exception {
        // Go to home page
        driver.get(baseUrl);

        // Click "Create Product" button
        WebElement createProductButton = driver.findElement(By.cssSelector("a[href='/product/create']"));
        createProductButton.click();

        // Wait for create product page to load
        Thread.sleep(500);

        // Verify we're on create product page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/create"));

        // Fill in product name
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys("Functional Test Product");

        // Fill in product quantity
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys("99");

        // Submit form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Wait for redirect to product list
        Thread.sleep(1000);

        // Verify we're redirected to product list
        String listUrl = driver.getCurrentUrl();
        assertTrue(listUrl.contains("/product/list"));

        // Verify the product appears in the list
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Functional Test Product"));
        assertTrue(pageSource.contains("99"));
    }

    @Test
    void createProductButton_isVisible_onHomePage(ChromeDriver driver) throws Exception {
        // Go to home page
        driver.get(baseUrl);

        // Find create product button
        WebElement createProductButton = driver.findElement(By.cssSelector("a[href='/product/create']"));

        // Verify button is displayed
        assertTrue(createProductButton.isDisplayed());
        assertEquals("Create Product", createProductButton.getText());
    }

    @Test
    void createProductPage_hasCorrectFormElements(ChromeDriver driver) throws Exception {
        // Go directly to create product page
        driver.get(baseUrl + "/product/create");

        // Verify page title
        String pageTitle = driver.getTitle();
        assertEquals("Create New Product", pageTitle);

        // Verify form elements exist
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        assertNotNull(nameInput);
        assertNotNull(quantityInput);
        assertNotNull(submitButton);
        assertEquals("Submit", submitButton.getText());
    }
}