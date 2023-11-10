package com.alamukannan.empmanagement.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


class EmployeeE2EIT {



	    private static int port =
	            Integer.parseInt(System.getProperty("server.port", "8082"));

	    private static String baseUrl = "http://localhost:" + port;

	    private WebDriver driver;


	    static {
	        System.setProperty("webdriver.chrome.whitelistedIps", "");
	    }

	    @BeforeClass
	    static void setupClass() {
	        // setup Chrome Driver
	        WebDriverManager.chromedriver().setup();
	    }

	    @Before
	    public void setup() {
	        baseUrl = "http://localhost:" + port;

	        driver = new ChromeDriver();
	    }

	    @After
	    public void teardown() {
	        driver.quit();
	    }

	    @Test
	    public void testDeleteEmployee() throws JSONException {


	        WebElement employe = createEmploye();

	        String employeId = employe.getAttribute("id");


	        WebElement deleteButton = employe.findElement(By.cssSelector(".delete"));

	        // Click the "delete" button to delete the last row
	        deleteButton.click();

	        assertThat(driver.findElements(By.cssSelector("tr[id='"+employeId+"']")).size() != 0
	        );
	    }

	    @Test
	    public void testEditEmployee() throws JSONException {

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


	        WebElement actual = driver.findElement(By.cssSelector("tr[id='" + employeId + "']"));

	        assertThat(
	                actual.getText()).contains("New First Name","New Last Name", "new.email@example.com");

	    }

	    @Test
	    public void testCreateNewEmployee() {
	        WebElement employe = createEmploye();

	        assertThat(employe.getText()).
	                contains("firstname","lastname", "employe@gmail.com");
	    }


	    @Test
	    public void testAllEmployees() throws JSONException {

	        driver.get(baseUrl);


	        WebDriverWait wait = new WebDriverWait(driver, 30);
	        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("empTable")));
	        List<WebElement> rows = table.findElements(By.tagName("tr"));

	        assertThat(rows.size()!=0);

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
