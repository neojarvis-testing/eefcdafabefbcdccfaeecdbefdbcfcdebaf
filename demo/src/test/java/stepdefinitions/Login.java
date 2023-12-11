package stepdefinitions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Login {
	
	WebDriver driver;
	
	@Given("User navigates to login page")
	public void user_navigates_to_login_page() throws MalformedURLException {
		System.out.println("Hello world");
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        URL url = new URL("http://localhost:4444/");
        driver = new RemoteWebDriver(url, capabilities);
        driver.get("https://tutorialsninja.com/demo/index.php?route=account/login");
	}
	
	@When("User enters valid credentials {string} {string}")
	public void User_enters_valid_credentials(String emailText, String passwordText) {
        WebElement email = driver.findElement(By.id("input-email"));
        WebElement password = driver.findElement(By.id("input-password"));
        email.sendKeys(emailText);
        password.sendKeys(passwordText);
	}

	@And("Clicks on login button")
	public void user_clicks_on_login_button() {
		driver.findElement(By.cssSelector("input[value='Login']")).click();
	}

	@Then("User should login successfully")
	public void user_should_get_successfully_logged_in() {
		String expectedURL = "https://tutorialsninja.com/demo/index.php?route=account/account";
		String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL);
	}

	@When("User enters invalid credentials {string} {string}")
	public void User_enters_invalid_credentials(String emailText, String passwordText) {
        WebElement email = driver.findElement(By.id("input-email"));
        WebElement password = driver.findElement(By.id("input-password"));
        email.sendKeys(emailText);
        password.sendKeys(passwordText);
	}

	@Then("User should get an warning message")
	public void user_should_get_a_proper_warning_message() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	WebElement warningMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger.alert-dismissible")));
    	Assert.assertTrue(warningMessage.isDisplayed());
	}
	
	@After
    public void closeWebDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
	
}