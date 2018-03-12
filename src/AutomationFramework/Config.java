package AutomationFramework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Config {
	
	public String FireFoxExePath;
	public String ChromeExePath;
	public String WebsiteURL;
	public String ValidCredFilePath;
	public String InvalidCredFilePath;
	public String PatientSearchFile;
	public String AcfSelectFile;
	
	@BeforeTest
	public void Read_PropertyFile() throws InterruptedException  {
		
		File configFile = new File("config.properties");
		
		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			FireFoxExePath = props.getProperty("FireFoxExePath");
			ChromeExePath = props.getProperty("ChromeExePath");
			WebsiteURL = props.getProperty("WebsiteURL");
			ValidCredFilePath = props.getProperty("ValidCredFilePath");
			InvalidCredFilePath = props.getProperty("InvalidCredFilePath");
			PatientSearchFile = props.getProperty("PatientSearchFile");
			AcfSelectFile = props.getProperty("AcfSelectFile");
			reader.close();
			} catch (FileNotFoundException ex) {
				// file does not exist
			} catch (IOException ex) {
				// I/O error
			}		
	}


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
				LoginPage objLoginPage;
				String credFile = ValidCredFilePath;
				FileReader FR = new FileReader(credFile);
				BufferedReader BR = new BufferedReader(FR);
				ArrayList<String> cred = new ArrayList<String>();
				
				for (int i = 0; BR.ready();i++)
				{
					cred.add(BR.readLine());
				}
				
				BR.close();
				
				System.out.println("Read Cred File");
				
				objLoginPage = new LoginPage(driver);
				
				Thread.sleep(5000);
				
				objLoginPage.loginToPulse(cred.get(0), cred.get(1));
				WebDriverWait wait = new WebDriverWait(driver,2);
				
				WebElement nextmessageElement = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.id("selectAcfPrefix")));
				
				//Wait for 5 Sec
				Thread.sleep(5000);
				
				if (nextmessageElement.isDisplayed()) { System.out.println("Successfully LoggedIn"); } 
				else {System.out.println("Login UnSuccessful");}
				return driver;
	}

	

}
