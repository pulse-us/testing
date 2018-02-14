package AutomationFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import java.util.Date;
//import junit.framework.Assert;
//import junit.framework.TestCase;
import org.testng.Assert; //import org.springframework.util.Assert;
import org.testng.annotations.Test;
import com.google.gson.Gson;
//import com.jayway.restassured.path.json.JsonPath;
//import com.jayway.restassured.response.Response;

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

		driver.findElement(By.id("login_submit")).click();

		WebDriverWait wait = new WebDriverWait(driver,2);

		WebElement nextmessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("alertbox")));

		if (nextmessageElement.isDisplayed()) { System.out.println("UnSuccessfully LogIn"); } else {System.out.println("Login Successful");}
		driver.quit();

	}

	@Test
	public void Search_Test_TC003() throws InterruptedException, IOException {
		WebDriver driver = Login();
		driver.quit();
	}

}
