package org.pulseus.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

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

    private static final String VALID_CRED_FILE = "PULSECredentials.txt";
    private static final String DEFAULT_PROPERTIES_FILE = "config.properties";
    private Properties properties;
	
	@BeforeTest
	public void Read_PropertyFile() throws InterruptedException  {
		
    try {
       InputStream in = Config.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES_FILE);
        if (in == null) {
            properties = null;
            throw new FileNotFoundException("Config Properties File not found in class path.");
        } else {
            properties = new Properties();
            properties.load(in);
            in.close();
        }
		
			FireFoxExePath = properties.getProperty("FireFoxExePath");
			ChromeExePath = properties.getProperty("ChromeExePath");
			WebsiteURL = properties.getProperty("WebsiteURL");
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
				return driver;
	}

	@Test
	public WebDriver Open_Chrome() throws InterruptedException {
				System.setProperty("webdriver.chrome.driver",ChromeExePath);
				WebDriver driver = new ChromeDriver();
				driver.get(WebsiteURL);

		        System.out.println("Successfully opened PULSE website");
				return driver;
	}
	
	@Test
	public WebDriver Login() throws InterruptedException, IOException {
				WebDriver driver = Open_Chrome();
				driver.manage().window().maximize();
				LoginPage objLoginPage;
				
		        InputStream in = PULSE_US_Test.class.getClassLoader().getResourceAsStream(VALID_CRED_FILE);
		        BufferedReader BR = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
				ArrayList<String> cred = new ArrayList<String>();
				
				while (BR.ready()) { cred.add(BR.readLine());}
				BR.close();
				in.close();
				
				System.out.println("Read Cred File");
				
				objLoginPage = new LoginPage(driver);
				
				Thread.sleep(5000);
				
				objLoginPage.loginToPulse(cred.get(0), cred.get(1));
				
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				driver.switchTo().defaultContent(); // you are now outside both frames
				driver.switchTo().frame(driver.findElement(By.id("_com_liferay_iframe_web_portlet_IFramePortlet_INSTANCE_9lPAiMRQi35X_iframe")));
				
				WebDriverWait wait = new WebDriverWait(driver,5);
				
				//WebElement nextmessageElement = wait.until(
						//ExpectedConditions.presenceOfElementLocated(By.id("selectAcfPrefix")));
					//	ExpectedConditions.presenceOfElementLocated(By.className("portlet-title-text")));
				//WebDriverWait wait = new WebDriverWait(driver,5);
				WebElement nextmessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("given")));
				Thread.sleep(5000);
				
				if (nextmessageElement.isDisplayed()) { System.out.println("Successfully LoggedIn"); } 
				else {System.out.println("Login UnSuccessful");}
				return driver;
	}
}
