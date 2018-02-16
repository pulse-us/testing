package AutomationFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

//import static com.jayway.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
//import junit.framework.Assert;
//import junit.framework.TestCase;
import org.testng.Assert; //import org.springframework.util.Assert;
import org.testng.annotations.Test;
import com.google.gson.Gson;
//import com.jayway.restassured.path.json.JsonPath;
//import com.jayway.restassured.response.Response;

import bsh.ParseException;

public class PULSE_US_Test  extends Config {
	
	@Test
	public void Login_Success_Test_TC001() throws InterruptedException, IOException {
		WebDriver driver = Login();
		driver.quit();
	}

	@Test
	public void Login_UnSuccessFul_Test_TC002() throws InterruptedException, IOException {
		WebDriver driver = Open_Chrome();

		String credFile = "C:\\Julie\\6-PULSE\\PULSEInvalidCredentials.txt";
		FileReader FR = new FileReader(credFile);
		BufferedReader BR = new BufferedReader(FR);
		ArrayList<String> cred = new ArrayList<String>();

		for (int i = 0; BR.ready();i++) { cred.add(BR.readLine()); }

		BR.close();
		driver.findElement(By.id("username")).sendKeys(cred.get(0));
		driver.findElement(By.id("password")).sendKeys(cred.get(1));
		driver.findElement(By.id("login-button")).click();
		
		WebDriverWait wait = new WebDriverWait(driver,2);

		WebElement nextmessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("error-box")));

		if (nextmessageElement.isDisplayed()) { System.out.println("UnSuccessfully LogIn"); } else {System.out.println("Login Successful");}
		driver.quit();

	}

	@Test
	public void Search_Test_TC003() throws InterruptedException, IOException {
		WebDriver driver = Login();
	
		
		Select selAcfPfx = new Select(driver.findElement(By.id("selectAcfPrefix")));
		selAcfPfx.selectByVisibleText("Amador");
		
		Select selAcfSfx = new Select(driver.findElement(By.id("selectAcfSuffix")));
		selAcfSfx.selectByVisibleText("04 High School");
		
		Thread.sleep(2000);
		
		driver.findElement(By.id("acfSelect")).click();
		
		System.out.println("Submitted ACF");
		WebDriverWait wait = new WebDriverWait(driver,2);
		
		WebElement nextmessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("given")));
		Thread.sleep(2000);
		
		System.out.println("In Search Page");
		
		String patFile = PatientSearchFile;
		FileReader FR = new FileReader(patFile);
		BufferedReader BR = new BufferedReader(FR);
		ArrayList<String> srchCrt = new ArrayList<String>();
		
		for (int i = 0; BR.ready();i++)
		{
			srchCrt.add(BR.readLine());
		}
		
		BR.close();
		
		//System.out.println("Input Search Data " +srchCrt.get(0) +" "+srchCrt.get(1)+" "+srchCrt.get(2)
		//+" "+srchCrt.get(3)+" "+srchCrt.get(4)+" "+srchCrt.get(5)
			//	);
		
		driver.findElement(By.id("given")).sendKeys(srchCrt.get(0));
		driver.findElement(By.id("family")).sendKeys(srchCrt.get(1));
		
		Select selSrchGen = new Select(driver.findElement(By.id("gender")));
		selSrchGen.selectByVisibleText(srchCrt.get(2));
		
		Select selSrchMM = new Select(driver.findElement(By.id("dobMonth")));
		selSrchMM.selectByVisibleText(srchCrt.get(3));
					
		Select selSrchDD = new Select(driver.findElement(By.id("dobDay")));
		selSrchDD.selectByVisibleText(srchCrt.get(4));
		
		//Select selSrchYYYY = new Select(driver.findElement(By.id("dobYear")));
		//selSrchYYYY.selectByVisibleText(srchCrt.get(5));
		
		driver.findElement(By.id("dobYear")).sendKeys(srchCrt.get(5));
		
		driver.findElement(By.id("queryFormSubmit")).click();
		
		LocalDateTime submitTime = LocalDateTime.now(); 
		//try { 
			DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm:ss a"); 
			String submitTimeStr = submitTime.format(format); 
			System.out.printf("Arriving at : %s %n", submitTimeStr); 
		//	} 
		/*catch (DateTimeException ex) 
		{ 
			System.out.printf("%s can't be formatted!%n", submitTime); 
			ex.printStackTrace(); 
		}*/ 

		//driver.findElement(By.name("fade ng-scope"));
		//driver.findElement(By.className(className));
		
		Thread.sleep(5000);
		List <WebElement> col = driver.findElements(By.xpath(
		"/html/body/div/div[1]/div[2]/div[2]/ai-patient-review/div/div/div[2]/div/div/div/table/tbody/tr[1]/td"));
				
		List <WebElement> row = driver.findElements(By.xpath(
		"/html/body/div/div[1]/div[2]/div[2]/ai-patient-review/div/div/div[2]/div/div/div/table/tbody/tr/td[1]"));
				
		System.out.println("No of columns : " +col.size());
		System.out.println("No of rows : " +row.size());
		
				
		WebElement baseTable = driver.findElement(By.tagName("table"));
		
		WebElement tabFullName = baseTable.findElement(By.xpath(
		"/html/body/div/div[1]/div[2]/div[2]/ai-patient-review/div/div/div[2]/div/div/div/table/tbody/tr[1]/td[1]"
		));
		
		System.out.println("TabFullName " +tabFullName.getText());
		
		WebElement tabDOB = baseTable.findElement(By.xpath(
		"/html/body/div/div[1]/div[2]/div[2]/ai-patient-review/div/div/div[2]/div/div/div/table/tbody/tr[1]/td[2]"
		));
				
		System.out.println("TabDOB " +tabDOB.getText());
		 
		WebElement tabGender = baseTable.findElement(By.xpath(
		"/html/body/div/div[1]/div[2]/div[2]/ai-patient-review/div/div/div[2]/div/div/div/table/tbody/tr[1]/td[3]"
		));
						
		System.out.println("TabGender " +tabGender.getText());
				 
		WebElement tabLastUpdated = baseTable.findElement(By.xpath(
		"/html/body/div/div[1]/div[2]/div[2]/ai-patient-review/div/div/div[2]/div/div/div/table/tbody/tr[1]/td[4]"
		));
								
		System.out.println("TabLastUpdated " +tabLastUpdated.getText());
		
		//DateTimeFormatter DOBformat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		/*
		SimpleDateFormat DOBformat = new SimpleDateFormat("MM/dd/yyyy");
		String InputDOBStr = srchCrt.get(3)+"/"+srchCrt.get(4)+"/"+srchCrt.get(5);
		try
		{
			Date InputDOBDate = DOBformat.parse(InputDOBStr);
			InputDOBDate = DOBformat.format(InputDOBDate);
		}catch (ParseException e) {
            e.printStackTrace();
        }
		*/
		
		System.out.println("Input FullName :" + srchCrt.get(0)+" "+srchCrt.get(1));
		System.out.println("Gender :"+ srchCrt.get(2).substring(0,1));
		System.out.println("SubmittedTime :"+submitTimeStr);
		if ((tabFullName.getText()).equals(srchCrt.get(0)+" "+srchCrt.get(1))
			//&& 	tabDOB.equals(InputDOBDate)
			&& (tabGender.getText()).equals(srchCrt.get(2).substring(0,1))
			&& (tabLastUpdated.getText()).equals(submitTimeStr)
			)
		{ System.out.println("Search Successfull");
		}
		
		driver.quit();
	}

}
