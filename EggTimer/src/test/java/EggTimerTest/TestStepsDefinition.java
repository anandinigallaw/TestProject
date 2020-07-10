/**
 * 
 */
/**
 * @author agalla1
 *
 */
package EggTimerTest;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TestStepsDefinition {

	public WebDriver driver;

@Given("user visits eggtimer.com")
public void user_visits_e_ggtimer_com() throws InterruptedException, TimeoutException {
	System.setProperty("webdriver.chrome.driver", "C://EggTimer//Webdrivers//chromedriver.exe");
	DesiredCapabilities dc = new DesiredCapabilities();
	dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
	
	//Creating a new chrome webdriver to open the given site
	
	try{
	driver = new ChromeDriver(dc);
	driver.manage().window().maximize();
	driver.get("http://e.ggtimer.com/");
	
	//Wait until page load is complete - I'm waiting until timergo button is enabled to be clickable
	WebDriverWait check = new WebDriverWait(driver, 100);
	check.until(ExpectedConditions.elementToBeClickable(By.id("timergo")));
	}catch (Exception e) {
		System.out.println(e);
	}

}

@When("Enter number {int} seconds")
public void enter_number_seconds(int int1) {
	//By finding the text box with its name we're entering the time, 
	//As the text box is pre-populated with text we're clearing it first and then entering the input value
	try{
	driver.findElement(By.name("start_a_timer")).clear();
	driver.findElement(By.name("start_a_timer")).sendKeys(int1+" seconds");
	}catch (Exception e) {
		System.out.println(e);
	}

}

@When("click Go button")
public void click_Go_button() {
    // Click on GO button
	driver.findElement(By.id("timergo")).click();

}

@Then("validate the countdown")
public void validate_the_countdown() {
	String Expected = null;    
	try{
		WebDriverWait checktimerload = new WebDriverWait(driver, 100);
		checktimerload.until(ExpectedConditions.visibilityOfElementLocated(By.id("progressText")));

		WebElement timer = driver.findElement(By.id("progressText"));
		String[] countdownvalue = timer.getText().split(" ");
		int[] Countdowntime = new int[(countdownvalue.length / 2)];
		int i = 0, j = 0;
		for (i = 0; i < countdownvalue.length; i++) {
			if (countdownvalue[i].length() < 3) {
				Countdowntime[j] = Integer.parseInt(countdownvalue[i]);
				j++;
			}
		}
		int timelength = Countdowntime.length;
		i = Countdowntime.length - 1;
		while (Countdowntime[i] >= 0) {
			if (timelength == 1){
				if (Countdowntime[i] == 2) {
					Expected = "1 second";
					WebDriverWait wait = new WebDriverWait(driver, 3);
					wait.until(ExpectedConditions.alertIsPresent());
					driver.switchTo().alert().accept();
					driver.switchTo().defaultContent();
					Thread.sleep(4000);
					driver.close();
				} else
					Expected = Integer.toString(Countdowntime[i] - 1) + " seconds";
			} else if (timelength == 2) {
				if(Countdowntime[i] == 1){
					if(Countdowntime[0]>1)
						Expected = Integer.toString(Countdowntime[0]) + " minutes";
					else {
						Expected = "1 minute";
						timelength--;
					}
					Countdowntime[0]--;
					Countdowntime[i] = 61;
					Thread.sleep(900);
				}else if ((Countdowntime[0] > 1) && (Countdowntime[1] == 2))
					Expected = Integer.toString(Countdowntime[0]) + " minutes "+  "1 second";
				else if ((Countdowntime[0] == 1) && (Countdowntime[1] > 2))
					Expected = "1 minute "+ Integer.toString(Countdowntime[1] - 1) + " seconds";
				else if ((Countdowntime[0] == 1) && (Countdowntime[1] == 2))
					Expected = "1 minute 1 second";
				else
					Expected = Integer.toString(Countdowntime[0]) + " minutes "+ Integer.toString(Countdowntime[1] - 1) + " seconds";
			} 
			if(Expected != "1 second"){
			WebDriverWait wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("progressText")),Expected));
			System.out.println("Actual: " + driver.findElement(By.id("progressText")).getText());
			System.out.println("Expected: " + Expected);
			Assert.assertEquals(driver.findElement(By.id("progressText")).getText(), Expected);
			Countdowntime[i]--;
			}
		}
		} catch (Exception e) {
			System.out.println(e);
		}
}

@When("Enter number {int} minutes {int} seconds")
public void enter_number_minutes_seconds(int int1, int int2) {
	driver.findElement(By.name("start_a_timer")).clear();
	driver.findElement(By.name("start_a_timer")).sendKeys(int1+ " minutes "+int2+" seconds");    
}

}