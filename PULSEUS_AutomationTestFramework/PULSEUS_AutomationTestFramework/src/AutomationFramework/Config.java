package AutomationFramework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Config {
	public String FireFoxExePath = "C:\\Downloaded Things\\geckodriver.exe";
	public String ChromeExePath = "C:\\Downloaded Things\\chromedriver.exe";
	public String WebsiteURL = "https://california.demo.collaborativefusion.com/";
	public String ValidCredFilePath = "C:\\Julie\\6-PULSE\\PULSECredentials.txt";
	
	@Test
	public WebDriver Open_FireFox() throws InterruptedException {
				System.setProperty("webdriver.gecko.driver", FireFoxExePath);
				WebDriver driver = new FirefoxDriver();
				driver.get(WebsiteURL);

				System.out.println("Successfully opened PULSE website");
		        //driver.quit();
				return driver;
	}

	@Test
	public WebDriver Open_Chrome() throws InterruptedException {
				System.setProperty("webdriver.chrome.driver",ChromeExePath);
				WebDriver driver = new ChromeDriver();
				driver.get(WebsiteURL);

		        System.out.println("Successfully opened PULSE website");
		        //driver.quit();
				return driver;
	}
	
	@Test
	public WebDriver Login() throws InterruptedException, IOException {
				WebDriver driver = Open_Chrome();
				
				String credFile = ValidCredFilePath;
				FileReader FR = new FileReader(credFile);
				BufferedReader BR = new BufferedReader(FR);
				ArrayList<String> cred = new ArrayList<String>();
				
				for (int i = 0; BR.ready();i++)
				{
					cred.add(BR.readLine());
				}
				
				BR.close();
				driver.findElement(By.id("username")).sendKeys(cred.get(0));
		        driver.findElement(By.id("password")).sendKeys(cred.get(1));
		        driver.findElement(By.id("login_submit")).click();
		        
				WebDriverWait wait = new WebDriverWait(driver,2);
				
				WebElement nextmessageElement = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.id("welcome_name")));
				
				//Wait for 5 Sec
				Thread.sleep(5000);
				
				if (nextmessageElement.isDisplayed()) { System.out.println("Successfully LoggedIn"); } else {System.out.println("Login UnSuccessful");}
				return driver;
	}

	

}
