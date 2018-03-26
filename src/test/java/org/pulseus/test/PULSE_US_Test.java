package org.pulseus.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.google.common.base.CharMatcher;

public class PULSE_US_Test  extends Config {
    
    private static WebDriver driver;
    private static final String ACF_SELECT_FILE = "ACFPrefixSuffix.txt";
    private static final String INVALID_CRED_FILE = "PULSEInvalidCredentials.txt";
    private static final String SEARCH_INPUT_FILE = "SearchInput.txt";
    
    /**
     * Close browser windows and terminate WebDriver session.
     */
    @AfterMethod
    public void AfterMethod() {
        driver.quit();
        System.out.println("InAfterMethod");
    }

    @Test
	public void Login_Success_Test_TC001() throws InterruptedException, IOException {
		driver = Login();
	}

	@Test
	public void Login_UnSuccessFul_Test_TC002() throws InterruptedException, IOException {
		driver = Open_Chrome();
		LoginPage objLoginPage;

		InputStream in = PULSE_US_Test.class.getClassLoader().getResourceAsStream(INVALID_CRED_FILE);
		BufferedReader BR = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
		ArrayList<String> cred = new ArrayList<String>();

		while(BR.ready()) { cred.add(BR.readLine()); }

		BR.close();
		in.close();
		
		objLoginPage = new LoginPage(driver);
		Thread.sleep(5000);
		objLoginPage.loginToPulse(cred.get(0), cred.get(1));
		
		WebDriverWait wait = new WebDriverWait(driver,2);

		WebElement nextmessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("error-box")));

		if (nextmessageElement.isDisplayed()) { System.out.println("UnSuccessfully LogIn"); } else {System.out.println("Login Successful");}
	}

	@Test
	public void Search_Test_TC003() throws InterruptedException, IOException {
		driver = Login();
		
		SearchPage objSearchPage;
		objSearchPage = new SearchPage(driver);

        InputStream in = PULSE_US_Test.class.getClassLoader().getResourceAsStream(ACF_SELECT_FILE);
        BufferedReader ABR = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
		ArrayList<String> acfCrt = new ArrayList<String>();
		
		while(ABR.ready()) 	{ 	acfCrt.add(ABR.readLine());  }
		
		ABR.close();
		in.close();
		
		objSearchPage.clickAcfSelect(acfCrt.get(0),acfCrt.get(1));
		
		System.out.println("Submitted ACF");
		
		WebDriverWait wait = new WebDriverWait(driver,2);
		WebElement nextmessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("given")));
		//Thread.sleep(2000);
		
		System.out.println("In Search Page");
		
		in = PULSE_US_Test.class.getClassLoader().getResourceAsStream(SEARCH_INPUT_FILE);
		BufferedReader BR = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
		ArrayList<String> srchCrt = new ArrayList<String>();
		
		while (BR.ready() ) 		{ 	srchCrt.add(BR.readLine()); }
		
		BR.close();
		in.close();
		
		objSearchPage.clickSearchSubmit(
										srchCrt.get(0),srchCrt.get(1),srchCrt.get(2),
										srchCrt.get(3),srchCrt.get(4),srchCrt.get(5),
										srchCrt.get(6),srchCrt.get(7),srchCrt.get(8),
										srchCrt.get(9),srchCrt.get(10)
										);
		
		LocalDateTime submitTime = LocalDateTime.now(); 
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm:ss a"); 
		String submitTimeStr = submitTime.format(format); 
		System.out.printf("Data Submission Time : %s %n", submitTimeStr); 
		
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
		
		System.out.println("Input FullName :" + srchCrt.get(0)+" "+srchCrt.get(1));
		System.out.println("Gender :"+ srchCrt.get(2).substring(0,1));
		System.out.println("SubmittedTime :"+submitTimeStr);
		if ((tabFullName.getText()).equals(srchCrt.get(0)+" "+srchCrt.get(1))
			&& (tabGender.getText()).equals(srchCrt.get(2).substring(0,1))
			)
		{ System.out.println("Search Successfull");
		}
		
		WebElement tabRecFound = baseTable.findElement(By.xpath(
				"/html/body/div/div[1]/div[2]/div[2]/ai-patient-review/div/div/div[2]/div/div/div/table/tbody/tr[1]/td[5]"
				));;
		while (tabRecFound.getText().contains("pending") )
		{
			//System.out.println("TabRecFound " +tabRecFound.getText());							
		
		}		
		
		//System.out.println("TabRecFound Cnt " +tabRecFound.getText());
		
		@SuppressWarnings("deprecation")
		int RecCnt = Integer.parseInt(CharMatcher.DIGIT.retainFrom(tabRecFound.getText()));
		
		System.out.println("TabRecFound Cnt " +RecCnt);
		
		if (RecCnt > 0 )
		{
			WebElement stg_button = driver.findElement(By.xpath("//*[@class='btn btn-success btn-sm ng-scope']"));
			
			//System.out.println("Stg Button Text " +stg_button.getText());
			
			stg_button.click();
			
			nextmessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='modal ng-isolate-scope in']")));
			
			
			//WebElement PatRecName = driver.findElement(By.xpath("//*[@class='ng-binding']"));
			WebElement PatRecTab = driver.findElement(By.xpath("//*[@class='table table-condensed']"));
			
			WebElement popPatName = PatRecTab.findElement(By.xpath(
					"/html/body/div[3]/div/div/div/div[2]/div[2]/table/tbody/tr/td[2]"
								));
			
			System.out.println("Pat Rec Name " + popPatName.getText());
			
			if(popPatName.getText().equalsIgnoreCase(srchCrt.get(0) + " " +srchCrt.get(1)))
			{
				System.out.println("Staging Pat Found");
				
				///Start from here
				WebElement popPatSelect = PatRecTab.findElement(
						By.xpath("//*[@class='ng-pristine ng-untouched ng-valid ng-empty']"));
						
						
						//"//*[@id=\"recordSelect-104\"]"));
				//By.xpath("//*[@class='fa fa-save']"));
				popPatSelect.click(); 
				
				Thread.sleep(2000);
				
				WebElement popPatSave = driver.findElement(By.xpath("//*[@class='btn btn-success pull-right']"));
				
				
				System.out.println("Save Button " +popPatSave.getText());
				
				popPatSave.click();
				
				Thread.sleep(2000);
				
				
				WebElement r_b = driver.findElement(By.xpath("//*[@class='btn btn-lg btn-block btn-primary']"));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", r_b);
				
				//reviewTab.click();
				
				nextmessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//*[@class='btn btn-sm panel-button']")));
				System.out.println("In Review Page");
				
				
				
				
				/*WebElement downButton = wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//*[@class='btn btn-sm panel-button']")));
				downButton.click();
				*/
				
				/*WebElement ReviewRecords = driver.findElement(By.xpath("//*[@class='panel panel-primary']"));
				String ReviewText = ReviewRecords.getText();
				System.out.println("Review Text " + ReviewText);
				while(!ReviewText.equals(srchCrt.get(0) + " " +srchCrt.get(1)))
				{
				ReviewText = ReviewRecords.getText();
				System.out.println("Staged Patient Found");
				}*/
				Thread.sleep(50000);
				wait = new WebDriverWait(driver,10);
				WebElement stagedPatRecord = wait.until(ExpectedConditions.presenceOfElementLocated(
				//By.xpath("//*[@class='btn btn-success']")));
						//By.xpath("/html/body/div/div[1]/div[2]/div/ai-acf-patient-list/div/div/div[2]/div/div/div/table/tbody/tr[1]/td[2]")));
				//By.xpath("//*[@class='ng-binding']")));
				//		By.xpath("/html/body/div/div[1]/div[2]/div/ai-acf-patient-list/div/div/div[2]/div/div/h3")));
				//		By.xpath("//h3[@class='ng-binding']")));
				 //By.xpath("/html/body/div/div[1]/div[2]/div/ai-acf-patient-list/div/div/div[2]/div/div/h3")));
				//By.xpath("/html/body/div/div[1]/div[2]/div/ai-acf-patient-list/div/div/div[2]/div/div/h3")));
						By.xpath("/html/body/div/div[1]/div[2]/div/ai-acf-patient-list/div/div/div[2]/div/div/h3/div/button[1]/span")));
				
				String stagedPatname = stagedPatRecord.getText();
				System.out.println("stagedPatname " + stagedPatname.substring(9,24));
				//if(stagedPatname.equals(srchCrt.get(0) + " " +srchCrt.get(1)))
				if(stagedPatname.substring(9,24).equals(srchCrt.get(0) + " " +srchCrt.get(1)))
				//System.out.println("GooButton Text " + gooButton.getText());
				//if (gooButton.getText().equals(srchCrt.get(0) + " " +srchCrt.get(1)))
						{
					System.out.println("Staged Patient Found");
						}
						
				
			}
			
			/*
			WebElement review_button = driver.findElement(By.xpath("//*[@class='col-md-2 col-sm-3']"));
			
			WebElement r_b = driver.findElement(By.xpath("//*[@class='btn btn-lg btn-block btn-primary']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", r_b);
			
			nextmessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='ng-isolate-scope']")));
			
			Thread.sleep(2000);
			
			System.out.println("Clicked Review Button");
			*/
		}
	}

	@Test
	public void Review_Test_TC004() throws InterruptedException, IOException {
		driver = Login();
		
		SearchPage objSearchPage;
		objSearchPage = new SearchPage(driver);
		
        InputStream in = PULSE_US_Test.class.getClassLoader().getResourceAsStream(ACF_SELECT_FILE);
        BufferedReader ABR = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
		ArrayList<String> acfCrt = new ArrayList<String>();
		
		while ( ABR.ready())
		{
			acfCrt.add(ABR.readLine());
		}
		
		ABR.close();
		in.close();
		
		objSearchPage.clickAcfSelect(acfCrt.get(0),acfCrt.get(1));
		
		System.out.println("Submitted ACF");
		WebDriverWait wait = new WebDriverWait(driver,4);
		
		WebElement review_button = driver.findElement(By.xpath("//*[@class='col-md-2 col-sm-3']"));
		
		WebElement r_b = driver.findElement(By.xpath("//*[@class='btn btn-lg btn-block btn-primary']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", r_b);
	}	
}
