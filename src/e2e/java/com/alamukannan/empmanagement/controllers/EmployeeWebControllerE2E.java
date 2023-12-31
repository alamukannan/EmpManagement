package com.alamukannan.empmanagement.controllers;

import com.alamukannan.AbstractContainerBaseTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.json.JSONException;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.mobile.NetworkConnection;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.org.awaitility.Awaitility;


import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Executes the tests against a running Spring Boot application; if you run this
 * as test from Eclipse, make sure you first manually run the Spring Boot
 * application.
 * 
 * No Spring specific testing mechanism is used here: this is a plain JUnit test.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeWebControllerE2E  extends AbstractContainerBaseTest {  // NOSONAR not a standard testcase name

	private static final Logger LOGGER =
		LoggerFactory.getLogger(EmployeeWebControllerE2E.class);


	@LocalServerPort
	private int port;

	private static String baseUrl = "http://localhost:8082";

	private WebDriver driver;


	static {
		System.setProperty("webdriver.chrome.whitelistedIps", "");
	}

	@BeforeAll
	public static void setupClass() {
		// setup Chrome Driver
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void setup() {
		baseUrl = "http://localhost:" + port;

		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	@AfterEach
	public void teardown() {
		driver.quit();
	}

	@Test
	void testDeleteEmployee() throws JSONException {


		WebElement employe = createEmploye();

		String employeId = employe.getAttribute("id");


		WebElement deleteButton = employe.findElement(By.cssSelector(".delete"));

		// Click the "delete" button to delete the last row
		deleteButton.click();

		assertThat(driver.findElements(By.cssSelector("tr[id='"+employeId+"']")).size()).isNotZero();
	}

	@Test
	void testEditEmployee() throws JSONException, InterruptedException {

		WebElement employe = createEmploye();

		String employeId = employe.getAttribute("id");

		WebElement editButton = employe.findElement(By.cssSelector(".edit"));

		// Click the "delete" button to delete the last row
		editButton.click();

		// Now you are in the edit mode, update the row data
		WebElement firstNameInput = driver.findElement(By.id("firstName"));
		WebElement lastNameInput = driver.findElement(By.id("lastName"));
		WebElement emailInput = driver.findElement(By.id("email"));


		firstNameInput.clear();
		firstNameInput.sendKeys("New First Name");
		lastNameInput.clear();
		lastNameInput.sendKeys("New Last Name");
		emailInput.clear();
		emailInput.sendKeys("new.email@example.com");

		// press Save
		driver.findElement(By.id("save-employe")).click();


		Awaitility.await().atMost(Duration.ofSeconds(2));  // Compliant
		WebElement actual = driver.findElement(By.cssSelector("tr[id='" + employeId + "']"));

		assertThat(
				actual.getText()).contains("New First Name","New Last Name", "new.email@example.com");

	}

	@Test
	void testCreateNewEmployee() {
		WebElement employe = createEmploye();

		assertThat(employe.getText()).
				contains("firstname","lastname", "employe@gmail.com");
	}


	@Test
	void testAllEmployees() throws JSONException {

		driver.get(baseUrl);


		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("empTable")));
		List<WebElement> rows = table.findElements(By.tagName("tr"));

		assertThat(rows.size()).isNotZero();

	}

	private WebElement createEmploye() {
		driver.get(baseUrl);

		// add a user using the "New employee" link
		driver.findElement(By.id("add-new")).click();


		// fill the form
		driver.findElement(By.id("firstName")).sendKeys("firstname");
		driver.findElement(By.id("lastName")).sendKeys("lastname");

		driver.findElement(By.id("email")).sendKeys("employe@gmail.com");
		// press Save
		driver.findElement(By.id("save-employe")).click();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("empTable")));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		return 	rows.get(rows.size() - 1);

	}


}